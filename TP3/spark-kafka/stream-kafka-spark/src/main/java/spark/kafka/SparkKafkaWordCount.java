package spark.kafka;

import static org.apache.spark.sql.functions.*;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class SparkKafkaWordCount {
  public static void main(String[] args) throws Exception {
    if (args.length != 3) {
      System.err.println("Usage: SparkKafkaWordCount <bootstrapServers> <topic> <groupId>");
      System.exit(1);
    }

    String bootstrap = args[0];
    String topic = args[1];
    String groupId = args[2];

    SparkSession spark = SparkSession.builder()
        .appName("SparkKafkaWordCount")
        .getOrCreate();

    Dataset<Row> kafkaDf = spark.readStream()
        .format("kafka")
        .option("kafka.bootstrap.servers", bootstrap)
        .option("subscribe", topic)
        .option("startingOffsets", "earliest")
        .option("kafka.group.id", groupId)
        .load();

    Dataset<Row> lines = kafkaDf.selectExpr("CAST(value AS STRING) AS value");

    Dataset<Row> words = lines
        .select(explode(split(col("value"), "\\s+")).alias("word"))
        .filter(length(col("word")).gt(0));

    Dataset<Row> counts = words.groupBy("word").count();

    counts.writeStream()
        .outputMode("complete")
        .format("console")
        .option("truncate", "false")
        // checkpoint local OK en --master local; si tu passes sur yarn → mettre HDFS
        .option("checkpointLocation", "hdfs://hadoop-master:9000/tmp/spark-kafka-wc-checkpoint-" + topic)
        .start()
        .awaitTermination();
  }
}
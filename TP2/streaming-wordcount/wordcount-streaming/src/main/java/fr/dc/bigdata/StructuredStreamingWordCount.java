package fr.dc.bigdata;

import static org.apache.spark.sql.functions.*;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class StructuredStreamingWordCount {
  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("Usage: StructuredStreamingWordCount <host> <port>");
      System.exit(1);
    }

    String host = args[0];
    int port = Integer.parseInt(args[1]);

    SparkSession spark = SparkSession.builder()
        .appName("StructuredStreamingWordCount")
        .getOrCreate();

    // Stream depuis un socket
    Dataset<Row> lines = spark.readStream()
        .format("socket")
        .option("host", host)
        .option("port", port)
        .load();

    // lines a une colonne "value"
    Dataset<Row> words = lines
        .select(explode(split(col("value"), "\\s+")).alias("word"))
        .filter(length(col("word")).gt(0));

    Dataset<Row> counts = words.groupBy("word").count();

    // Affiche en console
    counts.writeStream()
        .outputMode("complete")
        .format("console")
        .option("truncate", "false")
        .option("checkpointLocation", "hdfs://hadoop-master:9000/tmp/ss-wordcount-checkpoint")
        .start()
        .awaitTermination();
  }
}

package fr.dc.bigdata;

import java.util.Arrays;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;

public class WordCountTask {
  public static void main(String[] args) {
    if (args.length != 2) {
      System.err.println("Usage: WordCountTask <input> <output>");
      System.exit(1);
    }

    String input = args[0];
    String output = args[1];

    SparkConf conf = new SparkConf().setAppName("WordCountTask");
    JavaSparkContext sc = new JavaSparkContext(conf);

    JavaRDD<String> lines = sc.textFile(input);

    JavaRDD<String> words =
        lines.flatMap(line -> Arrays.asList(line.split("\\s+")).iterator())
             .filter(w -> !w.isEmpty());

    JavaPairRDD<String, Integer> wc =
        words.mapToPair(w -> new Tuple2<>(w, 1))
             .reduceByKey(Integer::sum);

    wc.saveAsTextFile(output);
    sc.close();
  }
}

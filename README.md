# Big Data - TP1 / TP2 / TP3 (WordCount)

Ce depot regroupe plusieurs exercices de traitement de donnees :

## TP1 (Hadoop MapReduce)

Projet Maven : `TP1/wordcount`

- `WordCount <input path> <output path>`
- `SalesByStore <input path> <output path>` (aggrege un montant par magasin)

Le jar (avec dependances) est genere par :

```bash
cd "TP1/wordcount"
mvn package
```

Lancer (Hadoop) :

```bash
hadoop jar target/wordcount-1.0-SNAPSHOT-jar-with-dependencies.jar hadoop.mapreduce.tp1.WordCount <input> <output>
hadoop jar target/wordcount-1.0-SNAPSHOT-jar-with-dependencies.jar hadoop.mapreduce.tp1.SalesByStore <input> <output>
```

Note `SalesByStore` : le mapper lit chaque ligne en separant par des espaces (au moins 6 tokens). Le nom du magasin est le token `tokens[2]` et le cout correspond a `tokens[tokens.length - 2]`.

## TP2 (Spark)

Projet Maven batch : `TP2/batch-wordcount/wordcount-batch`

```bash
cd "TP2/batch-wordcount/wordcount-batch"
mvn package
```

Lancer :

```bash
spark-submit --class fr.dc.bigdata.WordCountTask target/wordcount-batch-1.0-SNAPSHOT.jar <input> <output>
```

Projet Maven streaming (Structured Streaming) : `TP2/streaming-wordcount/wordcount-streaming`

```bash
cd "TP2/streaming-wordcount/wordcount-streaming"
mvn package
```

Lancer :

```bash
spark-submit --class fr.dc.bigdata.StructuredStreamingWordCount target/wordcount-streaming-1.0-SNAPSHOT.jar <host> <port>
```

Note : le `checkpointLocation` est configure par defaut sur `hdfs://hadoop-master:9000/tmp/ss-wordcount-checkpoint` (a adapter si besoin).

## TP3 (Spark Structured Streaming + Kafka)

Projet Maven : `TP3/spark-kafka/stream-kafka-spark`

```bash
cd "TP3/spark-kafka/stream-kafka-spark"
mvn package
```

Lancer :

```bash
spark-submit --class spark.kafka.SparkKafkaWordCount target/stream-kafka-spark-1-jar-with-dependencies.jar <bootstrapServers> <topic> <groupId>
```

Note : le `checkpointLocation` est configure avec `hdfs://hadoop-master:9000/tmp/spark-kafka-wc-checkpoint-<topic>` (a adapter selon ton HDFS).

## Remarques generales

- Pour les traitements streaming, la commande reste active (Ctrl+C pour arrêter).
- Les chemins de sortie doivent généralement ne pas exister au moment du lancement.

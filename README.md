# Big Data - Travaux Pratiques (M2)

Ce depot regroupe l'ensemble des TPs du module Big Data, organises par seance et par technologie.

## Structure du depot

- `TP1/wordcount` : Hadoop MapReduce (batch)
- `TP2/batch-wordcount/wordcount-batch` : Spark batch
- `TP2/streaming-wordcount/wordcount-streaming` : Spark Structured Streaming
- `TP3/spark-kafka/stream-kafka-spark` : Spark Structured Streaming + Kafka
- `TP4/hello-hbase` et `TP4/hbase-spark` : prise en main HBase + integration Spark/HBase
- `TP5/ecommerce-pipeline` : pipeline Big Data final de bout en bout

## Vue d'ensemble des TPs

### TP1 - Hadoop MapReduce

Objectif : implementer des traitements batch classiques avec MapReduce.

Exemples :

- `WordCount`
- `SalesByStore` (agregation du montant par magasin)

### TP2 - Spark (batch et streaming)

Objectif : decouvrir Spark sur les deux modes d'execution.

- Batch : `WordCountTask`
- Streaming : `StructuredStreamingWordCount`

### TP3 - Spark Streaming + Kafka

Objectif : connecter un flux Kafka a un traitement Spark Structured Streaming.

Le TP introduit la consommation de topics Kafka, la gestion des offsets et l'usage du checkpointing.

### TP4 - HBase et integration avec Spark

Objectif : manipuler HBase puis l'integrer dans un traitement Spark.

Ce TP sert de transition vers la construction d'un pipeline complet avec stockage NoSQL.

### TP5 - TP final : pipeline Big Data complet

Ce TP est le point d'orgue des quatre seances precedentes. L'objectif est de construire un pipeline Big Data complet, de l'ingestion des donnees jusqu'a la consultation des resultats, en mobilisant l'ensemble des technologies vues en cours.

Projet principal :

- `TP5/ecommerce-pipeline`

Composants mobilises :

- ingestion via Kafka
- traitement temps reel avec Spark Structured Streaming
- stockage/agregation dans HBase
- archivage des donnees brutes dans HDFS

Pour les details d'execution, captures et choix techniques, voir le README du TP5 :

- `TP5/ecommerce-pipeline/README.md`

## Commandes utiles (rappel)

Compilation Maven (exemple) :

```bash
cd "TPX/<projet>"
mvn package
```

Execution (exemple Spark) :

```bash
spark-submit --class <ClassePrincipale> target/<jar>.jar <args>
```

## Remarques generales

- Pour les traitements streaming, la commande reste active (Ctrl+C pour arreter).
- Les chemins de sortie Hadoop/HDFS doivent generalement ne pas exister avant lancement.
- Adapter les `checkpointLocation` et endpoints (`hdfs://`, Kafka bootstrap servers, etc.) a ton environnement.

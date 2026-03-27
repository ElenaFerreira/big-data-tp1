# TP4 — HBase, Spark et ImportTsv

## Contenu du TP

Ce dossier contient les différentes parties réalisées pour le TP4 :

- `hello-hbase` : utilisation de l’API Java HBase
- `hbase-spark` : lecture d’une table HBase avec Spark
- `importtsv-test` : activité de test de l’outil `ImportTsv`

## 1. Manipulation HBase Shell

Une table `sales_ledger` a été créée avec les familles de colonnes :

- `customer`
- `sales`

Des lignes ont ensuite été ajoutées manuellement avec les commandes `put`, puis vérifiées avec :

- `scan 'sales_ledger'`
- `get 'sales_ledger', '102', {COLUMN => 'sales:product'}`

Le résultat final contient 4 enregistrements (`101` à `104`).

## 2. API Java HBase

Un projet Maven `hello-hbase` a été créé afin de manipuler HBase en Java.

Le programme `HelloHBase.java` permet de :

- créer la table `user`
- ajouter des données dans les familles `PersonalData` et `ProfessionalData`
- relire la valeur `name` pour l’utilisateur `user1`

Résultat obtenu à l’exécution :

```text
Connecting
Creating Table
Done......
Adding user: user1
Adding user: user2
Reading data...
ahmed
```

## 3. Lecture HBase avec Spark

Un second projet Maven hbase-spark a été créé avec une classe HbaseSparkProcess.java.

Ce programme lit la table sales_ledger à l’aide de TableInputFormat et compte le nombre d’enregistrements via Spark.

Après insertion complète des données et exécution d’un flush 'sales_ledger', le résultat obtenu est :

```text
nombre d'enregistrements: 4
```

## 4. Activité — ImportTsv

### Objectif

Tester l’utilitaire `ImportTsv` de HBase afin d’importer un fichier TSV dans une table HBase.

Étapes réalisées

1. Création d’un fichier sales_ledger.tsv contenant 3 lignes.
2. Création de la table HBase sales_ledger_tsv avec les familles :
   - customer
   - sales

3. Copie du fichier TSV dans HDFS :
   - `hdfs dfs -mkdir -p /user/root`
   - `hdfs dfs -put -f sales_ledger.tsv /user/root/`
4. Exécution de l’import avec ImportTsv :

```bash
${HBASE_HOME}/bin/hbase org.apache.hadoop.hbase.mapreduce.ImportTsv \
-Dmapreduce.framework.name=local \
-Dimporttsv.separator=$'\t' \
-Dimporttsv.columns=HBASE_ROW_KEY,customer:name,customer:city,sales:product,sales:amount \
sales_ledger_tsv /user/root/sales_ledger.tsv
```

5. Vérification dans HBase avec :

```bash
scan 'sales_ledger_tsv'
count 'sales_ledger_tsv'
```

### Résultat obtenu

L’import a fonctionné correctement :

- scan 'sales_ledger_tsv' affiche 3 lignes
- count 'sales_ledger_tsv' retourne 3

### Remarque

Dans l’environnement Docker utilisé pour le TP, l’exécution via YARN échouait lors du lancement du job `ImportTsv`.
Le test a donc été validé en mode local avec l’option :

```bash
-Dmapreduce.framework.name=local
```

## Conclusion

Le TP4 a permis de :

- manipuler HBase en shell
- utiliser HBase en Java avec Maven
- interroger HBase avec Spark
- tester l’import de données tabulaires avec ImportTsv

L’ensemble des manipulations a été validé dans l’environnement Docker Hadoop/HBase fourni.

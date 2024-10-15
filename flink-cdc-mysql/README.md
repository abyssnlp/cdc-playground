Flink CDC MySQL
===

- Flink catalogs: https://www.decodable.co/blog/catalogs-in-flink-sql-a-primer
- Flink catalogs deep dive: https://www.decodable.co/blog/catalogs-in-flink-sql-hands-on
- Flink has 3 types of catalogs:
    - in-memory (default)
    - Hive metastore (backed by an RDBMS)
    - JDBC (doesn't support storing new objects)
- only in-memory catalogs ships with Flink (hive needs additional dependencies)
- Supports AWS Glue catalog
- Iceberg persistence options (DynamoDB, REST, JDBC)
- Metadata about catalogs is stored in the `FileCatalogStore` persisted to disk
- Delta flink cdc doesn't yet support update and delete changes

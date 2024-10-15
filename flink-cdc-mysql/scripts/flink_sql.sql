set execution.checkpointing.interval = 3s;

-- Create mysql cdc table on flink
create table user_source (
    database_name string metadata virtual,
    table_name string metadata virtual,
    `id` decimal(20, 0) not null,
    name string,
    address string,
    phone_number string,
    email string,
    primary key (`id`) not enforced
) with (
    'connector' = 'mysql-cdc',
    'hostname' = 'mysql',
    'port' = '3306',
    'username' = 'root',
    'password' = '123456',
    'database-name' = 'db_[0-9]+',
    'table-name' = 'user_[0-9]+'
);

-- create delta sink table
create table all_users_sink (
    database_name string,
    table_name string,
    `id` decimal(20, 0) not null,
    name string,
    address string,
    phone_number string,
    email string,
    primary key (database_name, table_name, `id`) not enforced
) with (
    'connector' = 'delta',
    'catalog-type' = 'in-memory',
    'table-path' = ''
)
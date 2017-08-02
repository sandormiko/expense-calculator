#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
  
    CREATE DATABASE expenses-db;
   \c expenses-db;
   create table if not exists expenses(

    id serial primary key ,
    amount numeric(10,2) not null,
    vat numeric(10,2) not null,
    creation_date date not null,
    reason varchar(500) not null

);
EOSQL
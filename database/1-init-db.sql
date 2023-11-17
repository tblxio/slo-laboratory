-- Create the database holding the Fleet Management System data
CREATE DATABASE fms;

-- Create Flyway user
CREATE USER flyway WITH ENCRYPTED PASSWORD 'Flyw4y!';
GRANT CONNECT, CREATE ON DATABASE fms TO flyway;

-- Create REST API user
CREATE USER restapi WITH ENCRYPTED PASSWORD 'R3stApi*';
GRANT CONNECT ON DATABASE fms to restapi;

-- Switch to the database
\c fms;

-- Create a faker schema, and install the extension in it
CREATE SCHEMA faker;
GRANT USAGE ON SCHEMA faker TO flyway;
CREATE EXTENSION IF NOT EXISTS faker SCHEMA faker CASCADE;

-- Grant access to the public schema to flyway
GRANT USAGE, CREATE ON SCHEMA public TO flyway;

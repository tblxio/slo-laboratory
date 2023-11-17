CREATE SCHEMA IF NOT EXISTS fms;
GRANT ALL PRIVILEGES ON SCHEMA fms TO restapi;

CREATE TABLE fms.operators (
    operator_id SERIAL PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    contact_name VARCHAR(255),
    contact_email VARCHAR(255) UNIQUE,
    phone_number VARCHAR(50),
    address TEXT
);

GRANT ALL PRIVILEGES ON TABLE fms.operators TO restapi;

CREATE TABLE fms.fleets (
    fleet_id SERIAL PRIMARY KEY,
    operator_id INT REFERENCES fms.operators(operator_id),
    fleet_name VARCHAR(255) NOT NULL,
    description TEXT
);

GRANT ALL PRIVILEGES ON TABLE fms.fleets TO restapi;

CREATE TYPE fms.status AS ENUM ('Operational', 'In Maintenance', 'Retired');

CREATE TABLE fms.vehicles (
    vehicle_id SERIAL PRIMARY KEY,
    fleet_id INT REFERENCES fms.fleets(fleet_id),
    license_plate VARCHAR(255) UNIQUE NOT NULL,
    status fms.status NOT NULL,
    last_service_date DATE NOT NULL
);

GRANT ALL PRIVILEGES ON TABLE fms.vehicles TO restapi;

CREATE TABLE fms.drivers (
    driver_id SERIAL PRIMARY KEY,
    operator_id INT REFERENCES fms.operators(operator_id),
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    license_number VARCHAR(255) UNIQUE NOT NULL,
    date_of_birth DATE NOT NULL,
    hire_date DATE NOT NULL
);

GRANT ALL PRIVILEGES ON TABLE fms.drivers TO restapi;

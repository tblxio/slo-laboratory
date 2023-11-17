-- Insert 10 random operators
INSERT INTO fms.operators(company_name, contact_name, contact_email, phone_number, address)
     SELECT faker.unique_company(),
            faker.unique_name(),
            faker.unique_email(),
            faker.unique_phone_number(),
            faker.unique_address()
       FROM generate_series(1, 10);

-- Insert 30 random fleets, randomly distributed in all operators
INSERT INTO fms.fleets (operator_id, fleet_name, description)
     SELECT (random() * ((select max(operator_id) from fms.operators) - 1) + 1)::INT,
            'Fleet ' || s.id, 
            faker.sentence()
       FROM generate_series(1, 30) AS s(id);

-- Insert 1000 random vehicles, randomly distributed in fleets
INSERT INTO fms.vehicles (fleet_id, license_plate, status, last_service_date)
     SELECT (random() * ((select max(fleet_id) from fms.fleets) - 1) + 1)::INT,
            faker.unique_license_plate(),
            'Operational'::fms.status,
            faker.date_this_decade()::DATE
       FROM generate_series(1, 1000);

-- Insert 300 drivers
INSERT INTO fms.drivers (operator_id, first_name, last_name, license_number, date_of_birth, hire_date)
     SELECT (random() * ((select max(operator_id) from fms.operators) - 1) + 1)::INT,
            faker.first_name(),
            faker.last_name(),
            'License-' || id,
            faker.date_this_century()::DATE,
            faker.date_this_decade()::DATE
       FROM generate_series(1, 300) AS s(id);
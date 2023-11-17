FROM postgres:16.1

# Install dependencies needed to add postgres extensions
RUN apt-get update \
        && apt-get install -y --no-install-recommends \
            postgresql-server-dev-16 \
            python3-pip \
            postgresql-plpython3-16 \
            pgxnclient \
            make \
            gcc \
        && rm -rf /var/lib/apt/lists/*

# Install faker python
RUN pip install faker --break-system-packages

# Install postgres_faker
RUN pgxn install postgresql_faker

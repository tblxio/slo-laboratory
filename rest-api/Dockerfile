## Build Image ##
FROM eclipse-temurin:17 AS build_image

ENV GRADLE_OPTS "-Dorg.gradle.daemon=false"
ENV APP_HOME /app
ARG ARTIFACTORY_USERNAME
ARG ARTIFACTORY_PASSWORD

WORKDIR $APP_HOME

COPY gradle $APP_HOME/gradle
COPY gradlew $APP_HOME/
COPY settings.gradle.kts $APP_HOME/
COPY build.gradle.kts $APP_HOME/
COPY src $APP_HOME/src
RUN $APP_HOME/gradlew clean build -x test


## Deploy Image ##
FROM gcr.io/distroless/java17-debian11:nonroot as release_image

# Env variables
ENV APP_HOME /app
ENV PROJECT_NAME "fms-backend"

# Argument variables
ARG UNAME=nonroot
USER $UNAME

# Workdir creation
WORKDIR $APP_HOME
COPY --chown=$UNAME:$UNAME --from=build_image $APP_HOME/build/libs/ .

# Container entrypoint
CMD ["/app/fms-backend.jar"]

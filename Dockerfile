## FIRST CONTAINER

## Build Angular
FROM node:22 AS ngbuild

WORKDIR /frontend

# Install Angular
# RUN npm i -g @angular/cli@17.3.8
RUN npm i -g @angular/cli

COPY frontend/angular.json .
COPY frontend/package*.json .
COPY frontend/tsconfig*.json .
COPY frontend/src src


# Install modules
RUN npm ci && ng build
# Or: RUN npm i
# && means only run the 2nd one if the first one is successful


## Build Spring Boot
FROM openjdk:21-jdk-bullseye AS javabuild

WORKDIR /backend

COPY backend/mvnw .
COPY backend/mvnw.cmd .
COPY backend/pom.xml .
COPY backend/.mvn .mvn
COPY backend/src src

# Copy Angular files to Spring Boot
COPY --from=ngbuild /frontend/dist/frontend/browser src/main/resources/static




RUN /backend/mvnw package -Dmaven.test.skip=true
# RUN ./mvnw package -Dmaven.test.skip=true
# RUN mvn package -Dmaven.test.skip=true


## SECOND CONTAINER

FROM openjdk:21-jdk-bullseye

WORKDIR /app

# Copying file from builder instead of locally
COPY --from=javabuild /backend/target/backend-0.0.1-SNAPSHOT.jar .

# Run
ENV S3_ENDPOINT=
ENV S3_REGION=
ENV S3_KEY_ACCESS=
ENV S3_KEY_SECRET=
ENV PORT=8080

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar backend-0.0.1-SNAPSHOT.jar
# If want to rename to weather.jar
# ENTRYPOINT SERVER_PORT=${PORT} java -jar weather.jar
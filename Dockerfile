## FIRST CONTAINER

## Build Angular
FROM node:22 AS ngbuild

WORKDIR /day39-frontend-full

# Install Angular
# RUN npm i -g @angular/cli@17.3.8
RUN npm i -g @angular/cli

COPY day39-frontend-full/angular.json .
COPY day39-frontend-full/package*.json .
COPY day39-frontend-full/tsconfig*.json .
COPY day39-frontend-full/src src


# Install modules
RUN npm ci && ng build
# Or: RUN npm i
# && means only run the 2nd one if the first one is successful


## Build Spring Boot
FROM openjdk:21-jdk-bullseye AS javabuild

WORKDIR /day39-backend-full

COPY day39-backend-full/mvnw .
COPY day39-backend-full/mvnw.cmd .
COPY day39-backend-full/pom.xml .
COPY day39-backend-full/.mvn .mvn
COPY day39-backend-full/src src

# Copy Angular files to Spring Boot
COPY --from=ngbuild /day39-frontend-full/dist/day39-frontend-full/browser src/main/resources/static




RUN /day39-backend-full/mvnw package -Dmaven.test.skip=true
# RUN ./mvnw package -Dmaven.test.skip=true
# RUN mvn package -Dmaven.test.skip=true


## SECOND CONTAINER

FROM openjdk:21-jdk-bullseye

WORKDIR /app

# Copying file from builder instead of locally
COPY --from=javabuild /day39-backend-full/target/day39-backend-full-0.0.1-SNAPSHOT.jar .

# Run
ENV PORT=8080

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar day39-backend-full-0.0.1-SNAPSHOT.jar
# If want to rename to weather.jar
# ENTRYPOINT SERVER_PORT=${PORT} java -jar weather.jar
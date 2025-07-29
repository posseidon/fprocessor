# fprocessor

A simple Java project template using Maven.

## Structure

- `src/main/java/hu/elte/inf/pet/project/App.java`: Main application entry point.
- `src/test/java/hu/elte/inf/pet/project/AppTest.java`: Unit tests for the application.
- `pom.xml`: Maven build configuration.

## Build

To build the project, run:

```sh
mvn clean package
```

## Run

To run the application:

```sh
mvn exec:java -Dexec.mainClass="hu.elte.inf.pet.project.App"
```

## Test

To run tests:

```sh
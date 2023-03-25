# Canvas

[![Build](https://github.com/jMaddrell/canvas/actions/workflows/maven.yml/badge.svg?branch=main)](https://github.com/jMaddrell/canvas/actions/workflows/maven.yml)

## Building
This project is built using Maven (tested with 3.8.7):

```shell
mvn package
```

## Running
The application can be launched using maven:
```shell
mvn exec:java
```

or directly:
```shell
java -jar target/canvas-1.0-SNAPSHOT.jar
```

## Assumptions
- Shapes cannot clip into each other
  - Newer shapes are placed higher on the canvas than older ones
- Elements cannot be drawn entirely are partially off the edge of the canvas
  - Drawing elements off the canvas can be used in some tools to hide them
- Canvas 'frame' is a visual aide since it is being drawn on the console

## Potential Improvements
- Keep the previously drawn frame and only draw the new element onto it


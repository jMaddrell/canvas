# Canvas

[![Build](https://github.com/jMaddrell/canvas/actions/workflows/main.yml/badge.svg?branch=main)](https://github.com/jMaddrell/canvas/actions/workflows/maven.yml)

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

## Notes
- Tested on macOS 13 (arm64) with OpenJDK 17
- Tested on Windows 11 with Azul 11
- Tested on ubuntu via GitHub Actions with temurin 11
- If AppTest.itHandlesInput() is failing this is likely because IntelliJ keeps trimming trailing whitespace at the end of lines, please reset the file.
- To enable object to clip into each other pass "enableClipping" as an argument eg:

```shell
mvn exec:java -Dexec.args="enableClipping"
```
or directly:
```shell
java -jar target/canvas-1.0-SNAPSHOT.jar enableClipping
```

## Assumptions
- Shapes cannot clip into each other
  - Newer shapes are placed higher on the canvas than older ones
- Elements cannot be drawn entirely are partially off the edge of the canvas
  - Drawing elements off the canvas can be used in some tools to hide them
- Canvas 'frame' is a visual aide since it is being drawn on the console

## Potential Improvements
- Keep the previously drawn frame and only draw the new element onto it


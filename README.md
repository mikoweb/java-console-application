# Java Sample Console Application

Go to `src/main/java/app/module/sample/application/command` directory to show commands source code.

## Installation

    mvn dependency:copy-dependencies

## Build JAR and run

Build:

    mvn clean compile spring-boot:process-aot package

In Idea IDE use tab `Maven` -> `Execute Maven Goal` button.

Run:

    java -jar target/JavaConsoleLearn-1.0-SNAPSHOT.jar

## Build a native executable

* [Ahead of Time Optimizations in Spring 6](https://www.baeldung.com/spring-6-ahead-of-time-optimizations)
* [Spring - GraalVM Native Image Support](https://docs.spring.io/spring-boot/docs/current/reference/html/native-image.html#native-image.developing-your-first-application.buildpacks.maven)

Install GraalVM Community:

https://www.graalvm.org/downloads/

    bash <(curl -sL https://get.graalvm.org/jdk)
    cd /path/to/graalvm/bin
    ./gu install native-image

Add `GRAALVM_HOME` to `.profile`

    export GRAALVM_HOME="/path/to/graalvm/bin/native-image"

Build:

    mvn clean compile spring-boot:process-aot package
    mvn -Pnative native:compile-no-fork

In Idea IDE use tab `Maven` -> `Execute Maven Goal` button.

### Execute native app

    target/console

## Copyrights

Copyright (c) Rafał Mikołajun 2023.

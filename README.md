# Spring Boot Command Line

This is a Spring Boot based playground using 
* [Spring Shell](https://projects.spring.io/spring-shell/) feat. [JLine](https://jline.github.io/)
* a [state machine](https://projects.spring.io/spring-statemachine/)
* a [rule engine](https://github.com/j-easy/easy-rules)

## Build & Run

`./gradlew clean build && java -jar build/libs/spring-boot-cli-0.0.1-SNAPSHOT.jar`

## State

External state can be set using the `state` property in `cli-state.properties`.
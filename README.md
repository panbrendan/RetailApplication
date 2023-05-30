<img align="left" width="48" height="48" src="./spring-boot-logo.jpg">

# Spring Boot Application Example

[![Build Status](https://travis-ci.org/mertakdut/Spring-Boot-Sample-Project.svg?branch=master)](https://travis-ci.org/mertakdut/Spring-Boot-Sample-Project)
[![Coverage Status](https://coveralls.io/repos/github/mertakdut/Spring-Boot-Sample-Project/badge.svg?branch=master)](https://coveralls.io/github/mertakdut/Spring-Boot-Sample-Project?branch=master)

This is a sample Java / Maven / Spring Boot application which provides RESTful services. It can be used as a starter project. Currently it is designed to work as [this project](https://github.com/mertakdut/React-Sample-Project)'s backend.

## Installation Instructions
  You can import the project as a maven application to your favorite IDE. I made my tests by using eclipse jee-2018-12.
  
  If lombok gets in your way, by referring [this answer](https://stackoverflow.com/a/22332248/4130569), you can install lombok by its jar file.

## To run the application
Use one of the several ways of running a Spring Boot application. Below are just three options:

1. Build using maven goal (or by using maven wrapper): `mvn clean package` and execute the resulting artifact as follows `java -jar RetailApplication-0.0.1-SNAPSHOT.jar` or
2. On Unix/Linux based systems: run `mvn clean package` then run the resulting jar as any other executable `./RetailApplication-0.0.1-SNAPSHOT.jar`

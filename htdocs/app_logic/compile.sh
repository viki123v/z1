#!/bin/bash 
shopt -s globstar 

javac -cp src/main/java **/*.java -d build 
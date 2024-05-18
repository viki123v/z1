#!/bin/bash 

docker run --rm \
--name it_zad1_cont \
-dit \
-p 8081:80 \
--mount type=bind,source=./htdocs,target=/usr/local/apache2/htdocs \
it_zad1
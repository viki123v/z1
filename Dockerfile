FROM openjdk:21-jdk as java 
FROM httpd as apache 

COPY --from=java /usr/java/openjdk-21 /usr/java/openjdk

WORKDIR /usr/local/apache2/htdocs 
ENV PATH=${PATH}:/usr/java/openjdk/bin
COPY httpd.conf /usr/local/apache2/conf/

RUN apt-get update 

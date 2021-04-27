FROM ubuntu:18.04

WORKDIR /root

VOLUME ["/home/newuser"]

RUN apt-get update && apt-get upgrade -y &&\
    apt-get install -y openjdk-8-jdk npm vim

RUN apt-get install -y curl

RUN curl -L -o sbt.deb "https://dl.bintray.com/sbt/debian/sbt-1.4.9.deb" &&\
    dpkg -i sbt.deb &&\
    rm sbt.deb &&\
    apt-get update &&\
    apt-get install -y sbt &&\
    sbt sbtVersion

RUN curl -L -o scala.tgz "https://downloads.lightbend.com/scala/2.12.13/scala-2.12.13.tgz" &&\
    mkdir /usr/share/local &&\
    tar -xzf scala.tgz -C /usr/share/local &&\
    rm scala.tgz

ENV PATH=/usr/share/local/scala-2.12.13/bin:${PATH}

WORKDIR /home/newuser

EXPOSE 3000
EXPOSE 9000

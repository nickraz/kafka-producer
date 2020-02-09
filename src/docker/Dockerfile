FROM anapsix/alpine-java

ARG
MAINTAINER nickraz@yandex.ru

RUN apk add mc
RUN mkdir /work && chmod -R 777 /work
RUN ls
RUN echo Hello world
RUN java -version

COPY ./spboot-0.0.1-SNAPSHOT.jar /work/spboot.jar
COPY run.sh /work/run.sh
RUN ls /work

EXPOSE 9080

CMD ["sh", "/work/run.sh", "run"]
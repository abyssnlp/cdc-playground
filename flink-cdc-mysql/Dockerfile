FROM flink:1.16.0-scala_2.12

COPY ./build/download-jars /opt/flink/lib
RUN apt-get update && apt-get install tree
# Spring Boot Kafka 模版

Spring Boot 3.3.0
JDK 22

ip: 192.168.31.20 为连接的ip地址
```yaml
version: '3'

services:
  zookeeper:
    image: 'bitnami/zookeeper:latest'
    networks:
      - kafka
    ports:
      - '2181:2181'
    environment:
      - ALLOW_ANONYMOUS_LOGIN=yes
      
  kafka:
    image: 'bitnami/kafka:latest'
    networks:
      - kafka
    ports:
      - '9092:9092'
      - '9094:9094'
    environment:
      - KAFKA_BROKER_ID=1
      - KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181
      - ALLOW_PLAINTEXT_LISTENER=yes
      - KAFKA_CFG_LISTENERS=PLAINTEXT://:9092,CONTROLLER://:9093,EXTERNAL://:9094
      - KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://kafka:9092,EXTERNAL://192.168.31.20:9094
      - KAFKA_CFG_LISTENER_SECURITY_PROTOCOL_MAP=CONTROLLER:PLAINTEXT,EXTERNAL:PLAINTEXT,PLAINTEXT:PLAINTEXT

    depends_on:
      - zookeeper

  kafdrop:
    image: 'obsidiandynamics/kafdrop'
    networks:
      - kafka
    ports:
      - '9999:9000'
    environment:
      - KAFKA_BROKERCONNECT=kafka:9092
      - JVM_OPTS=-Xms32M -Xmx64M
    depends_on:
      - kafka

networks:
  kafka:
    driver: bridge
```
# Advisor

Microservice application for portfolio management. Automates forming portfolio, balancing, trading.

## Services

* Financial data service
* Mail service
* Portfolio manager
* Broker

## How to run Kafka

Go to the 'kafka' directory and run the following command:

`docker-compose -f docker-compose.yml up -d`

It will run two containers: zookeeper and kafka

## Local database

`docker run --name pg -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 postgres`
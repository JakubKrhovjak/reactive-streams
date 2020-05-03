#!/usr/bin/env bash
mvn clean package
scp -i ~/.ssh/id_rsa reactive-producer/target/coins-tracker.jar root@134.209.229.109:/root
ssh -i ~/.ssh/id_rsa root@134.209.229.109 java -jar coins-tracker.jar

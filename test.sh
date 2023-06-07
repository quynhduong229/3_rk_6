#!/bin/bash

cd /home/gitlab-runner/3_rk_6/Backend
sudo rm target/backend-1.0.0.jar*
mvn clean package
java -jar /home/gitlab-runner/3_rk_6/Backend/target/backend-1.0.0.jar

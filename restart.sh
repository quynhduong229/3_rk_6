#!/bin/bash

sudo -u gitlab-runner screen -xr -X quit
cd /home/gitlab-runner/3_rk_6/Backend
git pull git@git.linux.iastate.edu:cs309/fall2021/3_rk_6.git
sudo rm target/backend-1.0.0.jar*
mvn clean package
sudo -u gitlab-runner screen -dmS springboot bash -c "java -jar target/backend-1.0.0.jar"

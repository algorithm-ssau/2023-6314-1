#!/bin/bash
BIGreen='\033[1;92m'
NC='\033[0m'
LRed='\033[1;31m'

#Docker clean------------------------------------------------------------------------------------
echo -e "\n-------- ${BIGreen}DOCKER: force killing all containers${NC} --------\n"
docker rm -f $(docker container ls -a | awk 'NR>1 {print $1}')

echo -e "\n-------- ${BIGreen}DOCKER: removing all images exclude postgres${NC} --------\n"
docker rmi $(docker images | grep -v "postgres" | grep -v "confluentinc/cp-kafka" | grep -v "confluentinc/cp-zookeeper" | grep -v "obsidiandynamics/kafdrop" | grep -v "config-service"  | grep -v "discovery-service" | grep -v "services-python-service" | awk 'NR>1 {print $1}')

echo -e "\n-------- ${BIGreen}DOCKER: removing all volumes${NC} --------\n"
docker volume rm $(docker volume ls -qf dangling=true)

#Maven offline build-----------------------------------------------------------------------------
echo -e "\n-------- ${BIGreen}MAVEN: start offline clean package${NC} --------\n"
cd ../
./mvnw -am -o -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -T 2C clean package

#Caching offline build, else build online--------------------------------------------------------
if [ $? -eq 0 ]; then
    echo -e "${BIGreen}MAVEN: success offline build${NC}"
else
    echo -e "${LRed}MAVEN: error offline build${NC}"

    echo -e "\n-------- ${BIGreen}MAVEN: start online clean package${NC} --------\n"
    ./mvnw -am clean -Dmaven.test.skip -Dmaven.javadoc.skip=true -T 2C clean package
fi

#Docker compose up-------------------------------------------------------------------------------
echo -e "\n-------- ${BIGreen}DOCKER: docker-compose up${NC} --------\n"
cd ./services/
docker-compose up --build
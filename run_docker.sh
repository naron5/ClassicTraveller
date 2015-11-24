#! /usr/bin/env bash

set -v

INPUT=service_versions.csv
OLDIFS=$IFS
IFS=,

if [ ! -f $INPUT ]
    then
    echo 'service_versions.csv does not exist'
    exit 1
fi

cp docker-compose.yml.template docker-compose.yml
while read service_name version
do

     echo sed -i swap s/%%$service_name%%/$version/g docker-compose.yml
     sed -i swap s/%%$service_name%%/$version/g docker-compose.yml
done < $INPUT

IFS=$OLDIFS

cat docker-compose.yml

eval $(docker-machine env dev)

docker-compose up -d --x-smart-recreate travellerdebug
sleep 60
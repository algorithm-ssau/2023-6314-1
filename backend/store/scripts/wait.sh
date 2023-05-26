#!/bin/bash
# wait.sh

host="$1"
port="$2"
url="http://$host:$port/actuator/health"

echo "$(date) - Starting wait for $host:$port"

while ! curl -s "$url" --connect-timeout 1 > /dev/null; do
  echo "$(date) - Trying connect to $host:$port"
  sleep 5
done

echo "$(date) - $host:$port is available"
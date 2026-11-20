#!/bin/sh
set -e

PORT="${PORT:-10000}"

sed -i 's/port="8005"/port="-1"/' \
    /usr/local/tomcat/conf/server.xml

sed -i "s/port=\"8080\"/port=\"$PORT\"/" \
    /usr/local/tomcat/conf/server.xml

exec catalina.sh run

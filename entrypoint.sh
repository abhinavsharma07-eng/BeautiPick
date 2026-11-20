
#!/bin/sh
set -e

PORT="${PORT:-10000}"

# Disable Tomcat shutdown port
sed -i 's/port="8005"/port="-1"/' \
    /usr/local/tomcat/conf/server.xml

# Configure HTTP port for Render
sed -i "s/port=\"8080\"/port=\"$PORT\"/" \
    /usr/local/tomcat/conf/server.xml

exec catalina.sh run

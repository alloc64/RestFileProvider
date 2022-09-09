FROM openjdk:8-jdk-alpine

COPY entrypoint.sh /
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]

COPY *.jar /bin/
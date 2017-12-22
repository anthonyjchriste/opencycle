FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/opencycle.jar /opencycle/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/opencycle/app.jar"]

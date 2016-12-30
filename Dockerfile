FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/sentence-sel.jar /sentence-sel/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/sentence-sel/app.jar"]

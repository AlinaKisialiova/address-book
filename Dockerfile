FROM adoptopenjdk:11-jre-hotspot
ADD target/address-book-*.jar address-book-app.jar
ENTRYPOINT ["java", "-jar", "address-book-app.jar"]
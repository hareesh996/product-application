export JAVA_HOME=D:/softwares/binaries/java_1_13
echo "JAVA_HOME is set to D:/softwares/binaries/java_1_13"
./gradlew build
echo "gradle build complete | Launching applications name now"
$JAVA_HOME/bin/java -jar microservices/product-composite-service/build/libs/*.jar & /
$JAVA_HOME/bin/java -jar microservices/product-service/build/libs/*.jar & /
$JAVA_HOME/bin/java -jar microservices/recommendation-service/build/libs/*.jar & /
$JAVA_HOME/bin/java -jar microservices/review-service/build/libs/*.jar

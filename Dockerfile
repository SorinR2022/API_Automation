FROM maven:3.9.6-eclipse-temurin-17
WORKDIR /app
COPY . .
ENTRYPOINT ["sh", "-c", "mvn -B test -DBASE_URL=${BASE_URL:-https://fakerestapi.azurewebsites.net}"]

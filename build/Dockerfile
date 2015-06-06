FROM clojure
ENV DATABASE_URL postgresql://db/postgres?user=postgres

COPY . /usr/src/app
WORKDIR /usr/src/app

RUN lein ring uberjar
RUN mv "/usr/src/app/target/cdlib.jar" /cdlib.jar
RUN rm -r /usr/src/app

EXPOSE 3000
CMD ["java", "-jar", "/cdlib.jar"]

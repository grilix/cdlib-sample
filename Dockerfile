FROM clojure
ADD . /app
WORKDIR /app
RUN lein deps

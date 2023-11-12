FROM  openjdk:17
WORKDIR /home/serviceregistry/

COPY    ./target/serviceregistry-0.0.1-snapshot.jar /home/serviceregistry/
COPY    ./ /home/serviceregistry/config/

EXPOSE 25695

CMD     java -jar serviceregistry-0.0.1-snapshot --spring.config.location=file:/home/serviceregistry/config/application.properties
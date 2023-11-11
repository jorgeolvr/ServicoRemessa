# Define a imagem base
FROM openjdk:17

# Copia o arquivo JAR do seu projeto para dentro do container
COPY target/servico-remessa-0.0.1-SNAPSHOT.jar /app/servico-remessa-0.0.1-SNAPSHOT.jar

# Define o diretório de trabalho
WORKDIR /app

# Expõe a porta do seu projeto
EXPOSE 8080

# Define o comando de inicialização do seu projeto
CMD ["java", "-jar", "servico-remessa-0.0.1-SNAPSHOT.jar"]

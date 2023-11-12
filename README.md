# Serviço remessa

Código desenvolvido durante o **processo seletivo** do [Inter](https://inter.co/). O objetivo da aplicação é permitir a
realização de remessa entre Pessoa Física (PF) e Pessoa Jurídica (PJ) que possuem carteira em Real e em Dólar.

## Dependências

As seguintes dependências do maven foram utilizadas na implementação do projeto:

- [spring-boot-starter](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter) - Core starter, including auto-configuration support, logging and YAML.
- [spring-boot-starter-web](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web) - Starter for building web, including RESTful, applications using Spring MVC. Uses Tomcat as the default embedded container.
- [h2](https://mvnrepository.com/artifact/com.h2database/h2) - A fast SQL database that can run embedded or a server mode with support for transactions, encryption, full search, etc. Storage can be disk-based or in-memory.
- [spring-boot-starter-webflux](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-webflux) - Starter for building WebFlux applications using Spring Framework's Reactive Web support.
- [spring-boot-starter-data-jpa](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa) - Starter for using Spring Data JPA with Hibernate.
- [spring-boot-devtools](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools) - Spring Boot Developer Tools.
- [lombok](https://mvnrepository.com/artifact/org.projectlombok/lombok) - Spice up your java: Automatic Resource Management, automatic generation of getters, setters, equals, hashCode and toString, and more!.
- [spring-boot-starter-test](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test) - Starter for testing Spring Boot applications with libraries including JUnit Jupiter, Hamcrest and Mockito.
- [springdoc-openapi-starter-webmvc-ui](https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui) - SpringDoc OpenAPI Starter WebMVC UI.
- [spring-security-crypto](https://mvnrepository.com/artifact/org.springframework.security/spring-security-crypto) - Spring Security.
- [spring-boot-starter-validation](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation) - Starter for using Java Bean Validation with Hibernate Validator.
- [spring-boot-starter-cache](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-cache) - Starter for using Spring Framework's caching support.

## Como clonar o repositório?

Para baixar, basta clonar este repositório na sua máquina:

```sh
git clone https://github.com/jorgeolvr/ServicoRemessa
```

## Inicialização da aplicação

O spring possui o maven como gerenciador de pacotes. Para isso é necessário instalar todas as dependências antes
prosseguir:

### Dentro da pasta do projeto

Execute o seguinte comando para utilizar todas as dependências:

```
mvn clean install
```

### Como testar na própria máquina?

Para usar a aplicação abra o terminal dentro da pasta do projeto e execute:

```
mvn spring-boot:run
```

### Como rodar os testes unitários?

Para rodar os testes unitários abra o terminal dentro da pasta do projeto e execute:

```
mvn test
```

### Como testar em um docker container?

Para usar em um container do  **docker** primeiro será necessário gerar um arquivo com extensão .jar através do seguinte comando:

```
mvn clean package
```

Com o arquivo devidamente localizado na pasta target execute:

```
docker build -t servico-remessa .
```

Após isso, basta executar o próximo comando para rodar a aplicação:

```
docker run --name servico-remessa -d -p 8080:8080 servico-remessa
```

Devido ao redirecionamento de portas a aplicação está completamente disponível para uso em sua máquina!

## Documentação das APIs

Para visualizar a documentação feita no Swagger basta estar com o projeto rodando e entrar na seguinte URL:

```
http://localhost:8080/swagger-ui/index.html
```

## H2 database

Para visualizar as tabelas do H2 database basta estar com o projeto rodando e entrar na seguinte URL com a seguinte configuração:

```
http://localhost:8080/h2

JDBC URL: jdbc:h2:mem:remessadb
User Name: sa
Password:
```
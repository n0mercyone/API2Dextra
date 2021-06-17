# Big Game Survey 
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/n0mercyone/Personal-Challenges/blob/master/LICENSE) 

# Sobre o projeto

https://api2dextra.herokuapp.com/swagger-ui/

Desafio proposto pela equipe da [Dextra](https://www.dextra.com.br/).

O projeto consiste em um CRUD utilizando uma API RESTful para cadastro de personagens do filme Harry Potter, bem como na integração com uma API parceira deles para a obtenção das casas que estão presentes no filme, como dependência para a criação do personagem.


## Swagger da API
![Swagger](https://github.com/n0mercyone/assets/blob/main/swagger-crud-spring-potter.png)

# Tecnologias utilizadas
## Back end
- Java
- Spring Boot
- JPA / Hibernate
- Maven
- Jarsey
## Implantação
- Back end: Heroku
- Banco de dados: Postgresql

# Como executar o projeto

## Back end
Pré-requisitos: Java 11

```bash
# clonar repositório
git clone https://github.com/devsuperior/sds1-wmazoni

# na raiz do projeto, entrar na pasta resources e configurar o arquivo application.properties para conectar ao endereço do seu banco de dados (Postgres 9.6 ou superior)

# na raiz do projeto executar o comando abaixo (assegure-se que o banco de dados esteja disponível no endereço, usuário e senha configurados no arquivo application.properties )
# executar o projeto
./mvnw spring-boot:run
```

# Autor

Frank Junior 

https://www.linkedin.com/in/jfrankjunior/


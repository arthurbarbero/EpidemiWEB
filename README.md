# EpidemiWEB
JAVA+Spring Boot+Spring Data JPA+JWT+Vue.JS


## Integrantes :bust_in_silhouette:

- Arthur Barbero [LinkedIn](https://www.linkedin.com/in/arthur-barbero/) / [Github](https://github.com/arthurbarbero);
- Felippe Alves [LinkedIn](https://www.linkedin.com/in/felippe-alves-de-paula/) / [Github](https://github.com/FelippeAlves);
- Gabriel Landim [LinkeIn](https://www.linkedin.com/in/gabriel-landim-2b5bb8181/) / [Github](https://github.com/Glandim);
- José Vinícius [LinkedIn](https://www.linkedin.com/in/jose-vinicius-ferreira-santana-903239181/) / [Github](https://github.com/JViniciusF).
- Thyago Odorico [LinkeIn](https://www.linkedin.com/in/thyago-odorico-10ab8a11a/) / [Github](https://github.com/togarci).


## Objetivos :dart:

Este projeto tem como objetivo apresentar os conhecimentos adquiridos e a criação de uma aplicação WEB baseada nas ferramentas acima para a avaliação das matérias ministradas pela FATEC SJC:
- Tópicos Especiais em Informática;
- Gestão e Governança de Técnologia da Informação;
- Gestão de Projetos;
- Inteligencia Artificial;


## Introdução :pencil:

Este projeto tem por objetivo trazer valor para as informações descentralizadas de doenças epidemiológicas.  

Considerada como o novo petróleo, a informação é tida como o grande recurso dos dias atuais, e não são poucos os tipos de softwares e registradores que tentam acessar nossos dados dia após dia na esperança de vincular nossos dados com consumos ou estilos de vida, para que as empresas possam oferecer seus serviços ou despontar à frente de seus concorrentes. Com essa visão, a informação também pode ser usada para o bem das sociedades, basta que consigamos reter informações dos usuários de forma inteligente e concisa.  

Desta maneira, gostaríamos de pensar e apresentar formas de capturar informações, retê-las e utilizá-las de forma a identificar novas doenças, padrões de relacionamento entre doenças-pacientes e doenças-sintomas, padrões de localidade e gerar insights de possíveis novas epidemias.  

O projeto tem por objetivo a elaboração de um aplicativo WEB, acessível, que seja possível o cadastro de usuários, cadastro de doenças, cadastro de incidências epidemiológicas, cadastro de sintomas, busca por doenças cadastradas, relatório diversos, alertas de possíveis epidemias e diagnósticos preliminares de doenças relacionadas aos sintomas escolhidos. 

Conforme cronograma, os objetivos serão segregados pelas áreas de conhecimento, sendo em cada uma de suas entregas apresentadas partes do software como Back-End, REST, Segurança e Front-End. 


## Tecnologias utilizadas :computer:

- JAVA;
- Spring Boot;
- Spring Data JPA;
- PostgreSQL;
- Autenticação JWT;
- Vue.JS .


## Features :wrench:

### Home :house:

### Login / Cadastrar :page_facing_up:

![Login]( | width="300" height="200" )

![Cadastro]( | width="300" height="200" )

### DashBoard :chart_with_upwards_trend:

![DashBoard]( | width="300" height="200" )
### Cadastrar Doença :syringe:

![Cadastrar_doenças]( | width="300" height="200" )
### Cadastrar Sintoma :pill:

![Cadastrar_sintomas]( | width="300" height="200" )
### Cadastrar Incidência :bookmark:

![Cadastrar_incidencias]( | width="300" height="200" )

## Entregas :white_check_mark:

### Back-End 23/03:
- Boiler Plate do projeto contendo as bases para a criação dos métodos e rotas;
- Modelagem e mapeamento Spring Data JPA;
- Criação dos principais métodos.

[Primeira Entrega](https://github.com/arthurbarbero/EpidemiWEB/blob/main/Entrega%201/entrega1.md)


### REST 13/04:
- Criação de Controllers e suas rotas para acesso aos métodos (GET, POST, PUT, DELETE);
- Formatar os resultados para JSON;
- Habilitar tratamento de CORS.

[Segunda Entrega](https://github.com/arthurbarbero/EpidemiWEB/blob/main/Entrega%202/entrega2.md)

### Segurança JWT 11/05:
- Inclusão de autenticação via JWT para as rotas;

[Terceira Entrega](https://github.com/arthurbarbero/EpidemiWEB/blob/main/Entrega%203/entrega3.md)

### Front-End 01/06:
- Boiler Plate para a criação dos componentes e paginas;
- Controle de estado via VueX;
- Rotas de paginação via VueRouter;
- Consumo do Back-End via axios com tratamento de erros;
- Elementos visuais diferentes por nível de acesso.

[Quarta Entrega - Repositório do Front-End]()


## Rodando o Projeto

### Pré-requisitos:

- [PostgreSQL](https://www.postgresql.org/);
- [Java JDK 8](https://www.oracle.com/br/java/technologies/javase/javase-jdk8-downloads.html);
- Conhecimentos básicos de SQL e POO.

- Crie o banco de dados inicial:
  - Crie um database com o nome `epidemiweb`;
  - O projeto ja conta com um arquivo sql que realiza a criação das tabelas, esquemas e usuários, apenas copie e utilize em seu gerenciador de bancos ou o próprio [PGAdmin](https://www.pgadmin.org/download/) que vem junto ao instalar o PostgreSQL;
  - Para mais informações sobre como criar e utilizar o PgAdmin, [acesse esse tutorial](https://www.devmedia.com.br/postgresql-tutorial/33025).

- Clone o repositório.
- Na raiz da pasta, abra um terminal ou cmd e utilize o comando `./mvnw.exe clean install` que ira instalar todas as dependências;
- Para rodar o projeto utilize o comando `./mvnw.exe clean spring-boot:run`.
- Acesse o projeto em `http://localhost:8082/api/`


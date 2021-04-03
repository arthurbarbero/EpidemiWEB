## Segunda Entrega

## Objetivos da entrega:

- Criação das rotas;
- Visualização em formato JsonView;
- Confirguração de tratamento de CORS.


### Criação das rotas:

O projeto se utiliza como base as prerrogativas de uma aplicação Java com o Framework Spring Boot. Sua base foi construída em cima destes padrões e para a realização do projeto como um todo, utilizamos as seguintes dependências de projeto:

Adicionamos a dependência "jackson-dataformat-xml" para ter acesso as facilidades do JsonView, demonstrando apenas os atributos que selecionarmos para visualização.


<details>
  <summary><strong>Clique para ver o arquivo de dependências pom.xml</strong></summary>
  
  [../pom.xml](https://github.com/arthurbarbero/EpidemiWEB/blob/main/pom.xml)
  
  ```xml
      <dependencies>
      <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
      </dependency>
      <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt</artifactId>
        <version>0.9.1</version>
      </dependency>
      <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
      </dependency>
      <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
      </dependency>
      <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
      </dependency>
      <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
      </dependency>
      <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
      </dependency>
      <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-test</artifactId>
        <scope>test</scope>
      </dependency>
      <dependency>
        <groupId>com.fasterxml.jackson.dataformat</groupId>
        <artifactId>jackson-dataformat-xml</artifactId>
		  </dependency>
    </dependencies>
  ```
</details>

Para a criação das rotas, seguimos o padrão do Spring Boot, se utilizando das anotações "*@RestController*", "*@RestMapping*", e também para os métodos HTTP como "*@GetMapping*", "*@PostMapping*", "*@PutMapping*" e "*@DeleteMapping*"

<details>
  <summary><strong>Clique para ver um exemplo de código usando as anotações</strong></summary>
  
  [../src/main/java/br/gov/sp/fatec/epidemiweb/Controller/UserController.java](https://github.com/arthurbarbero/EpidemiWEB/blob/main/src/main/java/br/gov/sp/fatec/epidemiweb/Controller/UserController.java)
  
  ```java
    @RestController
    @RequestMapping(value = "/user")
    public class UserController {
      
      @GetMapping(value = "/getById/{id}")
      public User GetUserById(@PathVariable(value = "id") int id) {...}

      @PostMapping(value = "/login")
      public User GetUserByEmailAndPassword(@RequestBody User user) {...}

      @PutMapping(value = "/update")
      public ResponseEntity<User> UpdateDisease(@RequestBody User user) {...}

      @DeleteMapping(value = "/deleteById/{userId}")
      public ResponseEntity<String> DeleteSymptomById(@PathVariable(value = "userId") User user) { ...}

      ...
    }
  ```
</details>



### Visualização em formato JsonView:

A nova dependência "*jackson-dataformat-xml*", traz consigo uma anotação "*@JsonView*" que permite a seleção dos atributos de uma classe específica que queremos demonstrar seus resultados em formato JSON ou XML.

<details>
  <summary><strong>Clique para ver um exemplo de código</strong></summary>
  
  [../src/main/java/br/gov/sp/fatec/epidemiweb/Controller/UserController.java](https://github.com/arthurbarbero/EpidemiWEB/blob/main/src/main/java/br/gov/sp/fatec/epidemiweb/Controller/UserController.java)
  
  ```java
    @JsonView(View.User.class)
    @GetMapping(value = "/getById/{id}")
    public User GetUserById(@PathVariable(value = "id") int id) { ... }
  ```

  [../src/main/java/br/gov/sp/fatec/epidemiweb/Controller/View.java](https://github.com/arthurbarbero/EpidemiWEB/blob/main/src/main/java/br/gov/sp/fatec/epidemiweb/Controller/View.java)

  ```java
  public class View {
    
    public static class User {}

    ...
  }
  ```

  [../src/main/java/br/gov/sp/fatec/epidemiweb/Controller/View.java](https://github.com/arthurbarbero/EpidemiWEB/blob/main/src/main/java/br/gov/sp/fatec/epidemiweb/Entity/User.java)

  ```java
  @Entity
  @Table(name = "usr_users", schema = "users")
  public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", columnDefinition = "Serial")
    private Integer id;

    @JsonView({View.User.class, View.DiseaseIncidences.class, View.IncidenceComplete.class})
    @Column(name="st_name", nullable=false)
    private String name;

    @JsonView(View.User.class)
    @Column(name="st_email", nullable=false, unique=true)
    private String email;

    ...
  }
  ```
</details>

No exemplo acima podemos perceber que se solicitarmos informações da rota "*/api/user/getById/{id}*", o seu retorno terá apenas os atributos escolhidos com a anotação "*@JsonView(View.User.class)*" formatados em Json ou XML conforme o requisitado nos headers, (*Content-type: application/json*).

 ## Confirguração de tratamento de CORS

 Neste projeto, para que nossa API esteja disponível para outros domínios, tratamos os problemas relacionados a CORS, utilizando da anotação "*@CrossOrigin*" da dependência "*spring-boot-starter-web*".

<details>
  <summary><strong>Clique para ver um exemplo de código</strong></summary>
  
  [../src/main/java/br/gov/sp/fatec/epidemiweb/Controller/UserController.java](https://github.com/arthurbarbero/EpidemiWEB/blob/main/src/main/java/br/gov/sp/fatec/epidemiweb/Controller/UserController.java)

  ```java
  @RestController
  @RequestMapping(value = "/user")
  @CrossOrigin
  public class UserController {
    
    @JsonView(View.User.class)
    @GetMapping(value = "/getById/{id}")
    public User GetUserById(@PathVariable(value = "id") int id) {
      return userService.getUserById(id);
    }

    ...
  }

  ```
</details>

Conforme o exemplo acima, a anotação pode ser usada tanto para anotar a classe, assim todas as rotas de cada método estarão disponíveis para os domínios selecionados, quanto para cada método, liberando o acesso de outros domínios somente em rotas específicas.


 ## Rodando o projeto
 
 ### Requisitos:
 
 - PostgreSql instalado;
 - Maven instalado; (Se o dispositivo for Windows poderá usar o utilitário `mvnw` sem precisar instalar o Maven)
 
 1 - Clone o projeto em sua máquina;
 
 2 - Na pasta raiz, abra um terminal de acordo com seu Sistema Operacional;
 
 3 - Criando o DataBase;
 
   3.1 - (Linux) Rode o comando `psql < echo CREATE DATABASE epidemiweb`;
    
   3.2 - (Windows) Use algum utilitário de SGBD como o PGAdmin por exemplo, ou acesse o SQL Shell (psql) que é instalado junto com o Postgres, entre com suas credenciais de localhost e rode os comandos `CREATE DATABASE epidemiweb`;
    
 4 - Rode o comando `mvn clean install` para instalar as dependências Maven; Caso não tenha instalado o Maven em sua máquina, rode o comando `mvnw clean install`
 
 5 - Rode o comando `mvn clean test` para ver o resultado dos testes.

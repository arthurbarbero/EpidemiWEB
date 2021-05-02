## Primeira Entrega

## Objetivos da entrega:

- Boiler Plate do projeto contendo as bases para a criação dos métodos e rotas;
- Modelagem e mapeamento Spring Data JPA;
- Criação dos principais métodos.


### Boiler Plate:

O projeto se utiliza como base as prerrogativas de uma aplicação Java com o Framework Spring Boot. Sua base foi construída em cima destes padrões e para a realização do projeto como um todo, utilizamos as seguintes dependências de projeto:


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
    </dependencies>
  ```
</details>

Podemos ressaltar das dependências acima a família "Spring-boot" responsável pela leitura das anotações, processos de builds, segurança, testes, conexão e mapeamento do banco de dados. Além disso, também é importante ressaltar a dependência "postgresql", responsável pela conexão com o banco de dados.
Toda construção inicial de back-end do projeto se tornou muito mais simples com a utilização do gerenciador de pacotes Maven e ajuda do site [Spring initializr](https://start.spring.io/) que cria a estrutura inicial do projeto em Java e já adiciona as dependências escolhidas. 


### Modelagem e mapeamento Spring Data JPA:

A modelagem do Banco de Dados foi realizada com base no problema proposto, conforme o Diagrama Entidade Relacionamento abaixo:


![Diagrama](https://github.com/arthurbarbero/EpidemiWEB/raw/main/Entrega%201/Diagrama%20ER.png)

A criação do diagrama acima foi realizada em cima das bases de normalização e contêm 3 tipos de relacionamento:
- "OneToOne" (usr_users - adr_address);
- "OneToMany/ManyToOne" (inc_incidence > usr_users; inc_incidence > dse_disease); e
- "ManyToMany/ManyToMany" (usr_users < users_groups > grp_groups; dse_disease < disease_symptoms > sym_symptoms)

<details>
  <summary><strong>Veja a modelagem realizada em linguagem SQL</strong></summary>
  
  [../DDL.sql](https://github.com/arthurbarbero/EpidemiWEB/blob/main/DDL.sql)
  
  ```sql
    create schema business;
    create schema users;

    CREATE USER epidemiuser WITH PASSWORD 'adm1n';

    GRANT CONNECT ON DATABASE epidemiweb TO epidemiuser;
    GRANT ALL ON SCHEMA business TO epidemiuser;
    GRANT ALL ON SCHEMA users TO epidemiuser;


    -- Users Tables
    CREATE TABLE users.adr_address (
        id_address SERIAL NOT NULL,
        st_address CHARACTER VARYING NOT NULL,
        nm_number INT NOT NULL,
        st_complement CHARACTER VARYING,
        st_district CHARACTER VARYING NOT NULL,
        st_city CHARACTER VARYING NOT NULL,
        st_state CHARACTER VARYING NOT NULL,
        st_country CHARACTER VARYING NOT NULL,
        created_at DATE NULL DEFAULT now(),
      updated_at DATE NULL DEFAULT now(),
        CONSTRAINT pk_id_address PRIMARY KEY (id_address)
    );

    CREATE TABLE users.usr_users (
        id_user SERIAL NOT NULL,
        st_name CHARACTER VARYING NOT NULL,
        st_email CHARACTER VARYING NOT NULL UNIQUE,
        st_password CHARACTER VARYING NOT NULL,
        id_address INTEGER NOT NULL,
        created_at DATE NULL DEFAULT now(),
      updated_at DATE NULL DEFAULT now(),
        CONSTRAINT pk_id_user PRIMARY KEY(id_user),
        CONSTRAINT fk_address FOREIGN KEY(id_address) 
            REFERENCES users.adr_address (id_address) 
                ON DELETE CASCADE 
                ON UPDATE CASCADE
    );

    CREATE TABLE users.grp_groups (
        id_group SERIAL NOT NULL,
        st_name CHARACTER VARYING NOT NULL UNIQUE,
        created_at DATE NULL DEFAULT now(),
      updated_at DATE NULL DEFAULT now(),
        CONSTRAINT pk_id_group PRIMARY KEY(id_group)
    );

    CREATE TABLE users.users_groups (
        id_user INTEGER NOT NULL,
        id_group INTEGER NOT NULL,
        CONSTRAINT fk_users_groups_user FOREIGN KEY (id_user)
            REFERENCES users.usr_users (id_user),
        CONSTRAINT fk_users_groups_groups FOREIGN KEY (id_group)
            REFERENCES users.grp_groups (id_group)
    );

    -- Business TABLES

    CREATE TABLE business.dse_disease (
      id_disease SERIAL NOT NULL,
      st_name CHARACTER VARYING NOT NULL UNIQUE,
      created_at DATE NOT NULL DEFAULT now(),
      updated_at DATE NOT NULL DEFAULT now(),
      CONSTRAINT pk_id_disease PRIMARY KEY (id_disease)
    );

    CREATE TABLE business.sym_symptoms (
        id_symptom SERIAL NOT NULL,
        st_name CHARACTER VARYING NOT NULL UNIQUE,
        st_description CHARACTER VARYING NOT NULL,
        nm_severity INT NOT NULL,
        created_at DATE NOT NULL DEFAULT now(),
      updated_at DATE NOT NULL DEFAULT now(),
        CONSTRAINT pk_id_symptom PRIMARY KEY (id_symptom)
    );

    CREATE TABLE business.disease_symptoms (
        id_disease Integer NOT NULL,
        id_symptom Integer NOT NULL,
        CONSTRAINT fk_disease_symptoms_disease FOREIGN KEY (id_disease)
            REFERENCES business.dse_disease (id_disease),
        CONSTRAINT fk_disease_symptoms_symptoms FOREIGN KEY (id_symptom)
            REFERENCES business.sym_symptoms (id_symptom)
    );

    CREATE TABLE business.inc_incidence (
        id_incidence SERIAL NOT NULL,
        id_disease Integer NOT NULL,
        id_user Integer NOT NULL,
        dt_incidence DATE NOT NULL DEFAULT now(),
        created_at DATE NOT NULL DEFAULT now(),
      updated_at DATE NOT NULL DEFAULT now(),
        CONSTRAINT pk_id_incidence PRIMARY KEY (id_incidence),
        CONSTRAINT fk_inc_incidence_disease FOREIGN KEY (id_disease)
            REFERENCES business.dse_disease (id_disease),
        CONSTRAINT fk_inc_incidence_user FOREIGN KEY (id_user)
            REFERENCES users.usr_users (id_user)
    );

    GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA business TO epidemiuser;
    GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA users TO epidemiuser;
    GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA business TO epidemiuser;
    GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA users TO epidemiuser;
  ```

</details

Após a criação de toda modelagem, realizamos o mapeamento e criação das entidades Data JPA, uma tradução das tabelas em classe que podem ser implementadas em interfaces da biblioteca Spring Data JPA

<details>
  <summary><strong>Confira este exemplo de Entidade</strong></summary>
  
  [../src/main/java/br/gov/sp/fatec/epidemiweb/Entities/Disease.java](https://github.com/arthurbarbero/EpidemiWEB/blob/main/src/main/java/br/gov/sp/fatec/epidemiweb/Entities/Disease.java)

  ```java
    import javax.persistence.Column;
    import javax.persistence.Entity;
    import javax.persistence.GeneratedValue;
    import javax.persistence.FetchType;
    import javax.persistence.GenerationType;
    import javax.persistence.Id;
    import javax.persistence.JoinColumn;
    import javax.persistence.JoinTable;
    import javax.persistence.ManyToMany;
    import javax.persistence.OneToMany;
    import javax.persistence.Table;

    import java.time.LocalDate;
    import java.util.HashSet;
    import java.util.Set;

    @Entity
    @Table(name = "dse_disease", schema = "business")
    public class Disease {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_disease", columnDefinition = "Serial")
        private Integer id;

        @Column(name="st_name", nullable=false)
        private String name;

        @Column(name="created_at", nullable=true)
        private LocalDate createdAt;

        @Column(name="updated_at", nullable=true)
        private LocalDate updateAt;

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name="disease_symptoms", schema = "business",
            joinColumns = { @JoinColumn(name="id_disease") },
            inverseJoinColumns = { @JoinColumn(name="id_symptom") })
        private Set<Symptom> symptoms;

        @OneToMany(fetch = FetchType.LAZY, mappedBy = "disease")
        private Set<Incidence> incidences;

        // Getters and Setters
    ```
    
</details>

Com as entidades criadas, para cada uma criamos a forma de acesso aos métodos que nos permitem conectar ao Banco de dados, os repositórios:

<details>
  <summary>Confira um exemplo de repositório JPA</summary>
  
  [../src/main/java/br/gov/sp/fatec/epidemiweb/Repositories/DiseaseRepository.java](https://github.com/arthurbarbero/EpidemiWEB/blob/main/src/main/java/br/gov/sp/fatec/epidemiweb/Repositories/DiseaseRepository.java)
  
  ```java
    import org.springframework.data.jpa.repository.JpaRepository;

    import br.gov.sp.fatec.epidemiweb.Entities.Disease;

    public interface DiseaseRepository extends JpaRepository<Disease, Integer>  {
    
    public Disease findByName(String name);

}
  ```
</details>

Os repositórios estendem suas funcionalidades aos métodos intrínsecos da biblioteca *JpaRepository*, podendo ser realizado os mais comuns métodos de acesso ao banco como *findAll(); findOne(); findById(); save();* e etc. Mas também podemos criar as nossas próprias pesquisas customizadas, podendo serem feitas de duas maneiras:

<details>
  <summary><strong>Pesquisa pelo nome do método</strong></summary>
  
  [../src/main/java/br/gov/sp/fatec/epidemiweb/Repositories/UserRepository.java](https://github.com/arthurbarbero/EpidemiWEB/blob/main/src/main/java/br/gov/sp/fatec/epidemiweb/Repositories/UserRepository.java)
  
  ```java
    public interface UserRepository extends JpaRepository<Users, Integer>  {
    
        public Users findByEmailAndPassword(String email, String password);

    }
  ```
 </details>
 
 ou
 
 <details>
  <summary><strong>Query em JPQL</strong></summary>
  
  [../src/main/java/br/gov/sp/fatec/epidemiweb/Repositories/IncidenceRepository.java](https://github.com/arthurbarbero/EpidemiWEB/blob/main/src/main/java/br/gov/sp/fatec/epidemiweb/Repositories/IncidenceRepository.java)
  
  ```java
    public interface IncidenceRepository extends JpaRepository<Incidence, Integer>  {
    
        @Query("select i from Incidence i inner join i.user u inner join i.disease d where u.id = :userId and d.id = :diseaseId")
        public List<Incidence> findAllIncidencesByUserAndDisease(int userId, int diseaseId);

    }
  ```
 </details>
 
- Criação dos principais métodos.

Após toda modelagem, realizamos a criação de alguns métodos que serão de extrema importância para personificação de nossas regras de negócio.
Muitas destas regras serão criadas de acordo com o andamento do projeto, porém, salientamos aqui apenas uma como exemplo


<details>
  <summary><strong>Métodos de criação e busca das incidências de doênças</strong></summary>
  
  [../src/main/java/br/gov/sp/fatec/epidemiweb/Services/IncidenceServiceImpl.java](https://github.com/arthurbarbero/EpidemiWEB/blob/main/src/main/java/br/gov/sp/fatec/epidemiweb/Services/IncidenceServiceImpl.java)
  
  ```java
      import java.time.LocalDate;
      import java.util.ArrayList;
      import java.util.List;

      import javax.transaction.Transactional;

      import org.springframework.beans.factory.annotation.Autowired;
      import org.springframework.stereotype.Service;

      import br.gov.sp.fatec.epidemiweb.Entities.Disease;
      import br.gov.sp.fatec.epidemiweb.Entities.Incidence;
      import br.gov.sp.fatec.epidemiweb.Entities.Users;
      import br.gov.sp.fatec.epidemiweb.Repositories.IncidenceRepository;

      @Service("incidenceService")
      @Transactional
      public class IncidenceServiceImpl implements IncidenceService{

          @Autowired
          private IncidenceRepository incidenceRepo;


          @Override
          public Incidence saveIncidence(LocalDate incidenceDate, Disease disease, Users user) {
              try {
                  Incidence newIncidence = new Incidence(incidenceDate, disease, user);
                  incidenceRepo.save(newIncidence);
                  if (newIncidence.getId() == null) {
                      throw new Exception("Ocorreu um erro ao tentar salva a nova incidencia, tente novamente.");
                  }
                  return newIncidence;
              } catch (Exception e) {
                  System.out.println(e.getMessage());
                  return null;
              }
          }

          @Override
          public List<Incidence> getAllIncidences() {
              try {
                  List<Incidence> allIncidence = new ArrayList<Incidence>(incidenceRepo.findAll());
                  if (allIncidence.size() <= 0) {
                      throw new Exception("Não foram encontradas incidências cadastradas.");
                  }
                  return allIncidence;
              } catch (Exception e) {
                  System.out.println(e.getMessage());
                  return null;
              }
          }

          @Override
          public List<Incidence> getAllIncidenceByUser(Users user) {
              try {
                  List<Incidence> allIncidence = new ArrayList<Incidence>(user.getIncidences());
                  if (allIncidence.size() <= 0) {
                      throw new Exception("Não foram encontradas incidências para o usuário informado.");
                  }
                  return allIncidence;
              } catch (Exception e) {
                  System.out.println(e.getMessage());
                  return null;
              }
          }

          @Override
          public List<Incidence> getAllIncidenceByDisease(Disease disease) {
              try {
                  List<Incidence> allIncidence = new ArrayList<Incidence>(disease.getIncidences());
                  if (allIncidence.size() <= 0) {
                      throw new Exception("Não foram encontradas incidências para a doença informada.");
                  }
                  return allIncidence;
              } catch (Exception e) {
                  System.out.println(e.getMessage());
                  return null;
              }
          }

          @Override
          public List<Incidence> getAllIncidenceByUserAndDisease(Users user, Disease disease) {
              try {
                  List<Incidence> allIncidence = new ArrayList<Incidence>(
                      incidenceRepo.findAllIncidencesByUserAndDisease(
                          user.getId(), disease.getId()
                      )
                  );

                  if (allIncidence.size() <= 0) {
                      throw new Exception("Não foram encontradas incidências para a doença informada.");
                  }
                  return allIncidence;
              } catch (Exception e) {
                  System.out.println(e.getMessage());
                  return null;
              }
          }
      }
  ```
 </details>

Todos os métodos seguem o padrão de implementarem apenas interfaces, por isso existe a segregação no projeto de *<método>Service.java* sendo sua interface e *<método>ServiceImpl.java* sua devida implementação de código.

Para todos os métodos criamos testes simples para que o projeto em si possa ser *executável*, basta clonar o repositório e rodar o comando `mvn clean test` na raiz do projeto, segue abaixo um exemplo de teste simples realizado:


<details>
  <summary><strong>Exemplo de teste</strong></summary>
  
  [../src/test/java/br/gov/sp/fatec/epidemiweb/IncidenceTests.java](https://github.com/arthurbarbero/EpidemiWEB/blob/main/src/test/java/br/gov/sp/fatec/epidemiweb/IncidenceTests.java)
  
  ```java
      import static org.junit.jupiter.api.Assertions.*;

      import java.time.LocalDate;
      import java.util.List;

      import javax.transaction.Transactional;

      import org.junit.jupiter.api.Test;
      import org.springframework.beans.factory.annotation.Autowired;
      import org.springframework.boot.test.context.SpringBootTest;
      import org.springframework.test.annotation.Rollback;

      import br.gov.sp.fatec.epidemiweb.Entities.Disease;
      import br.gov.sp.fatec.epidemiweb.Entities.Incidence;
      import br.gov.sp.fatec.epidemiweb.Entities.Users;
      import br.gov.sp.fatec.epidemiweb.Repositories.DiseaseRepository;
      import br.gov.sp.fatec.epidemiweb.Repositories.UserRepository;
      import br.gov.sp.fatec.epidemiweb.Services.IncidenceService;

      @SpringBootTest
      @Transactional
      @Rollback
      class IncidenceTests {

        @Autowired
        private IncidenceService incidenceBO;
          @Autowired
        private DiseaseRepository diseaseRepo;
          @Autowired
        private UserRepository userRepo;

        @Test
          void saveIncidenceTest() {
              Users tempUser = userRepo.findById(1).get();
              Disease tempDisease = diseaseRepo.findById(1).get();
              Incidence newIncidence = incidenceBO.saveIncidence(LocalDate.now(), tempDisease, tempUser);
          assertNotNull(newIncidence.getId());
        }

          @Test
          void getAllIncidenceTest() {
              List<Incidence> allIncidence = incidenceBO.getAllIncidences();
              assertNotNull(allIncidence.get(0));
          }

          @Test
          void getAllIncidenceByUserTest() {
              Users tempUser = userRepo.findById(1).get();
              List<Incidence> allIncidence = incidenceBO.getAllIncidenceByUser(tempUser);
              assertNotNull(allIncidence.get(0));
          }

          @Test
          void getAllIncidenceByDiseaseTest() {
              Disease tempDisease = diseaseRepo.findById(1).get();
              List<Incidence> allIncidence = incidenceBO.getAllIncidenceByDisease(tempDisease);
              assertNotNull(allIncidence.get(0));
          }

          @Test
          void getAllIncidenceByUserAndDiseaseTest() {
              Disease tempDisease = diseaseRepo.findById(1).get();
              Users tempUser = userRepo.findById(1).get();
              List<Incidence> allIncidence = incidenceBO.getAllIncidenceByUserAndDisease(tempUser, tempDisease);
              assertNotNull(allIncidence.get(0));
          }

      }
  ```
 </details>
 
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

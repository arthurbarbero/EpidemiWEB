## Terceira Entrega

## Objetivos da entrega:

- Inclusão de autenticação via JWT para as rotas.

### Configuração JWT em Java+SpringBoot:

Nossas Rotas devem ser protegidas para que apenas recebam acesso de pessoas com permissão, e para isso cada requisição após o Login do usuário, receberá um Token no formato JWT que informará os dados criptografados de login para a API.

<details>
<summary><strong>Clique para ver o Controlador de Login</strong></summary>

[.../Controller/LoginController](https://github.com/arthurbarbero/EpidemiWEB/blob/main/src/main/java/br/gov/sp/fatec/epidemiweb/Controller/LoginController.java)

```java
@RestController
@RequestMapping(value = "/login")
@CrossOrigin
public class LoginController {
    
    @Autowired
    private AuthenticationManager authManager;

    @PostMapping(value = "/")
    public LoginModel authenticate(@RequestBody LoginModel login) throws JsonProcessingException {
        Authentication auth = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
        auth = authManager.authenticate(auth);
        login.setPassword(null);
        login.setTokenKey(JwtUtils.generateToken(auth));
        login.setGroups(auth.getAuthorities().stream().map(x -> x.getAuthority().toString()).collect(Collectors.toList()).toArray(new String[auth.getAuthorities().size()]));
        return login;
    }
}
```
</details>


Após o usuário logar, o método estático `UsernamePasswordAuthenticationToken` recebe o email e senha para criar um token acessível nos padrões do JWT com tempo de expiração de 1 hora, antes de devolver a autenticação para o Front-end, apagamos a senha da resposta e incluímos quais os grupos de acesso e o próprio access Token para que nas próximas requisições, a API consiga logar o usuário a partir deste token Criptografado.

<details>
<summary><strong>Clique para ver o Método de autenticação</strong></summary>

[../Security/JWTUtils.java](https://github.com/arthurbarbero/EpidemiWEB/blob/main/src/main/java/br/gov/sp/fatec/epidemiweb/Security/JwtUtils.java)

```java
public static String generateToken(Authentication usuario) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		LoginModel usuarioSemSenha = new LoginModel();
		usuarioSemSenha.setEmail(usuario.getName());
		if (!usuario.getAuthorities().isEmpty()) {
				usuarioSemSenha.setGroups(usuario.getAuthorities().stream().map(x -> x.getAuthority().toString()).collect(Collectors.toList()).toArray(new String[usuario.getAuthorities().size()]));
		}
		String usuarioJson = mapper.writeValueAsString(usuarioSemSenha);
		Date agora = new Date();
		Long hora = 1000L * 60L * 60L; // Uma hora
		return Jwts.builder().claim("userDetails", usuarioJson).setIssuer("br.gov.sp.fatec")
						.setSubject(usuario.getName()).setExpiration(new Date(agora.getTime() + hora))
						.signWith(SignatureAlgorithm.HS512, KEY).compact();
}
```

</details>

Assim toda vez que uma requisição tentar acessar um método com a anotação `@PreAuthorize`, o midleware criado com o método `JwtUtils.parseToken` será ativado, buscando as informações do token no cabeçalho da requisição e incluindo no contexto da aplicação as informações do usuário e permissões de acesso.

<details>
<summary><strong>Clique para ver o Método de autenticação</strong></summary>

[../Security/JWTUtils.java](https://github.com/arthurbarbero/EpidemiWEB/blob/main/src/main/java/br/gov/sp/fatec/epidemiweb/Security/JwtUtils.java)

```java
public static Authentication parseToken(String token) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String credentialsJson = Jwts.parser()
				.setSigningKey(KEY)
				.parseClaimsJws(token)
				.getBody()
				.get("userDetails", String.class);
		
		LoginModel usuario = mapper.readValue(credentialsJson, LoginModel.class);

		UserDetails userDetails = User.builder()
						.username(usuario.getEmail())
						.password("secret")
						.authorities(usuario.getGroups())
				.build();
		
		return new UsernamePasswordAuthenticationToken(
								usuario.getEmail(), 
								usuario.getPassword(),
								userDetails.getAuthorities()
						);
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


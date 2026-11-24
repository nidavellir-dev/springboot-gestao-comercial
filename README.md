# Sistema de Gestão Comercial

**Sistema backend para gestão de produtos, categorias, compras, vendas e controlo de stock**, desenvolvido com Java e Spring Boot.

Este projecto consiste no desenvolvimento de uma API REST para gerir operações comerciais, aplicando princípios de arquitectura em camadas, persistência de dados, validação, tratamento de erros e gestão de transacções.

O sistema está a ser desenvolvido progressivamente, com foco na aplicação prática dos conceitos de engenharia de software e desenvolvimento backend.

## Objectivos do Projecto

- Desenvolver uma API REST utilizando Spring Boot.
- Implementar uma arquitectura organizada em camadas.
- Gerir produtos e respectivas categorias.
- Registar compras e integrar automaticamente as entradas de stock.
- Registar vendas e integrar as saídas de stock.
- Garantir a integridade dos dados através de transacções e restrições na base de dados.
- Implementar migrações versionadas da base de dados com Flyway.
- Aplicar boas práticas de desenvolvimento backend.

## Tecnologias Utilizadas

| Tecnologia | Versão / Utilização |
|---|---|
| Java | 21 |
| Spring Boot | 4.1.1 |
| Spring Web MVC | Desenvolvimento da API REST |
| Spring Data JPA | Persistência e acesso aos dados |
| Hibernate | Mapeamento objecto-relacional (ORM) |
| MySQL | 8.0.44 — Sistema de gestão de base de dados |
| Flyway | Migrações e versionamento da base de dados |
| Maven | Gestão de dependências e construção do projecto |
| Jakarta Validation | Validação dos dados |
| Git | Controlo de versões |
| GitHub | Hospedagem do código-fonte |
| Postman | Testes dos endpoints da API |
| IntelliJ IDEA | Ambiente de desenvolvimento |

## Arquitectura

A aplicação utiliza uma arquitectura em camadas, separando as responsabilidades de cada componente:

- **Controller:** recebe e responde às requisições HTTP.
- **Service:** implementa as regras de negócio.
- **Repository:** executa operações de persistência e consulta.
- **Entity:** representa as entidades persistidas na base de dados.
- **DTO:** define os dados recebidos e devolvidos pela API.
- **Exception:** centraliza o tratamento de erros e excepções.

O objectivo desta organização é promover separação de responsabilidades, manutenção e evolução do sistema.

## Funcionalidades e Módulos

### 1. Gestão de Produtos

- Registo de produtos.
- Consulta de produtos por identificador.
- Listagem de produtos.
- Actualização e eliminação de produtos.
- Associação de produtos às respectivas categorias.
- Paginação e ordenação de resultados.
- Validação dos dados recebidos pela API.

### 2. Gestão de Categorias

- Criação de categorias.
- Consulta de categorias.
- Associação de produtos às categorias.
- Relacionamento entre categorias e produtos através do JPA.

### 3. Gestão de Compras

- Registo de compras com vários produtos.
- Registo das quantidades e preços unitários.
- Cálculo automático dos subtotais.
- Cálculo do valor total da compra.
- Associação dos itens à respectiva compra.
- Integração das compras com o controlo de stock.

### 4. Gestão de Vendas

- Registo de vendas.
- Associação de vários produtos a uma venda.
- Registo das quantidades vendidas.
- Cálculo dos subtotais dos itens.
- Cálculo do valor total da venda.
- Consulta de vendas registadas.

### 5. Controlo de Stock

- Criação de registos de stock associados aos produtos.
- Consulta individual e listagem de stocks.
- Definição de quantidades mínimas.
- Registo de entradas de stock.
- Registo de saídas de stock.
- Histórico de movimentos de stock.
- Identificação do tipo de movimento: ENTRADA, SAIDA e AJUSTE.
- Validação da disponibilidade antes de efectuar saídas.
- Integração das entradas de stock com o módulo de compras.

### 6. Validação e Tratamento de Erros

- Validação dos dados recebidos pela API.
- Excepções personalizadas.
- Tratamento global de erros com `@RestControllerAdvice`.
- Respostas HTTP adequadas às situações de sucesso e erro.

### 7. Gestão da Base de Dados

- Persistência de dados com Spring Data JPA e Hibernate.
- Relacionamentos entre entidades.
- Gestão de transacções com `@Transactional`.
- Migrações versionadas com Flyway.
- Restrições de integridade na base de dados.

## Estrutura do Projecto

O projecto segue uma organização baseada na arquitectura em camadas do Spring Boot.

```text
springboot-aula01/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/davidandredaniel/springbootaula01/
│   │   │       │
│   │   │       ├── controller/
│   │   │       │   ├── CompraController.java
│   │   │       │   ├── HelloController.java
│   │   │       │   ├── ProdutoController.java
│   │   │       │   ├── StockController.java
│   │   │       │   ├── VendaController.java
│   │   │       │   └── categoriaController.java
│   │   │       │
│   │   │       ├── dto/
│   │   │       │
│   │   │       ├── entity/
│   │   │       │
│   │   │       ├── exception/
│   │   │       │
│   │   │       ├── repository/
│   │   │       │
│   │   │       ├── service/
│   │   │       │
│   │   │       └── SpringbootAula01Application.java
│   │   │
│   │   └── resources/
│   │       ├── db/
│   │       │   └── migration/
│   │       │       ├── V2__adicionar_estado_produto.sql
│   │       │       ├── V3__criar_tabela_estoque.sql
│   │       │       ├── V4__criar_tabela_movimento_estoque.sql
│   │       │       ├── V5__inicializar_stock_produtos_existentes.sql
│   │       │       └── V6__criar_tabelas_compras.sql
│   │       │
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│           └── com/davidandredaniel/springbootaula01/
│               └── SpringbootAula01ApplicationTests.java
│
├── .gitignore
├── .gitattributes
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

### Responsabilidade dos pacotes

| Pacote | Responsabilidade |
|---|---|
| `controller` | Exposição dos endpoints e comunicação HTTP. |
| `dto` | Objectos utilizados para transportar dados entre cliente e API. |
| `entity` | Representação das entidades persistidas na base de dados. |
| `exception` | Excepções personalizadas e tratamento global de erros. |
| `repository` | Acesso e operações sobre a base de dados. |
| `service` | Implementação das regras e operações de negócio. |
| `resources/db/migration` | Scripts SQL de evolução da estrutura da base de dados. |
| `resources` | Configurações e outros recursos da aplicação. |
| `test` | Código destinado aos testes automatizados. |

### Organização arquitectural

O fluxo principal de uma requisição segue esta sequência:

```text
Cliente HTTP (Postman)
        |
        v
   Controller
        |
        v
     Service
        |
        v
    Repository
        |
        v
      MySQL
```

Os DTOs são utilizados na comunicação de dados entre o cliente e a aplicação, enquanto as entidades representam os dados persistidos.

## Modelo de Dados e Relacionamentos

A aplicação utiliza MySQL como sistema de gestão de base de dados e Spring Data JPA com Hibernate para o mapeamento objecto-relacional.

O modelo de dados está organizado em entidades relacionadas, permitindo gerir as operações comerciais e controlar os movimentos de stock.

### Principais Entidades

| Entidade | Responsabilidade |
|---|---|
| `Produto` | Armazenar os dados dos produtos comercializados. |
| `Categoria` | Organizar os produtos por categorias. |
| `Stock` | Controlar as quantidades disponíveis de cada produto. |
| `MovimentoEstoque` | Registar as entradas e saídas de stock. |
| `Compra` | Representar uma operação de aquisição de produtos. |
| `ItemCompra` | Representar cada produto incluído numa compra. |
| `Venda` | Representar uma operação de venda. |
| `ItemVenda` | Representar cada produto incluído numa venda. |

### Relacionamentos entre Entidades

**1. Categoria e Produto**

- Uma categoria pode estar associada a vários produtos.
- Cada produto está associado a uma categoria através de `@ManyToOne`.

**2. Produto e Stock**

- Cada registo de stock está associado a um produto.
- O relacionamento é de um para um (`@OneToOne`).

**3. Stock e MovimentoEstoque**

- Um registo de stock pode possuir vários movimentos.
- Cada movimento pertence a um registo de stock.

**4. Compra e ItemCompra**

- Uma compra pode conter vários itens.
- Cada item pertence a uma compra.

**5. Venda e ItemVenda**

- Uma venda pode conter vários itens.
- Cada item pertence a uma venda.

### Integridade dos Dados

O sistema utiliza mecanismos de integridade para proteger os dados, incluindo:

- Chaves primárias e estrangeiras.
- Restrições de unicidade.
- Restrições para impedir quantidades negativas de stock.
- Validações dos dados recebidos pela API.
- Transacções para manter a consistência das operações comerciais.

### Gestão da Evolução da Base de Dados

O Flyway é utilizado para controlar a evolução do esquema da base de dados através de migrations SQL versionadas.

As migrations actualmente presentes no projecto são:

- `V2__adicionar_estado_produto.sql`
- `V3__criar_tabela_estoque.sql`
- `V4__criar_tabela_movimento_estoque.sql`
- `V5__inicializar_stock_produtos_existentes.sql`
- `V6__criar_tabelas_compras.sql`

Cada migration representa uma alteração controlada na estrutura da base de dados.

## Instalação e Configuração

### Pré-requisitos

Antes de executar a aplicação, é necessário ter instalado:

- Java Development Kit (JDK) 21.
- MySQL Server 8.0 ou superior.
- Git.
- IntelliJ IDEA ou outro ambiente de desenvolvimento Java.

O projecto utiliza Maven através do Maven Wrapper, pelo que não é necessário instalar o Maven separadamente.

### 1. Clonar o Repositório

```bash
git clone https://github.com/nidavellir-dev/springboot-gestao-comercial.git
```

Entrar na pasta do projecto:

```bash
cd springboot-gestao-comercial
```

### 2. Configurar a Base de Dados

Criar a base de dados MySQL utilizada pelo projecto:

```sql
CREATE DATABASE springboot_aula01;
```

### 3. Configurar as Variáveis de Ambiente

A aplicação utiliza variáveis de ambiente para obter as credenciais e os dados de ligação à base de dados.

Configurar as variáveis exigidas pelo ficheiro `application.properties`, de acordo com os nomes definidos na configuração local.

Não guardar senhas directamente no código-fonte nem publicar credenciais no GitHub.

### 4. Executar as Migrações

O Flyway gere automaticamente as migrations configuradas no projecto durante a inicialização da aplicação.

As alterações necessárias ao esquema da base de dados são aplicadas através dos scripts SQL versionados.

### 5. Executar a Aplicação

No Windows, através do Maven Wrapper:

```powershell
.\mvnw.cmd spring-boot:run
```

Também é possível executar a classe principal:

```text
SpringbootAula01Application.java
```

### 6. Aceder à API

Depois de iniciar correctamente, a aplicação fica disponível localmente na porta 8080:

```text
http://localhost:8080
```

As requisições aos endpoints podem ser realizadas através do Postman.

## 7. Endpoints da API

A aplicação disponibiliza uma API REST organizada por módulos. Os endpoints permitem gerir produtos, categorias, compras, vendas e operações de stock.

**URL base:** `http://localhost:8080`

### 7.1. Gestão de produtos

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/produtos` | Listar produtos |
| GET | `/produtos/{id}` | Consultar produto por ID |
| GET | `/produtos/categoria/{categoriaId}` | Listar produtos de uma categoria |
| GET | `/produtos/preco-superior` | Consultar produtos acima de determinado preço |
| GET | `/produtos/pequisar` | Pesquisar produtos |
| GET | `/produtos/paginados` | Listar produtos com paginação e ordenação |
| GET | `/produtos/jpql/preco-superior` | Consultar produtos através de JPQL |
| GET | `/produtos/jpql/categoria/{categoriaId}` | Consultar produtos por categoria através de JPQL |
| POST | `/produtos` | Registar produto |
| PUT | `/produtos/{id}` | Actualizar produto |
| DELETE | `/produtos/{id}` | Eliminar produto |

### 7.2. Gestão de categorias

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/categorias` | Listar categorias |
| GET | `/categorias/{id}` | Consultar categoria por ID |
| POST | `/categorias` | Registar categoria |
| PUT | `/categorias/{id}` | Actualizar categoria |
| DELETE | `/categorias/{id}` | Eliminar categoria |

### 7.3. Gestão de compras

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/compras` | Registar uma compra |
| GET | `/compras` | Listar compras |
| GET | `/compras/{id}` | Consultar compra por ID |

### 7.4. Gestão de vendas

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/vendas` | Registar uma venda |
| GET | `/vendas` | Listar vendas |
| GET | `/vendas/{id}` | Consultar venda por ID |

### 7.5. Controlo de stock

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/estoque` | Registar stock para um produto |
| GET | `/estoque` | Consultar o stock dos produtos |
| GET | `/estoque/{produtoId}` | Consultar stock de um produto |
| PATCH | `/estoque/{produtoId}/quantidade-minima` | Actualizar quantidade mínima |
| POST | `/estoque/{produtoId}/entradas` | Registar entrada de stock |
| POST | `/estoque/{produtoId}/saidas` | Registar saída de stock |
| GET | `/estoque/{produtoId}/movimentos` | Consultar movimentos de stock de um produto |

### 7.6. Endpoints de demonstração

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/hello` | Endpoint de demonstração |
| GET | `/curso` | Endpoint de demonstração |
| GET | `/aluno` | Endpoint de demonstração |

**Nota:** Os endpoints acima representam as rotas identificadas nos controllers do projecto. Os parâmetros específicos de cada operação devem ser consultados nas respectivas assinaturas dos métodos dos controllers.

## 8. Histórico de desenvolvimento

O Sistema de Gestão Comercial foi desenvolvido de forma incremental, aplicando conceitos de programação Java, Spring Boot, arquitectura em camadas, persistência de dados e desenvolvimento de APIs REST.

### 8.1. Evolução do projecto

- Configuração inicial do projecto Spring Boot com Maven e Java 21.
- Implementação da arquitectura em camadas: Controller, Service e Repository.
- Desenvolvimento da gestão de produtos e categorias.
- Integração com MySQL através de Spring Data JPA e Hibernate.
- Implementação de DTOs para separar os dados de entrada e saída da API.
- Aplicação de validações e tratamento global de excepções.
- Implementação de paginação, ordenação e consultas personalizadas.
- Desenvolvimento dos módulos de compras e vendas.
- Implementação do controlo de stock e do histórico de movimentos.
- Integração das compras com as entradas de stock.
- Introdução do Flyway para controlo das alterações ao esquema da base de dados.
- Configuração do Git e publicação do projecto no GitHub.
- Elaboração da documentação técnica através deste README.

### 8.2. Estado actual dos módulos

| Módulo | Estado |
|---|---|
| Gestão de produtos | Implementado |
| Gestão de categorias | Implementado |
| DTOs e validações | Implementado |
| Paginação e consultas | Implementado |
| Gestão de compras | Implementado |
| Integração compras-stock | Implementado |
| Gestão de vendas | Implementado |
| Controlo de stock | Implementado |
| Histórico de movimentos de stock | Implementado |
| Migrações com Flyway | Implementado |
| Documentação do projecto | Em desenvolvimento |

### 8.3. Próximos passos

A evolução do projecto continuará de forma incremental, com foco em:

- Reforçar os testes dos fluxos de compras, vendas e stock.
- Verificar e consolidar a integração entre vendas e saídas de stock.
- Melhorar a documentação dos contratos da API.
- Continuar a aplicação de boas práticas de desenvolvimento com Spring Boot.
- Evoluir a arquitectura e a qualidade do código à medida que o sistema cresce.

### 8.4. Controlo de versões

O projecto utiliza Git para controlo de versões e GitHub para alojamento do código-fonte.

As alterações são organizadas em commits, permitindo acompanhar a evolução do sistema e identificar as funcionalidades introduzidas em cada etapa.

**Repositório:** https://github.com/nidavellir-dev/springboot-gestao-comercial

## 9. Utilização e colaboração

### 9.1. Testar a API

Depois de iniciar a aplicação, os endpoints podem ser testados através de ferramentas como:

- **Postman Desktop:** para enviar requisições HTTP e analisar as respostas da API.
- **IntelliJ IDEA:** para desenvolver, executar e depurar a aplicação.

A API utiliza os principais métodos HTTP:

- `GET` — consultar informações.
- `POST` — criar novos registos.
- `PUT` — actualizar registos.
- `PATCH` — actualizar parcialmente um registo.
- `DELETE` — eliminar registos.

### 9.2. Desenvolvimento

O projecto está em desenvolvimento contínuo e serve também como ambiente de aprendizagem e aplicação prática dos conceitos de Java e Spring Boot.

As alterações devem respeitar a arquitectura existente, as regras de negócio, as validações e a integridade dos dados.

### 9.3. Contribuições

Contribuições, sugestões e identificações de problemas são bem-vindas.

Para contribuir:

1. Faça um fork do repositório.
2. Crie uma branch para a alteração.
3. Implemente e teste as modificações.
4. Registe as alterações através de commits descritivos.
5. Abra um Pull Request para análise.

### 9.4. Segurança

- Não publique credenciais da base de dados.
- Não inclua ficheiros `.env` ou configurações locais com informações sensíveis.
- Utilize variáveis de ambiente para configurar dados de acesso.
- Não envie credenciais ou informações privadas para o GitHub.

### 9.5. Licença

O projecto ainda não declara uma licença de código aberto.

A utilização, modificação e redistribuição do código devem respeitar os direitos do respectivo autor, até que seja adicionada uma licença formal.
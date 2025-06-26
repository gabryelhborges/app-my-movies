# MyMovies 🎬

Uma aplicação Android moderna para gerenciamento e visualização de filmes, desenvolvida em Java com arquitetura nativa Android e banco de dados MongoDB.

## 📱 Sobre o Projeto

MyMovies é um aplicativo que permite aos usuários visualizar, organizar e gerenciar uma coleção de filmes. A aplicação oferece uma interface intuitiva para navegar através de uma lista de filmes com informações detalhadas como título, ano de lançamento e imagens, utilizando uma API REST conectada ao MongoDB.

## 🏗️ Arquitetura do Projeto

O projeto está estruturado em duas partes principais:

### 📱 Aplicação Android (`mymovies/`)
- **Linguagem**: Java
- **Build System**: Gradle with Kotlin DSL
- **IDE**: Android Studio (configuração IntelliJ)
- **Target SDK**: Configurado via `gradle/libs.versions.toml`

### 🔧 API Backend (`movies_api/`)
- **Framework**: Spring Boot 3.3.5 (Maven)
- **Linguagem**: Java 21
- **Build System**: Maven
- **Banco de Dados**: MongoDB
- **Dependências Principais**:
  - `spring-boot-starter-data-mongodb` - Integração com MongoDB
  - `spring-boot-starter-web` - REST API
  - `gson` - Serialização JSON

## ✨ Funcionalidades Implementadas

### 🎯 Listagem de Filmes
A aplicação utiliza um [`MovieAdapter`](mymovies/app/src/main/java/com/example/mymovies/MovieAdapter.java) customizado que estende `ArrayAdapter<Movie>` para exibir a lista de filmes de forma otimizada:

- **Carregamento de Imagens**: Integração com biblioteca de carregamento de imagens (Glide/Picasso)
- **Fallback de Imagens**: Sistema de imagem padrão (`R.drawable.imgpadrao`) quando a URL é inválida
- **ViewHolder Pattern**: Otimização de performance na renderização da lista
- **Binding de Dados**: Exibição automática de título e ano do filme

### 🔍 Sistema de Busca Avançada
O [`MoviesRestController`](movies_api/src/main/java/fipp/unoeste/movies_api/restcontrollers/MoviesRestController.java) implementa busca inteligente no MongoDB:

- **Filtros Múltiplos**: Busca por título, elenco e sinopse
- **Case Insensitive**: Pesquisa que ignora maiúsculas/minúsculas
- **Regex Pattern**: Utilização de expressões regulares para busca flexível
- **Performance Otimizada**: Uso de índices MongoDB para consultas rápidas

### 📊 Banco de Dados MongoDB
- **Conexão Local**: `mongodb://localhost:27017`
- **Database**: `movies_db`
- **Collection**: `movies`
- **Migração de Dados**: Importação do [`movies2020.json`](movies2020.json) para MongoDB
- **Consultas Avançadas**: Uso de filtros BSON para busca eficiente

## 🔧 Implementação Técnica

### API REST Endpoints
O [`MoviesRestController`](movies_api/src/main/java/fipp/unoeste/movies_api/restcontrollers/MoviesRestController.java) expõe os seguintes endpoints:

```java
@GetMapping("/api/testar-conexao")   // Teste de conectividade
@GetMapping("/api/find-movies")      // Busca de filmes com filtro
```

### Integração MongoDB
```java
// Conexão direta com MongoDB
MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
MongoDatabase database = mongoClient.getDatabase("movies_db");
MongoCollection<Document> collection = database.getCollection("movies");

// Busca com múltiplos filtros
Bson filtro = or(Arrays.asList(
    eq("title", Pattern.compile(filter+"(?i)")),
    eq("cast", Pattern.compile(filter+"(?i)")),
    eq("extract", Pattern.compile(filter+"(?i)"))
));
```

### Adapter Pattern
O [`MovieAdapter`](mymovies/app/src/main/java/com/example/mymovies/MovieAdapter.java) implementa o padrão Adapter para conectar os dados do modelo `Movie` com as views da interface:

```java
@Override
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    // Reutilização de views para performance
    // Binding de dados (título, ano, imagem)
    // Tratamento de URLs de imagem inválidas
}
```

### Gerenciamento de Imagens
- **Carregamento Assíncrono**: Imagens carregadas de forma não bloqueante
- **Cache**: Sistema de cache para otimizar o carregamento
- **Tratamento de Erros**: Fallback automático para imagem padrão

### Build Configuration
- **Gradle Kotlin DSL**: Configuração moderna de build
- **Version Catalog**: Gerenciamento centralizado de dependências via `libs.versions.toml`
- **ProGuard**: Ofuscação e otimização configurada
- **Maven POM**: Gerenciamento de dependências Spring Boot no backend

## 🚀 Como Executar

### Pré-requisitos
- Android Studio Arctic Fox ou superior
- JDK 21
- SDK Android 21+ (mínimo)
- **MongoDB Community Server** instalado e executando na porta 27017

### Configuração do Banco de Dados
1. Instale o MongoDB Community Server
2. Inicie o serviço MongoDB
3. Importe os dados do arquivo [`movies2020.json`](movies2020.json):
```bash
mongoimport --db movies_db --collection movies --file movies2020.json --jsonArray
```

### Executando a API
```bash
cd movies_api
./mvnw spring-boot:run
```

### Executando o App Android
1. Abra o projeto `mymovies/` no Android Studio
2. Sincronize as dependências Gradle
3. Execute o projeto no emulador ou dispositivo físico

## 📂 Estrutura de Arquivos

```
├── movies2020.json          # Base de dados dos filmes (para importação)
├── movies_api/              # API REST Spring Boot
│   ├── src/main/java/       # Código fonte da API
│   │   └── fipp/unoeste/movies_api/
│   │       ├── restcontrollers/  # Controllers REST
│   │       └── entities/         # Modelos de dados
│   └── pom.xml             # Configurações Maven e dependências
└── mymovies/               # Aplicação Android
    ├── app/src/main/java/  # Código fonte Android
    ├── build.gradle.kts    # Configuração de build
    └── gradle/             # Dependências e wrapper
```

## 🎨 Interface do Usuário

A interface foi desenvolvida seguindo as diretrizes do Material Design, oferecendo:
- **Lista Responsiva**: Adaptação automática a diferentes tamanhos de tela
- **Carregamento Suave**: Animações e transições fluidas
- **Feedback Visual**: Indicadores de carregamento e estados de erro
- **Navegação Intuitiva**: Fluxo de usuário simplificado

## 🔄 Integração API-App-Database

A comunicação completa do sistema funciona da seguinte forma:
- **Android App** ↔ **Spring Boot API** ↔ **MongoDB**
- **Requisições HTTP**: GET requests para buscar dados dos filmes
- **Parsing JSON**: Conversão automática de JSON para objetos Java via Gson
- **Consultas MongoDB**: Filtros BSON para busca eficiente no banco
- **Tratamento de Erros**: Gerenciamento de falhas de rede, timeouts e conexão com BD
- **Performance**: Uso de índices MongoDB e cache de aplicação

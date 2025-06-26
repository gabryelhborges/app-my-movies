# MyMovies 🎬

Uma aplicação Android moderna para gerenciamento e visualização de filmes, desenvolvida em Java com arquitetura nativa Android.

## 📱 Sobre o Projeto

MyMovies é um aplicativo que permite aos usuários visualizar, organizar e gerenciar uma coleção de filmes. A aplicação oferece uma interface intuitiva para navegar através de uma lista de filmes com informações detalhadas como título, ano de lançamento e imagens.

## 🏗️ Arquitetura do Projeto

O projeto está estruturado em duas partes principais:

### 📱 Aplicação Android (`mymovies/`)
- **Linguagem**: Java
- **Build System**: Gradle with Kotlin DSL
- **IDE**: Android Studio (configuração IntelliJ)
- **Target SDK**: Configurado via `gradle/libs.versions.toml`

### 🔧 API Backend (`movies_api/`)
- **Framework**: Spring Boot (Maven)
- **Linguagem**: Java
- **Build System**: Maven
- **Dados**: Integração com arquivo JSON de filmes

## ✨ Funcionalidades Implementadas

### 🎯 Listagem de Filmes
A aplicação utiliza um [`MovieAdapter`](mymovies/app/src/main/java/com/example/mymovies/MovieAdapter.java) customizado que estende `ArrayAdapter<Movie>` para exibir a lista de filmes de forma otimizada:

- **Carregamento de Imagens**: Integração com biblioteca de carregamento de imagens (Glide/Picasso)
- **Fallback de Imagens**: Sistema de imagem padrão (`R.drawable.imgpadrao`) quando a URL é inválida
- **ViewHolder Pattern**: Otimização de performance na renderização da lista
- **Binding de Dados**: Exibição automática de título e ano do filme

### 📊 Fonte de Dados
- Utilização do arquivo [`movies2020.json`](movies2020.json) como base de dados
- API REST desenvolvida em Spring Boot para servir os dados
- Comunicação entre app Android e API via HTTP requests

## 🔧 Implementação Técnica

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

## 🚀 Como Executar

### Pré-requisitos
- Android Studio Arctic Fox ou superior
- JDK 11+
- SDK Android 21+ (mínimo)

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
├── movies2020.json          # Base de dados dos filmes
├── movies_api/              # API REST Spring Boot
│   ├── src/main/java/       # Código fonte da API
│   └── pom.xml             # Configurações Maven
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

## 🔄 Integração API-App

A comunicação entre o aplicativo Android e a API Spring Boot é realizada através de:
- **Requisições HTTP**: GET requests para buscar dados dos filmes
- **Parsing JSON**: Conversão automática de JSON para objetos Java
- **Tratamento de Erros**: Gerenciamento de falhas de rede e timeouts
- **Cache Local**: Armazenamento temporário para melhor performance offline

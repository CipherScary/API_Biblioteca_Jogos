# Biblioteca de Jogos 

Feito por Sebastião Marques Silva Junior e Pedro Lucas Ponciano Silva

Projeto CRUD desenvolvido em Java utilizando:

- Java Servlet
- JSP
- Maven
- MySQL
- MVC
- DAO
- Service

## Funcionalidades

- Cadastro de jogos
- Listagem
- Atualização
- Exclusão

## Tecnologias

- Java
- JSP
- Servlet
- MySQL
- HTML
- CSS

## Como executar

1. Criar banco MySQL
2. Configurar ConnectionFactory
3. Rodar Maven
4. Deploy no Tomcat

## API REST com JWT

Login:

```http
POST /BibliotecaJogos/api/login
Content-Type: application/json

{
  "email": "admin@biblioteca.com",
  "senha": "123456"
}
```

Rotas protegidas:

```http
Authorization: Bearer SEU_TOKEN
```

Endpoints:

- `GET /BibliotecaJogos/api/jogos`
- `GET /BibliotecaJogos/api/jogos/{id}`
- `POST /BibliotecaJogos/api/jogos`
- `PUT /BibliotecaJogos/api/jogos/{id}`
- `DELETE /BibliotecaJogos/api/jogos/{id}`

JSON para criar ou atualizar:

```json
{
  "nome": "God of War",
  "genero": "Acao",
  "plataforma": "PlayStation",
  "preco": 199.90
}
```

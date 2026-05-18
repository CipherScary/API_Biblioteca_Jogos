<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="br.com.biblioteca.model.Jogo" %>

<html>
<head>
    <title>Biblioteca de Jogos</title>
    <link rel="stylesheet" href="css/style.css">
</head>

<body>

<h1>Biblioteca de Jogos</h1>

<a href="cadastro-jogo.jsp">Cadastrar Novo Jogo</a>

<br><br>

<table border="1">

<tr>
    <th>ID</th>
    <th>Nome</th>
    <th>Gênero</th>
    <th>Plataforma</th>
    <th>Preço</th>
    <th>Ações</th>
</tr>

<%
List<Jogo> lista = (List<Jogo>) request.getAttribute("lista");

for(Jogo jogo : lista){
%>

<tr>

<td><%= jogo.getId() %></td>
<td><%= jogo.getNome() %></td>
<td><%= jogo.getGenero() %></td>
<td><%= jogo.getPlataforma() %></td>
<td>R$ <%= jogo.getPreco() %></td>

<td>
<a href="jogos?acao=editar&id=<%= jogo.getId() %>">Editar</a>

<a href="jogos?acao=deletar&id=<%= jogo.getId() %>">
Excluir
</a>
</td>

</tr>

<%
}
%>

</table>

</body>
</html>
<%@ page import="br.com.biblioteca.model.Jogo" %>

<%
Jogo jogo = (Jogo) request.getAttribute("jogo");
%>

<html>
<head>
    <title>Editar Jogo</title>
    <link rel="stylesheet" href="css/style.css">
</head>

<body>

<h1>Editar Jogo</h1>

<form action="jogos" method="post">

<input type="hidden" name="id" value="<%= jogo.getId() %>">

<input type="text" name="nome" value="<%= jogo.getNome() %>">
<br><br>

<input type="text" name="genero" value="<%= jogo.getGenero() %>">
<br><br>

<input type="text" name="plataforma" value="<%= jogo.getPlataforma() %>">
<br><br>

<input type="number" step="0.01" name="preco" value="<%= jogo.getPreco() %>">
<br><br>

<button type="submit">Atualizar</button>

</form>

</body>
</html>
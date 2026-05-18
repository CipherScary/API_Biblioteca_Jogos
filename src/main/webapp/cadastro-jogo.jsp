<html>
<head>
    <title>Cadastrar Jogo</title>
    <link rel="stylesheet" href="css/style.css">
</head>

<body>

<h1>Cadastrar Jogo</h1>

<form action="jogos" method="post">

<input type="text" name="nome" placeholder="Nome" required>
<br><br>

<input type="text" name="genero" placeholder="Gênero" required>
<br><br>

<input type="text" name="plataforma" placeholder="Plataforma" required>
<br><br>

<input type="number" step="0.01" name="preco" placeholder="Preço" required>
<br><br>

<button type="submit">Salvar</button>

</form>

</body>
</html>
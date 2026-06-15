package br.com.biblioteca.service;

import br.com.biblioteca.dao.JogoDAO;
import br.com.biblioteca.model.Jogo;

import java.util.List;

public class JogoService {

    private final JogoDAO dao = new JogoDAO();

    public Jogo salvar(Jogo jogo) {
        validar(jogo);
        int id = dao.cadastrar(jogo);
        jogo.setId(id);
        return jogo;
    }

    public List<Jogo> listar() {
        return dao.listar();
    }

    public boolean atualizar(Jogo jogo) {
        if (jogo.getId() <= 0) {
            throw new IllegalArgumentException("Id do jogo e obrigatorio");
        }

        validar(jogo);
        return dao.atualizar(jogo);
    }

    public boolean deletar(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id do jogo e obrigatorio");
        }

        return dao.deletar(id);
    }

    public Jogo buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id do jogo e obrigatorio");
        }

        return dao.buscarPorId(id);
    }

    private void validar(Jogo jogo) {
        if (jogo == null) {
            throw new IllegalArgumentException("Jogo e obrigatorio");
        }

        if (isBlank(jogo.getNome())) {
            throw new IllegalArgumentException("Nome e obrigatorio");
        }

        if (isBlank(jogo.getGenero())) {
            throw new IllegalArgumentException("Genero e obrigatorio");
        }

        if (isBlank(jogo.getPlataforma())) {
            throw new IllegalArgumentException("Plataforma e obrigatoria");
        }

        if (jogo.getPreco() < 0) {
            throw new IllegalArgumentException("Preco nao pode ser negativo");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}

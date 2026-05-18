package br.com.biblioteca.service;

import br.com.biblioteca.dao.JogoDAO;
import br.com.biblioteca.model.Jogo;

import java.util.List;

public class JogoService {

    private JogoDAO dao = new JogoDAO();

    public void salvar(Jogo jogo) {
        dao.cadastrar(jogo);
    }

    public List<Jogo> listar() {
        return dao.listar();
    }

    public void atualizar(Jogo jogo) {
        dao.atualizar(jogo);
    }

    public void deletar(int id) {
        dao.deletar(id);
    }

    public Jogo buscarPorId(int id) {
        return dao.buscarPorId(id);
    }
}
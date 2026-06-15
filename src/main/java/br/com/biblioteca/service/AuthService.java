package br.com.biblioteca.service;

import br.com.biblioteca.dao.UsuarioDAO;
import br.com.biblioteca.model.Usuario;
import br.com.biblioteca.util.JwtUtil;

public class AuthService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public String login(String email, String senha) {
        if (isBlank(email) || isBlank(senha)) {
            return null;
        }

        Usuario usuario = usuarioDAO.buscarPorEmailESenha(email, senha);

        if (usuario == null) {
            return null;
        }

        return JwtUtil.gerarToken(usuario);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}

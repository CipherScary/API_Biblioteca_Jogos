package br.com.biblioteca.dao;

import br.com.biblioteca.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    public Usuario buscarPorEmailESenha(String email, String senha) {
        String sql = "SELECT id, nome, email, senha FROM usuarios WHERE email = ? AND senha = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha"));
                    return usuario;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao autenticar usuario: " + e.getMessage(), e);
        }

        return null;
    }
}

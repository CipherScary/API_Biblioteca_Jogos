package br.com.biblioteca.dao;

import br.com.biblioteca.model.Jogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JogoDAO {

    public int cadastrar(Jogo jogo) {

        String sql = "INSERT INTO jogos(nome, genero, plataforma, preco) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, jogo.getNome());
            stmt.setString(2, jogo.getGenero());
            stmt.setString(3, jogo.getPlataforma());
            stmt.setDouble(4, jogo.getPreco());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar jogo: " + e.getMessage(), e);
        }

        return 0;
    }

    public List<Jogo> listar() {

        List<Jogo> lista = new ArrayList<>();

        String sql = "SELECT * FROM jogos ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                lista.add(mapearJogo(rs));
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar jogos: " + e.getMessage(), e);
        }

        return lista;
    }

    public boolean atualizar(Jogo jogo) {

        String sql = "UPDATE jogos SET nome=?, genero=?, plataforma=?, preco=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, jogo.getNome());
            stmt.setString(2, jogo.getGenero());
            stmt.setString(3, jogo.getPlataforma());
            stmt.setDouble(4, jogo.getPreco());
            stmt.setInt(5, jogo.getId());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar jogo: " + e.getMessage(), e);
        }
    }

    public boolean deletar(int id) {

        String sql = "DELETE FROM jogos WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar jogo: " + e.getMessage(), e);
        }
    }

    public Jogo buscarPorId(int id) {

        String sql = "SELECT * FROM jogos WHERE id=?";

        Jogo jogo = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                jogo = mapearJogo(rs);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar jogo: " + e.getMessage(), e);
        }

        return jogo;
    }

    private Jogo mapearJogo(ResultSet rs) throws SQLException {
        Jogo jogo = new Jogo();

        jogo.setId(rs.getInt("id"));
        jogo.setNome(rs.getString("nome"));
        jogo.setGenero(rs.getString("genero"));
        jogo.setPlataforma(rs.getString("plataforma"));
        jogo.setPreco(rs.getDouble("preco"));

        return jogo;
    }
}

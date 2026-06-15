package br.com.biblioteca.controller;

import br.com.biblioteca.model.Jogo;
import br.com.biblioteca.service.JogoService;
import br.com.biblioteca.util.JsonUtil;
import br.com.biblioteca.util.JwtUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet("/api/jogos/*")
public class JogoController extends HttpServlet {

    private final JogoService jogoService = new JogoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!isAutorizado(req, resp)) {
            return;
        }

        try {
            Integer id = getId(req);

            if (id == null) {
                JsonUtil.writeJson(resp, HttpServletResponse.SC_OK, JsonUtil.jogosToJson(jogoService.listar()));
                return;
            }

            Jogo jogo = jogoService.buscarPorId(id);

            if (jogo == null) {
                JsonUtil.writeJson(resp, HttpServletResponse.SC_NOT_FOUND, JsonUtil.messageJson("Jogo nao encontrado"));
                return;
            }

            JsonUtil.writeJson(resp, HttpServletResponse.SC_OK, JsonUtil.jogoToJson(jogo));
        } catch (IllegalArgumentException e) {
            JsonUtil.writeJson(resp, HttpServletResponse.SC_BAD_REQUEST, JsonUtil.messageJson(e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!isAutorizado(req, resp)) {
            return;
        }

        try {
            Jogo jogo = jogoService.salvar(toJogo(JsonUtil.parseObject(JsonUtil.readBody(req))));
            JsonUtil.writeJson(resp, HttpServletResponse.SC_CREATED, JsonUtil.jogoToJson(jogo));
        } catch (IllegalArgumentException e) {
            JsonUtil.writeJson(resp, HttpServletResponse.SC_BAD_REQUEST, JsonUtil.messageJson(e.getMessage()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!isAutorizado(req, resp)) {
            return;
        }

        try {
            Integer id = getId(req);

            if (id == null) {
                JsonUtil.writeJson(resp, HttpServletResponse.SC_BAD_REQUEST, JsonUtil.messageJson("Informe o id na URL"));
                return;
            }

            Jogo jogo = toJogo(JsonUtil.parseObject(JsonUtil.readBody(req)));
            jogo.setId(id);

            if (!jogoService.atualizar(jogo)) {
                JsonUtil.writeJson(resp, HttpServletResponse.SC_NOT_FOUND, JsonUtil.messageJson("Jogo nao encontrado"));
                return;
            }

            JsonUtil.writeJson(resp, HttpServletResponse.SC_OK, JsonUtil.jogoToJson(jogo));
        } catch (IllegalArgumentException e) {
            JsonUtil.writeJson(resp, HttpServletResponse.SC_BAD_REQUEST, JsonUtil.messageJson(e.getMessage()));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!isAutorizado(req, resp)) {
            return;
        }

        try {
            Integer id = getId(req);

            if (id == null) {
                JsonUtil.writeJson(resp, HttpServletResponse.SC_BAD_REQUEST, JsonUtil.messageJson("Informe o id na URL"));
                return;
            }

            if (!jogoService.deletar(id)) {
                JsonUtil.writeJson(resp, HttpServletResponse.SC_NOT_FOUND, JsonUtil.messageJson("Jogo nao encontrado"));
                return;
            }

            JsonUtil.writeJson(resp, HttpServletResponse.SC_OK, JsonUtil.messageJson("Jogo deletado com sucesso"));
        } catch (IllegalArgumentException e) {
            JsonUtil.writeJson(resp, HttpServletResponse.SC_BAD_REQUEST, JsonUtil.messageJson(e.getMessage()));
        }
    }

    private boolean isAutorizado(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String authorization = req.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            JsonUtil.writeJson(resp, HttpServletResponse.SC_UNAUTHORIZED, JsonUtil.messageJson("Acesso n\u00e3o autorizado"));
            return false;
        }

        String token = authorization.substring("Bearer ".length());

        if (!JwtUtil.validarToken(token)) {
            JsonUtil.writeJson(resp, HttpServletResponse.SC_UNAUTHORIZED, JsonUtil.messageJson("Acesso n\u00e3o autorizado"));
            return false;
        }

        return true;
    }

    private Integer getId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/") || pathInfo.trim().isEmpty()) {
            return null;
        }

        return Integer.parseInt(pathInfo.substring(1));
    }

    private Jogo toJogo(Map<String, String> dados) {
        Jogo jogo = new Jogo();

        jogo.setNome(dados.get("nome"));
        jogo.setGenero(dados.get("genero"));
        jogo.setPlataforma(dados.get("plataforma"));

        String preco = dados.get("preco");
        if (preco != null && !preco.trim().isEmpty()) {
            jogo.setPreco(Double.parseDouble(preco));
        }

        return jogo;
    }
}

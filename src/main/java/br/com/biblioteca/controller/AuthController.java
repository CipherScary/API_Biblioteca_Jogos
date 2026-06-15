package br.com.biblioteca.controller;

import br.com.biblioteca.service.AuthService;
import br.com.biblioteca.util.JsonUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet("/api/login")
public class AuthController extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Map<String, String> dados = JsonUtil.parseObject(JsonUtil.readBody(req));

        String token = authService.login(dados.get("email"), dados.get("senha"));

        if (token == null) {
            JsonUtil.writeJson(resp, HttpServletResponse.SC_UNAUTHORIZED, JsonUtil.messageJson("Email ou senha invalidos"));
            return;
        }

        JsonUtil.writeJson(resp, HttpServletResponse.SC_OK, JsonUtil.tokenJson(token));
    }
}

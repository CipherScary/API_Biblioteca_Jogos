package br.com.biblioteca.util;

import br.com.biblioteca.model.Jogo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonUtil {

    private static final Pattern JSON_FIELD = Pattern.compile("\"([^\"]+)\"\\s*:\\s*(\"(?:\\\\.|[^\"])*\"|-?\\d+(?:\\.\\d+)?|true|false|null)");

    private JsonUtil() {}

    public static String readBody(HttpServletRequest request) throws IOException {
        StringBuilder body = new StringBuilder();

        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
        }

        return body.toString();
    }

    public static Map<String, String> parseObject(String json) {
        Map<String, String> values = new HashMap<>();

        if (json == null || json.trim().isEmpty()) {
            return values;
        }

        Matcher matcher = JSON_FIELD.matcher(json);

        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2);

            if (value.startsWith("\"") && value.endsWith("\"")) {
                value = unescape(value.substring(1, value.length() - 1));
            }

            values.put(key, value);
        }

        return values;
    }

    public static Long parseLong(String json, String field) {
        Map<String, String> values = parseObject(json);
        String value = values.get(field);

        if (value == null) {
            return null;
        }

        return Long.parseLong(value);
    }

    public static void writeJson(HttpServletResponse response, int status, String json) throws IOException {
        response.setStatus(status);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    public static String jogoToJson(Jogo jogo) {
        if (jogo == null) {
            return "null";
        }

        return "{"
                + "\"id\":" + jogo.getId() + ","
                + "\"nome\":\"" + escape(jogo.getNome()) + "\","
                + "\"genero\":\"" + escape(jogo.getGenero()) + "\","
                + "\"plataforma\":\"" + escape(jogo.getPlataforma()) + "\","
                + "\"preco\":" + BigDecimal.valueOf(jogo.getPreco()).toPlainString()
                + "}";
    }

    public static String jogosToJson(List<Jogo> jogos) {
        StringBuilder json = new StringBuilder("[");

        for (int i = 0; i < jogos.size(); i++) {
            if (i > 0) {
                json.append(",");
            }
            json.append(jogoToJson(jogos.get(i)));
        }

        json.append("]");
        return json.toString();
    }

    public static String messageJson(String message) {
        return "{\"mensagem\":\"" + escape(message) + "\"}";
    }

    public static String tokenJson(String token) {
        return "{\"token\":\"" + escape(token) + "\"}";
    }

    public static String escape(String value) {
        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private static String unescape(String value) {
        return value
                .replace("\\\"", "\"")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t")
                .replace("\\\\", "\\");
    }
}

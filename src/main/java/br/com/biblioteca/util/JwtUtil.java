package br.com.biblioteca.util;

import br.com.biblioteca.model.Usuario;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

public class JwtUtil {

    private static final String SECRET = "biblioteca-jogos-chave-secreta";
    private static final long EXPIRACAO_SEGUNDOS = 3600;

    private JwtUtil() {}

    public static String gerarToken(Usuario usuario) {
        long agora = Instant.now().getEpochSecond();
        long expiraEm = agora + EXPIRACAO_SEGUNDOS;

        String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        String payload = "{"
                + "\"sub\":\"" + JsonUtil.escape(usuario.getEmail()) + "\","
                + "\"nome\":\"" + JsonUtil.escape(usuario.getNome()) + "\","
                + "\"iat\":" + agora + ","
                + "\"exp\":" + expiraEm
                + "}";

        String headerBase64 = base64Url(header.getBytes(StandardCharsets.UTF_8));
        String payloadBase64 = base64Url(payload.getBytes(StandardCharsets.UTF_8));
        String conteudo = headerBase64 + "." + payloadBase64;

        return conteudo + "." + assinar(conteudo);
    }

    public static boolean validarToken(String token) {
        try {
            if (token == null || token.trim().isEmpty()) {
                return false;
            }

            String[] partes = token.split("\\.");

            if (partes.length != 3) {
                return false;
            }

            String conteudo = partes[0] + "." + partes[1];
            String assinaturaEsperada = assinar(conteudo);

            if (!assinaturaEsperada.equals(partes[2])) {
                return false;
            }

            String payload = new String(Base64.getUrlDecoder().decode(partes[1]), StandardCharsets.UTF_8);
            Long exp = JsonUtil.parseLong(payload, "exp");

            return exp != null && exp >= Instant.now().getEpochSecond();
        } catch (Exception e) {
            return false;
        }
    }

    private static String assinar(String conteudo) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKey);
            return base64Url(mac.doFinal(conteudo.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao assinar token JWT", e);
        }
    }

    private static String base64Url(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}

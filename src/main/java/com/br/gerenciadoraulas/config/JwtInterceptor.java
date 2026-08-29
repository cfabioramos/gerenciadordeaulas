package com.br.gerenciadoraulas.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Permit preflight OPTIONS requests without token
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"error\": \"Token não fornecido ou inválido\"}");
            return false;
        }

        String token = authHeader.substring(7);
        try {
            DecodedJWT jwt = JwtTokenProvider.verifyToken(token);
            String username = jwt.getSubject();
            Boolean isAdmin = jwt.getClaim("admin").asBoolean();

            request.setAttribute("username", username);
            request.setAttribute("isAdmin", isAdmin);

            // Restringe escrita para administradores (exceto atualização de tema do próprio usuário)
            String method = request.getMethod();
            String uri = request.getRequestURI();
            boolean isThemeUpdate = "/auth/theme".equalsIgnoreCase(uri) || uri.endsWith("/auth/theme");

            if (!isThemeUpdate && 
                ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method) ||
                 "DELETE".equalsIgnoreCase(method) || "PATCH".equalsIgnoreCase(method)) &&
                (isAdmin == null || !isAdmin)) {
                
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"error\": \"Apenas administradores podem realizar esta operação\"}");
                return false;
            }

            return true;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"error\": \"Token inválido ou expirado\"}");
            return false;
        }
    }
}

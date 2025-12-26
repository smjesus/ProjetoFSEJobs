/**
 * Projeto:  FSE Jobs  -  Organizador de Trabalhos no FSE
 * 
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Balneario Camboriu/SC  -  2025
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.componentes;

import org.slf4j.Logger;
import java.io.IOException;
import org.slf4j.LoggerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * Esta classe realiza o Handler para Falhas de Autenticaçao.
 * Com esse handler obtemos no log da aplicacao o IP que tentou logar para analises futuras.
 * 
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String reason = "Credenciais inválidas";
        if (exception instanceof UsernameNotFoundException) {
            reason = "Usuário não encontrado";
        } else if (exception instanceof BadCredentialsException) {
            reason = "Usuário ou senha inválidos";
        }
        logger.info("Tentativa de LOGIN com usuário não cadastrado de " + request.getRemoteAddr() );
        logger.info("Provavel motivo da falha: " + reason);
        response.sendRedirect(request.getContextPath().trim() + "/login?error=true");
    }

}
/*                    End of Class                                            */
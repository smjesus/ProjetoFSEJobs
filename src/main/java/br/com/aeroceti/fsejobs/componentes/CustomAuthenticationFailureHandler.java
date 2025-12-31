/**
 * Projeto:  FSE Jobs  -  Organizador de Trabalhos no FSE
 * 
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Balneario Camboriu/SC  -  2025
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.componentes;

import br.com.aeroceti.fsejobs.servicos.I18nService;
import org.slf4j.Logger;
import java.io.IOException;
import org.slf4j.LoggerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * Esta classe realiza o Handler para Falhas de Autenticaçao (Banco de Dados).
 * Com esse handler obtemos no log da aplicacao o IP que tentou logar para analises futuras.
 * 
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private I18nService i18svc; 
    
    private final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        Locale currentLocale = LocaleContextHolder.getLocale();
        String reason = i18svc.buscarMensagem("login.bad.credentials", currentLocale); 
        
        if (exception instanceof UsernameNotFoundException) {
            reason = i18svc.buscarMensagem("login.notfound", currentLocale);
        } else if (exception instanceof BadCredentialsException) {
            reason = i18svc.buscarMensagem("login.invalid", currentLocale); 
        }
        
        logger.info("Tentativa de LOGIN com usuário não cadastrado de " + request.getRemoteAddr() );
        logger.info("Provavel motivo da falha: " + reason);
        
        request.getSession().setAttribute("LOGIN_ERROR_MESSAGE", reason);
        response.sendRedirect(request.getContextPath().trim() + "/login");
    }

}
/*                    End of Class                                            */
/**
 * Projeto:  FSE Jobs  -  Organizador de Trabalhos no FSE
 * 
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Balneario Camboriu/SC  -  2025
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.componentes;

import org.slf4j.Logger;
import java.util.Locale;
import java.io.IOException;
import org.slf4j.LoggerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import br.com.aeroceti.fsejobs.servicos.I18nService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 * Esta classe realiza o Handler para Falhas de Autenticaçao (OAUTH2).
 * Com esse handler obtemos no log da aplicacao o IP que tentou logar para analises futuras.
 * 
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@Component
public class CustomOAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private I18nService i18svc; 

    private final Logger logg = LoggerFactory.getLogger(CustomOAuth2FailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        Locale currentLocale = LocaleContextHolder.getLocale();
        String mensagem = i18svc.buscarMensagem("login.bad.credentials", currentLocale); 
        
        if (exception instanceof OAuth2AuthenticationException oauthEx) {
            mensagem = oauthEx.getMessage();
        }

        logg.info("Tentativa de LOGIN com usuário não cadastrado de " + request.getRemoteAddr() );
        logg.info("Provavel motivo da falha: " + mensagem);
        
        request.getSession().setAttribute("LOGIN_ERROR_MESSAGE", mensagem);
        response.sendRedirect(request.getContextPath().trim() + "/login");
    }

}
/*                    End of Class                                            */
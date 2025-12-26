/**
 * Projeto:  FSE Jobs  -  Organizador de Trabalhos no FSE
 * 
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Balneario Camboriu/SC  -  2025
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.componentes;

import br.com.aeroceti.fsejobs.entidades.user.UsuarioLogin;
import br.com.aeroceti.fsejobs.entidades.user.UsuarioOAuth2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *  Objeto base UsuarioAutenticado (Encapsula o user login).
 *
 * Esta classe representa um usuario LOGADO no sistema.
 * 
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
public final class UsuarioAutenticado {

    private UsuarioAutenticado() {}

    public static UsuarioLogin get() {
        Authentication auth =
            SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            return null;
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof UsuarioLogin ul) {
            return ul;
        }

        if (principal instanceof UsuarioOAuth2 oauth) {
            return oauth.getUsuarioLogin();
        }

        return null;
    }
    
}
/*                    End of Class                                            */
/**
 * Projeto:  FSE Jobs  -  Organizador de Trabalhos no FSE
 * 
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Balneario Camboriu/SC  -  2025
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.configuracoes;

import br.com.aeroceti.fsejobs.componentes.UsuarioAutenticado;
import br.com.aeroceti.fsejobs.entidades.user.UsuarioLogin;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author smurilo
 */
@ControllerAdvice
public class UsuarioGlobalAdvice {

    @ModelAttribute("usuarioLogado")
    public UsuarioLogin usuarioLogado() {
        return UsuarioAutenticado.get();
    }
    
}
/*                    End of Class                                            */
/**
 * Projeto:  Fleet Care Amazon  -  Sistema de Controle de Locacao e Manutencao de Veiculos.
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Manaus/AM  -  2023
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.controladores;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import br.com.aeroceti.fsejobs.entidades.user.Usuario;
import br.com.aeroceti.fsejobs.servicos.UsuarioService;
import br.com.aeroceti.fsejobs.entidades.user.Preferencias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Classe Controller para o objeto Usuario.
 *
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@Controller
@RequestMapping("/usuario/")
public class UsuarioController {

    @Autowired
    private UsuarioService userService;

    private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    /**
     * FORMULARIO para editar as preferencias de um Usuario da base de dados.
     * 
     * @param id - ID do usuario
     * @param modelo - objeto de manipulacao da view pelo Spring
     * @return  String para o Spring realizar o encaminhamento ao dashboard 
     */
    @GetMapping("/preferencias/{id}")
    public String atualizarPreferencias( @PathVariable("id") long id, Model modelo ) {
        Optional<Usuario> usuarioSolicitado = userService.buscar(id);
        if( usuarioSolicitado.isPresent() ) {
            Usuario usuario = usuarioSolicitado.get();
            logger.info("Recebida requisicao para editar as preferencias de: " + usuario.getNome());
            modelo.addAttribute("usuario", usuario);
            if( usuario.getPreferencias() == null ) {
                modelo.addAttribute("preferencias", new Preferencias());
            } else {
                modelo.addAttribute("preferencias", usuario.getPreferencias());
            }
        } else {
            logger.info("ALTERÇÃO NÃO REALIZADA - Referencia Invalida! ");
            return "redirect:/dashboard";
        }
        logger.info("Apresentando o formulario para edicao das preferencias... ");
        return "/usuarios/preferencias"; 
    }

    @PostMapping("/preferencias")
    public String salvarPreferencias(Preferencias preferencias,  @RequestParam Long usuarioId) {
        logger.info("Recebida requisicao para persistir as preferencias ... " );
        Optional<Usuario> usuarioSolicitado = userService.buscar(usuarioId);
        if( usuarioSolicitado.isPresent() ) {
            Usuario usuario = usuarioSolicitado.get();
            preferencias.setUsuario(usuario);
            //usuario.setPreferencias(preferencias);
            userService.atualizarPreferencia(preferencias);
        }
        return "redirect:/dashboard";
    }

}
/*                    End of Class                                            */
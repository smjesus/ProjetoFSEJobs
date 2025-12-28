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
import org.springframework.security.access.prepost.PreAuthorize;
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
     * Listagem de TODOS os Usuarios cadastrados no Banco de dados.
     *
     * @param modelo - Objeto Model para injetar dados na View
     * @return  String para o Spring realizar o encaminhamento a View 
     */
    @RequestMapping("/listar")
    public String listagem(Model modelo) {
        logger.info("Recebida requisicao para listar os usuarios no sistema ...");
        modelo.addAttribute("colecao",userService.listar(true));
        return"/usuarios/usuariosListagem";
    }

    /**
     * Listagem de TODOS os Usuarios cadastrados no Banco de dados (PAGINADA).
     * 
     * @param page     - numero da pagina em exibicao
     * @param pageSize - total de itens na pagina
     * @param modelo   - Objeto Model para injetar dados na View
     * @return  String para o Spring realizar o encaminhamento a View 
     */
    @RequestMapping("/paginar/{page}/{pageSize}")
    public String listar( @PathVariable int page, @PathVariable int pageSize, Model modelo ) {
        logger.info("Recebida requisicao para listar os Usuários (PAGINADOS) ...");
        modelo.addAttribute("colecao",userService.paginar(page, pageSize));
        return"/usuarios/usuarios";
    }

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

    /**
     * FORMULARIO para atualizar a SENHA do USUARIO.
     * ATUALIZA SOMENTE A SENHA DO USUARIO.
     * 
     * @param id      - ID do usuario que pediu a mudanca da senha 
     * @param modelo  - objeto de manipulacao da view pelo Spring
     * @return String - Padrao Spring para redirecionar a uma pagina
     */
    @GetMapping("/senha/{id}")
    public String atualizarSenha( @PathVariable("id") long id, Model modelo ) {
        logger.info("APRESENTANDO Formulário de ALTERACAO da senha do usuario ID = {}.", id);
        modelo.addAttribute("entidadeID", id);
        return "/usuarios/senha"; 
    }

    /**
     * ATUALIZA a SENHA do Colaborador no Banco de Dados.
     * ATUALIZA SOMENTE A SENHA DO USUARIO. A senha sera criptografada no SERVICE.
     * 
     * @param entidadeID     - ID do usuario que pediu a mudanca da senha 
     * @param novaSenha      - NOVA senha digitada no formulario
     * @param confirmarSenha - Nova senha CONFIRMADA no formulario
     * @param modelo         - objeto de manipulacao da view pelo Spring
     * @return String        - Padrao Spring para redirecionar a uma pagina
     */
    @PostMapping("/senha")
    @PreAuthorize("principal.id == #entidadeID")
    public String atualizarSenha(Long entidadeID, String novaSenha, String confirmarSenha, Model modelo) {
        logger.info("Tratando requisicao para ATUALIZAR a SENHA de um Usuario...");
        if( novaSenha.isBlank() || novaSenha.isEmpty() ) {
            modelo.addAttribute("mensagem", "Informe senhas válidas e seguras!");
            logger.info("Retornando para o formulario: Senha em branco!");
            modelo.addAttribute("entidadeID", entidadeID);
            return "/usuarios/senha"; 
        }
        if( !novaSenha.trim().equals(confirmarSenha.trim()) ) {
            modelo.addAttribute("mensagem", "Sua confirmação da senha não confere!! Verifique!");
            logger.info("Retornando para o formulario: Senhas digitadas não conferem!");
            modelo.addAttribute("entidadeID", entidadeID);
            return "/usuarios/senha"; 
        }
        Optional<Usuario> userSolicitado = userService.buscar(entidadeID);
        if( userSolicitado.isPresent() ) {
            logger.info("Encontrou Colaborador com nome: {} - Alterando sua senha!! ", userSolicitado.get().getNome());
            userSolicitado.get().setSenha(novaSenha.trim());
            userService.atualizarSenha(userSolicitado.get());
        } else {
            logger.info("Processando requisicao: ALTERÇÃO NÃO REALIZADA - Referencia Invalida! ");
        }
        return "redirect:/dashboard";
    }
    
    @GetMapping("/status/{id}/{code}")
    public String mudarStatusUsuario(@PathVariable Long id, @PathVariable boolean code){
        String situacao = code ? "ATIVAR" : "DESATIVAR" ;
        logger.info("Tratando requisicao para {} usuario ID={} ...", situacao, id );
        Optional<Usuario> userSolicitado = userService.buscar(id);
        if( userSolicitado.isPresent() ) {
            logger.info("Encontrou Colaborador com nome: {} - modificando seu Status!! ", userSolicitado.get().getNome());
            userSolicitado.get().setAtivo(code);
            userService.atualizarStatus(userSolicitado.get());
        } else {
            logger.info("Processando requisicao: ALTERÇÃO NÃO REALIZADA - Referencia Invalida! ");
        }
        return "redirect:/usuario/paginar/1/5";
    }
    
    
    /**
     * DELETA um USUARIO do Banco de dados.
     * 
     * @param id - ID do objeto a ser removido
     * @param modelo - Objeto Model para injetar dados na View
     * @return String Padrao Spring para redirecionar a uma pagina
     */
    @GetMapping("/remover/{id}")
    public String deletarRota( @PathVariable("id") long id, Model modelo ) {
        logger.info("Requisicao para DELETAR usuario com ID={} ", id);
        Optional<Usuario> userSolicitado = userService.buscar(id);
        if (userSolicitado.isPresent()) {
            userService.remover(userSolicitado.get());
        } else {
            logger.info("Processando requisicao: DELEÇÃO NÃO REALIZADA - Referencia Invalida! ");
        }	
        return "redirect:/usuario/paginar/1/15";
    }
    }
/*                    End of Class                                            */
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
import br.com.aeroceti.fsejobs.servicos.RotasService;
import br.com.aeroceti.fsejobs.entidades.user.Usuario;
import br.com.aeroceti.fsejobs.servicos.UsuarioService;
import br.com.aeroceti.fsejobs.entidades.user.RotasDeTrabalho;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
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
@RequestMapping("/rotas/")
public class RotasController {

    @Autowired
    private RotasService rotasSVC; 
    @Autowired
    private UsuarioService userService;

    private final Logger logger = LoggerFactory.getLogger(RotasController.class);


    /**
     * Listagem de TODAS as ROTAS cadastrados no Banco de dados.
     *
     * @param modelo - Objeto Model para injetar dados na View
     * @return  String para o Spring realizar o encaminhamento a View 
     */
    @RequestMapping("/listar")
    public String listagem(Model modelo) {
        logger.info("Recebida requisicao para listar as rotas do usuario no sistema ...");
        modelo.addAttribute("colecao",rotasSVC.listar());
        return"/usuarios/rotasListagem";
    }

    /**
     * Listagem de TODAS as ROTAS cadastradas no Banco de dados (PAGINADA).
     * 
     * @param page     - numero da pagina em exibicao
     * @param pageSize - total de itens na pagina
     * @param modelo   - Objeto Model para injetar dados na View
     * @return  String para o Spring realizar o encaminhamento a View 
     */
    @RequestMapping("/paginar/{page}/{pageSize}")
    public String listar( @PathVariable int page, @PathVariable int pageSize, Model modelo ) {
        logger.info("Recebida requisicao para listar as Permissoes PAGINADAS ...");
        modelo.addAttribute("colecao",rotasSVC.paginar(page, pageSize));
        return"/usuarios/rotas";
    }
    
    /**
     * FORMULARIO para cadastrar uma ROTA na base de dados.
     * Esta funcao abre a Pagina de Cadastro (Formulario).
     *
     * @param id
     * @param modelo - Objeto Model para injetar dados na View
     * @return  String para o Spring realizar o encaminhamento a View 
     */
    @GetMapping("/cadastrar/{id}")
    public String cadastrarRota(@PathVariable("id") long id, Model modelo) {
        Optional<Usuario> usuarioSolicitado = userService.buscar(id);
        if( usuarioSolicitado.isPresent() ) {
            Usuario usuario = usuarioSolicitado.get();
            logger.info("Recebida requisicao para cadastrar uma rota para: " + usuario.getNome());
            modelo.addAttribute("usuario", usuario);
        } else {
            logger.info("ALTERÇÃO NÃO REALIZADA - Referencia Invalida! ");
            return "redirect:/rotas/paginar/1/15";
        }
        modelo.addAttribute("rotasDeTrabalho", new RotasDeTrabalho());
        logger.info("Encaminhando ao formulario de cadastro ...");
        return "/usuarios/rotaCadastro";
    }

    /**
     * Cadastra uma ROTA na base de dados.
     *
     * @param rota - objeto a ser persistido
     * @param result  - objeto do contexto HTTP
     * @param usuarioId - ID do usuario que criou a rota
     * @param modelo - Objeto Model para injetar dados na View
     * @return  String para o Spring realizar o encaminhamento a View 
     */
    @PostMapping("/cadastrar")
    public String salvarRota(@Valid RotasDeTrabalho rota, BindingResult result,  @RequestParam Long usuarioId, Model modelo) {
        logger.info("Recebida requisicao para persistir a rota do usuario... " );
        if( result.hasErrors() ) {
            logger.info("GRAVAÇAO NÃO REALIZADA - Erro na Validacao! ");
            modelo.addAttribute("mensagem", "GRAVAÇAO NÃO REALIZADA - Erro na Validacao! ");
            return "/usuarios/rotaCadastro";
        }
        Optional<Usuario> usuarioSolicitado = userService.buscar(usuarioId);
        if( usuarioSolicitado.isPresent() ) {
            Usuario usuario = usuarioSolicitado.get();
            rota.setUsuario(usuario);
            //usuario.setPreferencias(preferencias);
            rotasSVC.atualizarRota(rota);
        }
        return "redirect:/rotas/paginar/1/15";
    }
    
    /**
     * SINCRONIZACAO das ROTAS de um Usuario com as informacoes do FSEconomy.
     *
     * @param id - ID do usuario das rotas a sincronizar
     * @param modelo - Objeto Model para injetar dados na View
     * @return  String para o Spring realizar o encaminhamento a View 
     */
    @GetMapping("/sincronizar/{id}")
    public String sincronizarJobs(@PathVariable("id") long id, Model modelo) {
        Optional<Usuario> usuarioSolicitado = userService.buscar(id);
        if( usuarioSolicitado.isPresent() ) {
            Usuario usuario = usuarioSolicitado.get();
            logger.info("Recebida requisicao para sincronizar os Jobs com as rotas de: " + usuario.getNome());
            for (RotasDeTrabalho rota : usuario.getRotas()) {
                rotasSVC.sincronizarRota(rota, usuario.getPreferencias().getApiKey());
            }
        } else {
            logger.info("SINCRONIZAÇÃO NÃO REALIZADA - Referencia Invalida! ");
        }   
        return "redirect:/rotas/paginar/1/15";
    }
    
    /**
     * FORMULARIO para atualizar uma ROTA na base de dados.
     * 
     * @param id - ID do objeto a ser persistido
     * @param modelo - objeto de manipulacao da view pelo Spring
     * @return String Padrao Spring para redirecionar a uma pagina
     */
    @GetMapping("/atualizar/{id}")
    public String atualizar( @PathVariable("id") long id, Model modelo ) {
        logger.info("Recebida requisicao para editar uma ROTA ...");
        Optional<RotasDeTrabalho> rotaSolicitada = rotasSVC.buscarRota(id);
        if( rotaSolicitada.isPresent() ) {
            RotasDeTrabalho rota = rotaSolicitada.get();
            logger.info("APRESENTANDO os dados para alteracao: {} - {}", rota.getOrigem(), rota.getDestino());
            modelo.addAttribute("usuarios", userService.listar(true));
            modelo.addAttribute("rotasDeTrabalho", rota);
            modelo.addAttribute("usuario", rota.getUsuario());
        } else {
            logger.info("SOLICITAÇÃO NÃO REALIZADA - Referencia Invalida! ");
            return "redirect:/rotas/paginar/1/15";
        }
        return "/usuarios/rotaCadastro";
    }    
    
    /**
     * DELETA uma ROTA do Banco de dados.
     * 
     * @param id - ID do objeto a ser atualizado
     * @param modelo - Objeto Model para injetar dados na View
     * @return String Padrao Spring para redirecionar a uma pagina
     */
    @GetMapping("/remover/{id}")
    public String deletarUsuario( @PathVariable("id") long id, Model modelo ) {
        logger.info("Requisicao para DELETAR uma rota com ID={} ", id);
        Optional<RotasDeTrabalho> rotaSolicitada = rotasSVC.buscarRota(id);
        if (rotaSolicitada.isPresent()) {
            rotasSVC.removerRota(rotaSolicitada.get());
        }	
        return "redirect:/rotas/paginar/1/15";
    }
    
}
/*                    End of Class                                            */
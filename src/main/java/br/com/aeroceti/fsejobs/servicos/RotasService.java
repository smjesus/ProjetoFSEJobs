/**
 * Projeto:  Fleet Care Amazon  -  Sistema de Controle de Locacao e Manutencao de Veiculos.
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Manaus/AM  -  2023
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.servicos;

import java.util.List;
import org.slf4j.Logger;
import java.util.Optional;
import java.util.ArrayList;
import org.slf4j.LoggerFactory;
import jakarta.xml.bind.JAXBException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import br.com.aeroceti.fsejobs.componentes.UsuarioAutenticado;
import br.com.aeroceti.fsejobs.entidades.fse.assigments.AssignmentItems;
import br.com.aeroceti.fsejobs.entidades.fse.IcaoJobsTo;
import br.com.aeroceti.fsejobs.entidades.user.RotasDeTrabalho;
import br.com.aeroceti.fsejobs.entidades.user.UsuarioLogin;
import br.com.aeroceti.fsejobs.repositorios.RotasDeTrabalhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Classe de SERVICOS para o objeto RotasDeServico.
 *
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@Service
public class RotasService {
    
    @Autowired
    private RotasDeTrabalhoRepository rotasRepository;
    @Autowired
    private XmlParserService xmlParserService; 
    
    @Value("${application.show.parsexml}")
    private boolean showMatchXmlResults;
    
    private final Logger logger = LoggerFactory.getLogger(RotasDeTrabalhoRepository.class);

    /**
     * Listagem de TODAS as ROTAS cadastradas no Banco de dados.
     *
     * @return ArrayList com varios objetos RotasDeTrabalho
     */
    public List<RotasDeTrabalho> listar() {
        UsuarioLogin usuario = UsuarioAutenticado.get();
        logger.info("Executado Servico de listagem das rotas do usuario (Nivel: {}) ...", usuario.getUsuario().getNivelAcesso().getNome());
        if (usuario == null) {
            return new ArrayList<>();
        }
        boolean admin = "Administrador".equalsIgnoreCase( usuario.getUsuario().getNivelAcesso().getNome().trim() ) ;
        if( admin) return rotasRepository.findAll();
        
        return rotasRepository.findByUsuarioEntidadeID(usuario.getId());
    }

    /**
     * Listagem PAGINADA de todos as ROTAS cadastradas no Banco de dados.
     * 
     * @param page     - pagina atual da requisicao
     * @param pageSize - tamanho de itens para apresentar na pagina
     * @return         - devolve pra View um objeto Pageable
     */
    public Page<RotasDeTrabalho> paginar(int page, int pageSize){
        Pageable pageable;
        UsuarioLogin usuario = UsuarioAutenticado.get();
        logger.info("Executado Servico de listagem PAGINADA das ROTAS do usuario (Nivel: {}) ...", usuario.getUsuario().getNivelAcesso().getNome());

        boolean admin = "Administrador".equalsIgnoreCase( usuario.getUsuario().getNivelAcesso().getNome() );

        if (admin) {
            pageable = PageRequest.of((page -1), pageSize, Sort.by("usuario.nome").ascending());
            return rotasRepository.findAll(pageable);
        }

        // usuário comum → sem ordenação
        pageable = PageRequest.of((page -1), pageSize);
        return rotasRepository.findByUsuarioEntidadeID(usuario.getId(), pageable);
    }
    
    /**
     * Busca uma ROTA pelo ID cadastrada no Banco de dados.
     *
     * @param  identidade - ID do objeto desejado do banco de dados
     * @return OPTIONAL   - Objeto Optional contendo a ROTA encontrada (se houver)
     */
    public Optional<RotasDeTrabalho> buscarRota(Long identidade) {
        return rotasRepository.findByEntidadeID(identidade);
    }
            
    /**
     * Metodo para atualizar uma ROTA do Usuario na base de dados.
     *
     * @param rota - Objeto RotasDeTrabalho com os dados a serem gravados
     * @return ResponseEntity contendo uma mensagem de erro OU um objeto Usuario cadastrado
     */
    public ResponseEntity<?> atualizarRota(RotasDeTrabalho rota) {
        logger.info("Persistindo a Rota do usuario (" + rota.getUsuario().getNome() + ") no banco de dados...");
        rotasRepository.save(rota);
        return new ResponseEntity<>("Preferencia Salva no Banco de Dados!", HttpStatus.OK);
    }    

    public ResponseEntity<?> sincronizarRota(RotasDeTrabalho rota, String apiKEY) {
        logger.info("Sincronizando a Rota do usuario ({} - {})...", rota.getOrigem(), rota.getDestino());
        if( showMatchXmlResults) logger.info("API KEY: {}", apiKEY);
        try{
            // Chama o serviço do FSEconomy e sumariza a disponibilidade da rota:
            IcaoJobsTo jobs = xmlParserService.parseXmlIcaoTo(rota.getDestino(), apiKEY.trim());
            rota.setTotalItensJobsTo(0); rota.setValorTotalJobsTo(0.0);
            jobs.getAssignments().stream().forEach( trabalho -> {
                if( trabalho.getFromIcao().trim().equalsIgnoreCase(rota.getOrigem()) && 
                    trabalho.getToIcao().trim()  .equalsIgnoreCase(rota.getDestino())        ) {
                    if( showMatchXmlResults) {
                        System.out.println("----------------------------------------------");
                        System.out.println("FROM ...: " + trabalho.getFromIcao());
                        System.out.println("TO .....: " + trabalho.getToIcao());
                        System.out.println("VALOR ..: " + trabalho.getPay());
                        System.out.println("VALOR ..: " + trabalho.getAmount());
                        System.out.println("----------------------------------------------");

                    }
                    rota.setTotalItensJobsTo(rota.getTotalItensJobsTo()+trabalho.getAmount());
                    rota.setValorTotalJobsTo(rota.getValorTotalJobsTo()+trabalho.getPay());
                }
            });
            // Chama novamente o serviço do FSEconomy e sumariza os Assignments do usuaio:
            AssignmentItems myJobs = xmlParserService.parseXmlAssigned(apiKEY.trim());
            rota.setTotalItensAssings(0); rota.setValorTotalAssings(0.0);
            myJobs.getAssignments().stream().forEach( trabalho -> {
                if( trabalho.getFrom().trim().equalsIgnoreCase(rota.getOrigem()) && 
                    trabalho.getDestination().trim()  .equalsIgnoreCase(rota.getDestino())        ) {
                    if( showMatchXmlResults) {
                        System.out.println("----------------------------------------------");
                        System.out.println("FROM ...: " + trabalho.getFrom());
                        System.out.println("TO .....: " + trabalho.getDestination());
                        System.out.println("VALOR ..: " + trabalho.getPay());
                        System.out.println("VALOR ..: " + trabalho.getAmount());
                        System.out.println("----------------------------------------------");

                    }
                    rota.setTotalItensAssings(rota.getTotalItensAssings()+trabalho.getAmount());
                    rota.setValorTotalAssings(rota.getValorTotalAssings()+trabalho.getPay());
                }
            });
        } catch (JAXBException e) {
            logger.info("FALHA na SINCRONIZACAO: JAXB error {}", e.getErrorCode());
        }
        rotasRepository.save(rota);        
        return new ResponseEntity<>("Dados sincronizados no Banco de Dados!", HttpStatus.OK);
    }
    
    /**
     * DELETA uma permissao do banco de dados.
     *
     * @param rota - Rota a ser deletada
     * @return ResponseEntity - Mensagem de Erro ou Sucesso na operacao
     */
    public ResponseEntity<?> removerRota(RotasDeTrabalho rota) {
        rota.setUsuario(null);
        rotasRepository.delete(rota);
        logger.info("Servico executado: Rota DELETADA no Sistema!");
        return ResponseEntity.status(HttpStatus.OK)
                .body(ProblemDetail.forStatusAndDetail(
                        HttpStatus.OK,
                        "Rota DELETADA no Sistema!"
        ));
    }

}
/*                    End of Class                                            */
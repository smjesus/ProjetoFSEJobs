/**
 * Projeto:  FSE Jobs  -  Organizador de Trabalhos no FSE
 * 
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Balneario Camboriu/SC  -  2025
 * Equipe:   Murilo, Victor, Allan
 */
package br.com.aeroceti.fsejobs.componentes;

import org.slf4j.Logger;
import java.util.Optional;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import br.com.aeroceti.fsejobs.entidades.user.NivelAcesso;
import br.com.aeroceti.fsejobs.entidades.user.Usuario;
import br.com.aeroceti.fsejobs.repositorios.NivelAcessoRepository;
import br.com.aeroceti.fsejobs.repositorios.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Este Componente inicializa um usuario Administrador e o Nivel Admin.
 * 
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@Component
public class InicializadorApplicationData implements CommandLineRunner {
    
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String mensagemDeBoasVindas;
    
    @Autowired
    private NivelAcessoRepository   nivelRepository;
    @Autowired
    private UsuarioRepository       userRepository;
    @Autowired
    private PasswordEncoder         passwdEncoder ;
    
    private final Logger logger = LoggerFactory.getLogger(InicializadorApplicationData.class);
    
    /**
     *  Metodo principal que inicializa um usuario padrao.
     * 
     * @param args - argumentos passados para a aplicacao
     * @throws java.lang.Exception
     */
    @Override
    public void run(String... args) throws Exception {
        Usuario userAdmin = new Usuario();
        NivelAcesso permissao = new NivelAcesso(null, "Administrador");
        NivelAcesso normal    = new NivelAcesso(null, "Participante");

        // Verifica se o Perfil Administrador existe no BD:
        int valor = nivelRepository.countByNome("Administrador");
        if( valor == 0 ) {
            // senao existe, cria um no BD:
            nivelRepository.save(permissao);
            logger.info("Sistema FSE-Jobs inicializando (Criada permissao de Admin) ... ");
        }
        // Verifica se o Perfil Participante existe no BD:
        valor = nivelRepository.countByNome("Participante");
        if( valor == 0 ) {
            // senao existe, cria um no BD:
            nivelRepository.save(normal);
            logger.info("Sistema FSE-Jobs inicializando (Criada permissao de Participante) ... ");
        }
        // Verifica se há administrador cadastrado:
        Optional<NivelAcesso> permissaoSolicitada = nivelRepository.findByNomeAndUsuarios(permissao.getNome());
        permissao = permissaoSolicitada.get();
        if( permissao.getUsuarios().isEmpty() ) {
            // Nao tem administrador, cadastrando um 'default':
            Optional userSolicitado = userRepository.findByEmail("smurilo@aeroceti.com.br");
            if (userSolicitado.isEmpty()) {
                userAdmin.setEmail("smurilo@aeroceti.com.br");
                userAdmin.setNome("Administrador do FSEJobs");
                userAdmin.setEntidadeID(null);
                logger.info("Sistema FSE-Jobs Inicializando (Criado Admin default) ... ");
            } else {
                userAdmin = (Usuario) userSolicitado.get();
            }
            userAdmin.setAtivo(true);
            userAdmin.setNivelAcesso(permissao);
            userAdmin.setSenha( passwdEncoder.encode("admin-fsejobs") );
            userRepository.save(userAdmin);
            logger.info("Sistema FSE-Jobs Inicializando ((Admin default configurado)!");            
        }
        logger.info("Google ID:  {}", this.mensagemDeBoasVindas);
        logger.info("Sistema FSE-Jobs Inicializado. (Runner concluido)");
    }
    
}
/*                    End of Class                                            */
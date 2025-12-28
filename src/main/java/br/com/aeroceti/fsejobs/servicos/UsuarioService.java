/**
 * Projeto:  Fleet Care Amazon  -  Sistema de Controle de Locacao e Manutencao de Veiculos.
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Manaus/AM  -  2023
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.servicos;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import br.com.aeroceti.fsejobs.entidades.user.Preferencias;
import br.com.aeroceti.fsejobs.entidades.user.Usuario;
import br.com.aeroceti.fsejobs.entidades.user.UsuarioLogin;
import br.com.aeroceti.fsejobs.repositorios.PreferenciasRepository;
import br.com.aeroceti.fsejobs.repositorios.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Classe de SERVICOS para o objeto Usuario (Logica do negocio).
 *
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@Service
public class UsuarioService  implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwdEncoder ;
    @Autowired
    private UsuarioRepository userRepository;
    @Autowired
    private PreferenciasRepository prefsRepository;

    private final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    /**
     * Metodo para o SpringSecurity carregar o usuario logado.
     * 
     * @param email  -  nome de usuario que realizou o login
     * @return       -  registra um UserDetails
     * @throws UsernameNotFoundException 
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario userLogin = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
        
        return new UsuarioLogin(userLogin);
    }

    /**
     * Listagem de TODOS os usuarios cadastrados no Banco de dados.
     *
     * @param ordenarByNome - Boolean para indicar se ordena por nome o resultado da pesquisa
     * @return ArrayList com varios objetos USUARIO
     */
    public List<Usuario> listar(boolean ordenarByNome) {
        List<Usuario> listagem;
        if (ordenarByNome) {
            logger.info("Obtendo uma listagem ORDENADA de todos os usuarios...");
            listagem = userRepository.findByOrderByNomeAsc();
        } else {
            logger.info("Obtendo uma listagem GENERICA de todos os usuarios...");
            listagem = userRepository.findAll();
        }
        for (Usuario user : listagem) { user.getNivelAcesso();   }
        return listagem;
    }
    
    /**
     * Listagem PAGINADA de todos os Usuarios cadastrados no Banco de dados.
     * 
     * @param page     - pagina atual da requisicao
     * @param pageSize - tamanho de itens para apresentar na pagina
     * @return         - devolve pra View um objeto Pageable
     */
    public Page<Usuario> paginar(int page, int pageSize){ 
        Pageable pageRequest = PageRequest.of((page -1), pageSize);
        Page<Usuario> paginas = userRepository.findAllUsuarios(pageRequest);
        return paginas; 
    }

    /**
     * Busca um Usuario pelo ID que esta cadastrado no Banco de dados.
     *
     * @param  identidade - ID do objeto desejado do banco de dados
     * @return OPTIONAL   - Objeto Optional contendo o Usuario encontrado (se houver)
     */
    public Optional<Usuario> buscar(Long identidade) {
        logger.info("Obtendo Usuario pelo ID: " + identidade);
        return userRepository.findByEntidadeID(identidade);
    }
    
    /**
     * Busca um Usuario pelo E-MAIL fornecido.
     *
     * @param email - E-mail do usuario desejado
     * @return ResponseEntity - Mensagem de Erro ou Objeto Usuario
     */
    public Optional<Usuario> selecionarEmail(String email) {
        logger.info("Obtendo um usuario com o E-Mail " + email);
        return userRepository.findByEmail(email);
    }

    public boolean mostrarSincronizador(Long id) {
        boolean resposta = false;
        Optional<Usuario> usuarioLogado = userRepository.findByEntidadeID(id);
        if( usuarioLogado.isPresent() ) {
            resposta = usuarioLogado.get().getPreferencias() != null ;
        }        
        return resposta;
    }
    
    
    /**
     * Metodo para atualizar uma PREFERENCIA do Usuario na base de dados.
     *
     * @param prefs - Objeto Preferencias com os dados a serem gravados
     * @return ResponseEntity contendo uma mensagem de erro OU um objeto Usuario cadastrado
     */
    public ResponseEntity<?> atualizarPreferencia(Preferencias prefs) {
        logger.info("Persistindo a Preferencia do usuario (" + prefs.getUsuario().getNome() + ") no banco de dados...");
        prefsRepository.save(prefs);
        return new ResponseEntity<>("Preferencia Salva no Banco de Dados!", HttpStatus.OK);
    }    
    
    /**
     * CRIPTOGRAFA a senha e atualiza o Usuario na base de dados.
     *
     * @param usuario - Objeto Usuario com os dados a serem atualizados
     * @return ResponseEntity contendo uma mensagem de erro OU um objeto Usuario cadastrado
     */
    public ResponseEntity<?> atualizarSenha(Usuario usuario) {
        // Criptografa a senha:
        String senhaCriptografada =  passwdEncoder.encode(usuario.getSenha()).trim();
        usuario.setSenha( senhaCriptografada );
        // ATUALIZA o objeto do banco de dados
        logger.info("Usuario " + usuario.getNome() + " atualizado no banco de dados!");
        return new ResponseEntity<>(userRepository.save(usuario), HttpStatus.OK);
    }
    
    /**
     * ATUALIZA o STATUS do Usuario na base de dados.
     *
     * @param usuario - Objeto Usuario com os dados a serem atualizados
     * @return ResponseEntity contendo uma mensagem de erro OU um objeto Usuario cadastrado
     */
    public ResponseEntity<?> atualizarStatus(Usuario usuario) {
        // ATUALIZA o objeto do banco de dados
        logger.info("Usuario " + usuario.getNome() + " atualizado no banco de dados!");
        return new ResponseEntity<>(userRepository.save(usuario), HttpStatus.OK);
    }
    
    /**
     * DELETA uma permissao do banco de dados.
     *
     * @param user - Usuario a ser deletado
     * @return ResponseEntity - Mensagem de Erro ou Sucesso na operacao
     */
    @Transactional
    public ResponseEntity<?> remover(Usuario user) {
        String mensagem = "Servico executado: Usuario " + user.getNome() + " DELETADO no Sistema!"; 
        userRepository.delete(user);
        logger.info( mensagem );
        return ResponseEntity.status(HttpStatus.OK)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.OK, mensagem));
    }
    
}
/*                    End of Class                                            */
/**
 * Projeto:  FSE Jobs  -  Organizador de Trabalhos no FSE
 * 
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Balneario Camboriu/SC  -  2025
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.servicos;

import br.com.aeroceti.fsejobs.controladores.DashboardController;
import br.com.aeroceti.fsejobs.entidades.user.UsuarioOAuth2;
import br.com.aeroceti.fsejobs.entidades.user.NivelAcesso;
import br.com.aeroceti.fsejobs.entidades.user.Usuario;
import br.com.aeroceti.fsejobs.entidades.user.UsuarioLogin;
import br.com.aeroceti.fsejobs.repositorios.NivelAcessoRepository;
import br.com.aeroceti.fsejobs.repositorios.UsuarioRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * Esta classe implementa um SERVICO para tratar o login por redes sociais
 * 
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@Service
public class UsuarioOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private NivelAcessoRepository   nivelRepository;
    
    Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oauth2User = super.loadUser(userRequest);
        String email = oauth2User.getAttribute("email");
        String nome  = oauth2User.getAttribute("name");
        logger.info("Configurando autenticacao social de {}", email);
        if (email == null) {
            throw new OAuth2AuthenticationException("Email não encontrado no Google");
        }

        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseGet(() -> criarNovoUsuario(email, nome));

        UsuarioLogin usuarioLogin = new UsuarioLogin(usuario);
        logger.info("Retornando usuario logado: {}", nome);
        return new UsuarioOAuth2(usuarioLogin, oauth2User.getAttributes());
    }

    private Usuario criarNovoUsuario(String email, String nome) {
        logger.info("Cadastrando Usuario ({}) no Banco de Dados...", nome);
        Usuario u = new Usuario();
        u.setEmail(email);
        u.setNome(nome);
        u.setAtivo(true);
        u.setSenha(null); 
        NivelAcesso permissao = null;
        Optional<NivelAcesso> permissaoSolicitada = nivelRepository.findByNome("Participante");
        if( permissaoSolicitada.isPresent()) {
            permissao = permissaoSolicitada.get();
        }
        u.setNivelAcesso(permissao); 
        return usuarioRepository.save(u);
    }
    
}
/*                    End of Class                                            */

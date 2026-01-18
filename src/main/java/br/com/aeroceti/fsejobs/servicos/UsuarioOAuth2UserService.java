/**
 * Projeto:  FSE Jobs  -  Organizador de Trabalhos no FSE
 * 
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Balneario Camboriu/SC  -  2025
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.servicos;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.aeroceti.fsejobs.entidades.user.NivelAcesso;
import br.com.aeroceti.fsejobs.entidades.user.Usuario;
import br.com.aeroceti.fsejobs.entidades.user.UsuarioLogin;
import br.com.aeroceti.fsejobs.repositorios.NivelAcessoRepository;
import br.com.aeroceti.fsejobs.repositorios.UsuarioRepository;
import java.util.Locale;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * Esta classe implementa um SERVICO para tratar o login por redes sociais
 * 
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@Service
public class UsuarioOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private I18nService i18svc; 
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private NivelAcessoRepository   nivelRepository;
    
    Logger logger = LoggerFactory.getLogger(UsuarioOAuth2UserService.class);

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        Locale currentLocale = LocaleContextHolder.getLocale();
        
        OAuth2User oauth2User = super.loadUser(userRequest);
        String email = oauth2User.getAttribute("email");
        String nome  = oauth2User.getAttribute("name");
        logger.info("Configurando autenticacao social de {}", email);
        if (email == null) {
            logger.info("Login não autorizado: {}", i18svc.buscarMensagem("login.notfound", currentLocale) );
            throw new OAuth2AuthenticationException( new OAuth2Error("Not Found", i18svc.buscarMensagem("login.notfound", currentLocale), "No URL" ) );
        }

        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseGet(() -> criarNovoUsuario(email, nome));

        if (!usuario.isAtivo()) {
            logger.info("Login não autorizado: {}", i18svc.buscarMensagem("login.disable", currentLocale) );
            throw new OAuth2AuthenticationException( new OAuth2Error("Disable", i18svc.buscarMensagem("login.disable", currentLocale), "No URL")  );
        }

        UsuarioLogin usuarioLogin = new UsuarioLogin(usuario);
        usuarioLogin.setAttributes(oauth2User.getAttributes());
        logger.info("Retornando usuario logado: {}", nome);
        return usuarioLogin;   
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

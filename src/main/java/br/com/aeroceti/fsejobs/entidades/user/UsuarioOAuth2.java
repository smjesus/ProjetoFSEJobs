/**
 * Projeto:  FSE Jobs  -  Organizador de Trabalhos no FSE
 * 
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Balneario Camboriu/SC  -  2025
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.entidades.user;

import java.util.Map;
import java.util.Collection;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * Esta classe cria um customizador para o OAUTH2 User
 * Obtem os dados do usuario logado pelas redes sociais e cria um UsuarioLogin
 * 
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
public class UsuarioOAuth2 implements OAuth2User {

    private final UsuarioLogin usuarioLogin;
    private final Map<String, Object> attributes;

    public UsuarioOAuth2(UsuarioLogin usuarioLogin,
                         Map<String, Object> attributes) {
        this.usuarioLogin = usuarioLogin;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return usuarioLogin.getAuthorities();
    }

    @Override
    public String getName() {
        return usuarioLogin.getUsername(); // email
    }

    public UsuarioLogin getUsuarioLogin() {
        return usuarioLogin;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public boolean equals(Object obj) {
         // 1. Verificação de referência (se é o mesmo objeto na memória)
        if (this == obj) {
            return true;
        }
        // 2. Verificação de nulidade do objeto 'obj' e compatibilidade de classe
        if (!(obj instanceof UsuarioOAuth2)) {
            return false;
        }
        // 3. Comparação dos atributos usando Objects.equals() para segurança contra NullPointerException
        UsuarioOAuth2 other = (UsuarioOAuth2) obj;
        return Objects.equals(this.getUsuarioLogin(), other.getUsuarioLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUsuarioLogin().getId());
    }
    
}
/*                    End of Class                                            */
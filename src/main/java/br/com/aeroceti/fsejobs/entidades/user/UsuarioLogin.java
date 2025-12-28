/**
 * Projeto:  FSE Jobs  -  Organizador de Trabalhos no FSE
 * 
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Balneario Camboriu/SC  -  2025
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.entidades.user;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * Entidade do Spring Security para login no sistema.
 * 
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
public class UsuarioLogin implements UserDetails, OAuth2User {

    private final Usuario usuario;
    private Map<String, Object> attributes;

    public UsuarioLogin(Usuario colaborador) {
        this.usuario = colaborador;
    }
      
    /* ================= UserDetails ================= */
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String listAuthoriry = usuario.getNivelAcesso() == null ? "Convidado" : usuario.getNivelAcesso().getNome();
        return AuthorityUtils.createAuthorityList(listAuthoriry);
    }
    
    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return usuario.isAtivo(); 
    }

    @Override
    public String toString() {
        return this.getNomeUsuario() + "(" + this.getUsername() + ")";
    }

    @Override
    public boolean equals(Object obj) {
         // 1. Verificação de referência (se é o mesmo objeto na memória)
        if (this == obj) {
            return true;
        }
        // 2. Verificação de nulidade do objeto 'obj' e compatibilidade de classe
        if (!(obj instanceof UsuarioLogin)) {
            return false;
        }
        // 3. Comparação dos atributos usando Objects.equals() para segurança contra NullPointerException
        UsuarioLogin other = (UsuarioLogin) obj;
        return Objects.equals(this.getUsuario(), other.getUsuario());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
    
    /* ================= OAuth2User ================= */

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    
    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
    
    @Override
    public String getName() {
        // obrigatório pelo contrato OAuth2User
        return usuario.getEmail();
    }

    /* ================= extras ================= */    
        
    public Long getId(){
        return usuario.getEntidadeID();
    }
    
    public String getNomeUsuario() {
        return usuario.getNome();
    }
    
    public Usuario getUsuario(){
        return this.usuario;
    }

}
/*                    End of Class                                            */
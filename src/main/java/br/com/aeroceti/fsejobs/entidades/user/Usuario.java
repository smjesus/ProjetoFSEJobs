/**
 * Projeto:  FSE Jobs  -  Organizador de Trabalhos no FSE
 * 
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Balneario Camboriu/SC  -  2025
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.entidades.user;

import java.util.Objects;
import jakarta.persistence.Id;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 *  Objeto base Usuario do Sistema.
 *
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@Entity
@Table(name = "Usuario")
public class Usuario {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "entidadeID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entidadeID;
    
    @Column(name = "nome")
    private String nome;
    
    @NotNull
    @NotBlank
    @Email
    @Pattern(
        regexp = "^[^@]+@[^@]+(\\.[a-zA-Z]{2,})+$",
        message = "{form.user.email.notcomplete}"
    )            
    @Column(name = "email")
    private String email;
    
    @Size(min = 6, message = "No minimo 6 caracteres.")
    @Column(name = "senha")
    private String senha;
    
    @Column(name = "ativo")
    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "nivelAcesso", nullable = true)
    private NivelAcesso nivelAcesso;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Preferencias preferencias;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RotasDeTrabalho> rotas = new HashSet<>();
    
    @Version
    @Column(name = "versao")
    private Long versao;

    public Usuario() {
    }
    
    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @return long contendo o Serial Version UID do Objeto
     */
    public Long getEntidadeID() {
        return entidadeID;
    }

    /**
     * @param  entidadeID contendo o Identificador unico do Usuario
     */
    public void setEntidadeID(Long entidadeID) {
        this.entidadeID = entidadeID;
    }

    /**
     * @return String contendo o Nome Pessoal do Usuario
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome String contendo o Nome Pessoal do Usuario
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return String contendo a Senha do Usuario
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha String contendo a Senha do Usuario
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return String contendo o e-mail do Usuario
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email String contendo o e-mail do Usuario
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return TRUE ou FALSE conforme esteja Ativo ou nao o Usuario no sistema
     */
    public boolean isAtivo() {
        return ativo;
    }

    /**
     * @param situacao Booleano contendo o Status do Usuario no sistema
     */
    public void setAtivo(boolean situacao) {
        this.ativo = situacao;
    }

    /**
     * @return Objeto NivelAcesso representando o Nivel do Usuario no sistema
     */
    public NivelAcesso getNivelAcesso() {
        return nivelAcesso;
    }

    /**
     * @param nivelAcesso Objeto representando o Nivel do Usuario no sistema
     */
    public void setNivelAcesso(NivelAcesso nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    /**
     * 
     * @return preferencias Objeto que guarda informacoes do usuario
     */
    public Preferencias getPreferencias() {
        return preferencias;
    }

    /**
     * 
     * @param preferencias Objeto que guarda informacoes do usuario
     */
    public void setPreferencias(Preferencias preferencias) {
        this.preferencias = preferencias;
    }

    public Set<RotasDeTrabalho> getRotas() {
        return rotas;
    }

    public void setRotas(Set<RotasDeTrabalho> rotas) {
        this.rotas = rotas;
    }
    
    /**
     * @return the versao
     */
    public Long getVersao() {
        return versao;
    }

    /**
     * @param versao the versao to set
     */
    public void setVersao(Long versao) {
        this.versao = versao;
    }

    @Override
    public String toString() {
        return this.nome + "[ID=" + this.entidadeID + "]";
    }

    @Override
    public boolean equals(Object obj) {
        // 1. Verificação de referência (se é o mesmo objeto na memória)
        if (this == obj) {
            return true;
        }
        // 2. Verificação de nulidade do objeto 'obj' e compatibilidade de classe
        if (!(obj instanceof Usuario)) {
            return false;
        }
        // 3. Comparação dos atributos usando Objects.equals() para segurança contra NullPointerException
        Usuario other = (Usuario) obj;
        return Objects.equals(this.getEntidadeID(), other.getEntidadeID());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEntidadeID());
    }

}
/*                    End of Class                                            */
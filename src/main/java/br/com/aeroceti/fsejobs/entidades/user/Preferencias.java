/**
 * Projeto:  FSE Jobs  -  Organizador de Trabalhos no FSE
 * 
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Balneario Camboriu/SC  -  2025
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.entidades.user;

import jakarta.persistence.Id;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.util.Objects;

/**
 *  Objeto base Preferencias.
 *
 * Esta classe representa um nivel de uma Preferencias do usuario no sistema.
 * 
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@Entity
@Table(name = "Preferencias")
public class Preferencias {
    
    private static final long serialVersionUID = 3L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "entidadeID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entidadeID;
    

    @Column(name = "apiKey",  length = 50)
    private String apiKey;

    @Version
    @Column(name = "versao")
    private Long versao;

    @OneToOne
    @JoinColumn(name = "usuario", unique = true)
    private Usuario usuario;

    public Preferencias() {
    }

    /**
     * @return long contendo o Serial Version UID do Objeto
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @return Long contendo o Identificador unico do Nivel de Acesso
     */
    public Long getEntidadeID() {
        return entidadeID;
    }

    /**
     * @param entidadeID Identificador unico do Nivel de Acesso
     */
    public void setEntidadeID(Long entidadeID) {
        this.entidadeID = entidadeID;
    }

    /**
     * @return String contendo o Nome do Nivel de Acesso
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * @param apiKey Nome do Nivel de Acesso
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * @return the usuarios
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuarios to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        return this.getApiKey() + "[ID=" + this.getEntidadeID() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        // 1. Verificação de referência (se é o mesmo objeto na memória)
        if (this == obj) {
            return true;
        }
        // 2. Verificação de nulidade do objeto 'obj' e compatibilidade de classe
        if (!(obj instanceof Preferencias)) {
            return false;
        }
        // 3. Comparação dos atributos usando Objects.equals() para segurança contra NullPointerException
        Preferencias other = (Preferencias) obj;
        return Objects.equals(this.getEntidadeID(), other.getEntidadeID());
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(getEntidadeID());
    }

}
/*                    End of Class                                            */

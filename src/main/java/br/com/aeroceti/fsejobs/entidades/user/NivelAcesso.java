/**
 * Projeto:  FSE Jobs  -  Organizador de Trabalhos no FSE
 * 
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Balneario Camboriu/SC  -  2025
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.entidades.user;

import java.util.List;
import jakarta.persistence.Id;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Objects;

/**
 *  Objeto base NivelAcesso (Niveis de usuarios).
 *
 * Esta classe representa um nivel de um usuario no sistema.
 * 
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@Entity
@Table(name = "NivelAcesso")
public class NivelAcesso {
    
    private static final long serialVersionUID = 2L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "entidadeID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entidadeID;
    
    @NotNull(message = "{roles.nome.notnull}")
    @NotBlank(message = "{roles.nome.notblank}")
    @Size(min=5, max=50, message = "{roles.nome.size.error}")
    @Column(name = "nome",  length = 50)
    private String nome;

    @Version
    @Column(name = "versao")
    private Long versao;

    @OneToMany(mappedBy = "nivelAcesso")
    private List<Usuario> usuarios;

    public NivelAcesso() {
    }

    public NivelAcesso(Long entidadeID, String nome) {
        this.entidadeID = entidadeID;
        this.nome = nome;
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
    public String getNome() {
        return nome;
    }

    /**
     * @param nome Nome do Nivel de Acesso
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the usuarios
     */
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
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
        return this.getNome() + "[ID=" + this.getEntidadeID() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        // 1. Verificação de referência (se é o mesmo objeto na memória)
        if (this == obj) {
            return true;
        }
        // 2. Verificação de nulidade do objeto 'obj' e compatibilidade de classe
        if (!(obj instanceof NivelAcesso)) {
            return false;
        }
        // 3. Comparação dos atributos usando Objects.equals() para segurança contra NullPointerException
        NivelAcesso other = (NivelAcesso) obj;
        return Objects.equals(this.getEntidadeID(), other.getEntidadeID());
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(getEntidadeID());
    }

}
/*                    End of Class                                            */
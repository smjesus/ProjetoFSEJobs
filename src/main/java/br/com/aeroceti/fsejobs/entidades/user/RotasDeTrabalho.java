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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

/**
 *  Objeto base RotasDeTrabalho.
 *
 * Esta classe representa uma Rotas De Trabalho no FSE.
 * 
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@Entity
@Table(name = "RotasDeTrabalho")
public class RotasDeTrabalho {
    
    private static final long serialVersionUID = 4L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "entidadeID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entidadeID;
    
    @NotNull(message = "{roles.nome.notnull}")
    @NotBlank(message = "{roles.nome.notblank}")
    @Column(name = "origem",  length = 5)
    private String origem;

    @NotNull(message = "{roles.nome.notnull}")
    @NotBlank(message = "{roles.nome.notblank}")
    @Column(name = "destino",  length = 5)
    private String destino;
    
    private int totalItensJobsTo = 0;
    
    private double valorTotalJobsTo = 0d;
    
    private int totalItensAssings = 0;
    
    private double valorTotalAssings = 0d;
    
    @Version
    @Column(name = "versao")
    private Long versao;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    public RotasDeTrabalho() {
    }

    public RotasDeTrabalho(Long entidadeID, String nome) {
        this.entidadeID = entidadeID;
        this.origem = nome;
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
    public String getOrigem() {
        return origem;
    }

    /**
     * @param origem Nome do Nivel de Acesso
     */
    public void setOrigem(String origem) {
        this.origem = origem.trim().toUpperCase();
    }

    public void setDestino(String destino) {
        this.destino = destino.trim().toUpperCase();
    }

    public String getDestino() {
        return destino;
    }

    /**
     * @return the usuarios
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuario(Usuario usuarios) {
        this.usuario = usuarios;
    }

    public void setValorTotalJobsTo(double valorTotalJobsTo) {
        this.valorTotalJobsTo = valorTotalJobsTo;
    }

    public double getValorTotalJobsTo() {
        return this.valorTotalJobsTo;
    }

    public String getValorTotalJobsToDollar() {
        return NumberFormat.getCurrencyInstance(Locale.US).format(valorTotalJobsTo);
    }

    public void setTotalItensJobsTo(int totalItensJobsTo) {
        this.totalItensJobsTo = totalItensJobsTo;
    }

    public int getTotalItensJobsTo() {
        return totalItensJobsTo;
    }

    public void setValorTotalAssings(double valorTotalJobsTo) {
        this.valorTotalAssings = valorTotalJobsTo;
    }

    public double getValorTotalAssings() {
        return this.valorTotalAssings;
    }

    public String getValorTotalAssingsDollar() {
        return NumberFormat.getCurrencyInstance(Locale.US).format(valorTotalAssings);
    }

    public void setTotalItensAssings(int totalItensJobsTo) {
        this.totalItensAssings = totalItensJobsTo;
    }

    public int getTotalItensAssings() {
        return totalItensAssings;
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
        return this.getOrigem() + "[ID=" + this.getEntidadeID() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        // 1. Verificação de referência (se é o mesmo objeto na memória)
        if (this == obj) {
            return true;
        }
        // 2. Verificação de nulidade do objeto 'obj' e compatibilidade de classe
        if (!(obj instanceof RotasDeTrabalho)) {
            return false;
        }
        // 3. Comparação dos atributos usando Objects.equals() para segurança contra NullPointerException
        RotasDeTrabalho other = (RotasDeTrabalho) obj;
        return Objects.equals(this.getEntidadeID(), other.getEntidadeID());
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(getEntidadeID());
    }

}
/*                    End of Class                                            */

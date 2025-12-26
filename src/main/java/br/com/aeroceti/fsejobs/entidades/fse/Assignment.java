/**
 * Projeto:  FSE Jobs  -  Organizador de Trabalhos no FSE
 * 
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Balneario Camboriu/SC  -  2025
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.entidades.fse;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Assignment", namespace = "https://server.fseconomy.net")
@XmlAccessorType(XmlAccessType.FIELD) 
public class Assignment {

    @XmlElement(name = "Id")
    private long id;

    @XmlElement(name = "Location")
    private String location;

    @XmlElement(name = "ToIcao")
    private String toIcao;

    @XmlElement(name = "FromIcao")
    private String fromIcao;

    @XmlElement(name = "Amount")
    private int amount;

    @XmlElement(name = "UnitType")
    private String unitType;

    @XmlElement(name = "Commodity")
    private String commodity;

    @XmlElement(name = "Pay")
    private double pay;

    @XmlElement(name = "Expires")
    private String expires;

    @XmlElement(name = "ExpireDateTime")
    private String expireDateTime; // Pode usar java.time.LocalDateTime para melhor tratamento de data/hora

    @XmlElement(name = "Express")
    private boolean express; // JAXB consegue converter "True"/"False" para boolean

    @XmlElement(name = "PtAssignment")
    private boolean ptAssignment;

    @XmlElement(name = "Type")
    private String type;

    @XmlElement(name = "AircraftId")
    private int aircraftId;

    // Construtor sem argumentos é obrigatório para JAXB
    public Assignment() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getToIcao() {
        return toIcao;
    }

    public void setToIcao(String toIcao) {
        this.toIcao = toIcao;
    }

    public String getFromIcao() {
        return fromIcao;
    }

    public void setFromIcao(String fromIcao) {
        this.fromIcao = fromIcao;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getExpireDateTime() {
        return expireDateTime;
    }

    public void setExpireDateTime(String expireDateTime) {
        this.expireDateTime = expireDateTime;
    }

    public boolean isExpress() {
        return express;
    }

    public void setExpress(boolean express) {
        this.express = express;
    }

    public boolean isPtAssignment() {
        return ptAssignment;
    }

    public void setPtAssignment(boolean ptAssignment) {
        this.ptAssignment = ptAssignment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(int aircraftId) {
        this.aircraftId = aircraftId;
    }

}
/*                    End of Class                                            */
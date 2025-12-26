/**
 * Projeto:  FSE Jobs  -  Organizador de Trabalhos no FSE
 * 
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Balneario Camboriu/SC  -  2025
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.entidades.fse.assigments;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(
    name = "AssignmentItemType",
    namespace = "https://server.fseconomy.net"
)
@XmlAccessorType(XmlAccessType.FIELD)
public class Assignment {

    @XmlElement(name = "Id")
    private long id;

    @XmlElement(name = "Status")
    private String status;

    @XmlElement(name = "Location")
    private String location;

    @XmlElement(name = "From")
    private String from;

    @XmlElement(name = "Destination")
    private String destination;

    @XmlElement(name = "Assignment")
    private String assignment;

    @XmlElement(name = "Amount")
    private int amount;

    @XmlElement(name = "Units")
    private String units;

    @XmlElement(name = "Pay")
    private double pay;

    @XmlElement(name = "PilotFee")
    private double pilotFee;

    @XmlElement(name = "Expires")
    private String expires;

    @XmlElement(name = "ExpireDateTime")
    private String expireDateTime; // Pode usar java.time.LocalDateTime para melhor tratamento de data/hora

    @XmlElement(name = "Type")
    private String type;

    @XmlElement(name = "Express")
    private boolean express; // JAXB consegue converter "True"/"False" para boolean

    @XmlElement(name = "PtAssignment")
    private boolean ptAssignment;

    @XmlElement(name = "Comment")
    private String comment;

    @XmlElement(name = "Locked")
    private String locked;

    // Construtor sem argumentos é obrigatório para JAXB
    public Assignment() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    public double getPilotFee() {
        return pilotFee;
    }

    public void setPilotFee(double pilotFee) {
        this.pilotFee = pilotFee;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

}
/*                    End of Class                                            */
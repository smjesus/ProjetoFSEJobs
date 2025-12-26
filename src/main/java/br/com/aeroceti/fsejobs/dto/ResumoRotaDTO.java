/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package br.com.aeroceti.fsejobs.dto;

/**
 *
 * @author smurilo
 */
public class ResumoRotaDTO {

    private String origem;
    private String destino;
    private int totalPAX;
    private double totalPay;

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getTotalPAX() {
        return totalPAX;
    }

    public void setTotalPAX(int totalPAX) {
        this.totalPAX = totalPAX;
    }

    public double getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(double totalPay) {
        this.totalPay = totalPay;
    }

}

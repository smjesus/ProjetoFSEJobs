/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.aeroceti.fsejobs.controladores;

import br.com.aeroceti.fsejobs.dto.ResumoRotaDTO;
import br.com.aeroceti.fsejobs.entidades.fse.IcaoJobsTo;
import br.com.aeroceti.fsejobs.servicos.XmlParserService;
import jakarta.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author smurilo
 */
//@Controller
//@RequestMapping("/jobs")
public class IcaoJobsToController {

    @Autowired
    private XmlParserService xmlParserService;   
    

    @GetMapping("/listar")
    public String getParsedJobs() {
        try {
            
            String icaoFrom = "SBGW";
            String icaoTo   = "SBAF";
            String ukey = "A962F6D042792170";
            ResumoRotaDTO resumo = new ResumoRotaDTO();
            resumo.setOrigem(icaoFrom);
            resumo.setDestino(icaoTo);
            resumo.setTotalPAX(0); resumo.setTotalPay(0);
            
            // Chama o serviço para converter o XML em objeto Java
            IcaoJobsTo jobs = xmlParserService.parseXmlIcaoTo(icaoTo, ukey);
            
// Imprime o resultado (usando os getters)
                System.out.println("\nResultado do Parsing:");
                System.out.println("Total de trabalhos: " + jobs.getTotal());
                
                jobs.getAssignments().stream().forEach( trabalho -> {
                    System.out.println("ID de Assignment: " + trabalho.getId());
                    System.out.println("FROM ...: " + trabalho.getFromIcao());
                    System.out.println("TO .....: " + trabalho.getToIcao());
                    System.out.println("VALOR ..: " + trabalho.getPay());
                    System.out.println("----------------------------------------------");
                    
                    if( trabalho.getFromIcao().trim().equalsIgnoreCase(icaoFrom) && 
                        trabalho.getToIcao().trim()  .equalsIgnoreCase(icaoTo)        ) {
                        
                        resumo.setTotalPAX( resumo.getTotalPAX()+1);
                        resumo.setTotalPay(resumo.getTotalPay()+trabalho.getPay());
                    }
                });
                
                System.out.println("RESUMO: " + resumo.getOrigem() + " - " +  resumo.getDestino() + " - Passageiros: " +  resumo.getTotalPAX() + "  - Valor:  " + resumo.getTotalPay() );
                
                
        } catch (JAXBException e) {
            e.printStackTrace();
            return "error";
        }
        return "listagem";

    }    
}

/**
 * Projeto:  FSE Jobs  -  Organizador de Trabalhos no FSE
 * 
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Balneario Camboriu/SC  -  2025
 * Equipe:   Murilo, Victor, Allan
 */
package br.com.aeroceti.fsejobs.controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller para a apresentacao do Dashboard do sistema.
 * 
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@Controller
public class DashboardController {
 
    Logger logger = LoggerFactory.getLogger(DashboardController.class);
    
    @GetMapping( {"/", "/login"} )
    public String getHomePage(){
        logger.info("Redirecionando view para pagina inicial do sistema (Login) ...");
        return "index";
    }

    @GetMapping("/dashboard")
    public String getDashboard(){
        logger.info("Redirecionando para o Dashboard...");
        return "dashboard";
    }
  
}
/*                    End of Class                                            */
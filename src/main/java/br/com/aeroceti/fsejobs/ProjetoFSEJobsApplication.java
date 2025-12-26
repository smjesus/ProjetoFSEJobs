/**
 * Projeto:  FSE Jobs  -  Organizador de Trabalhos no FSE
 * 
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Belo Horizonte/MG  -  2025
 * Equipe:   Murilo, Victor, Allan
 */
package br.com.aeroceti.fsejobs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Classe principal da Aplicacao usando o Spring Boot.
 * 
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class ProjetoFSEJobsApplication  extends SpringBootServletInitializer  {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoFSEJobsApplication.class, args);
	}

}

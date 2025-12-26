/**
 * Projeto:  FSE Jobs  -  Organizador de Trabalhos no FSE
 * 
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Balneario Camboriu/SC  -  2025
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.configuracoes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuracao do Bean para o WEBClient do FSEconomy.
 * Esta classe cria um objeto de comunicacao com o FSE.
 *
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@Configuration
public class WebClientConfig {
    
    private static final String FSECONOMY_BASE_URL = "https://server.fseconomy.net/data";

    @Bean
    public WebClient fseconomyWebClient(WebClient.Builder webClientBuilder) {
        // Cria uma instância do WebClient com a URL base predefinida
        return webClientBuilder.baseUrl(FSECONOMY_BASE_URL).build();
    }

}
/*                    End of Class                                            */
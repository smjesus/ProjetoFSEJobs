/**
 * Projeto:  FSE Jobs  -  Organizador de Trabalhos no FSE
 * 
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Balneario Camboriu/SC  -  2025
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.repositorios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Componente para o WEBClient do FSEconomy.
 * Esta classe cria um objeto de comunicacao com o FSE.
 *
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@Component
public class FseApiRepository {

    @Autowired
    private WebClient webClient;

    /**
     * Busca os trabalhos disponíveis (JobsTo) para um ICAO específico no FSEconomy.
     * @param icao O código ICAO (ex: SBAF)
     * @param key  Chave de Acesso do usuario ao FSEconomy
     * @return O XML retornado pela API como uma String.
     */
    public String fetchJobsXmlByIcao(String icao, String key) {
        
        // Define os parâmetros da requisição
        String xmlString = webClient.get()
            // Monta o restante da URI: ?format=xml&query=icao&search=jobsto&icaos=SBAF
            .uri(uriBuilder -> uriBuilder
                .queryParam("userkey", key)
                .queryParam("format", "xml")
                .queryParam("query", "icao")
                .queryParam("search", "jobsto")
                .queryParam("icaos", icao)
                .build())
            
            // Faz a requisição e transforma o corpo da resposta em String.
            // .retrieve() inicia a recuperação do corpo
            // .bodyToMono(String.class) espera a resposta e transforma em um objeto String reativo (Mono)
            // .block() bloqueia a thread *temporariamente* para obter o resultado. 
            //         (Usamos block() aqui para simplificar a integração com o seu código de parsing síncrono)
            .retrieve()
            .bodyToMono(String.class)
            .block();
            
        return xmlString;
    }
    
    public String fetchJobsXmlAssigned(String key) {
            // Define os parâmetros da requisição
        String xmlString = webClient.get()
        // monta o restante da URI: ?userkey=A962F6D042792170&format=xml&query=assignments&search=key&readaccesskey=A962F6D042792170
            .uri(uriBuilder -> uriBuilder
                .queryParam("userkey", key)
                .queryParam("format", "xml")
                .queryParam("query", "assignments")
                .queryParam("search", "key")
                .queryParam("readaccesskey", key)
                .build())
            .retrieve()
            .bodyToMono(String.class)
            .block();
        return xmlString;
    }
    
}
/*                    End of Class                                            */
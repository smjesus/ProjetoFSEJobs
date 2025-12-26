/**
 * Projeto:  FSE Jobs  -  Organizador de Trabalhos no FSE
 * 
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Balneario Camboriu/SC  -  2025
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.configuracoes;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import br.com.aeroceti.fsejobs.entidades.fse.IcaoJobsTo;
import br.com.aeroceti.fsejobs.entidades.fse.assigments.AssignmentItems;

/**
 * Configuracao para o Parser XML do JAXB.
 *
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@Configuration
public class XmlParserConfig {
    // 1. Cria o JAXBContext como Bean.
    @Bean
    public JAXBContext jaxbContext() throws JAXBException {
        // Inicializa o contexto para a classe raiz.
        return JAXBContext.newInstance(
                IcaoJobsTo.class,
                AssignmentItems.class
        );
    }

    // 2. Cria o Unmarshaller como Bean.
    // O JAXBContext é injetado automaticamente pelo Spring.
    @Bean
    public Unmarshaller unmarshaller(JAXBContext jaxbContext) throws JAXBException {
        // Cria o Unmarshaller a partir do contexto.
        return jaxbContext.createUnmarshaller();
    }

}
/*                    End of Class                                            */
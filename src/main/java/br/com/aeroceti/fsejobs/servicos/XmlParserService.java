/**
 * Projeto:  FSE Jobs  -  Organizador de Trabalhos no FSE
 * 
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Balneario Camboriu/SC  -  2025
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.servicos;

import br.com.aeroceti.fsejobs.entidades.fse.assigments.AssignmentItems;
import br.com.aeroceti.fsejobs.entidades.fse.IcaoJobsTo;
import br.com.aeroceti.fsejobs.repositorios.FseApiRepository;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.StringReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servico para realizar o Parser do XML do FSEconomy.
 *
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@Service
public class XmlParserService {
    
    @Autowired
    // O Unmarshaller é injetado automaticamente pelo Spring a partir do Bean configurado.
    private Unmarshaller unmarshaller;
    
    @Autowired
    private FseApiRepository fseApiRepository;

    /**
     * Método para realizar o Unmarshalling dos dados vindos via XML para a classe IcaoJobsTo
     * 
     * @throws jakarta.xml.bind.JAXBException
     * @param icao - Indicador do Aerodromo de destino 
     * @param userKey - KEY do usuario no FSEconomy
     * @return Objeto IcaoJobsTo contendo todos os dados recebidos
     */
    public IcaoJobsTo parseXmlIcaoTo(String icao, String userKey) throws JAXBException {
        // 1. CHAMA A API EXTERNA
        String xmlString = fseApiRepository.fetchJobsXmlByIcao(icao, userKey);
        // 2. FAZ O PARSING (Unmarshalling)
        StringReader reader = new StringReader(xmlString);
        // O método unmarshal é sincronizado (thread-safe) para evitar problemas de concorrência.
        synchronized (unmarshaller) {
            // Realiza o Unmarshalling e faz o cast para a classe raiz.
            return (IcaoJobsTo) unmarshaller.unmarshal(reader);
        }
    }
    /**
     * Método para realizar o Unmarshalling dos dados vindos via XML para a classe IcaoJobsTo
     * 
     * @throws jakarta.xml.bind.JAXBException
     * @param userKey - KEY do usuario no FSEconomy
     * @return Objeto IcaoJobsTo contendo todos os dados recebidos
     */    
    public AssignmentItems parseXmlAssigned(String userKey) throws JAXBException {
        String xmlString = fseApiRepository.fetchJobsXmlAssigned(userKey);
        StringReader reader = new StringReader(xmlString);
        // O método unmarshal é sincronizado (thread-safe) para evitar problemas de concorrência.
        synchronized (unmarshaller) {
            // Realiza o Unmarshalling e faz o cast para a classe raiz.
            return (AssignmentItems) unmarshaller.unmarshal(reader);
        }
    }
    
}
/*                    End of Class                                            */
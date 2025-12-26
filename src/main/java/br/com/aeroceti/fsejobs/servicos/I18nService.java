/**
 * Projeto:  FSEJobs  -  Sistema de busca de Josb no FSEconomy
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Balneario Camboriu/SC  -  2025
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.servicos;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 * Esta classe cria o servico para a internacionalizacao (i18n).
 * 
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@Service
public class I18nService {

	@Autowired
	private MessageSource message; // internacionalização
	
	public String buscarMensagem(String chave, Locale locale) {
		String msn = message.getMessage(chave, null, locale);
		return msn;
	}
}
/*                    End of Class                                            */
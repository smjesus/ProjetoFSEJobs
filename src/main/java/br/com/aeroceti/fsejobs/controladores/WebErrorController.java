/**
 * Projeto:  FSE Jobs  -  Organizador de Trabalhos no FSE
 * 
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Balneario Camboriu/SC  -  2025
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.controladores;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import br.com.aeroceti.fsejobs.servicos.I18nService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;

/**
 * Controller para a apresentacao da pagina de erro.
 * 
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@Controller
public class WebErrorController implements ErrorController {

    @Autowired
    private I18nService i18svc;
    
    Logger logger = LoggerFactory.getLogger(WebErrorController.class);
    
    @GetMapping("/error")
    public ModelAndView handleError (HttpServletRequest request, Model model, Locale locale) {
        List<String> rotas = Arrays.asList("/usuario", "/rotas", "/dashboard");
        var mensagem1 = i18svc.buscarMensagem("error.all.page.1", locale);
        var mensagem2 = i18svc.buscarMensagem("error.all.page.2", locale);
        logger.info("Tratamento de erro de requisicao vinda de " + request.getRemoteAddr() );

        Object httpStatus = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object uri = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);

        if(httpStatus != null){
            int code = Integer.parseInt(httpStatus.toString());
            // Erros de acesso negado: 
            if( code == 403 ) {
                logger.info("Configurando mensagem 403 ...");
                mensagem1 = i18svc.buscarMensagem("error.403.page.1", locale);
                mensagem2 = i18svc.buscarMensagem("error.403.page.2", locale);
            }
            // Erros de solicitacao invalida:
            if( (code >399 && code < 499) && (code != 403) ) {
                logger.info("Configurando mensagem de Solicitacao Invalida ...");
                mensagem1 = i18svc.buscarMensagem("error.400.page.1", locale);
                mensagem2 = i18svc.buscarMensagem("error.400.page.2", locale);
            }
            // Erros de processamento:
            if( code >499 && code < 599){
                logger.info("Configurando mensagem Erro Interno ...");
                mensagem1 = i18svc.buscarMensagem("error.500.page.1", locale);
                mensagem2 = i18svc.buscarMensagem("error.500.page.2", locale);
            }
        }
        model.addAttribute("errorPage1", mensagem1);
        model.addAttribute("errorPage2", mensagem2);
        mensagem1 = rotas.stream().anyMatch(uri.toString()::contains) ? "Dashboard" : "Public";
        model.addAttribute("errorOrigem", mensagem1);
        model.addAttribute("errorType", "NOTFOUND");
        logger.info("Encaminhando para a pagina de erro ...");
        return new ModelAndView("error");
    }
    
}
/*                    End of Class                                            */
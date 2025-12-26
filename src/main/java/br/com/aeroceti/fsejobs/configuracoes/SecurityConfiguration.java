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
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import br.com.aeroceti.fsejobs.componentes.CustomAuthenticationFailureHandler;
import br.com.aeroceti.fsejobs.servicos.UsuarioOAuth2UserService;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Esta classe realiza a configuracao da Seguranca do Spring.
 * 
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    
    private final CustomAuthenticationFailureHandler failureHandler;    
    private final UsuarioOAuth2UserService usuarioOAuth2UserService;
    
    public SecurityConfiguration(CustomAuthenticationFailureHandler failure, UsuarioOAuth2UserService usrOAuth2UserSvc) {
        this.failureHandler = failure;
        this.usuarioOAuth2UserService = usrOAuth2UserSvc;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        
                        .requestMatchers(HttpMethod.GET, "/error").permitAll()
                        .requestMatchers(HttpMethod.GET, "/termos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/novaconta").permitAll()
                        .requestMatchers(HttpMethod.GET, "/privacidade").permitAll()
                        .requestMatchers(HttpMethod.GET, "/documentacao").permitAll()
                        .requestMatchers(HttpMethod.GET, "/usuario/uuid/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuario/novaconta").permitAll()
                        .requestMatchers("/content-scripts/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/css/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/assets/**").permitAll()
                        .anyRequest().authenticated()
                        

                )
                
                .oauth2Login( oac -> oac
                        .loginPage("/login") 
                        .userInfoEndpoint(userInfo ->
                            userInfo.userService(usuarioOAuth2UserService)
                        )
                        .defaultSuccessUrl("/dashboard")
                        .permitAll() 
                )
                
                .formLogin( (formulario) -> formulario
                        .loginPage("/login")
                        .failureHandler(failureHandler)
                        .defaultSuccessUrl("/dashboard")
                        .permitAll() 
                )
                
                .rememberMe(remember -> remember
                        .key("key-Remember-Me-OnFSE-JOBS")
                        .tokenValiditySeconds(5 * 24 * 60 * 60) // 5 dias
                )
        
                .logout( (logout) -> logout
                        .logoutUrl("/logout").logoutSuccessUrl("/").permitAll() )
                
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
/*                    End of Class                                            */
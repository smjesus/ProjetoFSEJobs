/**
 * Projeto:  FSE Jobs  -  Organizador de Trabalhos no FSE
 * 
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Balneario Camboriu/SC  -  2025
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.repositorios;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import br.com.aeroceti.fsejobs.entidades.user.Preferencias;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface para o Repositorio de Preferencias.
 * Esta classe abstrai diversos metodos de persistencia do JPA.
 *
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@Repository
public interface PreferenciasRepository extends JpaRepository<Preferencias, Long> {

    // obtem o numero de Permissoes por ID
    int countByEntidadeID(Long entidadeID);

    // obtem uma Permissao atraves do ID
    Optional<Preferencias> findByEntidadeID(Long chavePesquisa);

    // obtem uma Preferencia de um usuario
    Optional<Preferencias> findByUsuarioEntidadeID(Long usuarioId);

    // obtem uma Permissao atraves do nome
    Optional<Preferencias> findByApiKey(String chavePesquisa);

}
/*                    End of Class                                            */
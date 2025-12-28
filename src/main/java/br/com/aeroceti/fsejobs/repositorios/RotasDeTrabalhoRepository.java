/**
 * Projeto:  FSE Jobs  -  Organizador de Trabalhos no FSE
 * 
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Balneario Camboriu/SC  -  2025
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.repositorios;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.aeroceti.fsejobs.entidades.user.RotasDeTrabalho;

/**
 * Interface para o Repositorio de Rotas do Usuario.
 * Esta classe abstrai diversos metodos de persistencia do JPA.
 *
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@Repository
public interface RotasDeTrabalhoRepository extends JpaRepository<RotasDeTrabalho, Long> {

    // obtem o numero de Permissoes por ID
    int countByEntidadeID(Long entidadeID);

    // obtem uma Permissao atraves do ID
    Optional<RotasDeTrabalho> findByEntidadeID(Long chavePesquisa);
    
    // obtem uma lista de rotas de um usuario (ID)
    List<RotasDeTrabalho> findByUsuarioEntidadeID(Long usuarioId);
    
    // obtem uma lista de rotas PAGINADA por USUARIO
    Page<RotasDeTrabalho> findByUsuarioEntidadeID(Long usuarioId, Pageable pageable);

    // obtem uma lista de rotas PAGINADA
    @Override
    @EntityGraph(attributePaths = "usuario")
    Page<RotasDeTrabalho> findAll(Pageable pageable);
        
}
/*                    End of Class                                            */
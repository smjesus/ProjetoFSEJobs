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
import br.com.aeroceti.fsejobs.entidades.user.NivelAcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Interface para o Repositorio de NivelAcesso (Niveis de Acesso).
 * Esta classe abstrai diversos metodos de persistencia do JPA.
 *
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@Repository
public interface NivelAcessoRepository extends JpaRepository<NivelAcesso, Long> {

    // obtem o numero de Permissoes por nome
    int countByNome(String ruleName);    

    // obtem o numero de Permissoes por ID
    int countByEntidadeID(Long entidadeID);

    // obtem uma Permissao atraves do ID
    Optional<NivelAcesso> findByEntidadeID(Long chavePesquisa);

    // obtem uma Permissao atraves do nome
    Optional<NivelAcesso> findByNome(String chavePesquisa);
    
    // obtem uma lista de Permissoes ORDENADA por nome
    List<NivelAcesso> findByOrderByNomeAsc();

    // obtem uma lista de Niveis de Acesso com PAGINACAO
    @Query(value = "select * from NivelAcesso order by nome ASC", nativeQuery = true )
    Page<NivelAcesso> findAllNivelAcesso(Pageable page);

    // obtem uma Permissao atraves do nome
    @Query("SELECT n FROM NivelAcesso n LEFT JOIN FETCH n.usuarios WHERE n.nome = :nome")
    Optional<NivelAcesso> findByNomeAndUsuarios(@Param("nome") String chavePesquisa);

}
/*                    End of Class                                            */
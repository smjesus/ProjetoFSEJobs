/**
 * Projeto:  FSE Jobs  -  Organizador de Trabalhos no FSE
 * 
 * Gerente:  Sergio Murilo  -  smurilo at GMail
 * Data:     Balneario Camboriu/SC  -  2025
 * Equipe:   Murilo, Victor
 */
package br.com.aeroceti.fsejobs.repositorios;

import br.com.aeroceti.fsejobs.entidades.user.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Interface para o Repositorio de Usuarios.
 * Esta classe abstrai diversos metodos de persistencia do JPA.
 *
 * @author Sergio Murilo - smurilo at Gmail.com
 * @version 1.0
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // 🔍 Busca um colaborador específico trazendo NivelAcesso e Servidores
    @Query("""
           SELECT c FROM Usuario c
           LEFT JOIN FETCH c.nivelAcesso
           WHERE c.entidadeID = :id
           """)
    Optional<Usuario> findByEntidadeIDComRelacionamentos(@Param("id") Long id);

    // 🔍 Busca todos os colaboradores com seus Niveis de Acesso e Servidores
    @Query("""
           SELECT DISTINCT c FROM Usuario c
           LEFT JOIN FETCH c.nivelAcesso
           """)
    List<Usuario> findAllComRelacionamentos();    

    // obtem uma lista de Usuarioes com PAGINACAO
    @Query(value = "select * from Usuario order by nomePessoal ASC", nativeQuery = true )
    Page<Usuario> findAllUsuarios(Pageable page);
    
    // obtem uma Permissao atraves do nome
    Optional<Usuario> findByNome(String chavePesquisa);

    // obtem uma Permissao atraves do email
    Optional<Usuario> findByEmail(String chavePesquisa);

    // obtem uma Permissao atraves do ID
    Optional<Usuario> findByEntidadeID(Long chavePesquisa);

    // obtem uma lista de Permissoes ORDENADA por nome
    List<Usuario> findByOrderByNomeAsc();

    // obtem o numero de Permissoes por ID
    int countByEntidadeID(Long entidadeID);
    
    // obtem o numero de Permissoes por nome
    int countByEmail(String contaEmail);    

}
/*                    End of Class                                            */
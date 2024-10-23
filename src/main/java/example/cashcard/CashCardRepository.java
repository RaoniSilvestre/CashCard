package example.cashcard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repositório de dados para a entidade CashCard.
 * 
 * Este repositório fornece operações CRUD e suporte à paginação e ordenação para CashCards.
 * Estende {@link CrudRepository} para operações básicas e {@link PagingAndSortingRepository}
 * para paginação e ordenação.
 */
interface CashCardRepository extends CrudRepository<CashCard, Long>, PagingAndSortingRepository<CashCard, Long> {

    /**
     * Encontra um CashCard pelo ID e proprietário (owner).
     * 
     * @param id o ID do CashCard.
     * @param owner o nome do proprietário do CashCard.
     * @return o CashCard correspondente ao ID e proprietário, ou null se não for encontrado.
     */
    CashCard findByIdAndOwner(Long id, String owner);


    /**
     * Encontra todos os CashCards de um proprietário específico, com suporte à paginação.
     * 
     * @param owner o nome do proprietário dos CashCards.
     * @param pageRequest a configuração de paginação e ordenação.
     * @return uma página de CashCards pertencentes ao proprietário.
     */
    Page<CashCard> findByOwner(String owner, PageRequest pageRequest);


    /**
     * Verifica se existe um CashCard com o ID e proprietário especificados.
     * 
     * @param id o ID do CashCard.
     * @param owner o nome do proprietário do CashCard.
     * @return true se o CashCard existir, false caso contrário.
     */
    boolean existsByIdAndOwner(Long id, String owner);
}

package example.cashcard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.security.Principal;


/**
 * Controlador REST para gerenciamento de CashCards.
 * 
 * Este controlador fornece endpoints para criar, atualizar, buscar e deletar CashCards.
 */
@RestController
@RequestMapping("/cashcards")
class CashCardController {
    /**
     * Repositório utilizado pelo controller para acessar os dados
     */
    private final CashCardRepository cashCardRepository;
    /**
    * Construtor do CashCardController.
    * 
    * @param cashCardRepository o repositório de CashCard para acesso aos dados.
    */
    private CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }
    
    /**
    * Busca um CashCard pelo ID para o usuário autenticado.
    * 
    * @param requestedId ID do CashCard a ser buscado.
    * @param principal   o usuário autenticado que está fazendo a solicitação.
    * @return ResponseEntity contendo o CashCard encontrado ou um 404 se não encontrado.
    */
    @GetMapping("/{requestedId}")
    private ResponseEntity<CashCard> findById(@PathVariable Long requestedId, Principal principal) {
        CashCard cashCard = cashCardRepository.findByIdAndOwner(requestedId, principal.getName());

        if (cashCard != null) {
            return ResponseEntity.ok(cashCard);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Busca todos os CashCards para o usuário autenticado, com suporte a paginação.
     * 
     * @param pageable  parâmetros de paginação e ordenação.
     * @param principal o usuário autenticado que está fazendo a solicitação.
     * @return ResponseEntity contendo a lista de CashCards do usuário.
     */
    @GetMapping
    private ResponseEntity<List<CashCard>> findAll(Pageable pageable, Principal principal) {
        Page<CashCard> page = cashCardRepository.findByOwner(principal.getName(),
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "amount"))));
        return ResponseEntity.ok(page.getContent());
    }

    /**
     * Cria um novo CashCard para o usuário autenticado.
     * 
     * @param newCashCardRequest dados do CashCard a ser criado.
     * @param ucb                UriComponentsBuilder para construção da URI de resposta.
     * @param principal          o usuário autenticado que está criando o CashCard.
     * @return ResponseEntity com a localização do novo CashCard criado.
     */
    @PostMapping
    private ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb,
            Principal principal) {
        CashCard cashCardWithOwner = new CashCard(null, newCashCardRequest.amount(), principal.getName());
        CashCard savedCashCard = cashCardRepository.save(cashCardWithOwner);
        URI locationOfNewCashCard = ucb
                .path("cashcards/{id}")
                .buildAndExpand(savedCashCard.id())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }

    /**
     * Atualiza um CashCard existente para o usuário autenticado.
     * 
     * @param requestedId   ID do CashCard a ser atualizado.
     * @param cashCardUpdate dados atualizados do CashCard.
     * @param principal     o usuário autenticado que está atualizando o CashCard.
     * @return ResponseEntity com status 204 (No Content) se o CashCard foi atualizado ou 404 se não foi encontrado.
     */
    @PutMapping("/{requestedId}")
    private ResponseEntity<Void> putCashCard(@PathVariable Long requestedId, @RequestBody CashCard cashCardUpdate,
            Principal principal) {
        CashCard cashCard = cashCardRepository.findByIdAndOwner(requestedId, principal.getName());

        if (cashCard == null) {
            return ResponseEntity.notFound().build();
        }

        CashCard updatedCashCard = new CashCard(cashCard.id(), cashCardUpdate.amount(), principal.getName());
        cashCardRepository.save(updatedCashCard);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deleta um CashCard existente para o usuário autenticado.
     * 
     * @param requestedId ID do CashCard a ser deletado.
     * @param principal   o usuário autenticado que está deletando o CashCard.
     * @return ResponseEntity com status 204 (No Content) se o CashCard foi deletado ou 404 se não foi encontrado.
     */
    @DeleteMapping("/{requestedId}")
    private ResponseEntity<Void> deleteCashCard(@PathVariable Long requestedId, Principal principal) {
        if (!cashCardRepository.existsByIdAndOwner(requestedId, principal.getName())) {
            return ResponseEntity.notFound().build();
        }
        cashCardRepository.deleteById(requestedId);
        return ResponseEntity.noContent().build();
    }
}

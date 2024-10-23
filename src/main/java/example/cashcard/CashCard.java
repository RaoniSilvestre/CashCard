package example.cashcard;

import org.springframework.data.annotation.Id;

/**
 * Representa um cartão de pagamento com um identificador, saldo e proprietário.
 *
 * <p>Um {@code CashCard} contém um identificador único, um saldo que
 * representa a quantidade de dinheiro disponível no cartão e o nome do
 * proprietário do cartão.</p>
 *
 * @param id O identificador único do cartão.
 * @param amount O saldo disponível no cartão.
 * @param owner O nome do proprietário do cartão.
 */
record CashCard(@Id Long id, Double amount, String owner) {
}

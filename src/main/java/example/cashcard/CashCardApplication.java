package example.cashcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * A classe principal da aplicação CashCard.
 *
 * <p>Esta aplicação é uma aplicação Spring Boot que gerencia cartões de
 * pagamento. O método {@link #main(String[])} é o ponto de entrada da aplicação.</p>
 */
@SpringBootApplication
public class CashCardApplication {
    /**
     * O ponto de entrada da aplicação CashCard.
     *
     * @param args Argumentos da linha de comando que podem ser passados ao
     *              iniciar a aplicação.
     */
    public static void main(String[] args) {
        SpringApplication.run(CashCardApplication.class, args);
    }
}

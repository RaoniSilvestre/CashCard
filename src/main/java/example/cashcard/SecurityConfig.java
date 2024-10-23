package example.cashcard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuração de segurança da aplicação.
 * 
 * Define as regras de segurança para o acesso aos endpoints e usuários em memória.
 */
@Configuration
class SecurityConfig {
    /**
     * Define a cadeia de filtros de segurança para proteger os endpoints da aplicação.
     * 
     * A rota "cashcards/**" requer autenticação com a role "CARD-OWNER". Utiliza
     * autenticação básica HTTP (HTTP Basic).
     * 
     * @param http o objeto HttpSecurity para configurar as regras de segurança.
     * @return a configuração finalizada do filtro de segurança.
     * @throws Exception caso ocorra um erro na configuração de segurança.
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("cashcards/**")
                        .hasRole("CARD-OWNER"))
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    /**
     * Define um serviço de usuários em memória para fins de teste.
     * 
     * Cria dois usuários: "sarah1" com a role "CARD-OWNER" e "hank-owns-no-cards"
     * com a role "NON-OWNER".
     * 
     * @param passwordEncoder o codificador de senhas usado para criptografar as senhas dos usuários.
     * @return um serviço de gerenciamento de usuários em memória.
     */
    @Bean
    UserDetailsService testOnlyUser(PasswordEncoder passwordEncoder) {
        User.UserBuilder users = User.builder();
        UserDetails sarah = users
                .username("sarah1")
                .password(passwordEncoder.encode("abc123"))
                .roles("CARD-OWNER")
                .build();

        UserDetails hankOwnsNoCards = users
                .username("hank-owns-no-cards")
                .password(passwordEncoder.encode("qrs456"))
                .roles("NON-OWNER")
                .build();

        return new InMemoryUserDetailsManager(sarah, hankOwnsNoCards);
    }

    
    /**
     *  Encoder básico de senhas
     *
     * @return PasswordEncoder
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

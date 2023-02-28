package com.generation.italy.projectwork.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.generation.italy.projectwork.auth.AuthService;



@Configuration
@EnableWebSecurity
// Configurazione base di SpringSecurity, andiamo a definirne uno nostro
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter { 
	
	// Oggetto che serve per criptografare le password
	private final PasswordEncoder passwordEncoder;
	// Il servizio che ci fornisce gli utenti
	private final AuthService authService;

	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, AuthService authService) {
		this.passwordEncoder = passwordEncoder;
		this.authService = authService;
	}

	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable() // Andate a vedere cos'è il csrf, in poche parole, la disabilito se mi serve
		// utilizzare l'app anche come sistema rest per applicazioni "esterne"
				.authorizeRequests().antMatchers("/", "index.html", "/css/**", "/js/**", "style.css", "/cookie", "/signup.html", "/signup", "/login", "/fail.html", "/forbidden.html", "/assets/**"
						).permitAll() 
				
				
				
				// tutti possono accedere a questi percorsi
				.antMatchers("/index.html").permitAll()
				
				.antMatchers("/azienda/modifica/**").hasAnyRole(Roles.ROOT)
				.antMatchers("/aziende/delete/**").hasAnyRole(Roles.ROOT)
				.antMatchers("/personale/delete/**").hasAnyRole(Roles.ADMIN, Roles.ROOT)
				.antMatchers("/aggiungiazienda.html").hasAnyRole(Roles.ROOT)
				.antMatchers("/aggiungidipendente.html").hasAnyRole(Roles.ADMIN, Roles.ROOT)
				
				.antMatchers("/permessi.html").hasAnyRole(Roles.ROOT)
				
				
			
				// Questa riga sotto mi permette di fare tutto anche se non sono loggato
				.anyRequest().permitAll() //authenticated() //permitAll()   //authenticated() // tutte le (altre) richieste richiedono authenticazione
				
				
				.and()
				.exceptionHandling()
//				.accessDeniedPage("/forbidden.html")
				.and()
				// SpringSecurity di base ci fornisce una pagina di default, ma andiamo a
				// cambiarla con una nostra
				.formLogin()
				.loginPage("/login.html")// indirizzo a cui arriveranno le richieste login
				.loginProcessingUrl("/login")
				.permitAll() // Giustamente tutti devono riuscire ad accedere
				.defaultSuccessUrl("/index.html", true)// se riesce ad accedere lo rimando ad index.html
				.failureUrl("/fail.html")
				.and()
				// configuriamo anche la pagina per il logout
				.logout().logoutUrl("/logout")
				//.logoutSuccessUrl("/loggedout.html")// indirizzo per fare logout
				.clearAuthentication(true).logoutSuccessUrl("/index.html")
				;

	}

	@Bean 
	// questo oggetto servirà a spring security per andare a cercare gli utenti da un service
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(authService);
		return provider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

}

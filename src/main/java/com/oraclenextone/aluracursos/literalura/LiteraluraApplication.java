package com.oraclenextone.aluracursos.literalura;

import com.oraclenextone.aluracursos.literalura.principal.Principal;
import com.oraclenextone.aluracursos.literalura.repository.AutorRepository;
import com.oraclenextone.aluracursos.literalura.repository.LibroRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner{

	@Autowired
	private LibroRepository repository;
	@Autowired
	private AutorRepository autorRepository;

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();

		System.setProperty("DB_HOST", dotenv.get("DB_HOST"));
		System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
		System.setProperty("DB_USER", dotenv.get("DB_USER"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository, autorRepository);
		principal.aplicacionLiteralura();
    }
}

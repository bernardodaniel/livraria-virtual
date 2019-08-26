package br.com.uniciv.livraria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LivroRepositorio {

	private Map<Long, Livro> livros = new HashMap<Long, Livro>();

	public LivroRepositorio() {
		Livro livro1 = new Livro(
				1L, "Livro A", "ISBN-1234", "Genero A", 23.99, "Autor 1");
		Livro livro2 = new Livro(
				2L, "Livro B", "ISBN-4321", "Genero B", 19.99, "Autor 2");
		
		livros.put(livro1.getId(), livro1);
		livros.put(livro2.getId(), livro2);
	}
	
	public List<Livro> getLivros() {
		return new ArrayList<>(livros.values());
	}
	
}

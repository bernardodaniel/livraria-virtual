package br.com.uniciv.livraria;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "livros")
@XmlAccessorType(XmlAccessType.FIELD)
public class Livros {

	@XmlElement(name="livro")
	private List<Livro> livros = new ArrayList<Livro>();
	
	public List<Livro> getLivros() {
		return livros;
	}
	
	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}
}

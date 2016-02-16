package mcomic.com.model;

import java.util.List;

public class Genero extends AbstractModel {
	private String nome;
	
	private List<Manga> mangas;

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the mangas
	 */
	public List<Manga> getMangas() {
		return mangas;
	}

	/**
	 * @param mangas the mangas to set
	 */
	public void setMangas(List<Manga> mangas) {
		this.mangas = mangas;
	}
	
	

}

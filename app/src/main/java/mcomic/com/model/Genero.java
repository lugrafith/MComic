package mcomic.com.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;
@DatabaseTable
public class Genero extends AbstractModel {
	@DatabaseField
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

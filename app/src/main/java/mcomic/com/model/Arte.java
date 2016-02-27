package mcomic.com.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = "arte")
public class Arte extends AbstractModel {
	@DatabaseField
	private String nome;
	@DatabaseField
	private String descricao;
	
	private List<Manga> mangas;

	public Arte() {
		this.mangas = new ArrayList<>();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Manga> getMangas() {
		return mangas;
	}

	public void setMangas(List<Manga> mangas) {
		this.mangas = mangas;
	}
}

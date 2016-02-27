package mcomic.com.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by lu_gr on 25/02/2016.
 */
@DatabaseTable(tableName = "favorito")
public class Favorito extends AbstractModel{
    @DatabaseField(foreign = true, unique = true)
    private Manga manga;
    @DatabaseField(foreign = true)
    private Capitulo capituloAtual;
    @DatabaseField(foreign = true)
    private Page paginaAtual;
    @DatabaseField
    private boolean favorito;

    public Favorito() {

    }

    public Favorito(Manga manga, Capitulo capituloAtual, Page paginaAtual, boolean favorito) {
        this.manga = manga;
        this.capituloAtual = capituloAtual;
        this.paginaAtual = paginaAtual;
        this.favorito = favorito;
        setUrl(manga.getUrl());
    }

    public Manga getManga() {
        return manga;
    }

    public void setManga(Manga manga) {
        this.manga = manga;
    }

    public Capitulo getCapituloAtual() {
        return capituloAtual;
    }

    public void setCapituloAtual(Capitulo capituloAtual) {
        this.capituloAtual = capituloAtual;
    }

    public Page getPaginaAtual() {
        return paginaAtual;
    }

    public void setPaginaAtual(Page paginaAtual) {
        this.paginaAtual = paginaAtual;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }
}

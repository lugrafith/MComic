package mcomic.com.model;

import android.graphics.Bitmap;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@DatabaseTable
public class Manga extends AbstractModel {

    @DatabaseField
    private String title;
    @DatabaseField
    private String urlCover;
    @DatabaseField
    private Source source;
    @DatabaseField
    private String sinopse;
    @DatabaseField
    private String ano;
    @DatabaseField(foreign = true)
    private Arte arte;
    @DatabaseField(foreign = true)
    private Autor autor;
    //@ForeignCollectionField
    private List<Genero> generos;
    //@ForeignCollectionField(eager = false)
    private List<Capitulo> capitulos;

    private transient Bitmap imageCover;

    public Manga() {
        capitulos = new ArrayList<>();
        generos = new ArrayList<>();
    }

    public Manga(Source source) {
        this.source = source;
        capitulos = new ArrayList<>();
        generos = new ArrayList<>();
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlCover() {
        return urlCover;
    }

    public void setUrlCover(String urlCover) {
        this.urlCover = urlCover;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public Arte getArte() {
        return arte;
    }

    public void setArte(Arte arte) {
        this.arte = arte;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

    public List<Capitulo> getCapitulos() {
        return capitulos;
    }

    public void setCapitulos(List<Capitulo> capitulos) {
        this.capitulos = capitulos;
    }

    public Bitmap getImageCover() {
        return imageCover;
    }

    public void setImageCover(Bitmap imageCover) {
        this.imageCover = imageCover;
    }

    @Override
    public String toString() {
        return source + " | " + title + " - " + getUrl() + " - " + urlCover + " - " + sinopse;
    }
}

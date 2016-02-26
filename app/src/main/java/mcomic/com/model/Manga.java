package mcomic.com.model;

import android.graphics.Bitmap;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = "manga")
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
    //@DatabaseField
    private Arte arte;
    //@DatabaseField
    private Autor autor;
    //@ForeignCollectionField
    private List<Genero> generos;
    //@ForeignCollectionField(eager = false)
    private List<Capitulo> capitulos;

    private transient Bitmap imageCover;

    public Manga() {
    }

    public Manga(Source source) {
        this.source = source;
        capitulos = new ArrayList<>();
        generos = new ArrayList<>();
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the urlCover
     */
    public String getUrlCover() {
        return urlCover;
    }

    /**
     * @param urlCover the urlCover to set
     */
    public void setUrlCover(String urlCover) {
        this.urlCover = urlCover;
    }


    public Source getSource() {
        return source;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return source.getName() + " | " + title + " - " + getUrl() + " - " + urlCover + " - " + sinopse;
    }

    /**
     * @return the ano
     */
    public String getAno() {
        return ano;
    }

    /**
     * @param ano the ano to set
     */
    public void setAno(String ano) {
        this.ano = ano;
    }

    /**
     * @return the arte
     */
    public Arte getArte() {
        return arte;
    }

    /**
     * @param arte the arte to set
     */
    public void setArte(Arte arte) {
        this.arte = arte;
    }

    /**
     * @return the autor
     */
    public Autor getAutor() {
        return autor;
    }

    /**
     * @param autor the autor to set
     */
    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    /**
     * @return the generos
     */
    public List<Genero> getGeneros() {
        return generos;
    }

    /**
     * @param generos the generos to set
     */
    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

    /**
     * @return the capitulos
     */
    public List<Capitulo> getCapitulos() {
        return capitulos;
    }

    /**
     * @param capitulos the capitulos to set
     */
    public void setCapitulos(List<Capitulo> capitulos) {
        this.capitulos = capitulos;
    }

    public Bitmap getImageCover() {
        return imageCover;
    }

    public void setImageCover(Bitmap imageCover) {
        this.imageCover = imageCover;
    }
}

package mcomic.com.model;

import java.util.ArrayList;
import java.util.List;

public class Capitulo extends AbstractModel {

    private String nome;

    private List<Page> pages;

    private String urlPadraoCapitulo;

    private int totalPaginas;

    public Capitulo() {
        pages = new ArrayList<>();
    }

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

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public String getUrlPadraoCapitulo() {
        return urlPadraoCapitulo;
    }

    public void setUrlPadraoCapitulo(String urlPadraoCapitulo) {
        this.urlPadraoCapitulo = urlPadraoCapitulo;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }
}

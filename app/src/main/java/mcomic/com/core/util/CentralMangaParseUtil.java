package mcomic.com.core.util;


import android.annotation.TargetApi;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

import mcomic.com.core.Aplication;
import mcomic.com.model.Source;
import mcomic.com.model.Arte;
import mcomic.com.model.Autor;
import mcomic.com.model.Capitulo;
import mcomic.com.model.Genero;
import mcomic.com.model.Manga;

public final class CentralMangaParseUtil {
    //central
    private static final String startUrl_CENTRAL = "<a href=\"";
    private static final String endUrl_CENTRAL = "\">";

    /**
     * COnverte o HTML para o Objecto Manga com Titulo e Url de Acesso
     *
     * @param html
     * @return
     */
    public static List<Manga> htmlToMangaCentral(StringBuilder html) throws StringIndexOutOfBoundsException {
        List<Manga> mangas = new ArrayList<>();
        //Central
        String index = "<table class=\"table table-striped table-bordered\">";
        String end = "</td></tr></tbody></table>";
        html = new StringBuilder(html.substring(html.indexOf(index), html.lastIndexOf(end) + end.length()));
        String startManga = "<tr><td>";
        String part = "</td></tr>";
        String endMAnga = "</tbody></table>";
        while (html.indexOf(startManga) > -1) {
            String mangaHTML = html.substring(html.indexOf(startManga), html.lastIndexOf(endMAnga));
            Manga manga = new Manga(Source.CENTRAL_DE_MANGAS);
            manga.setUrl(mangaHTML.substring(mangaHTML.indexOf(startUrl_CENTRAL) + startUrl_CENTRAL.length(), mangaHTML.indexOf(endUrl_CENTRAL)));
            String startTitle = "<a href=\"" + manga.getUrl() + "\">";
            String endTitle = "</a></td>";
            manga.setTitle(mangaHTML.substring(mangaHTML.indexOf(startTitle) + startTitle.length(), mangaHTML.indexOf(endTitle)));
            html = new StringBuilder(html.substring(html.indexOf(part) + part.length()));
            mangas.add(manga);

        }
        return mangas;
    }


    public static void htmlToMangaCentral(Manga manga, StringBuilder builder) throws StringIndexOutOfBoundsException {
        //Reduz a String
        String inicio = "<div class=\"row\"><div class=\"col-xs-9 col-md-9\"><div class=\"page-header\">";
        String fim = "</div></div></div></div><div class=\"col-xs-3 col-md-3\">";
        builder = new StringBuilder(builder.substring(builder.indexOf(inicio)
                , builder.lastIndexOf(fim)));
        System.out.println("- Titulo		: " + manga.getTitle());
        System.out.println("- Url			: " + manga.getUrl());
        //Imagem da Capa
        String index = "<div class=\"row\"><div class=\"col-xs-12 col-md-12\"><div class=\"pull-left\" style=\"margin-right: 10px\"><img class=\"img-thumbnail\" src=\"";
        String end = "\"></img>";
        manga.setUrlCover(builder.substring(builder.indexOf(index) + index.length(), builder.indexOf(end, builder.indexOf(index))));
        System.out.println("- Imagem da Capa: " + manga.getUrlCover());
        //Sinopse
        index = "</img></div><p><p>";
        if (builder.indexOf(index) < 0) {
            index = end + "</div><p>";
        }
        end = "</p>";
        manga.setSinopse(builder.substring(builder.indexOf(index) + index.length(), builder.indexOf(end, builder.indexOf(index) + index.length())));
        manga.setSinopse(removeTagsHTML(manga.getSinopse()).trim());
        System.out.println("- Sinopse : " + manga.getSinopse());
        //Ano
        index = "<h4>Ano</h4></div></div><div class=\"row\"><div class=\"col-xs-12 col-md-12\">";
        end = "</div>";
        manga.setAno(builder.substring(builder.indexOf(index) + index.length(), builder.indexOf(end, builder.indexOf(index) + index.length())).trim());
        System.out.println("- Ano			: " + manga.getAno());
        //Arte
        index = "<h4>Arte</h4></div></div><div class=\"row\"><div class=\"col-xs-12 col-md-12\">";
        end = "";
        if (builder.indexOf(index) > -1) {
            builder = new StringBuilder(builder.substring(builder.indexOf(index)));
            index = "<a href=\"";
            Arte arte = new Arte();
            arte.setUrl(builder.substring(builder.indexOf(index) + index.length(), builder.indexOf("\"", builder.indexOf(index) + index.length())));
            arte.setNome(builder.substring(builder.indexOf(">", builder.indexOf(arte.getUrl())) + 1, builder.indexOf("</a>")));
            manga.setArte(arte);
            System.out.println("Arte : " + arte.getNome() + " - " + arte.getUrl());
        }
        //Autor
        index = "Autor";
        end = "</a>";
        Autor autor = new Autor();

        String autorHTML = builder.substring(builder.indexOf("<a", builder.indexOf(index)), builder.indexOf(end, builder.indexOf(index)) + end.length());
        index = "<a href=\"";
        autor.setUrl(autorHTML.substring(autorHTML.indexOf(index) + index.length(), autorHTML.indexOf("\"", autorHTML.indexOf(index) + index.length())));
        autor.setNome(autorHTML.substring(autorHTML.indexOf(">", autorHTML.indexOf(index)) + 1, autorHTML.indexOf(end)));
        manga.setAutor(autor);
        System.out.println("Autor : " + autor.getNome() + " - " + autor.getUrl());
        //Genero
        index = "<h4>Gênero</h4></div></div><div class=\"row\"><div class=\"col-xs-12 col-md-12\">";
        end = "<h4>";
        String generoHTML = builder.substring(builder.indexOf(index) + index.length(), builder.indexOf(end, builder.indexOf(index) + index.length()));
        index = "<a href=\"";
        end = "</a>";
        while (generoHTML.indexOf(index) > -1) {
            Genero genero = new Genero();
            genero.setUrl(generoHTML.substring(generoHTML.indexOf(index) + index.length(), generoHTML.indexOf("\"", generoHTML.indexOf(index) + index.length())));
            genero.setNome(generoHTML.substring(generoHTML.indexOf(">") + 1, generoHTML.indexOf(end)));
            manga.getGeneros().add(genero);
            generoHTML = generoHTML.substring(generoHTML.indexOf(end) + end.length());
            System.out.println("Genero : " + genero.getNome() + " - " + genero.getUrl());
        }

        //Capitulos
        index = "<div class=\"col-xs-12 col-md-12\"><div class=\"row\"><center>";
        end = "</center>";
        builder = new StringBuilder(builder.substring(builder.indexOf(index) + index.length() - 8, builder.lastIndexOf(end) + end.length()));
        index = "<center>";
        end = "</center>";
        while (builder.indexOf(index) > -1) {
            Capitulo capitulo = new Capitulo();
            capitulo.setUrl(builder.substring(builder.indexOf("\"") + 1, builder.indexOf("\"", builder.indexOf("\"") + 1)));
            capitulo.setNome(builder.substring(builder.indexOf(">", builder.indexOf("<a")) + 1, builder.indexOf("</a>")));
            manga.getCapitulos().add(capitulo);
            builder = new StringBuilder(builder.substring(builder.indexOf(end) + end.length()));
            System.out.println("Capitulo : " + capitulo.getNome() + " - " + capitulo.getUrl());
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static String removeTagsHTML(String value) {
        value = (value.replace("<br>", System.lineSeparator()));
        while (value.indexOf("<") > -1) {
            int positionMe = value.indexOf("<");
            int positionMa = value.indexOf(">") + 1;
            String remove = removeTagsHTML(value, positionMe, positionMa);
            value = value.replace(remove, "");
        }
        return value;
    }


    private static String removeTagsHTML(String value, int start, int end) {
        return value.substring(start, end);
    }

    public static String htmlToPageMaxValue(StringBuilder html) {
        String index = "<select id=\"manga_pages\">";
        String end = "</option></select>";
        html = new StringBuilder(
                html.substring(
                        html.indexOf(index) + index.length(),
                        html.indexOf(end, html.indexOf(index) + index.length()))
        );

        html = new StringBuilder(html.substring(html.lastIndexOf(">") + 1));

        return html.toString();
    }

    public static String htmlToPageURLPage(StringBuilder html) {
        String index = "var pages = [\"";
        String end = "\",";
        html = new StringBuilder(
                html.substring(
                        html.indexOf(index) + index.length(),
                        html.indexOf(end, html.indexOf(index))
                ).replace("\\", "")
        );
        //http://mangas2014.centraldemangas.com.br/aaa/aaa001-06.jpg

        html = new StringBuilder(html.substring(0, html.indexOf("-") + 1) + "{page}" + html.substring(html.length() - 4, html.length()));

        return html.toString();
    }

}

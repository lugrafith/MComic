package mcomic.com.core.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import mcomic.com.core.Aplication;
import mcomic.com.core.exceptions.AplicationException;
import mcomic.com.core.iservice.Service;
import mcomic.com.core.util.CentralMangaParseUtil;
import mcomic.com.model.Capitulo;
import mcomic.com.model.Manga;
import mcomic.com.model.Page;
import mcomic.com.model.Source;

/**
 * Created by lu_gr on 30/01/2016.
 */
public class ServiceCentralManga implements Service {



    public ServiceCentralManga(){

    }

    @Override
    public List<Manga> search(String title) throws AplicationException{
        title = title.replace(" ", "+");
        List<Manga> mangas = new ArrayList<Manga>();
        try {
            URL url = null;
            if(title == null || title.trim().equals("")){
                url = new URL(Source.CENTRAL_DE_MANGAS.getUrl() + "/mangas/list/*");
            }else {
                url = new URL(Source.CENTRAL_DE_MANGAS.getUrl() + Source.CENTRAL_DE_MANGAS.getSearch() + title);
            }
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(100000);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            StringBuilder builderHtml = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                builderHtml.append(line.trim());
            }
            //
            mangas = CentralMangaParseUtil.htmlToMangaCentral(builderHtml);
            bufferedReader.close();
            urlConnection.disconnect();
        } catch (MalformedURLException e1) {
            throw new AplicationException("Recurso não Encontrado ou url inválida. - " + e1.getMessage() + " - " + e1.getCause().getMessage());
        } catch (IOException e1) {
            throw new AplicationException("Erro ao buscar Mangá. - " + e1.getMessage() + " - " + e1.getCause().getMessage());
        }catch (StringIndexOutOfBoundsException e){
            throw new AplicationException("Nenhum Mangá encontrado.");
        }
        return mangas;
    }



    public void completeInfoManga(Manga manga) throws AplicationException{
        if(manga.getImageCover() == null){
            URL url = null;
            try {
                if (manga.getUrl().indexOf("http") > -1) {
                    url = new URL(manga.getUrl());
                } else {
                    url = new URL(manga.getSource().getUrl() + manga.getUrl());
                }

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(200000);
                BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = buffer.readLine()) != null) {
                    builder.append(line.trim());
                }

                CentralMangaParseUtil.htmlToMangaCentral(manga, builder);
                manga.setImageCover(getImage(manga.getUrlCover()));
                buffer.close();
                connection.disconnect();
            }catch (IOException e){
                throw new AplicationException(e);
            }catch (IndexOutOfBoundsException e){
                throw new AplicationException(e);
            }
        }
    }

    public Bitmap getImage(String urlImage) throws IOException {
        URL url = new URL(urlImage);
        return BitmapFactory.decodeStream(url.openConnection().getInputStream());
    }
    public Bitmap getImagePage(String urlImage, Capitulo capitulo) throws IOException {
        System.out.println("Recuperando Imagem da Pagina - > " + urlImage);
        System.gc();
        URL url = new URL(urlImage);
        HttpURLConnection conect = (HttpURLConnection) url.openConnection();
        conect.setRequestMethod("GET");
        conect.setRequestProperty("Accept", "image/webp,image/*,*/*;q=0.8");
        conect.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
        conect.setRequestProperty("Accept-Language", "image/webp,image/*,*/*;q=0.8");
        conect.setRequestProperty("Connection", "keep-alive");
        conect.setRequestProperty("DNT", "1");
        conect.setRequestProperty("Host", urlImage.substring((urlImage.indexOf("//") + 2), (urlImage.indexOf("/", urlImage.indexOf("//") + 2))));
        conect.setRequestProperty("Referer", capitulo.getUrl()); //"http://centraldemangas.org/online/aaa/001"
        conect.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36");

        BufferedInputStream bufferedInputStream = new BufferedInputStream(conect.getInputStream());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len = bufferedInputStream.read(buffer)) != -1){
            byteArrayOutputStream.write(buffer, 0, len);
        }
        conect.disconnect();
        byteArrayOutputStream.close();
        bufferedInputStream.close();
        byte[] data = byteArrayOutputStream.toByteArray();
        //return Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(data, 0, data.length), 673, 985, true);
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    public Page getPage(Manga manga, Capitulo capitulo, int page) throws IOException {
        System.out.println("Recuperando Pagina " + page + " do manga " + manga.getTitle() + ", Capitulo " + capitulo.getNome());
        //http://mangas2016.centraldemangas.com.br/kyou_no_kerberos/kyou_no_kerberos000-01.jpg
        String url = null;
        if(page < 10){
            url = capitulo.getUrlPadraoCapitulo().replace("{page}", "0" + String.valueOf(page));
        }else {
            url = capitulo.getUrlPadraoCapitulo().replace("{page}", String.valueOf(page));
        }
        System.out.print("URL -> " + url);
        return new Page(url, getImagePage(url, capitulo), page);
    }

    public int getMaxPages(Manga manga, Capitulo capitulo) throws IOException {
        String urlPage = manga.getSource().getUrl()
                + capitulo.getUrl();
        URL url = new URL(urlPage);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(200000);
        System.out.println("Recuperando total de paginas do manga " + manga.getTitle() + ", Capitulo " + capitulo.getNome() + "...");
        BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        StringBuilder builder = new StringBuilder();
        String line = null;
        while ((line = buffer.readLine()) != null) {
            builder.append(line.trim());
        }
        capitulo.setUrlPadraoCapitulo(CentralMangaParseUtil.htmlToPageURLPage(builder));
        int total = Integer.parseInt(CentralMangaParseUtil.htmlToPageMaxValue(builder));
        System.out.println("-> " + total + " paginas");
        return total;
    }
}

package mcomic.com.core.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import mcomic.com.core.db.OrmliteOpenHelper;
import mcomic.com.model.Capitulo;
import mcomic.com.model.Favorito;
import mcomic.com.model.Manga;
import mcomic.com.model.Page;

/**
 * Created by lu_gr on 25/02/2016.
 */
public class FavoritoDao extends AbstractDao<Favorito> {

    private MangaDao mangaDao;

    private CapituloDao capituloDao;

    private PageDao pageDao;


    public FavoritoDao(OrmliteOpenHelper helper) {
        super(helper, Favorito.class);
        mangaDao = new MangaDao(helper);
        capituloDao = new CapituloDao(helper);
        pageDao = new PageDao(helper);
    }

    @Override
    public Favorito insert(Favorito persist) throws SQLException {
        mangaDao.insert(persist.getManga());
        capituloDao.insert(persist.getCapituloAtual());
        pageDao.insert(persist.getPaginaAtual());
        return super.insert(persist);
    }

    public Favorito getForManga(Manga manga) throws SQLException {
        if(manga.getId() == 0){
            MangaDao mangaDao = new MangaDao(getHelper());
            Manga m = mangaDao.getForUrl(manga.getUrl());
            if(m != null){
                manga.setId(m.getId());
            }
        }
        HashMap<String, Object> map = new HashMap();
        map.put(manga.getClass().getSimpleName() + "_id", manga);
        List<Favorito> favoritos = super.getList(map);
        if (favoritos != null && favoritos.size() > 0){
            completeFavorito(favoritos.get(0));
            return favoritos.get(0);
        }
        return  null;
    }

    private void completeFavorito(Favorito favorito) throws SQLException {
        favorito.setManga(mangaDao.get(favorito.getManga().getId()));
        favorito.setCapituloAtual(capituloDao.get(favorito.getCapituloAtual().getId()));
        favorito.setPaginaAtual(pageDao.get(favorito.getPaginaAtual().getId()));
    }

    @Override
    public List<Favorito> listAll() throws SQLException {
        List<Favorito> favoritos = super.listAll();
        for (Favorito f : favoritos){
            completeFavorito(f);
        }
        return favoritos;
    }
}

package mcomic.com.core.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import mcomic.com.model.Favorito;
import mcomic.com.model.Manga;

/**
 * Created by lu_gr on 25/02/2016.
 */
public class FavoritoDao extends AbstractDao<Favorito> {

    private MangaDao mangaDao;

    private CapituloDao capituloDao;

    private PageDao pageDao;


    public FavoritoDao() {
        super(Favorito.class);
        mangaDao = new MangaDao();
        capituloDao = new CapituloDao();
        pageDao = new PageDao();
    }

    @Override
    public Favorito insert(Favorito persist) throws SQLException {
        mangaDao.insert(persist.getManga());
        if(persist.getCapituloAtual() != null){
            capituloDao.insert(persist.getCapituloAtual());
        }
        if(persist.getPaginaAtual() != null){
            pageDao.insert(persist.getPaginaAtual());
        }
        mangaDao.listAll();
        return super.insert(persist);
    }

    private void completeFavorito(Favorito favorito) throws SQLException {
        favorito.setManga(mangaDao.getForUrl(favorito.getUrl()));
        if(favorito.getCapituloAtual() != null){
            favorito.setCapituloAtual(capituloDao.get(favorito.getCapituloAtual().getId()));
            if(favorito.getPaginaAtual() != null) {
                favorito.setPaginaAtual(pageDao.get(favorito.getPaginaAtual().getId()));
            }
        }
    }

    @Override
    public Favorito getForUrl(String url) throws SQLException {
        Favorito favorito = super.getForUrl(url);
        if(favorito != null){
            completeFavorito(favorito);
        }
        return favorito;
    }

    @Override
    public List<Favorito> listAll() throws SQLException {
        List<Favorito> favoritos = super.listAll();
        for (Favorito f : favoritos){
            completeFavorito(f);
        }
        return favoritos;
    }


    @Override
    public void delete(Favorito persist) throws SQLException {
        super.delete(persist);
        mangaDao.delete(persist.getManga());
        capituloDao.delete(persist.getCapituloAtual());
        pageDao.delete(persist.getPaginaAtual());
    }
}

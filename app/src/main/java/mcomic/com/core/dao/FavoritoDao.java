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

    public FavoritoDao(OrmliteOpenHelper helper) {
        super(helper, Favorito.class);
    }

    @Override
    public Favorito insert(Favorito persist) throws SQLException {
        persist.setManga(getHelper().getDao(Manga.class).createIfNotExists(persist.getManga()));
        persist.setCapituloAtual(getHelper().getDao(Capitulo.class).createIfNotExists(persist.getCapituloAtual()));
        persist.setPaginaAtual(getHelper().getDao(Page.class).createIfNotExists(persist.getPaginaAtual()));
        return super.insert(persist);
    }

    public Favorito getForManga(Manga manga) throws SQLException {
        HashMap<String, Object> map = new HashMap();
        map.put(manga.getClass().getSimpleName() + "_id", manga);
        List<Favorito> favoritos = super.getList(map);
        if (favoritos != null && favoritos.size() > 0){
            return favoritos.get(0);
        }
        return  null;
    }


}

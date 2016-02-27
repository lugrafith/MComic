package mcomic.com.core.dao;

import mcomic.com.core.db.OrmliteOpenHelper;
import mcomic.com.model.Capitulo;

/**
 * Created by lu_gr on 26/02/2016.
 */
public class CapituloDao extends AbstractDao<Capitulo> {

    public CapituloDao(OrmliteOpenHelper helper) {
        super(helper, Capitulo.class);
    }
}

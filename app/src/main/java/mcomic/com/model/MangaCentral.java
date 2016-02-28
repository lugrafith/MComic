package mcomic.com.model;

import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by lu_gr on 30/01/2016.
 */
public class MangaCentral  extends Manga{

    public MangaCentral() {
        super(Source.CENTRAL_DE_MANGAS);
    }

}

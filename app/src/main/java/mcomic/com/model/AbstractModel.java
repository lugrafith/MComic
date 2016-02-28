package mcomic.com.model;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

public abstract class AbstractModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField(unique = true)
    private String url;

    public AbstractModel() {
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}

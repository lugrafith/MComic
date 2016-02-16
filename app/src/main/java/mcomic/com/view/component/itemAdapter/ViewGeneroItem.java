package mcomic.com.view.component.itemAdapter;

import android.widget.Button;

import mcomic.com.model.Genero;

/**
 * Created by lu_gr on 02/02/2016.
 */
public class ViewGeneroItem {
    private Button button;
    private Genero genero;

    public ViewGeneroItem(Genero g) {
        this.genero = g;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}

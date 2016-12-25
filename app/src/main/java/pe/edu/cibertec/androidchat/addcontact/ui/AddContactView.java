package pe.edu.cibertec.androidchat.addcontact.ui;

/**
 * Created by Administrador on 03/12/2016.
 */

public interface AddContactView {
    void showInput();
    void hideInput();
    void showProgress();
    void hideProgress();

    void contactAdded();
    void contactNotAdded();
}

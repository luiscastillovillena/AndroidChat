package pe.edu.cibertec.androidchat.addcontact;

import pe.edu.cibertec.androidchat.addcontact.events.AddContactEvent;

/**
 * Created by Administrador on 03/12/2016.
 */

public interface AddContactPresenter {
    void onShow();
    void onDestroy();

    void addContact(String email);
    void onEventMainThread(AddContactEvent event);
}

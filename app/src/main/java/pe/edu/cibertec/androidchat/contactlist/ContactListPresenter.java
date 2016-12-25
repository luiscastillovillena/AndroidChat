package pe.edu.cibertec.androidchat.contactlist;

import pe.edu.cibertec.androidchat.contactlist.events.ContactListEvent;

/**
 * Created by Administrador on 01/11/2016.
 */
public interface ContactListPresenter {
    void onPause();
    void onResume();
    void onCreate();
    void onDestroy();
    void signOff();

    void removeContact(String email);

    void onEventMainThread(ContactListEvent event);

    String getCurrentUserEmail();
}

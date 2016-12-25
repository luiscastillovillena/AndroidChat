package pe.edu.cibertec.androidchat.contactlist;

/**
 * Created by Administrador on 01/11/2016.
 */

public interface ContactListInteractor {
    void subscribeForContactEvents();
    void unSubscribeForContactEvents();
    void destroyContactListListener();
    void removeContact(String email);
}

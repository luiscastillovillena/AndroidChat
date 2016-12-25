package pe.edu.cibertec.androidchat.contactlist;

/**
 * Created by Administrador on 01/11/2016.
 */

public interface ContactListRepository {
    void signOff();
    void removeContact(String email);
    void destroyContactListListener();
    void subscribeForContactListUpdates();
    void unSubscribeForContactListUpdates();
    void changeUserConnectionStatus(boolean online);
    String getCurrentEmail();
}

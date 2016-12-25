package pe.edu.cibertec.androidchat.contactlist;

/**
 * Created by Administrador on 01/11/2016.
 */

public interface ContactListSessionInteractor {
    void signOff();
    void changeConnectionStatus(boolean online);
    String getCurrentUserEmail();
}

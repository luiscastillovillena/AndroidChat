package pe.edu.cibertec.androidchat.addcontact.events;

/**
 * Created by Administrador on 03/12/2016.
 */
public class AddContactEvent {
    boolean error = false;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}

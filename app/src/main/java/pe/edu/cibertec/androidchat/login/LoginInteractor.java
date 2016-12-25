package pe.edu.cibertec.androidchat.login;

/**
 * Created by Administrador on 03/10/2016.
 */

public interface LoginInteractor {
    void checkAlreadyAuthenticated();
    void doSignUp(String email, String password);
    void doSignIn(String email, String password);
}

package pe.edu.cibertec.androidchat.login;

/**
 * Created by Administrador on 03/10/2016.
 */

public interface LoginRepository {
    void signUp(String email, String password);
    void signIn(String email, String password);
    void checkAlreadyAuthenticated();
}

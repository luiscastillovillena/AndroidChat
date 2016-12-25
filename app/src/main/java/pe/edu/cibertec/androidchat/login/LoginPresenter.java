package pe.edu.cibertec.androidchat.login;

import pe.edu.cibertec.androidchat.login.events.LoginEvent;

/**
 * Created by Administrador on 03/10/2016.
 */

public interface LoginPresenter {
    void onCreate();
    void onDestroy();
    void checkForAuthenticatedUser();
    void validateLogin(String email, String password);
    void registerNewUser(String email, String password);
    void onEventMainThread(LoginEvent event);
}

package pe.edu.cibertec.androidchat.login;

/**
 * Created by Administrador on 04/10/2016.
 */

public class LoginInteractorImpl implements  LoginInteractor{
    private LoginRepository loginRepository;


    public LoginInteractorImpl() {
        loginRepository = new LoginRepositoryImpl();
    }

    @Override
    public void checkAlreadyAuthenticated() {
        loginRepository.checkAlreadyAuthenticated();
    }

    @Override
    public void doSignUp(String email, String password) {
        loginRepository.signUp(email, password);
    }


    @Override
    public void doSignIn(String email, String password) {
        loginRepository.signIn(email, password);
    }

}
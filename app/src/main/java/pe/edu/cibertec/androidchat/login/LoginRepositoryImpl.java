package pe.edu.cibertec.androidchat.login;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.GenericTypeIndicator;
//import com.google.firebase.database.ValueEventListener;

import pe.edu.cibertec.androidchat.domain.FirebaseHelper;
import pe.edu.cibertec.androidchat.contactlist.entities.User;
import pe.edu.cibertec.androidchat.lib.EventBus;
import pe.edu.cibertec.androidchat.lib.GreenRobotEventBus;
import pe.edu.cibertec.androidchat.login.events.LoginEvent;

/**
 * Created by Administrador on 04/10/2016.
 */

public class LoginRepositoryImpl implements  LoginRepository{
    private FirebaseHelper helper;
    private DatabaseReference dataReference;
    private DatabaseReference myUserReference;


    public LoginRepositoryImpl() {
        this.helper = FirebaseHelper.getInstance();
        this.dataReference = helper.getDataReference();
        this.myUserReference = helper.getMyUserReference(); //¡OJO!es nulo la primera vez.
    }

    @Override
    public void signUp(final String email, final String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        postEvent(LoginEvent.onSignUpSuccess);
                        signIn(email, password);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        postEvent(LoginEvent.onSignUpError, e.getMessage());
                    }
                });
    }

    @Override
    public void signIn(final String email, final String password) {
        try {
            FirebaseAuth auth = FirebaseAuth.getInstance();

            auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            myUserReference = helper.getMyUserReference();
                            myUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    initSignIn(snapshot);
                                }
                                @Override
                                public void onCancelled(DatabaseError firebaseError) {
                                    postEvent(LoginEvent.onSignInError, firebaseError.getMessage());
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            postEvent(LoginEvent.onSignInError, e.getMessage());
                        }
                    });

        } catch (Exception e) {
            postEvent(LoginEvent.onSignInError, e.getMessage());
        }
    }

    @Override
    public void checkAlreadyAuthenticated() {
        // Si ya esta autentificado
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            // Obtiene la referencia "DatabaseReference" del usuario logueado
            myUserReference = helper.getMyUserReference();

            // Le asigna un listener a esa referencia
            myUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    initSignIn(snapshot);
                }

                @Override
                public void onCancelled(DatabaseError firebaseError) {
                    postEvent(LoginEvent.onSignInError, firebaseError.getMessage());
                }
            });
        } else {
            postEvent(LoginEvent.onFailedToRecoverSession);
        }
    }

    private void initSignIn(DataSnapshot snapshot){
        User currentUser = snapshot.getValue(User.class);

        if (currentUser == null) {
            registerNewUser();
        }

        helper.changeUserConnectionStatus(User.ONLINE);

        postEvent(LoginEvent.onSignInSuccess);
    }

    private void registerNewUser(){
        String email = helper.getAuthUserEmail();

        if (email != null) {
            User currentUser = new User(email, true, null);

            myUserReference.setValue(currentUser);
        }
    }

    private void postEvent(int type, String errorMessage) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);

        if (errorMessage != null) {
            loginEvent.setErrorMessage(errorMessage);
        }

        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);
    }

    private void postEvent(int type) {
        postEvent(type, null);
    }
}

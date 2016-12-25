package pe.edu.cibertec.androidchat.domain;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import pe.edu.cibertec.androidchat.contactlist.entities.User;

/**
 * Created by Administrador on 29/09/2016.
 */

public class FirebaseHelper {
    private DatabaseReference dataReference;
    private final static String SEPARATOR = "___";
    private final static String CHATS_PATH = "chats";
    private final static String USERS_PATH = "users";
    private final static String CONTACTS_PATH = "contacts";
    //private final static String FIREBASE_URL = "https://android-chat-example-1402d.firebaseio.com";

    public static class SingletonHolder{
        private static final FirebaseHelper INSTANCE = new FirebaseHelper();
    }

    /* Devuelve la instancia de FirebaseHelper */
    public static FirebaseHelper getInstance(){
        return SingletonHolder.INSTANCE;
    }

   /* Constructor */
    public FirebaseHelper () {
        //this.dataReference = FirebaseDatabase.getInstance().getReferenceFromUrl(FIREBASE_URL);

        /* Almacena la Referencia inicial para la BD o repositorio */
        this.dataReference = FirebaseDatabase.getInstance().getReference();
    }

    /* Devuelve la Referencia inicial para la BD o repositorio */
    public DatabaseReference getDataReference() {
        return dataReference;
    }

    /* Devuelve el correo del usuario autenticado */
    public String getAuthUserEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String email = null;

        if (user != null) {
            email = user.getEmail();
        }

        return email;
    }

    public DatabaseReference getUserReference(String email) {
        /* Devuelve la Referencia inicial del usuario registrado con "email" */
        DatabaseReference userReference = null;

        if (email != null) {
            String emailKey = email.replace(".", "_");
            userReference = dataReference.getRoot().child(USERS_PATH).child(emailKey);
        }

        return userReference;
    }

    public DatabaseReference getMyUserReference(){
        return getUserReference(getAuthUserEmail());
    }

    public DatabaseReference getContactsReference(String email){
        return getUserReference(email).child(CONTACTS_PATH);
    }

    public DatabaseReference getMyContactsReference(){
        return getContactsReference(getAuthUserEmail());
    }

    public DatabaseReference getOneContactReference(String mainEmail, String childEmail){
        String childKey = childEmail.replace(".", "_");
        return getUserReference(mainEmail).child(CONTACTS_PATH).child(childKey);
    }

    public DatabaseReference getChatsReference(String receiver){
        String keySender = getAuthUserEmail().replace(".", "_");
        String keyReceiver = receiver.replace(".", "_");

        String keyChat = keySender + SEPARATOR + keyReceiver;

        if (keySender.compareTo(keyReceiver) > 0) {
            keyChat = keyReceiver + SEPARATOR + keySender;
        }

        return dataReference.getRoot().child(CHATS_PATH).child(keyChat);
    }

    public void changeUserConnectionStatus(boolean online) {
        if (getMyUserReference() != null) {
            Map <String, Object> updates = new HashMap<String, Object>();

            updates.put("online", online);

            getMyUserReference().updateChildren(updates);
            notifyContactsOfConnectionChange(online);
        }
    }

    public void notifyContactsOfConnectionChange(boolean online) {
        notifyContactsOfConnectionChange(online, false);
    }

    private void notifyContactsOfConnectionChange(final boolean online, final boolean signoff) {
        final String myEMail = getAuthUserEmail();

        getMyContactsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String email = child.getKey();

                    DatabaseReference reference = getOneContactReference(email, myEMail);
                    reference.setValue(online);
                }

                if (signoff) {
                    changeUserConnectionStatus(false);
                    FirebaseAuth.getInstance().signOut();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void signOff(){
        notifyContactsOfConnectionChange(User.OFFLINE, true);
    }
}

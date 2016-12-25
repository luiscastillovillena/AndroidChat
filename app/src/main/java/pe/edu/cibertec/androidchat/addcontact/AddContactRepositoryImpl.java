package pe.edu.cibertec.androidchat.addcontact;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import pe.edu.cibertec.androidchat.addcontact.events.AddContactEvent;
import pe.edu.cibertec.androidchat.contactlist.entities.User;
import pe.edu.cibertec.androidchat.domain.FirebaseHelper;
import pe.edu.cibertec.androidchat.lib.EventBus;
import pe.edu.cibertec.androidchat.lib.GreenRobotEventBus;

/**
 * Created by Administrador on 03/12/2016.
 */
public class AddContactRepositoryImpl implements AddContactRepository{
    @Override
    public void addContact(final String email) {
        // Antes de añadir el nuevo contacto (email), lo busca en la collection "users"

        // Reemplaza los puntos del email con guión bajo
        // ya que la clave que contiene los email de los users
        // no pueden contener "."
        final String key = email.replace(".","_");

        // Obtiene una referencia al user que tiene por clave al email
        FirebaseHelper helper = FirebaseHelper.getInstance();
        final DatabaseReference userReference = helper.getUserReference(email);

        // Le asigna un listener.
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            // Este método funciona incluso la primera vez
            // que se define el listener
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Se usa para almacenar el usuario si existe
                User user = snapshot.getValue(User.class);
                AddContactEvent event = new AddContactEvent();

                // Si el usuario (nuevo contacto a añadir) exíste
                if (user != null) {
                    // Almacena el estado onLine
                    boolean online = user.isOnline();

                    // Obtiene una instancia de FirebaseHelper
                    FirebaseHelper helper = FirebaseHelper.getInstance();

                    // Obtiene una referencia de los Contactos del usuario autentificado
                    DatabaseReference userContactsReference = helper.getMyContactsReference();

                    // ... y le añade el nuevo contacto asignando su estado actual.
                    userContactsReference.child(key).setValue(online);

                    // Obtiene el usuario autentificado
                    String currentUserEmailKey = helper.getAuthUserEmail();
                    currentUserEmailKey = currentUserEmailKey.replace(".","_");

                    // ... y lo busca en los contactos del usuario recientemente añadido
                    DatabaseReference reverseUserContactsReference = helper.getContactsReference(email);

                    // y añade el usuario logueado a la lista de contactos del nuevo usuario.
                    reverseUserContactsReference.child(currentUserEmailKey).setValue(User.ONLINE);

                // Si el usuario no exíste
                } else {
                    event.setError(true);
                }

                // Se envia el evento a EventBus
                EventBus eventBus = GreenRobotEventBus.getInstance();
                eventBus.post(event);
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {}
        });
    }
}

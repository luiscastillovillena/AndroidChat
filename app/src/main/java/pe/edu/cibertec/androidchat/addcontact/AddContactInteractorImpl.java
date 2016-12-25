package pe.edu.cibertec.androidchat.addcontact;

/**
 * Created by Administrador on 03/12/2016.
 */
public class AddContactInteractorImpl implements AddContactInteractor {
    AddContactRepositoryImpl addContactRepository;

    public AddContactInteractorImpl(AddContactRepositoryImpl addContactRepository) {
        this.addContactRepository = addContactRepository;
    }

    @Override
    public void addContact(String email) {
        addContactRepository.addContact(email);
    }
}

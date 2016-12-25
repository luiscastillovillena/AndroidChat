package pe.edu.cibertec.androidchat.contactlist;

/**
 * Created by Administrador on 01/12/2016.
 */
public class ContactListSessionInteractorImpl implements ContactListSessionInteractor {
    ContactListRepositoryImpl contactListRepository;

    public ContactListSessionInteractorImpl() {
        this.contactListRepository = new ContactListRepositoryImpl();
    }

    @Override
    public void signOff() {
        contactListRepository.signOff();
    }

    @Override
    public void changeConnectionStatus(boolean online) {
        contactListRepository.changeUserConnectionStatus(online);
    }

    @Override
    public String getCurrentUserEmail() {
        return contactListRepository.getCurrentEmail();
    }
}

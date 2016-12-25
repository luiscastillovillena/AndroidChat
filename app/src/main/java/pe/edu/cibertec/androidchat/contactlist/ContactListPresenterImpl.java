package pe.edu.cibertec.androidchat.contactlist;

import org.greenrobot.eventbus.Subscribe;

import pe.edu.cibertec.androidchat.contactlist.ContactListPresenter;
import pe.edu.cibertec.androidchat.contactlist.entities.User;
import pe.edu.cibertec.androidchat.contactlist.events.ContactListEvent;
import pe.edu.cibertec.androidchat.contactlist.ui.ContactListActivity;
import pe.edu.cibertec.androidchat.contactlist.ui.ContactListView;
import pe.edu.cibertec.androidchat.lib.EventBus;
import pe.edu.cibertec.androidchat.lib.GreenRobotEventBus;


/**
 * Created by Administrador on 01/12/2016.
 */
public class ContactListPresenterImpl implements ContactListPresenter {
    EventBus eventBus;
    ContactListView contactListView;
    ContactListSessionInteractor contactListSessionInteractor;
    ContactListInteractor contactListInteractor;


    public ContactListPresenterImpl(ContactListView contactListView) {
        this.eventBus = GreenRobotEventBus.getInstance();
        this.contactListView = contactListView;
        this.contactListSessionInteractor = new ContactListSessionInteractorImpl();
        this.contactListInteractor = new ContactListInteractorImpl();
    }

    @Override
    public void onPause() {
        contactListSessionInteractor.changeConnectionStatus(User.OFFLINE);
        contactListInteractor.unSubscribeForContactEvents();
    }

    @Override
    public void onResume() {
        contactListSessionInteractor.changeConnectionStatus(User.ONLINE);
        contactListInteractor.subscribeForContactEvents();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        contactListInteractor.destroyContactListListener();
        contactListView = null;
    }

    @Override
    public void signOff() {
        contactListSessionInteractor.changeConnectionStatus(User.OFFLINE);
        contactListInteractor.destroyContactListListener();
        contactListInteractor.unSubscribeForContactEvents();
        contactListSessionInteractor.signOff();
    }

    @Override
    public void removeContact(String email) {
        contactListInteractor.removeContact(email);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ContactListEvent event) {
        User user = event.getUser();

        switch (event.getEventType()) {
            case ContactListEvent.onContactAdded:
                onContactAdded(user);
                break;

            case ContactListEvent.onContactChanged:
                onContactChanged(user);
                break;

            case ContactListEvent.onContactRemoved:
                onContactRemoved(user);
                break;
        }
    }

    public void onContactAdded(User user) {
        if (contactListView != null) {
            contactListView.onContactAdded(user);
        }
    }

    public void onContactChanged(User user) {
        if (contactListView != null) {
            contactListView.onContactChanged(user);
        }
    }

    public void onContactRemoved(User user) {
        if (contactListView != null) {
            contactListView.onContactRemoved(user);
        }
    }

    @Override
    public String getCurrentUserEmail() {
        return contactListSessionInteractor.getCurrentUserEmail();
    }
}

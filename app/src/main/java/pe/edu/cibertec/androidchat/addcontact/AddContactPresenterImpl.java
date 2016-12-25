package pe.edu.cibertec.androidchat.addcontact;

import org.greenrobot.eventbus.Subscribe;

import pe.edu.cibertec.androidchat.addcontact.events.AddContactEvent;
import pe.edu.cibertec.androidchat.addcontact.ui.AddContactFragment;
import pe.edu.cibertec.androidchat.addcontact.ui.AddContactView;
import pe.edu.cibertec.androidchat.lib.EventBus;
import pe.edu.cibertec.androidchat.lib.GreenRobotEventBus;

/**
 * Created by Administrador on 03/12/2016.
 */
public class AddContactPresenterImpl implements AddContactPresenter {
    EventBus eventBus;
    AddContactView addContactView;
    AddContactInteractor addContactInteractor;

    public AddContactPresenterImpl(AddContactView addContactView) {
        this.eventBus = GreenRobotEventBus.getInstance();
        this.addContactView = addContactView;
        this.addContactInteractor = new AddContactInteractorImpl(new AddContactRepositoryImpl());
    }

    @Override
    public void onShow() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        addContactView = null;
        eventBus.unregister(this);
    }

    @Override
    public void addContact(String email) {
        addContactView.hideInput();
        addContactView.showProgress();
        this.addContactInteractor.addContact(email);
    }

    @Override
    @Subscribe
    public void onEventMainThread(AddContactEvent event) {
        if (addContactView != null) {
            addContactView.hideProgress();
            addContactView.showInput();

            if (event.isError()) {
                addContactView.contactNotAdded();
            } else {
                addContactView.contactAdded();
            }
        }
    }
}

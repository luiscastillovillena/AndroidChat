package pe.edu.cibertec.androidchat.contactlist.ui;


import pe.edu.cibertec.androidchat.contactlist.entities.User;

public interface ContactListView {
    void onContactAdded(User user);
    void onContactChanged(User user);
    void onContactRemoved(User user);
}

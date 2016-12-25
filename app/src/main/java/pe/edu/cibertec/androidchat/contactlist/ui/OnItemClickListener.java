package pe.edu.cibertec.androidchat.contactlist.ui;

import pe.edu.cibertec.androidchat.contactlist.entities.User;

/**
 * Created by Administrador on 04/11/2016.
 */

public interface OnItemClickListener {
    void onItemClick(User user);
    void onItemLongClick(User user);
}

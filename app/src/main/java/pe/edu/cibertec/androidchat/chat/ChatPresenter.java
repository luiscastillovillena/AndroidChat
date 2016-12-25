package pe.edu.cibertec.androidchat.chat;

import pe.edu.cibertec.androidchat.chat.events.ChatEvent;

/**
 * Created by Administrador on 05/12/2016.
 */

public interface ChatPresenter {
    void onPause();
    void onResume();
    void onCreate();
    void onDestroy();

    void setChatRecipient(String recipient);

    void sendMessage(String msg);
    void onEventMainThread(ChatEvent event);
}

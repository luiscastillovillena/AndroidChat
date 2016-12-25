package pe.edu.cibertec.androidchat.chat.ui;

import pe.edu.cibertec.androidchat.chat.entities.ChatMessage;

/**
 * Created by Administrador on 05/12/2016.
 */

public interface ChatView {
    void sendMessage();
    void onMessageReceived(ChatMessage msg);
}

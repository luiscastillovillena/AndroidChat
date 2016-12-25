package pe.edu.cibertec.androidchat.chat.events;

import pe.edu.cibertec.androidchat.chat.entities.ChatMessage;

/**
 * Created by Administrador on 05/12/2016.
 */
public class ChatEvent {
    ChatMessage msg;

    public ChatEvent(ChatMessage msg) {
        this.msg = msg;
    }

    public ChatMessage getMessage() {
        return msg;
    }
}

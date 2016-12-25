package pe.edu.cibertec.androidchat.chat;

/**
 * Created by Administrador on 05/12/2016.
 */

public interface ChatInteractor {
    void sendMessage(String msg);
    void setRecipient(String recipient);

    void destroyChatListener();
    void subscribeForChatUpdates();
    void unSubscribeForChatUpdates();
}

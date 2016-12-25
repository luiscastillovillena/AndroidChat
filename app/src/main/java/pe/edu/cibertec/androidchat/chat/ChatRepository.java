package pe.edu.cibertec.androidchat.chat;

/**
 * Created by Administrador on 05/12/2016.
 */

public interface ChatRepository {
    void sendMessage(String msg);
    void setReceiver(String receiver);

    void destroyChatListener();
    void subscribeForChatUpdates();
    void unSubscribeForChatUpdates();

    void changeUserConnectionStatus(boolean online);
}

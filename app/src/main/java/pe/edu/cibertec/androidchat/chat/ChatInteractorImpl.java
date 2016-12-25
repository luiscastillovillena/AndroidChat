package pe.edu.cibertec.androidchat.chat;

/**
 * Created by Administrador on 08/12/2016.
 */
public class ChatInteractorImpl implements ChatInteractor {
    ChatRepository chatRepository;

    public ChatInteractorImpl() {
        this.chatRepository = new ChatRepositoryImpl();
    }

    @Override
    public void sendMessage(String msg) {
        chatRepository.sendMessage(msg);
    }

    @Override
    public void setRecipient(String recipient) {
        chatRepository.setReceiver(recipient);
    }

    @Override
    public void destroyChatListener() {
        chatRepository.destroyChatListener();
    }

    @Override
    public void subscribeForChatUpdates() {
        chatRepository.subscribeForChatUpdates();
    }

    @Override
    public void unSubscribeForChatUpdates() {
        chatRepository.unSubscribeForChatUpdates();
    }
}

package pe.edu.cibertec.androidchat.chat;

/**
 * Created by Administrador on 08/12/2016.
 */
public class ChatSessionInteractorImpl implements ChatSessionInteractor {
    ChatRepository chatRepository;

    public ChatSessionInteractorImpl() {
        this.chatRepository = new ChatRepositoryImpl();
    }

    @Override
    public void changeConnectionStatus(boolean online) {
        chatRepository.changeUserConnectionStatus(online);
    }
}

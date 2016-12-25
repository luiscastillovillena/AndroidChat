package pe.edu.cibertec.androidchat.chat;

import org.greenrobot.eventbus.Subscribe;

import pe.edu.cibertec.androidchat.chat.entities.ChatMessage;
import pe.edu.cibertec.androidchat.chat.events.ChatEvent;
import pe.edu.cibertec.androidchat.chat.ui.ChatActivity;
import pe.edu.cibertec.androidchat.chat.ui.ChatView;
import pe.edu.cibertec.androidchat.contactlist.entities.User;
import pe.edu.cibertec.androidchat.lib.EventBus;
import pe.edu.cibertec.androidchat.lib.GreenRobotEventBus;

/**
 * Created by Administrador on 05/12/2016.
 */
public class ChatPresenterImpl implements ChatPresenter {
    EventBus eventBus;
    ChatView chatView;
    ChatInteractor chatInteractor;
    ChatSessionInteractor chatSessionInteractor;

    public ChatPresenterImpl(ChatView chatView) {
        this.chatView = chatView;
        this.eventBus = GreenRobotEventBus.getInstance();

        this.chatInteractor = new ChatInteractorImpl();
        this.chatSessionInteractor = new ChatSessionInteractorImpl();
    }

    @Override
    public void onPause() {
        chatInteractor.unSubscribeForChatUpdates();
        chatSessionInteractor.changeConnectionStatus(User.OFFLINE);
    }

    @Override
    public void onResume() {
        chatInteractor.subscribeForChatUpdates();
        chatSessionInteractor.changeConnectionStatus(User.ONLINE);
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        chatInteractor.destroyChatListener();
        chatView = null;
    }

    @Override
    public void setChatRecipient(String recipient) {
        this.chatInteractor.setRecipient(recipient);
    }

    @Override
    public void sendMessage(String msg) {
        chatInteractor.sendMessage(msg);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ChatEvent event) {
        if (chatView != null) {
            ChatMessage msg = event.getMessage();
            chatView.onMessageReceived(msg);
        }
    }
}

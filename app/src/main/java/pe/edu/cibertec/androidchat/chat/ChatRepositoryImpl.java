package pe.edu.cibertec.androidchat.chat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import pe.edu.cibertec.androidchat.chat.entities.ChatMessage;
import pe.edu.cibertec.androidchat.chat.events.ChatEvent;
import pe.edu.cibertec.androidchat.domain.FirebaseHelper;
import pe.edu.cibertec.androidchat.lib.EventBus;
import pe.edu.cibertec.androidchat.lib.GreenRobotEventBus;

/**
 * Created by Administrador on 08/12/2016.
 */
public class ChatRepositoryImpl implements ChatRepository {
    private String receiver;
    private FirebaseHelper helper;
    private ChildEventListener chatEventListener;

    public ChatRepositoryImpl() {
        helper = FirebaseHelper.getInstance();
    }

    @Override
    public void sendMessage(String msg) {
        String keySender = helper.getAuthUserEmail().replace(".","_");
        ChatMessage chatMessage = new ChatMessage(keySender, msg);
        DatabaseReference chatsReference = helper.getChatsReference(receiver);
        chatsReference.push().setValue(chatMessage);
    }

    @Override
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public void destroyChatListener() {
        chatEventListener = null;
    }

    @Override
    public void subscribeForChatUpdates() {
        if (chatEventListener == null) {
            chatEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildKey) {
                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    String msgSender = chatMessage.getSender();
                    msgSender = msgSender.replace("_",".");

//                    String currentUserEmail = helper.getAuthUserEmail();
//                    chatMessage.setSentByMe(msgSender.equals(currentUserEmail));
                    chatMessage.setSentByMe(msgSender.equals(helper.getAuthUserEmail()));

                    ChatEvent chatEvent = new ChatEvent(chatMessage);
                    EventBus eventBus = GreenRobotEventBus.getInstance();
                    eventBus.post(chatEvent);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String previousChildKey) {}

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {}

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onCancelled(DatabaseError firebaseError) {}
            };

            helper.getChatsReference(receiver).addChildEventListener(chatEventListener);
        }
    }

    @Override
    public void unSubscribeForChatUpdates() {
        if (chatEventListener != null) {
            helper.getChatsReference(receiver).removeEventListener(chatEventListener);
        }
    }

    @Override
    public void changeUserConnectionStatus(boolean online) {
        helper.changeUserConnectionStatus(online);
    }
}

package pe.edu.cibertec.androidchat.chat.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import pe.edu.cibertec.androidchat.AndroidChatApplication;
import pe.edu.cibertec.androidchat.R;
import pe.edu.cibertec.androidchat.chat.ChatPresenter;
import pe.edu.cibertec.androidchat.chat.ChatPresenterImpl;
import pe.edu.cibertec.androidchat.chat.adapters.ChatAdapter;
import pe.edu.cibertec.androidchat.chat.entities.ChatMessage;
import pe.edu.cibertec.androidchat.contactlist.ContactListPresenterImpl;
import pe.edu.cibertec.androidchat.domain.AvatarHelper;
import pe.edu.cibertec.androidchat.lib.ImageLoader;

public class ChatActivity extends AppCompatActivity implements ChatView{

    @Bind(R.id.toolbar)             Toolbar toolbar;
    @Bind(R.id.imgAvatar)           CircleImageView imgAvatar;
    @Bind(R.id.txtUser)             TextView txtUser;
    @Bind(R.id.txtStatus)           TextView txtStatus;
    @Bind(R.id.messageRecyclerView) RecyclerView recyclerView;
    @Bind(R.id.editTxtMessage)      EditText inputMessage;
    @Bind(R.id.btnSendMessage)      ImageButton btnSendMessage;

    public final static String EMAIL_KEY = "email";
    public final static String ONLINE_KEY = "online";

    private ChatAdapter chatAdapter;
    private ChatPresenter chatPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        chatPresenter = new ChatPresenterImpl(this);
        chatPresenter.onCreate();

        setSupportActionBar(toolbar);
        setToolbarData(getIntent());

//        Intent intent = getIntent();
//        setToolbarData(intent);

        setupAdapter();
        setupRecyclerView();
    }

    private void setToolbarData(Intent intent) {
        // Asigna valores que se visualizan en el ToolBar

        // Extrae el valor EMAIL_KEY del intent
        String recipient = intent.getStringExtra(EMAIL_KEY);

        // Se la envia al presentador
        chatPresenter.setChatRecipient(recipient);

        // Extrae el valor ONLINE_KEY del intent
        boolean online = intent.getBooleanExtra(ONLINE_KEY, false);

        // Según el resultado asigna a la cadena status, online u offline
        String status = online ? "online" : "offline";

        // Asigna el color respectivo
        int color = online ? Color.GREEN : Color.RED;

        // Visualiza la información
        txtUser.setText(recipient);
        txtStatus.setText(status);
        txtStatus.setTextColor(color);

//        AndroidChatApplication app = (AndroidChatApplication)getApplication();
//        ImageLoader imageLoader = app.getImageLoader();
//        imageLoader.load(imgAvatar, AvatarHelper.getAvatarUrl(recipient));
    }
    private void setupAdapter() {
        chatAdapter = new ChatAdapter(this, new ArrayList<ChatMessage>());
 /*
        // Test
        ChatMessage msg1 = new ChatMessage();
        ChatMessage msg2 = new ChatMessage();

        msg1.setMsg("hola");
        msg2.setMsg("Cómo estás?");

        msg1.setSentByMe(true);
        msg2.setSentByMe(false);

        chatAdapter = new ChatAdapter(this, Arrays.asList(new ChatMessage[]{msg1, msg2}));
*/
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        chatPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        chatPresenter.onPause();
    }

    @Override
    protected void onDestroy() {
        chatPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    @OnClick(R.id.btnSendMessage)
    public void sendMessage() {
        chatPresenter.sendMessage(inputMessage.getText().toString());
        inputMessage.setText("");
    }

    @Override
    public void onMessageReceived(ChatMessage msg) {
        chatAdapter.add(msg);
        recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);

        recyclerView.notify();
        chatAdapter.notify();
    }
}

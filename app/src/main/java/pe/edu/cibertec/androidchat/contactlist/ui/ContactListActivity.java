package pe.edu.cibertec.androidchat.contactlist.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pe.edu.cibertec.androidchat.AndroidChatApplication;
import pe.edu.cibertec.androidchat.R;
import pe.edu.cibertec.androidchat.addcontact.ui.AddContactFragment;
import pe.edu.cibertec.androidchat.chat.ui.ChatActivity;
import pe.edu.cibertec.androidchat.contactlist.ContactListPresenter;
import pe.edu.cibertec.androidchat.contactlist.ContactListPresenterImpl;
import pe.edu.cibertec.androidchat.contactlist.adapters.ContactListAdapter;
import pe.edu.cibertec.androidchat.contactlist.entities.User;
import pe.edu.cibertec.androidchat.lib.ImageLoader;
import pe.edu.cibertec.androidchat.login.ui.LoginActivity;

public class ContactListActivity extends AppCompatActivity
                                implements ContactListView, OnItemClickListener {

    private ContactListPresenter contactListPresenter;
    private ContactListAdapter adapter;

    @Bind(R.id.toolbar)                 Toolbar toolbar;
    @Bind(R.id.recyclerViewContacts)    RecyclerView recyclerViewContacts;
    @Bind(R.id.fab)                     FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        ButterKnife.bind(this);

        contactListPresenter = new ContactListPresenterImpl(this);
        contactListPresenter.onCreate();

        toolbar.setSubtitle(contactListPresenter.getCurrentUserEmail());
        setSupportActionBar(toolbar);

        setupAdapter();
        setupRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contactlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            contactListPresenter.signOff();

            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    private void setupAdapter() {
        AndroidChatApplication app = (AndroidChatApplication) this.getApplication();
        ImageLoader imageLoader = app.getImageLoader();

        adapter = new ContactListAdapter(new ArrayList <User>(), imageLoader,  this);

/*
        User user = new User();
        user.setOnline(false);
        user.setEmail("lcastillovillena@gmail.com");


        adapter = new ContactListAdapter(Arrays.asList(new User[] {user}), imageLoader,  this);
*/

    }

    private void setupRecyclerView() {
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewContacts.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        contactListPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        contactListPresenter.onPause();
    }

    @Override
    protected void onDestroy() {
        contactListPresenter.onDestroy();
        super.onDestroy();
    }


    @Override
    public void onContactAdded(User user) {
        adapter.add(user);
    }

    @Override
    public void onContactChanged(User user) {
        adapter.update(user);
    }

    @Override
    public void onContactRemoved(User user) {
        adapter.remove(user);
    }

    @OnClick(R.id.fab)
    public void addContact(){
        AddContactFragment frag = new AddContactFragment();
        frag.show(getSupportFragmentManager(), "");
    }

    @Override
    public void onItemClick(User user) {
        Intent intent = new Intent(this, ChatActivity.class);

        intent.putExtra(ChatActivity.EMAIL_KEY, user.getEmail());
        intent.putExtra(ChatActivity.ONLINE_KEY, user.isOnline());

        startActivity(intent);
    }

    @Override
    public void onItemLongClick(User user) {
        contactListPresenter.removeContact(user.getEmail());
    }
}

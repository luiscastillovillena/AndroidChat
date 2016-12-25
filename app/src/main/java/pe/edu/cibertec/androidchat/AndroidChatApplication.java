package pe.edu.cibertec.androidchat;

import android.app.Application;
import com.google.firebase.database.FirebaseDatabase;

import pe.edu.cibertec.androidchat.lib.GlideImageLoader;
import pe.edu.cibertec.androidchat.lib.ImageLoader;

/**
 * Created by Administrador on 28/09/2016.
 */

public class AndroidChatApplication extends Application {
    private ImageLoader imageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        setupFirebase();
    }

    private void setupFirebase(){
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    private void setupImageLoader() {
        imageLoader = new GlideImageLoader(this);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}

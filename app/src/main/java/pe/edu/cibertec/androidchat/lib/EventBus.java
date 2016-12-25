package pe.edu.cibertec.androidchat.lib;

/**
 * Created by Administrador on 04/10/2016.
 */

public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);
}

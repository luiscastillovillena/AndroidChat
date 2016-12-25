package pe.edu.cibertec.androidchat.chat.adapters;

import android.content.Context;

import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pe.edu.cibertec.androidchat.R;
import pe.edu.cibertec.androidchat.chat.entities.ChatMessage;

/**
 * Created by Administrador on 05/12/2016.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private Context context;
    private List<ChatMessage> chatMessages;

    public ChatAdapter(Context context, List<ChatMessage> chatMessages) {
        this.context = context;
        this.chatMessages = chatMessages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);

        String msg = chatMessage.getMsg();
        holder.txtMessage.setText(msg);

        int color = fetchColor(R.attr.colorPrimary);
        int gravity = Gravity.LEFT;

        if (!chatMessage.isSentByMe()) {
            gravity = Gravity.RIGHT;
            color = fetchColor(R.attr.colorAccent);
        }

        // Asigna el color adecuado a los mensajes
        holder.txtMessage.setBackgroundColor(color);

        // Obtiene una referencia a los parametros de txtMessage del ViewHolder enviado.
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();

        // Asigna la posición adecuada del emisor a la propiedad gravity.
        params.gravity = gravity;

        // Asigna los parametros modificados al ViewHolder.
        holder.txtMessage.setLayoutParams(params);
    }

    private int fetchColor(int color) {
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typedValue.data,
                new int[] { color });

        int returnColor = a.getColor(0, 0);
        a.recycle();

        return returnColor;
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public void add(ChatMessage message) {
        if (!alreadyInAdapter(message)) {
            this.chatMessages.add(message);
            this.notifyDataSetChanged();
        }
    }

    private boolean alreadyInAdapter(ChatMessage newMsg){
        boolean alreadyInAdapter = false;
        for (ChatMessage msg : this.chatMessages) {
            if (msg.getMsg().equals(newMsg.getMsg()) &&
                    msg.getSender().equals(newMsg.getSender())) {
                alreadyInAdapter = true;
                break;
            }
        }

        return alreadyInAdapter;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.txtMessage) TextView txtMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

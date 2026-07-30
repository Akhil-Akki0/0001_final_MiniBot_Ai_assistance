package com.akkio.assistant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.akkio.assistant.models.ChatMessage;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<ChatMessage> messages;

    public ChatAdapter(List<ChatMessage> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        holder.tvSpeaker.setText(message.getSpeaker());
        holder.tvMessage.setText(message.getText());
        holder.tvTime.setText(message.getFormattedTime());

        if (message.isAI()) {
            holder.tvSpeaker.setTextColor(0xFFFF0000);
            holder.tvMessage.setBackgroundResource(R.drawable.bg_message_akkio);
        } else {
            holder.tvSpeaker.setTextColor(0xFF4488FF);
            holder.tvMessage.setBackgroundResource(R.drawable.bg_message_user);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSpeaker, tvMessage, tvTime;

        ViewHolder(View itemView) {
            super(itemView);
            tvSpeaker = itemView.findViewById(R.id.tvSpeaker);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
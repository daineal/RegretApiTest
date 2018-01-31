package com.rat.regresapitest.presentation.ui.global.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rat.regresapitest.R;
import com.rat.regresapitest.presentation.ui.global.views.dribbbletextview.RatTextView;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    public final TextView userName;
    public final ImageView userAvatar;
    public final RatTextView commentText;
    public final TextView likesCount;

    public CommentViewHolder(View itemView) {
        super(itemView);

        userName = itemView.findViewById(R.id.user_name);
        userAvatar = itemView.findViewById(R.id.user_avatar);
        commentText = itemView.findViewById(R.id.text);
        likesCount = itemView.findViewById(R.id.likes_count);
    }

}
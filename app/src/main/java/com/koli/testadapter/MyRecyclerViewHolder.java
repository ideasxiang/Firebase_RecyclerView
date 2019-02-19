package com.koli.testadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView mUserName, mUserStatus;
    public Button mDeleteRow;

    public MyRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        mUserName = itemView.findViewById(R.id.mRowUserName);
        mUserStatus = itemView.findViewById(R.id.mRowUserStatus);
        mDeleteRow = itemView.findViewById(R.id.mRowDeleteBtn);
    }
}

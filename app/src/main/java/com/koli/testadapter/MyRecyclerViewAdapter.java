package com.koli.testadapter;

import android.support.annotation.NonNull;
import android.support.v4.util.LogWriter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder> {

    MainActivity mainActivity;
    ArrayList<User> userArrayList;

    public MyRecyclerViewAdapter(MainActivity mainActivity, ArrayList<User> userArrayList) {
        this.mainActivity = mainActivity;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mainActivity.getBaseContext());
        View view = layoutInflater.inflate(R.layout.single_row, parent, false);

        return new MyRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, final int position) {

        holder.mUserName.setText(userArrayList.get(position).getUserName());
        holder.mUserStatus.setText(userArrayList.get(position).getUserStatus());
        holder.mDeleteRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedRow(position);
            }
        });

    }

    private void deleteSelectedRow(int position) {
        mainActivity.db.collection("users")
                .document(userArrayList.get(position).getUserId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(mainActivity.getBaseContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        mainActivity.loadDataFromFirebase();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mainActivity, "Unable To Delete --3--", Toast.LENGTH_SHORT).show();
                        Log.w("--3--",e.getMessage());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }
}

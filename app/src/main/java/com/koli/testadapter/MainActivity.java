package com.koli.testadapter;


import android.support.annotation.NonNull;
import android.support.v4.util.LogWriter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    RecyclerView mRecyclerView;
    ArrayList<User> userArrayList;
    MyRecyclerViewAdapter adapter;
    Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        userArrayList = new ArrayList<>();

        setUpRecyclerView();
        setUpFireBase();
        addTestDataToFirebase();
        loadDataFromFirebase();
        setUpUpdateButton();

    }

    private void setUpUpdateButton() {
        updateButton = findViewById(R.id.mUpdateBtn);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDataFromFirebase();
            }
        });
    }

    public void loadDataFromFirebase() {

        if (userArrayList.size() > 0)
            userArrayList.clear();

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot: task.getResult()){
                            User user = new User(documentSnapshot.getId(),
                                    documentSnapshot.getString("name"),
                                    documentSnapshot.getString("status"));
                            userArrayList.add(user);
                        }
                        adapter = new MyRecyclerViewAdapter(MainActivity.this, userArrayList);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Problem ---1---", Toast.LENGTH_SHORT).show();
                        Log.w("---1---", e.getMessage());
                    }
                });
    }

    private void addTestDataToFirebase() {

        for (int i = 0; i < 2; i++){
            Random random = new Random();
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("name", "try name" + random.nextInt(50));
            dataMap.put("status", "try status" + random.nextInt(50));

            db.collection("users")
                    .add(dataMap)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(MainActivity.this, "Added Test Data", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void setUpFireBase() {
        db = FirebaseFirestore.getInstance();
    }

    private void setUpRecyclerView() {
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}

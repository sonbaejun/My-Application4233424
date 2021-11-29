package com.example.myapplication4233424;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

import listener.OnPostListener;

public class Community extends BasicActivity {

    Button logout;
    FloatingActionButton addText;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    private MainAdapter mainAdapter;
    private ArrayList<PostInfo> postList;
    private static final String TAG = "Community";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        if(firebaseUser == null) {
            startLoginActivity(SignUpActivity.class);
        }else {
            firebaseFirestore = FirebaseFirestore.getInstance();
            DocumentReference documentReference = firebaseFirestore.collection("users").document(firebaseUser.getUid());
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d(TAG, "No such document");
                                startLoginActivity(MemberActivity.class);
                            }
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }


//        logout = (Button) findViewById(R.id.logout);
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                startLoginActivity(SignUpActivity.class);
//            }
//        });
        addText = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        addText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoginActivity(WritePostActivity.class);
            }
        });
        postList = new ArrayList<>();
        mainAdapter = new MainAdapter(Community.this, postList);
        mainAdapter.setOnPostListener(onPostListener);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Community.this));
        recyclerView.setAdapter(mainAdapter);

    }


    @Override
    protected void onResume() {
        super.onResume();
        postUpdate();
    }

    private void startLoginActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    private void postUpdate() {
        if(firebaseUser != null) {
            CollectionReference collectionReference = firebaseFirestore.collection("posts");
            collectionReference.orderBy("createdAt", Query.Direction.DESCENDING).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                postList.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    postList.add(new PostInfo(
                                            document.getData().get("title").toString(),
                                            (ArrayList) document.getData().get("contents"),
                                            document.getData().get("publisher").toString(),
                                            new Date(document.getDate("createdAt").getTime()),
                                            document.getId()
                                    ));
                                }
                                mainAdapter.notifyDataSetChanged();
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
    }

    OnPostListener onPostListener = new OnPostListener() {
        @Override
        public void onDelete(String id) {
            firebaseFirestore.collection("posts").document(id)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            postUpdate();
                            Toast.makeText(getApplicationContext(), "삭제를 완료했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "삭제에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        @Override
        public void onModify(String id) {
            startLoginActivity(WritePostActivity.class, id);
        }
    };
    private void startLoginActivity(Class c, String id) {
        Intent intent = new Intent(this, c);
        intent.putExtra("id",id);
        startActivity(intent);
    }
}
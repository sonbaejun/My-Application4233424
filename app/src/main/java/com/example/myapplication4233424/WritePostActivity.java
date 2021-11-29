package com.example.myapplication4233424;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

public class WritePostActivity extends BasicActivity{
    Button check;
    Button image, imageModify, delete;
    private static final String TAG ="WritePostActivity";
    private FirebaseUser user;
    private ArrayList<String> pathList = new ArrayList<>();
    private LinearLayout parent;
    private int pathCount;
    private RelativeLayout buttonsBackgroundLayout;
    private RelativeLayout loaderLayout;
    private ImageView selectedImageView;
    private EditText selectedEditText, contentsEditText, titleEditText;
    private int successCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);
        parent = findViewById(R.id.contentsLayout);

        loaderLayout = findViewById(R.id.loaderLayout);

        check = (Button) findViewById(R.id.check);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storageUpload();
            }
        });

        image = (Button) findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoginActivity(GalleryActivity.class, 0);
            }
        });

        buttonsBackgroundLayout = (RelativeLayout) findViewById(R.id.buttonsBackgroundLayout);
        buttonsBackgroundLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonsBackgroundLayout.getVisibility() == View.VISIBLE) {
                    buttonsBackgroundLayout.setVisibility(View.GONE);
                }
            }
        });

        imageModify = (Button) findViewById(R.id.imageModify);
        delete = (Button) findViewById(R.id.delete);

        imageModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoginActivity(GalleryActivity.class, 1);
                buttonsBackgroundLayout.setVisibility(View.GONE);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent.removeView((View) selectedImageView.getParent());
                buttonsBackgroundLayout.setVisibility(View.GONE);
            }
        });

        contentsEditText = (EditText) findViewById(R.id.contentsEditText);
        contentsEditText.setOnFocusChangeListener(onFocusChangeListener);

        titleEditText = (EditText) findViewById(R.id.titleEditText);
        titleEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    selectedEditText = null;
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == Activity.RESULT_OK) {
                    String profilePath = data.getStringExtra("profilePath");
                    pathList.add(profilePath);

                    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    LinearLayout linearLayout = new LinearLayout(WritePostActivity.this);
                    linearLayout.setLayoutParams(layoutParams);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);


                    if(selectedEditText == null) {
                        parent.addView(linearLayout);

                    } else {
                        for(int i=0; i < parent.getChildCount(); i++) {
                            if(parent.getChildAt(i) == selectedEditText.getParent()) {
                                parent.addView(linearLayout, i+1);
                                break;
                            }
                        }
                    }

                    ImageView imageView = new ImageView(WritePostActivity.this);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setAdjustViewBounds(true);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            buttonsBackgroundLayout.setVisibility(View.VISIBLE);
                            selectedImageView = (ImageView) view;
                        }
                    });
                    Glide.with(this).load(profilePath).override(1000).into(imageView);
                    linearLayout.addView(imageView);

                    EditText editText = new EditText(WritePostActivity.this);
                    editText.setLayoutParams(layoutParams);
                    editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_CLASS_TEXT);
                    editText.setHint("내용");
                    editText.setOnFocusChangeListener(onFocusChangeListener);
                    linearLayout.addView(editText);
                }
                break;
                case 1:
                    if (resultCode == Activity.RESULT_OK) {
                        String profilePath = data.getStringExtra("profilePath");
                        Glide.with(this).load(profilePath).override(1000).into(selectedImageView);
                    }
                    break;
        }
    }


    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if(b) {
                selectedEditText = (EditText) view;
            }
        }
    };

    private void storageUpload() {
        final String title = ((EditText) findViewById(R.id.titleEditText)).getText().toString();

        if (title.length() > 0) {
            loaderLayout.setVisibility(View.VISIBLE);
            final ArrayList<String> contentsList = new ArrayList<>();
            user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            String id = getIntent().getStringExtra("id");

            DocumentReference dr;
            if(id == null) {
                dr = firebaseFirestore.collection("posts").document();
            }else {
                dr = firebaseFirestore.collection("posts").document(id);
            }

            final DocumentReference documentReference = dr;

            for(int i=0; i < parent.getChildCount(); i++) {
                LinearLayout linearLayout = (LinearLayout) parent.getChildAt(i);

                for(int ii=0; ii < linearLayout.getChildCount(); ii++) {
                    View view = linearLayout.getChildAt(ii);
                    if(view instanceof EditText) {
                        String text = ((EditText)view).getText().toString();
                        if(text.length() > 0) {
                            contentsList.add(text);
                        }
                    } else {
                        contentsList.add(pathList.get(pathCount));
                        String[] pathArray = pathList.get(pathCount).split("\\.");
                        final StorageReference mountainImagesRef = storageRef.child("posts/" + documentReference.getId() + "/"+pathCount+"."+pathArray[pathArray.length-1]);
                        try {
                            InputStream stream = new FileInputStream(new File(pathList.get(pathCount)));
                            StorageMetadata metadata = new StorageMetadata.Builder().setCustomMetadata("index",""+(contentsList.size()-1)).build();
                            UploadTask uploadTask = mountainImagesRef.putStream(stream, metadata);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    final int index = Integer.parseInt(taskSnapshot.getMetadata().getCustomMetadata("index"));
                                    mountainImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            contentsList.set(index, uri.toString());
                                            successCount++;
                                            if(pathList.size() == successCount) {
                                                //완료
                                                PostInfo postInfo = new PostInfo(title, contentsList, user.getUid(), new Date());
                                                storeUpload(documentReference, postInfo);
                                            }
                                        }
                                    });
                                }
                            });
                        }catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        pathCount++;
                    }
                }
            }
            if(pathList.size() == 0) {
                PostInfo postInfo = new PostInfo(title, contentsList, user.getUid(), new Date());
                storeUpload(documentReference, postInfo);
            }

        }else {
            Toast.makeText(getApplicationContext(), "제목을 입력 해 주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private void storeUpload(DocumentReference documentReference, PostInfo postInfo) {
        documentReference.set(postInfo)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "DocumentSnapshot successfully written!");
                    loaderLayout.setVisibility(View.GONE);
                    finish();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error writing document", e);
                    loaderLayout.setVisibility(View.GONE);
                }
            });

    }

    private void startLoginActivity(Class c, int requestCode) {
        Intent intent = new Intent(this, c);
        startActivityForResult(intent, requestCode);
    }
}

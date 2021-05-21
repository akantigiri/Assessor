package com.google.firebase.udacity.friendlychat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class assignements extends AppCompatActivity {
    String jo;
    private static final String TAG = "MainActivity";
    public static String mu;
    public static FirebaseStorage mFirebaseStorage;
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mMessagesDatabaseReference;
    public static DatabaseReference mTimetdb;
    public static List<FriendlyMessage> friendlyMessages;
    public static MessageAdapter mMessageAdapter;
    SimpleDateFormat formatter;
    public static String mUsername;
    private SharedPreferences sharedpreferences;
    private SharedPreferences shrdprfrncs;
    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    public static int mSelectedItem;
    long mi, csk;
    static String fy;

    private ProgressBar mProgressBar;
    private ImageButton mPhotoPickerButton;
    private ListView mMessageListView;
    private TextView Timestamp;
    private EditText mMessageEditTextques;
    private long a, b, c, d;
    private EditText mDuration;
    private Button mSendButton;
    boolean y = false;
    private Button mChat;

    String huo;

    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public static final int RC_SIGN_IN = 1;
    public static final int RC_PHOTO_PICKER = 2;
    private StorageReference mChatPhotosStorageReference;

    private boolean z = false;
    String s1, s2, s3, s4;
    int yo;
    static int act;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        act=0;
        yo = -1;
        super.onCreate(savedInstanceState);

        // msettimer.setEnabled(true);
        setContentView(R.layout.activity_chat);

        setTitle(SecondActivity.hy+" Assignments");
        FirebaseApp.initializeApp(this);
        FirebaseDatabase mFirebaseDatabase;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mChatPhotosStorageReference = mFirebaseStorage.getReference().child("chat_photos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        formatter = new SimpleDateFormat("h:mm a");
        huo=formatter.format(new Date());


        if(SecondActivity.hy!="CSN-254")
            mMessagesDatabaseReference = mFirebaseDatabase.getReference().child(SecondActivity.hy+"D");
        else
            mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("CSN_254D");


        mTimetdb = mFirebaseDatabase.getReference().child("timet");
        mTimetdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String lo = "";
                for (DataSnapshot locationSnapshot : snapshot.getChildren()) {
                    lo = locationSnapshot.getValue().toString().trim();
                }
                Log.d("TAGE", "AB" + lo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAGE", "ABCD");

            }
        });
        mUsername = ANONYMOUS;
        mUsername=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        msgs(mUsername);
        // Initialize references to views
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mMessageListView = (ListView) findViewById(R.id.messageListView);
        mPhotoPickerButton = (ImageButton) findViewById(R.id.photoPickerButton);
        mMessageEditTextques = (EditText) findViewById(R.id.messageEditTextques);

        mSendButton = (Button) findViewById(R.id.sendButton);

        // Initialize message ListView and its adapter
        y = false;
        List<FriendlyMessage> friendlyMessages = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(this, R.layout.item_message, friendlyMessages);
        mMessageListView.setAdapter(mMessageAdapter);

        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        // ImagePickerButton shows an image picker to upload a image for a message
        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Fire an intent to show an image picker
            }
        });

        mMessageEditTextques.setHint("AssignmentDoubts");



        // Enable Send button when there's text to send
        mMessageEditTextques.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        mMessageEditTextques.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});

        //Toast.makeText(MainActivity.this,"Hello",Toast.LENGTH_SHORT).show();










        // Send button sends a message and clears the EditText
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click
                String s = mMessageEditTextques.getText().toString();

                FriendlyMessage friendlyMessage;formatter = new SimpleDateFormat("h:mm a");
                huo=formatter.format(new Date());

                friendlyMessage = new FriendlyMessage(s, mUsername, null,huo);

                mMessagesDatabaseReference.push().setValue(friendlyMessage);
                mMessageEditTextques.setText("");


            }
        });




        // ImagePickerButton shows an image picker to upload a image for a message
        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });
    }



    private void getMessages(){
        mMessagesDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void msgs(String displayName) {

        if (mChildEventListener == null) {

            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    FriendlyMessage obj = snapshot.getValue(FriendlyMessage.class);
                    mMessageAdapter.add(obj);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
            mMessagesDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_CANCELED) {
                finish();
            }
        } else if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            final StorageReference ref = mChatPhotosStorageReference.child(uri.getLastPathSegment());
            ref.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        formatter = new SimpleDateFormat("h:mm a");
                        huo=formatter.format(new Date());
                        FriendlyMessage friendlyMessage = new FriendlyMessage(null, mUsername, downloadUri.toString(),huo);
                        mMessagesDatabaseReference.push().setValue(friendlyMessage);
                    } else {
                        Toast.makeText(assignements.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}



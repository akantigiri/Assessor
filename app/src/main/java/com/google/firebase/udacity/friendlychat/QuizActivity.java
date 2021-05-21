package com.google.firebase.udacity.friendlychat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static String mu;
    public static FirebaseStorage mFirebaseStorage;
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mMessagesDatabaseReference;
    public static List<FriendlyMessage> friendlyMessages;
    public static MessageAdapter mMessageAdapter;

    public String mUsername;
    private SharedPreferences sharedpreferences;
    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    public static int mSelectedItem;


    private ListView mMessageListView;
    private ProgressBar mProgressBar;
    private ImageButton mPhotoPickerButton;
    private EditText mMessageEditTextques;
    private EditText mMessageEditTextopA;
    private EditText mMessageEditTextopB;
    private EditText mMessageEditTextopC;
    private EditText mMessageEditTextopD;
    private EditText mMessageEditTextcrctop;
    private Button mQuiz;
    private long a, b, c, d;
    private EditText mDuration;
    private Button mSendButton;
    boolean y = false;
    private Button mChat;
    private Button mQuiza;
    private Button mAssgn;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    public static final int RC_SIGN_IN = 1;
    public static final int RC_PHOTO_PICKER = 2;

    private StorageReference mChatPhotosStorageReference;
    private Button msettimer;
    private TextView mtime;
    private boolean z = false;
    String s1, s2, s3, s4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // msettimer.setEnabled(true);
        setContentView(R.layout.activity_quiz);
        sharedpreferences = getSharedPreferences("SCORE", Context.MODE_PRIVATE);
        FirebaseApp.initializeApp(this);
        FirebaseDatabase mFirebaseDatabase;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mChatPhotosStorageReference = mFirebaseStorage.getReference().child("chat_photos");
        mDuration = (EditText) findViewById(R.id.duration);
        mQuiza = (Button) findViewById(R.id.Quiz);
        mAssgn = (Button) findViewById(R.id.Assgn);
        String u = mDuration.getText().toString();

        if (u.length() > 0) {
            y = true;
        }
        if (y) {
            c = Integer.parseInt(mDuration.getText().toString());
            if (c > 0) {
                a = Integer.parseInt(mDuration.getText().toString());
                a = a * 1000;
            }
        }
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("messages");
        mMessagesDatabaseReference = mFirebaseDatabase.getReference("").child("messages");

        mUsername = ANONYMOUS;
        // Initialize references to views
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mMessageListView = (ListView) findViewById(R.id.messageListView);
        mPhotoPickerButton = (ImageButton) findViewById(R.id.photoPickerButton);
        mMessageEditTextques = (EditText) findViewById(R.id.messageEditTextques);
        mMessageEditTextopA = (EditText) findViewById(R.id.messageEditTextopA);
        mMessageEditTextopB = (EditText) findViewById(R.id.messageEditTextopB);
        mMessageEditTextopC = (EditText) findViewById(R.id.messageEditTextopC);
        mMessageEditTextopD = (EditText) findViewById(R.id.messageEditTextopD);
        mMessageEditTextcrctop = (EditText) findViewById(R.id.messageEditTextcrctop);
        mSendButton = (Button) findViewById(R.id.sendButton);
        msettimer = (Button) findViewById(R.id.settimer);
        mQuiz = (Button) findViewById(R.id.finishQuiz);
        mtime = (TextView) findViewById(R.id.time);
        // Initialize message ListView and its adapter
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

        mMessageEditTextques.setHint("Chat");
        mMessageEditTextopA.setVisibility(View.GONE);
        mMessageEditTextopB.setVisibility(View.GONE);
        mMessageEditTextopC.setVisibility(View.GONE);
        mMessageEditTextopD.setVisibility(View.GONE);
        mMessageEditTextcrctop.setVisibility(View.GONE);
        mDuration.setVisibility(View.GONE);
        msettimer.setVisibility(View.GONE);
        mAssgn.setVisibility(View.GONE);

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
        mMessageEditTextopA.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});
        mMessageEditTextopB.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});
        mMessageEditTextopC.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});
        mMessageEditTextopD.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});
        mMessageEditTextcrctop.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});
        msettimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = mDuration.getText().toString();


                mQuiz.setEnabled(true);
                if (u.length() > 0) {
                    y = true;
                    z = true;
                }
                if (y) {
                    c = Integer.parseInt(mDuration.getText().toString());
                    if (c > 0) {
                        a = Integer.parseInt(mDuration.getText().toString());
                        a = a * 1000;
                    }
                }
                mDuration.setText("");
                Toast.makeText(QuizActivity.this, "Timer is set for " + (a / 1000) + " seconds", Toast.LENGTH_SHORT).show();
                new CountDownTimer(a, 1000) {

                    @Override
                    public void onTick(long a) {
                        b = a;
                        b = (a / 1000);
                        Log.v("TAGE", "A" + b);
                        long hours = (b / 3600);
                        b -= (hours * 3600);

                        long minutes = (b / 60);
                        b -= (minutes * 60);

                        long seconds = b;
                        String g = "";


                        c = hours;
                        Log.v("TAGE", "" + c);
                        if (c < 10) {
                            g = g + "0" + c;
                        } else {
                            g = g + c;
                        }
                        c = minutes;
                        Log.v("TAGE", "" + c);
                        g = g + ":";
                        if (c < 10) {
                            g = g + "0" + c;
                        } else {
                            g = g + c;
                        }
                        g = g + ":";
                        c = seconds;
                        Log.v("TAGE", "" + c);
                        if (c < 10) {
                            g = g + "0" + c;
                        } else {
                            g = g + c;
                        }
                        mtime.setText(g);

                    }

                    public void onFinish() {
                        if (y) {
                            int score = sharedpreferences.getInt("SCORE", 0);
                            SharedPreferences.Editor ey = sharedpreferences.edit();
                            ey.remove("SCORE");
                            ey.apply();

                            sharedpreferences = getSharedPreferences("SCORE", Context.MODE_PRIVATE);
                            FriendlyMessage friendlyMessage = new FriendlyMessage("" + score, mUsername, null);
                            mMessagesDatabaseReference.push().setValue(friendlyMessage);
                            y = false;
                            mQuiz.setEnabled(false);
                            z = false;
                        }

                    }
                }.start();

            }
        });
        mAssgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessageEditTextques.setHint("Chat");
                mMessageEditTextopA.setVisibility(View.GONE);
                mMessageEditTextopB.setVisibility(View.GONE);
                mMessageEditTextopC.setVisibility(View.GONE);
                mMessageEditTextopD.setVisibility(View.GONE);
                mMessageEditTextcrctop.setVisibility(View.GONE);

                    mtime.setVisibility(View.VISIBLE);
                mDuration.setVisibility(View.GONE);
                msettimer.setVisibility(View.GONE);
                mAssgn.setVisibility(View.GONE);
            }
        });
        mQuiza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("TAGE", "A");
                String av = "Akanti Giri";
                mUsername = mUsername.trim();
                if (mUsername.equals(av)) {

                    mMessageEditTextques.setHint("Question");
                    mMessageEditTextopA.setVisibility(View.VISIBLE);
                    mMessageEditTextopB.setVisibility(View.VISIBLE);
                    mMessageEditTextopC.setVisibility(View.VISIBLE);
                    mMessageEditTextopD.setVisibility(View.VISIBLE);
                    mMessageEditTextcrctop.setVisibility(View.VISIBLE);

                    mtime.setVisibility(View.GONE);
                    mDuration.setVisibility(View.VISIBLE);
                    msettimer.setVisibility(View.VISIBLE);
                    mAssgn.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(QuizActivity.this, "You are not allowed to set Quiz", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (y) {

                    int score = sharedpreferences.getInt("SCORE", 0);
                    SharedPreferences.Editor ey = sharedpreferences.edit();
                    ey.remove("SCORE");
                    ey.apply();

                    FriendlyMessage friendlyMessage = new FriendlyMessage("" + score, mUsername, null);
                    mMessagesDatabaseReference.push().setValue(friendlyMessage);
                    y = false;
                    z = false;
                    String giri = "00:00:00";
                    mtime.setText(giri);
                }

            }
        });


        // Send button sends a message and clears the EditText
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click
                String s = mMessageEditTextques.getText().toString();
                s1 = mMessageEditTextopA.getText().toString();
                s2 = mMessageEditTextopB.getText().toString();
                s3 = mMessageEditTextopC.getText().toString();
                s4 = mMessageEditTextopD.getText().toString();
                FriendlyMessage friendlyMessage;
                if (!s1.equals("") || !s2.equals("") || !s3.equals("") || !s4.equals("")) {
                    friendlyMessage = new FriendlyMessage(s, "Question", null);
                } else {
                    friendlyMessage = new FriendlyMessage(s, mUsername, null);
                }
                mMessagesDatabaseReference.push().setValue(friendlyMessage);
                s = " " + mMessageEditTextopA.getText().toString();
                String u = "Option-A";
                if (!s.equals(" ")) {
                    friendlyMessage = new FriendlyMessage(s, u, null);
                    mMessagesDatabaseReference.push().setValue(friendlyMessage);
                }
                s = " " + mMessageEditTextopB.getText().toString();
                if (!s.equals(" ")) {
                    friendlyMessage = new FriendlyMessage(s, "Option-B", null);
                    mMessagesDatabaseReference.push().setValue(friendlyMessage);
                }
                s = " " + mMessageEditTextopC.getText().toString();
                if (!s.equals(" ")) {
                    friendlyMessage = new FriendlyMessage(s, "Option-C", null);
                    mMessagesDatabaseReference.push().setValue(friendlyMessage);
                }
                s = " " + mMessageEditTextopD.getText().toString();
                if (!s.equals(" ")) {
                    friendlyMessage = new FriendlyMessage(s, "Option-D", null);
                    mMessagesDatabaseReference.push().setValue(friendlyMessage);
                }
                s = mMessageEditTextcrctop.getText().toString();
                if (!s.equals("")) {
                    friendlyMessage = new FriendlyMessage(s, " ", null);
                    mMessagesDatabaseReference.push().setValue(friendlyMessage);
                }
                mMessageEditTextques.setText("");
                mMessageEditTextopA.setText("");
                mMessageEditTextopB.setText("");
                mMessageEditTextopC.setText("");
                mMessageEditTextopD.setText("");
                mMessageEditTextcrctop.setText("");
            }
        });

        mMessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FriendlyMessage obj = mMessageAdapter.getItem(position);
                if (obj.getPhotoUrl() != null) {
                    return;
                }
                String s = obj.getText();
                String g;
                mQuiz.setEnabled(true);
                if (s.length() < 1) {
                    return;
                }
                String v = "";
                v = v + s.charAt(0);
                if (v.equals(" ")) {

                    FriendlyMessage obje = mMessageAdapter.getItem(position + 1);
                    String sa = obje.getText();
                    v = "";
                    v = v + sa.charAt(0);
                    if (!(v.equals(" "))) {
                        g = "";
                        for (int i = 1; i < s.length(); i++) {
                            g += s.charAt(i);
                        }
                        if (g.equals(sa)) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putInt("SCORE", sharedpreferences.getInt("SCORE", 0) + 1);
                            editor.apply();
                            int score = sharedpreferences.getInt("SCORE", 0);
                            return;
                        } else {
                            return;
                        }
                    }
                    obje = mMessageAdapter.getItem(position + 2);
                    sa = obje.getText();
                    v = "";
                    v = v + sa.charAt(0);
                    if (!(v.equals(" "))) {
                        g = "";
                        for (int i = 1; i < s.length(); i++) {
                            g += s.charAt(i);
                        }
                        if (g.equals(sa)) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putInt("SCORE", sharedpreferences.getInt("SCORE", 0) + 1);
                            editor.apply();
                            int score = sharedpreferences.getInt("SCORE", 0);
                            return;
                        } else {
                            return;
                        }
                    }
                    obje = mMessageAdapter.getItem(position + 3);
                    sa = obje.getText();
                    v = "";
                    v = v + sa.charAt(0);
                    if (!(v.equals(" "))) {
                        g = "";
                        for (int i = 1; i < s.length(); i++) {
                            g += s.charAt(i);
                        }
                        if (g.equals(sa)) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putInt("SCORE", sharedpreferences.getInt("SCORE", 0) + 1);
                            editor.apply();
                            int score = sharedpreferences.getInt("SCORE", 0);
                            return;
                        } else {
                            return;
                        }
                    }
                    obje = mMessageAdapter.getItem(position + 4);
                    sa = obje.getText();
                    v = "";
                    v = v + sa.charAt(0);
                    if (!(v.equals(" "))) {
                        g = "";
                        for (int i = 1; i < s.length(); i++) {
                            g += s.charAt(i);
                        }
                        if (g.equals(sa)) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putInt("SCORE", sharedpreferences.getInt("SCORE", 0) + 1);
                            editor.apply();
                            int score = sharedpreferences.getInt("SCORE", 0);

                            return;
                        } else {
                            return;
                        }
                    }

                }

            }

        });

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    Toast.makeText(QuizActivity.this, "Youre signed-in", Toast.LENGTH_SHORT).show();

                    onSignedInInitialize(user.getDisplayName());

                } else {
                    onSignedOutCleanup();
                    startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setIsSmartLockEnabled(false).setAvailableProviders(Arrays.asList(
                            new AuthUI.IdpConfig.EmailBuilder().build())).build(), RC_SIGN_IN);
                }
            }
        };
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

    private void onSignedOutCleanup() {
        mUsername = ANONYMOUS;
        mMessageAdapter.clear();
        if (mChildEventListener != null) {
            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    private void onSignedInInitialize(String displayName) {

        if (mChildEventListener == null) {
            mUsername = displayName;
            mu = mUsername;
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



    @Override
    protected void onPause() {
        super.onPause();

        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }

        if (mChildEventListener != null) {
            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
        mUsername = ANONYMOUS;
        mMessageAdapter.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
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
                        FriendlyMessage friendlyMessage = new FriendlyMessage(null, mUsername, downloadUri.toString());
                        mMessagesDatabaseReference.push().setValue(friendlyMessage);
                    } else {
                        Toast.makeText(QuizActivity.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}


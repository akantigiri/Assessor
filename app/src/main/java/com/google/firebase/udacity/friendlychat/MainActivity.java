package com.google.firebase.udacity.friendlychat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private FirebaseAuth firebaseAuth;
    private TextView userRegistration;
    private ImageView sampleimage;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
sampleimage=(ImageView)findViewById(R.id.imageView);
        Login = (Button)findViewById(R.id.btnLogin);
        userRegistration = (TextView)findViewById(R.id.tvRegister);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null){
            finish();

            QuizActivity ob=new QuizActivity();

            startActivity(new Intent(MainActivity.this,SecondActivity.class));
            //error in second activity because i havent created any file with name secong activity it will automatically go in one
            // such file is created

        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });
        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Registrationactivity.class));
            }
        });


    }
    private void validate(String userName, String userPassword) {

        progressDialog.setMessage("Wait we are checking your credentials");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                    finish();



                    startActivity(new Intent(MainActivity.this, SecondActivity.class));


                    //checkEmailVerification(); i removed email verification not needed for our app
                }else{
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();


                    progressDialog.dismiss();

                }
            }
        });


    }
}
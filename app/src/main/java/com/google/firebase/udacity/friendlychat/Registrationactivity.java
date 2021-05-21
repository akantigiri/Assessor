package com.google.firebase.udacity.friendlychat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registrationactivity extends AppCompatActivity {
    private EditText Name, userPassword, userEmail;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myref;
    String email, age, password;
    static String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Name = (EditText)findViewById(R.id.etName);
        userPassword = (EditText)findViewById(R.id.etUserPassword);
        userEmail = (EditText)findViewById(R.id.etUserEmail);
        regButton = (Button)findViewById(R.id.btnRegister);
        userLogin = (TextView)findViewById(R.id.tvUserLogin);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    //Upload data to the database
                    final String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();
                    final String user_name = Name.getText().toString().trim();


                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if(task.isSuccessful()) {
                                //sendEmailVerification();
                                //sendUserData();


                                FirebaseUser user= firebaseAuth.getCurrentUser();

                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(user_name).build();

                                user.updateProfile(profileUpdates);
                                myref= firebaseDatabase.getReference();
                                String id= firebaseAuth.getUid();
                                User x=new User();
                                x.setName(user_name);
                                x.setEmail(user_email);
                                myref.child("users").child(id).setValue(x);
                                firebaseAuth.signOut();
                                Toast.makeText(Registrationactivity.this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(Registrationactivity.this, MainActivity.class));
                            }else{
                                Toast.makeText(Registrationactivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registrationactivity.this, MainActivity.class));
            }
        });

    }
    private Boolean validate(){
        Boolean result = false;

        name = Name.getText().toString();
        password = userPassword.getText().toString();
        email = userEmail.getText().toString();



        if(name.isEmpty() || password.isEmpty() || email.isEmpty() ){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }

        return result;
    }

}
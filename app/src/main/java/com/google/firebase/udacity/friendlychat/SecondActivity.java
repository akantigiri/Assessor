package com.google.firebase.udacity.friendlychat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private TextView name;
    static String hy="CSN-254";
    static String x="0";
    static String y="0";static String z="0";

    static String g="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        firebaseAuth = FirebaseAuth.getInstance();
        setTitle("Your Courses");

        ArrayList<strng> a=new ArrayList<strng>();
        a.add(new strng("CSN-254","Software Engineering"));
        a.add(new strng("CSN-232"," Operating Systems"));
        a.add(new strng("CSN-212"," Design and Analysis of Algorithms"));
        a.add(new strng("CSN-252"," System Software"));
        a.add(new strng("MTN-105"," Electronic and Electrical Materials"));
        a.add(new strng("ECN-252"," Digital Electronic Circuits Labaratory "));
        a.add(new strng("CSN-351"," Data Base Management Systems"));
        ListView l=(ListView) findViewById(R.id.listay);
        strngadapter b=new strngadapter(this,a);
        l.setAdapter(b);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position==4){hy="MTN_105";x="MTN_105A";y="MTN_105B";z="MTN_105C";
                    Intent i=new Intent(SecondActivity.this,coursehome.class);
                    startActivity(i);g="MTN_105";
                }
                else if(position==2){hy="CSN_212";x="we";y="ew";z="es";
                    Intent i=new Intent(SecondActivity.this,coursehome.class);
                    startActivity(i);g="CSN_212";

                }
                else if(position==1){
                    hy="CSN_232";x="we";y="ew";z="es";
                    Intent i=new Intent(SecondActivity.this,coursehome.class);
                    startActivity(i);g="CSN_232";
                }
                else if(position==3){hy="CSN_252";x="CSN_252A";y="CSN_252B";z="CSN_252C";
                    Intent i=new Intent(SecondActivity.this,coursehome.class);
                    startActivity(i);g="CSN_252";
                }
                else if(position==5){ hy="ECN_252";x="ECN_252A";y="ECN_252B";z="ECN_252C";
                    Intent i=new Intent(SecondActivity.this,coursehome.class);
                    startActivity(i);g="ECN_252";

                }
                else if(position==6){hy="CSN_351";x="CSN_351A";y="CSN_351B";z="CSN_351C";
                    Intent i=new Intent(SecondActivity.this,coursehome.class);
                    startActivity(i);g="CSN_351";}

                else {
                    hy="CSN-254";x="polls";y="resp";z="pok";
                    Intent i=new Intent(SecondActivity.this,coursehome.class);
                    startActivity(i);g="CSN_254";}

            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menubar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutMenu:{
                Logout();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(SecondActivity.this, MainActivity.class));
    }
}
package com.google.firebase.udacity.friendlychat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class coursehome extends AppCompatActivity {
   private ImageView iquiz,ipolls,iassign,ichat;
   private TextView  tquiz,tpolls,tassign,tchat,coursename;
    static String h;
    static String z;
    static String y;
    static String x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coursehome);
      iquiz =findViewById(R.id.imageView2);
        ipolls =findViewById(R.id.imageView4);
        ichat =findViewById(R.id.imageView3);
        iassign =findViewById(R.id.imageView5);
        tquiz =findViewById(R.id.textView);
        tchat =findViewById(R.id.textView2);
        tpolls =findViewById(R.id.textView4);
       tassign =findViewById(R.id.textView5);


       setTitle(SecondActivity.hy);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       iquiz.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(coursehome.this,tests.class));
           }
       });
        tquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(coursehome.this,tests.class));
            }
        });
        ichat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(coursehome.this,chat.class));
            }
        });
        tchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(coursehome.this,chat.class));
            }
        });
        iassign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(coursehome.this,assignements.class));
            }
        });
        tassign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(coursehome.this,assignements.class));
            }
        });
        ipolls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x="Polls";
                startActivity(new Intent(coursehome.this,myapplication.class));
            }
        });
        tpolls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x="Polls";
                startActivity(new Intent(coursehome.this,myapplication.class));
            }
        });



    }
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



}
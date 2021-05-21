package com.google.firebase.udacity.friendlychat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class tests extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference myRef;
private FirebaseAuth auth;
    private ListView listView;
    private TestAdapter testAdapter;
    private int lastPos = -1;
    private boolean isAdmin=false;

    ArrayList<mytest> tests1=new ArrayList<>();



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);


          setTitle("Quizzes");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       auth=FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();
        myRef=database.getReference();
        listView=findViewById(R.id.test_listview);
        testAdapter=new TestAdapter(tests.this,tests1);
        listView.setAdapter(testAdapter);
        getQues();

    }



    public void getQues(){
        //addListenerForSingleValueEvent
        myRef.child(SecondActivity.hy).child("quizzes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tests1.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    mytest t=new mytest();
                    t.setName(snapshot.getKey());
                    t.setTime(Long.parseLong(snapshot.child("Time").getValue().toString()));
                    ArrayList<Question> ques=new ArrayList<>();
                    for (DataSnapshot qSnap:snapshot.child("Questions").getChildren()){
                        ques.add(qSnap.getValue(Question.class));
                    }
                    t.setQuestions(ques);
                    tests1.add(t);

                }
                testAdapter.dataList=tests1;
                testAdapter.notifyDataSetChanged();

                Log.e("The read success: " ,"su"+tests1.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Log.e("The read failed: " ,databaseError.getMessage());
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.testsmenubar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.createquiz:{
               startActivity(new Intent(tests.this,createquiz.class ));
                break;
            }
            case android.R.id.home:{
                finish();
                break;
            }
            case R.id.results:{
                startActivity(new Intent(tests.this,ResultsAdmin.class));
            }

        }
        return super.onOptionsItemSelected(item);
    }
    public void checkForAdmin() {

        myRef.child(SecondActivity.hy).child("admins").addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(Objects.requireNonNull(auth.getUid()))
                        .exists()&& Objects.requireNonNull(dataSnapshot.child(auth.getUid())
                        .getValue()).toString().equals("true")){
                    isAdmin=true;
                    Toast.makeText(getApplicationContext(),"Hello Admin", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    class TestAdapter extends ArrayAdapter<mytest> implements Filterable {
        private Context mContext;
        ArrayList<mytest> dataList;
        public TestAdapter( Context context,ArrayList<mytest> list) {
            super(context, 0 , list);
            mContext = context;
            dataList = list;
        }

        @SuppressLint("SetTextI18n")
        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItem = convertView;
            if(listItem == null)
                listItem = LayoutInflater.from(mContext).inflate(R.layout.test_item,parent,false);

            ((ImageView)listItem.findViewById(R.id.item_imageView)).
                    setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.quiz1));

            ((TextView)listItem.findViewById(R.id.item_textView))
                    .setText(dataList.get(position).getName()+" : "+dataList.get(position).getTime()+"Min");

            ((Button)listItem.findViewById(R.id.item_button)).setText("Attempt");

            (listItem.findViewById(R.id.item_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, AttemptTest.class);
                    intent.putExtra("Questions",dataList.get(position));
                    intent.putExtra("TESTNAME",dataList.get(position).getName());
                    startActivity(intent);
                }
            });


            return listItem;
        }
    }


}
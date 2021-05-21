package com.google.firebase.udacity.friendlychat;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

public class ResultsAdmin extends AppCompatActivity {

    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private DatabaseReference myRef;

    private ListView listView;
    private ResultsAdmin.TestAdapter testAdapter;
    ArrayList<String> result=new ArrayList<>();
    private int lastPos = -1;
    private boolean isAdmin = false;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        auth= FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        if(!isAdmin)
            setTitle("Results");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        database= FirebaseDatabase.getInstance();
        myRef=database.getReference();
        listView=findViewById(R.id.test_listview);
        testAdapter=new ResultsAdmin.TestAdapter(ResultsAdmin.this,result);
        listView.setAdapter(testAdapter);
        getResults();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getResults(){

        if(isAdmin) {

            myRef.child("Results").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    result.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        result.add(snapshot.getKey());
                    }
                    testAdapter.dataList = result;
                    testAdapter.notifyDataSetChanged();

                    Log.e("The read success: ", "su" + result.size());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Log.e("The read failed: ", databaseError.getMessage());
                }
            });
        }
        else  {

            myRef.child(SecondActivity.hy).child("Results").addListenerForSingleValueEvent(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    result.clear();
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        if(snapshot.hasChild(Objects
                                .requireNonNull(auth.getUid())))
                            result.add(snapshot.getKey());
                    }
                    testAdapter.dataList=result;
                    testAdapter.notifyDataSetChanged();

                    Log.e("The read success: " ,"su"+result.size());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Log.e("The read failed: " ,databaseError.getMessage());
                }
            });
        }
    }


    class TestAdapter extends ArrayAdapter<String> {

        private Context mContext;
        ArrayList<String> dataList;

        public TestAdapter( Context context,ArrayList<String> list) {
            super(context, 0 , list);
            mContext = context;
            dataList = list;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View listItem = convertView;
            if(listItem == null)
                listItem = LayoutInflater.from(mContext).inflate(R.layout.test_item,parent,false);
            ((ImageView)listItem.findViewById(R.id.item_imageView))
                    .setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ranking));
            ((ImageView)listItem.findViewById(R.id.item_imageView)).setPadding(10,0,0,0);
            ((TextView)listItem.findViewById(R.id.item_textView)).setText(dataList.get(position));
            ((Button)listItem.findViewById(R.id.item_button)).setText("View");

                        lastPos = position;
            ((Button)listItem.findViewById(R.id.item_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(ResultsAdmin.this, ResultsAdminDetailed.class);
                    intent.putExtra("test",dataList.get(position));
                    intent.putExtra("ISAdmin",isAdmin);
                    startActivity(intent);
                }
            });
            return listItem;
        }
    }

}
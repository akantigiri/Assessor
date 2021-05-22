package com.google.firebase.udacity.friendlychat;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class myapplication extends AppCompatActivity {

    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mMessagesDatabaseReference;

    public static DatabaseReference nMessagesDatabaseReference;
    public static DatabaseReference oMessagesDatabaseReference;
    public static DatabaseReference pMessagesDatabaseReference;
    FirebaseAuth firebaseAuth;

    public static List<FriendlyMessage> friendlyMessages;
    SimpleDateFormat formatter;
    String huo;
    public static String mUsernam;

    public static final String ANONYMOUS = "anonymous";

    LinearLayout lo;
    EditText a, b, c, d, e;
    Button f;
    SeekBar seekBar1, seekBar2, seekBar3, seekBar4;
    TextView tvoption1, tvoption2, tvoption3, tvoption4, tvoption5;
    TextView g;
    TextView tvpercent1, tvpercent2, tvpercent3, tvpercent4;
    long count1 = 0, count2 = 0, count3 = 0, count4 = 0;
    String h;
    int uo;
    boolean yw = true;
    boolean flag1 = true, flag2 = true, flag3 = true, flag4 = true;
    SharedPreferences s;
    String s1, s2, s3, s4, s5;
    String p, q, r;
    String gz;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        gz = SecondActivity.hy;
        if (gz.equals("messages")) {
            gz = "CSN_254D";
        } else {
            gz += "D";
        }
        p = SecondActivity.x;
        q = SecondActivity.y;
        r = SecondActivity.z;
        setContentView(R.layout.activity_myapplication);
        s = getSharedPreferences("user", 0);
        tvoption1 = findViewById(R.id.tv_option1);
        g = findViewById(R.id.tv_question);
        tvoption2 = findViewById(R.id.tv_option2);
        tvoption3 = findViewById(R.id.tv_option3);
        tvoption4 = findViewById(R.id.tv_option4);
        seekBar1 = findViewById(R.id.seek_bar1);
        seekBar2 = findViewById(R.id.seek_bar2);
        seekBar3 = findViewById(R.id.seek_bar3);
        seekBar4 = findViewById(R.id.seek_bar4);
        a = findViewById(R.id.edquestion);
        b = findViewById(R.id.edoptiona);
        c = findViewById(R.id.edoptionb);
        d = findViewById(R.id.edoptionc);
        FirebaseApp.initializeApp(this);
       username=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child(p);
        mMessagesDatabaseReference = mFirebaseDatabase.getReference("").child(p);

        pMessagesDatabaseReference = mFirebaseDatabase.getReference().child(gz);
        pMessagesDatabaseReference = mFirebaseDatabase.getReference("").child(gz);
        String nithin = SecondActivity.g + " " + coursehome.x;
        setTitle(nithin);
        nMessagesDatabaseReference = mFirebaseDatabase.getReference().child(q);
        nMessagesDatabaseReference = mFirebaseDatabase.getReference("").child(q);
        oMessagesDatabaseReference = mFirebaseDatabase.getReference().child(r);
        oMessagesDatabaseReference = mFirebaseDatabase.getReference("").child(r);
        e = findViewById(R.id.edoptiond);
        f = findViewById(R.id.post);

        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hp = "";
                String o = a.getText().toString();
                hp = hp + o;
                hp = hp + "/";
                g.setText(o);
                o = b.getText().toString();
                hp = hp + o;
                hp = hp + "/";
                tvoption1.setText(o);
                o = c.getText().toString();
                hp = hp + o;
                hp = hp + "/";
                tvoption2.setText(o);
                o = d.getText().toString();
                hp = hp + o;
                hp = hp + "/";
                tvoption3.setText(o);
                o = e.getText().toString();
                hp = hp + o;
                tvoption4.setText(o);
                count1 = count2 = count3 = count4 = 0;
                calculatePercentage();
                lo.setVisibility(View.GONE);
                formatter = new SimpleDateFormat("h:mm a");
                huo = formatter.format(new Date());
                FriendlyMessage ok = new FriendlyMessage(hp, "rohith", null, huo);
                oMessagesDatabaseReference.push().setValue(ok);
                SharedPreferences.Editor esy = s.edit();
                esy.remove("user");
                esy.apply();
                esy.putInt("user", 0);
                esy.apply();
                uo = s.getInt("user", 0);
            }
        });

        Query quraey = oMessagesDatabaseReference.orderByKey().limitToLast(1);
//Toast.makeText(MainActivity.this,"Hello",Toast.LENGTH_SHORT).show();
        quraey.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String lo = "";
                for (DataSnapshot esnapshot : snapshot.getChildren()) {
                    lo = esnapshot.child("text").getValue(String.class);
                    if (lo != null) {
                        Log.v("TAGE", lo);
                    }
                }
                String ga = "";
                String u = "";
                long y = 0, w = 0;
                for (int i = 0; i < lo.length(); i++) {
                    u = "";
                    u = u + lo.charAt(i);
                    if (!u.equals("/")) {
                        ga = ga + lo.charAt(i);
                    } else {
                        y = i;
                        break;
                    }

                }
                s1 = ga;
                g.setText(ga);
                String g = "";
                u = "";
                for (int i = (int) y + 1; i < lo.length(); i++) {
                    u = "";
                    u = u + lo.charAt(i);
                    if (!u.equals("/")) {
                        g = g + lo.charAt(i);
                    } else {
                        y = i;
                        break;
                    }

                }
                s2 = g;
                tvoption1.setText(g);
                g = "";
                u = "";
                for (int i = (int) (y + 1); i < lo.length(); i++) {
                    u = "";
                    u = u + lo.charAt(i);
                    if (!u.equals("/")) {
                        g = g + lo.charAt(i);
                    } else {
                        y = i;
                        break;
                    }

                }
                s3 = g;
                tvoption2.setText(g);
                g = "";
                u = "";
                for (int i = (int) (y + 1); i < lo.length(); i++) {
                    u = "";
                    u = u + lo.charAt(i);
                    if (!u.equals("/")) {
                        g = g + lo.charAt(i);
                    } else {
                        y = i;
                        break;
                    }

                }
                s4 = g;
                tvoption3.setText(g);
                g = "";
                u = "";
                for (int i = (int) (y + 1); i < lo.length(); i++) {
                    u = "";
                    u = u + lo.charAt(i);
                    if (!u.equals("/")) {
                        g = g + lo.charAt(i);
                    } else {
                        y = i;
                        break;
                    }

                }
                s5 = g;
                tvoption4.setText(g);
                count1 = count2 = count3 = count4 = 0;
                calculatePercentage();
                SharedPreferences.Editor esy = s.edit();
                esy.remove("user");
                esy.apply();
                esy.putInt("user", 0);
                esy.apply();
                uo = s.getInt("user", 0);
                yw = true;
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        g.setText(s1);
        tvoption1.setText(s2);
        tvoption2.setText(s3);
        tvoption3.setText(s4);
        tvoption4.setText(s5);
        lo = findViewById(R.id.dhon);
        lo.setVisibility(View.GONE);
        Query quray = nMessagesDatabaseReference.orderByKey().limitToLast(1);
//Toast.makeText(MainActivity.this,"Hello",Toast.LENGTH_SHORT).show();
        quray.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SharedPreferences.Editor esy = s.edit();
                esy.remove("user");
                esy.apply();
                esy.putInt("user", 0);
                esy.apply();
                uo = s.getInt("user", 0);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        tvpercent1 = findViewById(R.id.tv_percent1);
        tvpercent2 = findViewById(R.id.tv_percent2);
        tvpercent3 = findViewById(R.id.tv_percent3);
        tvpercent4 = findViewById(R.id.tv_percent4);
        uo = s.getInt("user", 0);
        Log.d("TAGE", "A");
        tvoption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uo == 1) {
                    Toast.makeText(myapplication.this, "You have already submitted your response", Toast.LENGTH_SHORT).show();
                    yw = false;
                    calculatePercentage();
                    return;
                }

                h = "" + count1;
                h = h + "o";
                h = h + count2;
                h = h + "o";
                h = h + count3;
                h = h + "o";
                h = h + count4;
                FriendlyMessage obaj = new FriendlyMessage(h, "oolad", null, huo);
                mMessagesDatabaseReference.push().setValue(obaj);
                if (!yw) {
                    calculatePercentage();
                    Toast.makeText(myapplication.this, "You have already submitted your response", Toast.LENGTH_SHORT).show();
                    return;
                }
                count1++;
                h = "" + count1;
                h = h + "o";
                h = h + count2;
                h = h + "o";
                h = h + count3;
                yw = false;
                h = h + "o";
                h = h + count4;
                Log.d("TAGE", h);
                formatter = new SimpleDateFormat("h:mm a");
                huo = formatter.format(new Date());
                FriendlyMessage obj = new FriendlyMessage(h, "" +username, null, huo);
                mMessagesDatabaseReference.push().setValue(obj);
                SharedPreferences.Editor esy = s.edit();
                esy.remove("user");
                esy.apply();
                esy.putInt("user", 1);
                esy.apply();
                uo = s.getInt("user", 0);
                yw = false;


                calculatePercentage();

            }
        });
        tvoption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uo == 1) {
                    Toast.makeText(myapplication.this, "You have already submitted your response", Toast.LENGTH_SHORT).show();
                    calculatePercentage();
                    yw = false;
                    return;
                }

                h = "" + count1;
                h = h + "o";
                h = h + count2;
                h = h + "o";
                h = h + count3;
                h = h + "o";
                h = h + count4;
                FriendlyMessage obaj = new FriendlyMessage(h, "oolad", null, huo);
                mMessagesDatabaseReference.push().setValue(obaj);
                if (!yw) {
                    calculatePercentage();
                    Toast.makeText(myapplication.this, "You have already submitted your response", Toast.LENGTH_SHORT).show();
                    return;
                }
                count2++;
                h = "" + count1;
                h = h + "o";
                h = h + count2;
                h = h + "o";
                h = h + count3;
                yw = false;
                h = h + "o";
                h = h + count4;
                Log.d("TAGE", h);
                formatter = new SimpleDateFormat("h:mm a");
                huo = formatter.format(new Date());
                FriendlyMessage obj = new FriendlyMessage(h, "" +username, null, huo);
                mMessagesDatabaseReference.push().setValue(obj);
                SharedPreferences.Editor esy = s.edit();
                esy.remove("user");
                esy.apply();
                esy.putInt("user", 1);
                esy.apply();
                uo = s.getInt("user", 0);
                yw = false;


                calculatePercentage();

            }
        });
        tvoption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uo == 1) {
                    Toast.makeText(myapplication.this, "You have already submitted your response", Toast.LENGTH_SHORT).show();
                    yw = false;
                    calculatePercentage();
                    return;
                }

                h = "" + count1;
                h = h + "o";
                h = h + count2;
                h = h + "o";
                h = h + count3;
                h = h + "o";
                h = h + count4;
                FriendlyMessage obaj = new FriendlyMessage(h, "oolad", null, huo);
                mMessagesDatabaseReference.push().setValue(obaj);
                if (!yw) {
                    calculatePercentage();
                    Toast.makeText(myapplication.this, "You have already submitted your response", Toast.LENGTH_SHORT).show();
                    return;
                }
                count3++;
                h = "" + count1;
                h = h + "o";
                yw = false;
                h = h + count2;
                h = h + "o";
                h = h + count3;
                h = h + "o";
                h = h + count4;
                Log.d("TAGE", h);
                formatter = new SimpleDateFormat("h:mm a");
                huo = formatter.format(new Date());
                FriendlyMessage obj = new FriendlyMessage(h, "" + username, null, huo);
                mMessagesDatabaseReference.push().setValue(obj);
                SharedPreferences.Editor esy = s.edit();
                esy.remove("user");
                esy.apply();
                esy.putInt("user", 1);
                esy.apply();
                uo = s.getInt("user", 0);
                yw = false;


                calculatePercentage();

            }
        });
        tvoption4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uo == 1) {
                    Toast.makeText(myapplication.this, "You have already submitted your response", Toast.LENGTH_SHORT).show();
                    yw = false;
                    calculatePercentage();
                    return;
                }

                h = "" + count1;
                h = h + "o";
                h = h + count2;
                h = h + "o";
                h = h + count3;
                h = h + "o";
                h = h + count4;
                FriendlyMessage obaj = new FriendlyMessage(h, "oolad", null, huo);
                mMessagesDatabaseReference.push().setValue(obaj);
                if (!yw) {
                    calculatePercentage();
                    Toast.makeText(myapplication.this, "You have already submitted your response", Toast.LENGTH_SHORT).show();
                    return;
                }
                count4++;
                h = "" + count1;
                h = h + "o";
                h = h + count2;
                h = h + "o";
                h = h + count3;
                h = h + "o";
                yw = false;
                h = h + count4;
                Log.d("TAGE", h);
                formatter = new SimpleDateFormat("h:mm a");
                huo = formatter.format(new Date());
                FriendlyMessage obj = new FriendlyMessage(h, "" + username, null, huo);
                mMessagesDatabaseReference.push().setValue(obj);
                SharedPreferences.Editor esy = s.edit();
                esy.remove("user");
                esy.apply();
                esy.putInt("user", 1);
                esy.apply();
                uo = s.getInt("user", 0);
                yw = false;


                calculatePercentage();

            }
        });

//Toast.makeText(MainActivity.this,"Hello",Toast.LENGTH_SHORT).show();
       mMessagesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String lo = "";String gz="0o0o0o0";String oh;
                for (DataSnapshot esnapshot : snapshot.getChildren()) {
                    lo = esnapshot.child("name").getValue(String.class);
                    // Log.v("TAGE","DO"+lo);
                    if(!lo.equals("oolad")){gz=esnapshot.child("text").getValue(String.class);}
                    if (lo != null) { //Log.v("TAGE", lo);
                        String ufo = MainActivity.mUsername;
                        if(ufo==null){continue;}
                        ufo.trim();
                        // Log.v("TAGE", ufo);
                        if (lo.equals(ufo)) {
                            yw = false;
                        }
                    }
                    lo = esnapshot.child("text").getValue(String.class);
                    //if(lo != null){ Log.v("TAGE", lo);}
                }//Log.v("TAGE","BOO"+yw);
                String g = "";
                String u = "";
                long y = 0, w = 0;lo=gz;
                for (int i = 0; i < lo.length(); i++) {
                    u = "";
                    u = u + lo.charAt(i);
                    if (!u.equals("o")) {
                        g = g + lo.charAt(i);
                    } else {
                        y = i;
                        break;
                    }

                }
                if (g.equals("")) {
                    return;
                }
                count1 = Integer.parseInt(g);
                g = "";
                u = "";
                for (int i = (int) y + 1; i < lo.length(); i++) {
                    u = "";
                    u = u + lo.charAt(i);
                    if (!u.equals("o")) {
                        g = g + lo.charAt(i);
                    } else {
                        y = i;
                        break;
                    }

                }
                count2 = Integer.parseInt(g);
                g = "";
                u = "";
                for (int i = (int) (y + 1); i < lo.length(); i++) {
                    u = "";
                    u = u + lo.charAt(i);
                    if (!u.equals("o")) {
                        g = g + lo.charAt(i);
                    } else {
                        y = i;
                        break;
                    }

                }
                count3 = Integer.parseInt(g);
                g = "";
                u = "";
                for (int i = (int) (y + 1); i < lo.length(); i++) {
                    u = "";
                    u = u + lo.charAt(i);
                    if (!u.equals("o")) {
                        g = g + lo.charAt(i);
                    } else {
                        y = i;
                        break;
                    }

                }
                count4 = Integer.parseInt(g);


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
       /* seekBar1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

       seekBar2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        seekBar3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        seekBar4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });*/
        calculatePercentage();

    }

    private void calculatePercentage() {
        double tot = count1 + count2 + count3 + count4;
        int ok = (int) tot;
        String y;
        y = "" + ok;
        if (y.equals("0")) {
            y = "0.00%";
            tvpercent1.setText(y);
            seekBar1.setProgress(0);
            tvpercent2.setText(y);
            seekBar2.setProgress(0);
            tvpercent3.setText(y);
            seekBar3.setProgress(0);
            tvpercent4.setText(y);
            seekBar4.setProgress(0);
            return;
        }

        double percent1 = (count1 / tot) * 100;
        double percent2 = (count2 / tot) * 100;
        double percent3 = (count3 / tot) * 100;
        double percent4 = (count4 / tot) * 100;
       /* percent1 =roundDouble(percent1);
        percent2 =roundDouble(percent2);
        percent3 =roundDouble(percent3);
        percent4 =roundDouble(percent4);*/
        int you = (int) Math.ceil(percent1);

        if (you == 0.00) {
            y = "0.00%";
        } else {
            y = String.format("%.2f", percent1);
            y = y + "%";
        }
        tvpercent1.setText(y);
        Log.v("TAGE", "" + percent1 + " " + percent2 + " " + percent3 + " " + percent4);
        seekBar1.setProgress((int) percent1);
        you = (int) Math.ceil(percent2);
        if (you == 0.00) {
            y = "0.00%";
        } else {
            y = String.format("%.2f", percent2);
            y = y + "%";
        }
        tvpercent2.setText(y);
        seekBar2.setProgress((int) percent2);
        you = (int) Math.ceil(percent3);
        if (you == 0.00) {
            y = "0.00%";
        } else {
            y = String.format("%.2f", percent3);
            y = y + "%";
        }

        tvpercent3.setText(y);
        seekBar3.setProgress((int) percent3);
        you = (int) Math.ceil(percent4);
        if (you == 0.00) {
            y = "0.00%";
        } else {
            y = String.format("%.2f", percent4);
            y = y + "%";
        }
        tvpercent4.setText(y);
        seekBar4.setProgress((int) percent4);
    }

    private static double roundDouble(double d) {
        int o;
        d = d + 0.005;
        o = (int) (d * 100);
        double b = (o / 100);
        return b;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.poll_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ClearPoll:
                mUsernam = username;

                if (mUsernam == null) {
                    mUsernam = username;
                    if (mUsernam == null) {
                        return true;
                    }
                }
                mUsernam.trim();
                Log.d("TAGE", mUsernam);
                if (!mUsernam.equals("Akanti Giri Nandan")) {
                    Toast.makeText(myapplication.this, "Sorry! You are not allowed to do so.", Toast.LENGTH_SHORT).show();
                    return true;
                }
                mMessagesDatabaseReference.setValue(null);
                count1 = count2 = count3 = count4 = 0;
                h = "" + count1;
                h = h + "o";
                h = h + count2;
                h = h + "o";
                h = h + count3;
                h = h + "o";
                h = h + count4;
                Log.d("TAGE", h);
                formatter = new SimpleDateFormat("h:mm a");
                huo = formatter.format(new Date());
                FriendlyMessage obj = new FriendlyMessage(h, "opo", null, huo);
                mMessagesDatabaseReference.push().setValue(obj);
                calculatePercentage();
                FriendlyMessage ob = new FriendlyMessage("0", "opo", null, huo);
                nMessagesDatabaseReference.push().setValue(ob);
                yw = true;
                return true;

            case R.id.CreatePoll:
                mUsernam = username;
                count1 = count2 = count3 = count4 = 0;
                calculatePercentage();
                if (mUsernam == null) {
                    mUsernam = username;
                    if (mUsernam == null) {
                        return true;
                    }
                }
                mUsernam.trim();
                Log.d("TAGE", mUsernam);
                if (!mUsernam.equals("Akanti Giri Nandan")) {
                    Toast.makeText(myapplication.this, "Sorry! You are not allowed to do so.", Toast.LENGTH_SHORT).show();
                    return true;
                }

                mMessagesDatabaseReference.setValue(null);
                yw = true;
                calculatePercentage();
                lo.setVisibility(View.VISIBLE);
                return true;
            case R.id.RefreshPoll:
                if (!yw) {
                    calculatePercentage();
                } else {

                    Toast.makeText(myapplication.this, "Results are refreshed when you submit your vote.", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

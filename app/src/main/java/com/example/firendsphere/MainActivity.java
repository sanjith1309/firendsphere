package com.example.firendsphere;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databasereference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://firendsphere-default-rtdb.firebaseio.com/");
    private TextView dataview,creden;
    EditText content;
    Button post;
    Button logou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String myname = sharedPreferences.getString("name", " ");

        if(myname.equals(" ")){
            Intent i =new Intent(MainActivity.this,entername.class);
            startActivity(i);
        }

        creden=(TextView)findViewById(R.id.textView5);
        creden.setText(myname);
        logou=findViewById(R.id.button3);
        post = findViewById(R.id.button);
        content = findViewById(R.id.editTextTextPersonName);
        dataview = findViewById(R.id.textView2);
        getdata();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                databasereference.child("data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String qwerty = snapshot.getValue(String.class);
                        dataview.setText(qwerty);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }, 0, 2000);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content1 = content.getText().toString().trim();
                if (content1.isEmpty()) {
                    content.setError("type something");
                    content.requestFocus();
                    return;
                }else {
                    databasereference.child("data").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String data = snapshot.getValue(String.class);
                            String data1 = data +"  "+content1 +"\n---"+myname+" \n--------------------------------------------\n";
                            dataview.setText(data1);
                            content.setText("");
                            databasereference.child("data").setValue(data1);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        logou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(MainActivity.this,entername.class);
                startActivity(i);
            }
        });
    }


    private void getdata() {
        databasereference.child("data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String abc = snapshot.getValue(String.class);
                dataview.setText(abc);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }







}
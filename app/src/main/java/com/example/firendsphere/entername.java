package com.example.firendsphere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class entername extends AppCompatActivity {
    EditText myname;
    String name;
    Button but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entername);

        but=(Button)findViewById(R.id.button2);
        myname=(EditText) findViewById(R.id.editTextTextPersonName2);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=myname.getText().toString();
                if (name.length() < 4) {
                    myname.setError("Name should be greater than 3 letters");
                    myname.requestFocus();
                    return;
                }
                else{

                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", name);
                    editor.apply();
                    Intent i =new Intent(entername.this,MainActivity.class);
                    startActivity(i);

                }
            }

        });


    }
}
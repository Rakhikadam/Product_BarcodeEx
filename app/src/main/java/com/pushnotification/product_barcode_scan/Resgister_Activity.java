package com.pushnotification.product_barcode_scan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Resgister_Activity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    DBHelper helper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgister);
        EditText email = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Spinner spinner = findViewById(R.id.spinner);
        Button register = findViewById(R.id.register);
        Button loginback = findViewById(R.id.login);
        preferences = getSharedPreferences("MYAPP",MODE_PRIVATE);
        helper = new DBHelper(Resgister_Activity.this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                String type = spinner.getSelectedItem().toString();

                if (Email.isEmpty() || Password.isEmpty()||type.isEmpty()){
                    Toast.makeText(Resgister_Activity.this, "Please Enter Details", Toast.LENGTH_SHORT).show();
                   return;
                }

                if (Pattern.matches("[[a-zA-Z0-9._%+-]{0,10}@[a-zA-Z.-]{5,7}\\.[a-zA-Z\\.]{3,5}]{8,30}",Email)){
                    if (Pattern.matches("[a-zA-Z0-9]{8}",Password)){


                        if (helper.getUserbyEmailNPass(Email,Password).size()<1){
                            helper.adduser(Email,Password,type); //add data into SQLite
                            Intent intent = new Intent(Resgister_Activity.this,MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(Resgister_Activity.this, "User Registered", Toast.LENGTH_SHORT).show();
                            Log.e("TAG", "onClick: "+email.getText().toString() );

                        }
                        else {
                            Intent intent = new Intent(Resgister_Activity.this,MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(Resgister_Activity.this, "User already Exist.", Toast.LENGTH_SHORT).show();

                            Log.e("TAG", "onClick: "+email.getText().toString() );
                        }

                        editor = preferences.edit();
                        editor.putString("email",Email);
                        editor.putString("password",Password);
                        editor.putString("type",type);
                    }

                    else {
                        Toast.makeText(Resgister_Activity.this, "Enter the Password", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(Resgister_Activity.this, "Enter the Email", Toast.LENGTH_SHORT).show();
                }


            }
        });


        loginback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Resgister_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });



    }
}
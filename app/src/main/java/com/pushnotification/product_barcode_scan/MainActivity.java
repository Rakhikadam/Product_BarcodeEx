package com.pushnotification.product_barcode_scan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ProgressDialog dialog;
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        Button register = findViewById(R.id.register);
        preferences = getSharedPreferences("MYAPP",MODE_PRIVATE);
        helper = new DBHelper(MainActivity.this);


        if (preferences.getBoolean("login_status",false)==true) {


            if (preferences.getString("type", "").equalsIgnoreCase("Distributor")) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, new Distributor_dashboard());
                transaction.commit();
            } else if (preferences.getString("type", "").equalsIgnoreCase("Retailer")) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, new Retailer_DashBoard());
                transaction.commit();
            }
            Log.e("TAG", "onCreate: "+preferences.getBoolean("login_status",false) );
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                if (Pattern.matches("[[a-zA-Z0-9._%+-]{0,10}@[a-zA-Z.-]{5,7}\\.[a-zA-Z\\.]{3,5}]{8,30}",Email)){
                    if (Pattern.matches("[a-zA-Z0-9]{8}",Password)){

                       /*  dialog = new ProgressDialog(MainActivity.this);
                        dialog.setMessage("Please Wait");  //dialog using only for Onlinedatabase save.ex firebase
                        dialog.show();
*/
                        if (helper.getUserbyEmailNPass(Email,Password).size()<1){
                            Intent intent = new Intent(MainActivity.this,Resgister_Activity.class);
                            startActivity(intent);
                        }
                        else {

                            user curent_user = helper.getUserbyEmailNPass(Email, Password).get(0);
                            editor = preferences.edit();
                            editor.putString("email", curent_user.getEmail());
                            editor.putString("password", curent_user.getPassword());
                            editor.putString("type",curent_user.getType());
                            editor.putBoolean("login_status", true);
                            editor.commit();
                        }



                        if (preferences.getString("type","").equalsIgnoreCase("Distributor")){
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame,new Distributor_dashboard());
                            transaction.commit();

                        }
                        else if (preferences.getString("type","").equalsIgnoreCase("Retailer")){
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame,new Retailer_DashBoard());
                            transaction.commit();

                        }



                    }
                    else {

                        Toast.makeText(MainActivity.this, "Enter the vaild password", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "Enter the vaild mail", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Resgister_Activity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()){
            if (fragment instanceof productlist_recyclerview){
                FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
                transaction.remove(fragment);
                transaction.commit();
                return;
            }
        }
   for (Fragment fragment : getSupportFragmentManager().getFragments()){
            if (fragment instanceof Distributor_dashboard){
                FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
                transaction.remove(fragment);
                transaction.commit();
                return;
            }
        }

        for (Fragment fragment : getSupportFragmentManager().getFragments()){
            if (fragment instanceof Retailer_DashBoard){
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.remove(fragment);
                transaction.commit();
                return;
            }
        }
    }
}
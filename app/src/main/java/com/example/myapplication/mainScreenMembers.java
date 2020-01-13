package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class mainScreenMembers extends AppCompatActivity {
    Button button_menu;
    Button btnLogout;
    FirebaseAuth mFirebaseAuth;
    String u;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_members);

        btnLogout = findViewById(R.id.button_log_out);
        mFirebaseAuth= FirebaseAuth.getInstance();
        //  Toast.makeText(mainScreenMembers.this,"Please Login"+ mFirebaseAuth.getCurrentUser().getUid(),Toast.LENGTH_SHORT).show();
        //   u=FirebaseAuth.getCurrentUser().getUid();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(mainScreenMembers.this, MainActivity.class);
                startActivity(intToMain);
            }
        });

        button_menu = findViewById(R.id. button_menu);
        button_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   FirebaseAuth.getInstance().signOut();

                Intent intToMain = new Intent(mainScreenMembers.this,memberMenu.class);
                // intToMain.putExtra("uid",mFirebaseAuth.getCurrentUser().getUid());

                startActivity(intToMain);
            }
        });

    }
}

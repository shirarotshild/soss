package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addDish1 extends AppCompatActivity {
    EditText txtdishName;
    EditText txtdishPrice;
    EditText txtdishType;
    Button button_save;
    public DatabaseReference ref;
    dishInformation DI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish1);



        txtdishName= (EditText)findViewById(R.id.editText_dis_name);
        txtdishPrice = (EditText)findViewById(R.id.editText_price);
        txtdishType=(EditText)findViewById(R.id.editText_type);
        ref= FirebaseDatabase.getInstance().getReference().child("dishInformation");
        DI= new dishInformation();
        button_save= (Button)findViewById(R.id.button_save);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dishName= txtdishName.getText().toString();
                String dishPrice=txtdishPrice.getText().toString();
                String dishType=txtdishType.getText().toString();
                //String key=ref.child(DI.getDish_type()).push().getKey();
                DI.setDish_name(dishName);
                DI.setDish_price(dishPrice);
                DI.setDish_type(dishType);
               // String dishId1=Integer.toString(DI.getDish_id());
                ref.child(DI.getDish_type()).child(ref.child(DI.getDish_type()).push().getKey()).setValue(DI);
                Toast.makeText(addDish1.this, "data insert sucessfully",Toast.LENGTH_LONG).show();
                Intent i= new Intent(addDish1.this, editMenu.class);
                startActivity(i);
            }
        });



            }
}

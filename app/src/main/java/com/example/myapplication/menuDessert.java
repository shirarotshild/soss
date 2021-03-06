package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class menuDessert extends AppCompatActivity {
    ListView list_dishes;
    DatabaseReference reff;
    DatabaseReference reff2;
    DatabaseReference ref;
    DatabaseReference reff1;
    ArrayList<dishInformation> arrayList= new ArrayList<>();
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_dessert);
        reff2= FirebaseDatabase.getInstance().getReference().child("Users").child("Members").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("favoriteDishes").child("desserts");
        reff1=FirebaseDatabase.getInstance().getReference().child("order").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("price");
        reff = FirebaseDatabase.getInstance().getReference("dishInformation").child("desserts");
        ref=FirebaseDatabase.getInstance().getReference("order").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("desserts");
        list_dishes = findViewById(R.id.list_dishes);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        list_dishes.setAdapter(adapter);
        list_dishes = findViewById(R.id.list_dishes);
        reff.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                dishInformation dish = dataSnapshot.getValue(dishInformation.class);
                String key=dataSnapshot.getKey();
                dish.setDish_key(key);
                arrayList.add(dish);
                adapter.notifyDataSetChanged();

            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        list_dishes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final dishInformation dish=(dishInformation) parent.getItemAtPosition(position);
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(menuDessert.this);
                mBuilder.setMessage(dish.getDish_name());

                mBuilder.setPositiveButton("order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            long count=1;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot data: dataSnapshot.getChildren()){
                                    if( data.getKey().equals(dish.getDish_key_())) {
                                        count = (long) data.getValue() + 1;
                                        break;
                                    }
                                }
                                ref.child(dish.getDish_key_()).setValue(count);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        reff1.addListenerForSingleValueEvent(new ValueEventListener() {
                            long price;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.getValue()==null)
                                    reff1.setValue(Integer.parseInt(dish.getDish_price())) ;
                                else {
                                    price = (long) dataSnapshot.getValue();
                                    reff1.setValue(price + Integer.parseInt(dish.getDish_price()));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }

                        });

                        Toast.makeText(getApplicationContext(), "order", Toast.LENGTH_SHORT).show();
                        Intent i= new Intent(menuDessert.this, menuDessert.class);

                        startActivity(i);

                    }});
                mBuilder.setNeutralButton("Add to Favorites", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reff2.child(dish.getDish_key_()).setValue(0);
                        startActivity(new Intent(menuDessert.this,menuDessert.class));
                    }
                });
                mBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { ;
                        Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(menuDessert.this, menuDessert.class));
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();



            }

        });
    }



}
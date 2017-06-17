package com.example.chungyu.justeat;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements ChildEventListener {

    TextView txv;
    Random random;
    int idx;
    int len;
    String text_name = "";
    String text_addr = "";
    ArrayList<String> result ;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txv = (TextView) findViewById(R.id.txv);
        result = new ArrayList<String>();
        result.clear();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        myRef = db.getReference("breakfast");
        myRef.addChildEventListener(this);
    }

    public void gotolist(View v)
    {
        Intent balance_it = new Intent(this,ListActivity.class);
        startActivity(balance_it);
    }

    public void cli(View V) {
        random = new Random();
        idx = random.nextInt(100000);

        idx = idx % result.size();
        txv.setText(result.get(idx));


    }

    public void write(View V) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("請輸入店名及地址");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                text_name = input.getText().toString();
                myRef.child(String.valueOf(result.size()+1)).child("name").setValue(text_name);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        result.add(String.valueOf(dataSnapshot.child("name").getValue().toString()));
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}

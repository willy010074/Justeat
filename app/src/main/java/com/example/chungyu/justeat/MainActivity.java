package com.example.chungyu.justeat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    ArrayList<String> result ;
    DatabaseReference myRef;
    Toast tos;

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
        tos = Toast.makeText(this,"",Toast.LENGTH_SHORT);
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

    public void push(View V){
        //LayoutInflater inflater = this.getLayoutInflater();
        final View view = View.inflate(this,R.layout.my_dialog,null);
        final EditText text_name = (EditText) view.findViewById(R.id.name);
        final EditText text_addr = (EditText) view.findViewById(R.id.addr);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("請輸入店名及地址").setView(view).setPositiveButton("Ok",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id){
                myRef.child(String.valueOf(result.size()+1)).child("name").setValue(text_name.getText().toString());
                myRef.child(String.valueOf(result.size()+1)).child("addr").setValue(text_addr.getText().toString());
                tos.setText("成功新增"+text_name.getText().toString()+"!!");
                tos.show();
            }
        }).setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id){
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
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

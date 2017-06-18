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

public class MainActivity extends AppCompatActivity {

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
        db.getReference("breakfast").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    result.add(ds.child("name").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        db.getReference("brunch").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    result.add(ds.child("name").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        tos = Toast.makeText(this,"",Toast.LENGTH_SHORT);
    }

    public void gotolist(View v)
    {
        txv.setText(Integer.toString(result.size()));
        /*Intent balance_it = new Intent(this,ListActivity.class);
        startActivity(balance_it);*/
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
}

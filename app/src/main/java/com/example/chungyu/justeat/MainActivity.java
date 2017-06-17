package com.example.chungyu.justeat;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
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
    int flag;

    Toast tos;
    Spinner time_sp;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txv = (TextView) findViewById(R.id.txv);
        db = FirebaseDatabase.getInstance();
        tos = Toast.makeText(this,"",Toast.LENGTH_SHORT);
        time_sp = (Spinner) findViewById(R.id.spinner_time);

        /*flag = 0;
        myBreakFast = db.getReference("breakfast");
        myBreakFast.addChildEventListener(this);*/

        /*flag = 1;
        myBrunch = db.getReference("brunch");
        myBrunch.addChildEventListener(this);

        /*flag = 2;
        myLunch = db.getReference("lunch");
        myLunch.addChildEventListener(this);

        flag = 3;
        myDinner = db.getReference("dinner");
        myDinner.addChildEventListener(this);*/
    }

    public void gotolist(View v)
    {
        Intent balance_it = new Intent(this,ListActivity.class);
        startActivity(balance_it);
    }

    public void cli(View V) {
        random = new Random();
        idx = random.nextInt(100000);

        String[] times = getResources().getStringArray(R.array.time);
        int index = time_sp.getSelectedItemPosition();
        switch (index) {
            case 0:
                final ArrayList<String> resultBreakfast = new ArrayList<String>();
                DatabaseReference db_breakfast = FirebaseDatabase.getInstance().getReference("breakfast");
                db_breakfast.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        resultBreakfast.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren() ){
                            resultBreakfast.add(ds.child("name").getValue().toString());
                        }

                        idx = idx % resultBreakfast.size();
                        txv.setText(resultBreakfast.get(idx));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                break;

            case 1:
                final ArrayList<String> result_Branch = new ArrayList<String>();
                DatabaseReference db_brunch = FirebaseDatabase.getInstance().getReference("brunch");
                db_brunch.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        result_Branch.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren() ){
                            result_Branch.add(ds.child("name").getValue().toString());
                        }

                        idx = idx % result_Branch.size();
                        txv.setText(result_Branch.get(idx));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                break;

            case 2:
                final ArrayList<String> result_Lunch = new ArrayList<String>();
                DatabaseReference db_lunch = FirebaseDatabase.getInstance().getReference("lunch");
                db_lunch.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        result_Lunch.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren() ){
                            result_Lunch.add(ds.child("name").getValue().toString());
                        }

                        idx = idx % result_Lunch.size();
                        txv.setText(result_Lunch.get(idx));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;

            case 3:
                final ArrayList<String> result_Dinner = new ArrayList<String>();
                DatabaseReference db_dinner = FirebaseDatabase.getInstance().getReference("dinner");
                db_dinner.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        result_Dinner.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren() ){
                            result_Dinner.add(ds.child("name").getValue().toString());
                        }

                        idx = idx % result_Dinner.size();
                        txv.setText(result_Dinner.get(idx));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;
        }
    }

    /*public void push(View V){
        //LayoutInflater inflater = this.getLayoutInflater();
        final View view = View.inflate(this,R.layout.my_dialog,null);
        final EditText text_name = (EditText) view.findViewById(R.id.name);
        final EditText text_addr = (EditText) view.findViewById(R.id.addr);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("請輸入店名及地址").setView(view).setPositiveButton("Ok",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id){
                myBreakFast.child(String.valueOf(result_Breakfast.size()+1)).child("name").setValue(text_name.getText().toString());
                myBreakFast.child(String.valueOf(result_Breakfast.size()+1)).child("addr").setValue(text_addr.getText().toString());
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
    }*/



    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        //result.add(String.valueOf(dataSnapshot.child("name").getValue().toString()));
        /*txv.setText(Integer.toString(flag));
        switch (flag) {
            case 0:
                result_Breakfast.add(String.valueOf(dataSnapshot.child("name").getValue().toString()));
                break;

            case 1:
                result_Brunch.add(String.valueOf(dataSnapshot.child("name").getValue().toString()));
                break;

            case 2:
                result_Lunch.add(String.valueOf(dataSnapshot.child("name").getValue().toString()));
                break;

            case 3:
                result_Dinner.add(String.valueOf(dataSnapshot.child("name").getValue().toString()));
                break;
        }*/

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

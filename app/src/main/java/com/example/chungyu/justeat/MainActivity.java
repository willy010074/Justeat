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
    int breakfast_num=50;
    int brunch_num=50;
    int lunch_num=50;
    int dinner_num=50;

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


    }

    public void gotolist(View v)
    {
        int index = time_sp.getSelectedItemPosition();
        Intent balance_it = new Intent(this,ListActivity.class);
        balance_it.putExtra("200",index);
        startActivity(balance_it);
    }

    public void cli(View V) {
        random = new Random();
        idx = random.nextInt(100000);

        //String[] times = getResources().getStringArray(R.array.time);
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
                final ArrayList<String> result_Brunch = new ArrayList<String>();
                DatabaseReference db_brunch = FirebaseDatabase.getInstance().getReference("brunch");
                db_brunch.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        result_Brunch.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren() ){
                            result_Brunch.add(ds.child("name").getValue().toString());
                        }

                        idx = idx % result_Brunch.size();
                        txv.setText(result_Brunch.get(idx));
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

    public void push(View V){
        //LayoutInflater inflater = this.getLayoutInflater();
        final View view = View.inflate(this,R.layout.my_dialog,null);
        final EditText text_name = (EditText) view.findViewById(R.id.name);
        final EditText text_addr = (EditText) view.findViewById(R.id.addr);
        final Spinner sp_time = (Spinner) view.findViewById(R.id.sp_time);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("請輸入店名及地址").setView(view).setPositiveButton("Ok",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id){
                int index = sp_time.getSelectedItemPosition();
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
                                    breakfast_num++;
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        db_breakfast.child(String.valueOf(breakfast_num)).child("name").setValue(text_name.getText().toString());
                        db_breakfast.child(String.valueOf(breakfast_num)).child("addr").setValue(text_addr.getText().toString());

                        break;

                    case 1:
                        final ArrayList<String> resultBrunch = new ArrayList<String>();
                        DatabaseReference db_brunch = FirebaseDatabase.getInstance().getReference("brunch");
                        db_brunch.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                resultBrunch.clear();
                                for (DataSnapshot ds : dataSnapshot.getChildren() ){
                                    resultBrunch.add(ds.child("name").getValue().toString());
                                    brunch_num++;
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        db_brunch.child(String.valueOf(brunch_num)).child("name").setValue(text_name.getText().toString());
                        db_brunch.child(String.valueOf(brunch_num)).child("addr").setValue(text_addr.getText().toString());

                        break;

                    case 2:
                        final ArrayList<String> resultLunch = new ArrayList<String>();
                        DatabaseReference db_lunch = FirebaseDatabase.getInstance().getReference("lunch");
                        db_lunch.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                resultLunch.clear();
                                for (DataSnapshot ds : dataSnapshot.getChildren() ){
                                    resultLunch.add(ds.child("name").getValue().toString());
                                    lunch_num++;
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        db_lunch.child(String.valueOf(lunch_num)).child("name").setValue(text_name.getText().toString());
                        db_lunch.child(String.valueOf(lunch_num)).child("addr").setValue(text_addr.getText().toString());

                        break;

                    case 3:
                        final ArrayList<String> resultDinner = new ArrayList<String>();
                        DatabaseReference db_dinner = FirebaseDatabase.getInstance().getReference("dinner");
                        db_dinner.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                resultDinner.clear();
                                for (DataSnapshot ds : dataSnapshot.getChildren() ){
                                    resultDinner.add(ds.child("name").getValue().toString());
                                    dinner_num++;
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        db_dinner.child(String.valueOf(dinner_num)).child("name").setValue(text_name.getText().toString());
                        db_dinner.child(String.valueOf(dinner_num)).child("addr").setValue(text_addr.getText().toString());
                        break;
                }
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

package com.jica.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//Insert,Update,Delete,Search예제
public class MainActivity extends AppCompatActivity {
    Button btnInsert, btnUpdate, btnView,btnDelete,btntestPost;
    EditText etName, etEmail, etPassword, etID;
    RadioButton rbID,rbEmail,rbName;

    String ID;
    String name;
    String email;
    String password;
    String sort = "id";

    ArrayAdapter<String> arrayAdapter;
    private DatabaseReference mPostReference;

    static ArrayList<String> arrayIndex = new ArrayList<>();
    static ArrayList<String> arrayData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInsert = findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ID = etID.getText().toString();
                name = etName.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                if(!IsExistID()){
                    postFirebaseDatabase(true);
                    getFirebaseDatabase();
                    setInsertMode();
                } else {
                    Toast.makeText(getApplicationContext(),"이미 존재하는 ID입니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ID = etID.getText().toString();
                name = etName.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                postFirebaseDatabase(true);
                getFirebaseDatabase();
            }
        });
        btnView = findViewById(R.id.btnView);
        rbEmail = findViewById(R.id.rbEmail);
        rbID = findViewById(R.id.rbID);
        rbName = findViewById(R.id.rbName);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbEmail.isChecked()) {sort="email";}
                else if (rbID.isChecked()) {sort ="id";}
                else if(rbName.isChecked()) {sort="name";}
                getFirebaseDatabase();
            }
        });

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ID=etID.getText().toString();
                postFirebaseDatabase(false);
                getFirebaseDatabase();
                setInsertMode();
                Toast.makeText(getApplicationContext(),ID+"에 해당하는 데이터를 삭제했습니다.",Toast.LENGTH_SHORT).show();
            }
        });

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etID = findViewById(R.id.etID);
        etPassword = findViewById(R.id.etPassword);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        btntestPost = findViewById(R.id.btntestPost);
        btntestPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PostingsTestActivity.class);
                startActivity(intent);
            }
        });


        getFirebaseDatabase();
    }

    public boolean IsExistID(){
        boolean IsExist = arrayIndex.contains(ID);
        return IsExist;
    }

    public void setInsertMode() {
        etID.setText("");
        etName.setText("");
        etEmail.setText("");
        etPassword.setText("");
    }

    public void postFirebaseDatabase(boolean add) {
        mPostReference = FirebaseDatabase.getInstance().getReference();
        Map<String,Object> childUpdates = new HashMap<>();
        Map<String,Object> postValues = null;
        if(add) {
            Firebasetest1 post = new Firebasetest1(ID,name,password,email);
            postValues = post.toMap();
        }
        childUpdates.put("/user_list/"+ID,postValues);
        mPostReference.updateChildren(childUpdates);
    }

    public void getFirebaseDatabase() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayData.clear();
                arrayIndex.clear();
                for(DataSnapshot postsnapshot : snapshot.getChildren()) {
                    String key = postsnapshot.getKey();
                    Firebasetest1 get = postsnapshot.getValue(Firebasetest1.class);
                    String[] info = {get.id,get.name,get.email,get.password};
                    String result = info[0]+info[1]+info[2]+info[3];
                    arrayData.add(result);
                    arrayIndex.add(key);
                }
                arrayAdapter.clear();
                arrayAdapter.addAll(arrayData);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("getFirebaseDatabase","loadPost:onCancelled", error.toException());
            }
        };
        Query sortbyID = FirebaseDatabase.getInstance().getReference().child("user_list").orderByChild(sort);
//        FirebaseDatabase.getInstance().getReference().child("user_list2").;
        sortbyID.addListenerForSingleValueEvent(postListener);
    }
}
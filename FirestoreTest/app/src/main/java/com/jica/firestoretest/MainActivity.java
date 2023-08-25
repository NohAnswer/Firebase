package com.jica.firestoretest;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnInsert, btnUpdate, btnView,btnDelete, btntestPost;
    EditText etName, etEmail, etPassword, etID;
    RadioButton rbID,rbEmail,rbName;

    String ID;
    String name;
    String email;
    String password;
    String sort = "id";
    ArrayList<String> arrayData = new ArrayList<>();
    ArrayList<String> arraykeys = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etID = findViewById(R.id.etID);
        etPassword = findViewById(R.id.etPassword);

        btnInsert = findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ID = etID.getText().toString();
                name = etName.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                if(!IsExistID()){
                    postDatas();
                    getDatas();
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
                postDatas();
                getDatas();
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
                getDatas();
            }
        });

        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arraykeys.contains(ID)) {
                    ID = etID.getText().toString();
                    DeleteDatas();
                    getDatas();
                }else {
                    Toast.makeText(getApplicationContext(),ID+"에 해당하는 데이터가 없습니다.",Toast.LENGTH_SHORT).show();
                }
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


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        getDatas();

    }

    public void DeleteDatas() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user_list")
                .document(ID)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),ID+"에 해당하는 데이터를 삭제했습니다.",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),"데이터 삭제 중 오류 발생.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void postDatas() {
        User user = new User(ID,name,password,email);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user_list")
                .document(ID)
                .set(user.toMap())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public boolean IsExistID(){
        boolean IsExist = arraykeys.contains(ID);
        return IsExist;
    }

    public void getDatas() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user_list")
                .orderBy(sort)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        arrayData.clear();
                        arraykeys.clear();
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                String result = user.id+user.name+user.email+user.password;
                                arrayData.add(result);
                                arraykeys.add(document.getId());
                            }
                            arrayAdapter.clear();
                            arrayAdapter.addAll(arrayData);
                            arrayAdapter.notifyDataSetChanged();
                        } else {
                            Log.w("getFirestore", "Error getting documents.", task.getException());
                        }
                    }
                });

    }
}
package com.jica.firestoretest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PostingsTestActivity extends AppCompatActivity {
    Button btnallpost, btnvote, btntips;
    ListView lvpostlist;
    String sort = "postingdate";
    String postingtype,user,postingdate,productortitle,category,price,body,paiddate;
    //참고*******postingdate는 servertimestamp로 관리, paiddate는 postingdate와 일치하게 설정하나, 수정 로직 따로 작성해서 수정할 수 있도록
    Boolean ispulic,ispaid;
    Long likesorvotepay, votesave,voteduration;
    ArrayAdapter<String> arrayAdapter;
    static ArrayList<String> arraykeys = new ArrayList<>();
    static ArrayList<String> arrayData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_test);

        btnallpost = findViewById(R.id.btnallpost);
        btnallpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllPost();
            }
        });
        btnvote = findViewById(R.id.btnvote);
        btnvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getvotePost();
            }
        });
        btntips = findViewById(R.id.btntips);
        btntips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gettipsPost();
            }
        });


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        lvpostlist = findViewById(R.id.lvpostlist);
        lvpostlist.setAdapter(arrayAdapter);

        getAllPost();
    }

    public void getAllPost() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("postings")
                .orderBy(sort)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        arrayData.clear();
                        arraykeys.clear();
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                String result = "";
                                AllPost postings = document.toObject(AllPost.class);
                                if(postings.postingtype.equals("vote")){
                                    result = postings.postingtype +
                                            postings.user +
                                            postings.postingdate +
                                            postings.productortitle +
                                            postings.category +
                                            postings.body +
                                            postings.paiddate +
                                            String.valueOf(postings.ispublic) +
                                            String.valueOf(postings.ispaid) +
                                            String.valueOf(postings.price) +
                                            String.valueOf(postings.likesorvotepay) +
                                            String.valueOf(postings.votesave) +
                                            String.valueOf(postings.voteduration);
                                }else if (postings.postingtype.equals("tips")) {
                                    result = postings.postingtype +
                                            postings.user +
                                            postings.postingdate +
                                            postings.productortitle +
                                            postings.category +
                                            postings.body +
                                            String.valueOf(postings.likesorvotepay);
                                } else if (postings.postingtype.equals("pay")) {
                                    result = postings.postingtype +
                                            postings.user +
                                            postings.postingdate +
                                            postings.productortitle +
                                            postings.category +
                                            postings.body +
                                            postings.paiddate +
                                            String.valueOf(postings.ispublic) +
                                            String.valueOf(postings.price) +
                                            String.valueOf(postings.likesorvotepay);
                                }
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
    public void getvotePost() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("postings")
                .whereEqualTo("postingtype","vote")
                .orderBy("postingdate")  //복합쿼리시 firestore database 콘솔에서 index생성 필요 --> 모르겠으면 일단 실행하고 오류에 있는 링크 타고 색인 생성 하면 됨
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        arrayData.clear();
                        arraykeys.clear();
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                AllPost postings = document.toObject(AllPost.class);
                                String result = "";
                                result = postings.postingtype +
                                        postings.user +
                                        postings.postingdate +
                                        postings.productortitle +
                                        postings.category +
                                        postings.body +
                                        String.valueOf(postings.likesorvotepay);
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
    public void gettipsPost() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("postings")
                .whereEqualTo("postingtype","tips")
                .orderBy("postingdate")  //복합쿼리시 firestore database 콘솔에서 index생성 필요 --> 모르겠으면 일단 실행하고 오류에 있는 링크 타고 색인 생성 하면 됨
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        arrayData.clear();
                        arraykeys.clear();
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                AllPost postings = document.toObject(AllPost.class);
                                String result = "";
                                result = postings.postingtype +
                                        postings.user +
                                        postings.postingdate +
                                        postings.productortitle +
                                        postings.category +
                                        postings.body +
                                        postings.paiddate +
                                        String.valueOf(postings.ispublic) +
                                        String.valueOf(postings.ispaid) +
                                        String.valueOf(postings.price) +
                                        String.valueOf(postings.likesorvotepay) +
                                        String.valueOf(postings.votesave) +
                                        String.valueOf(postings.voteduration);
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
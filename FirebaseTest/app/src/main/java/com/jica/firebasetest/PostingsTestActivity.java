package com.jica.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PostingsTestActivity extends AppCompatActivity {
    Button btnallpost, btnvote, btntips;
    ListView lvpostlist;
    String sort = "postingdate";
    String postingtype,user,postingdate,productortitle,category,price,body,paiddate;
    Boolean ispulic,ispaid;
    Long likesorvotepay, votesave,voteduration;
    ArrayAdapter<String> arrayAdapter;
    private DatabaseReference PostReference;
    static ArrayList<String> arraykeys = new ArrayList<>();
    static ArrayList<String> arrayData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postings_test);

        //리스트뷰 설정
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        lvpostlist = findViewById(R.id.lvpostlist);
        lvpostlist.setAdapter(arrayAdapter);

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

        getAllPost();
    }
    public void getAllPost() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayData.clear();
                arraykeys.clear();
                for(DataSnapshot postsnapshot: snapshot.getChildren()) {
                    String key = postsnapshot.getKey();
                    AllPost postings = postsnapshot.getValue(AllPost.class);
                    String result = "";
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
                    arraykeys.add(key);
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
        Query all = FirebaseDatabase.getInstance().getReference().child("postings").orderByChild(sort);
        all.addListenerForSingleValueEvent(postListener);
    }

    public void getvotePost() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayData.clear();
                arraykeys.clear();
                for(DataSnapshot postsnapshot: snapshot.getChildren()) {
                    String key = postsnapshot.getKey();
                    AllPost postings = postsnapshot.getValue(AllPost.class);
                    if(!postings.postingtype.equals("vote")) {continue;}
                    String result = postings.postingtype +
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
                    arraykeys.add(key);
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
        Query voteposting = FirebaseDatabase.getInstance().getReference().child("postings").orderByChild(sort);
        voteposting.addListenerForSingleValueEvent(postListener);
    }

    public void gettipsPost() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayData.clear();
                arraykeys.clear();
                for(DataSnapshot postsnapshot: snapshot.getChildren()) {
                    String key = postsnapshot.getKey();
                    AllPost postings = postsnapshot.getValue(AllPost.class);
                    String result = postings.postingtype +
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
                    arraykeys.add(key);
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
        Query all = FirebaseDatabase.getInstance().getReference().child("postings").orderByChild(sort);
        all.addListenerForSingleValueEvent(postListener);
    }
}
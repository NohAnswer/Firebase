package com.jica.firestoretest;


import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class AllPost {
    public String postingtype,user,productortitle,category,body;
    public Date postingdate,paiddate;
    public Boolean ispublic,ispaid;
    public Long price,likesorvotepay, votesave,voteduration;

    public AllPost() {}

    public AllPost(String postingtype, String user, String productortitle, String category, String body, Date postingdate, Date paiddate, Boolean ispublic, Boolean ispaid, Long price, Long likesorvotepay, Long votesave, Long voteduration) {
        this.postingtype = postingtype;
        this.user = user;
        this.productortitle = productortitle;
        this.category = category;
        this.body = body;
        this.postingdate = postingdate;
        this.paiddate = paiddate;
        this.ispublic = ispublic;
        this.ispaid = ispaid;
        this.price = price;
        this.likesorvotepay = likesorvotepay;
        this.votesave = votesave;
        this.voteduration = voteduration;
    }

    @Exclude
    public Map<String,Object> toMap() {
        HashMap<String,Object> result = new HashMap<>();
        result.put("postingtype",postingtype);
        result.put("user",user);
        result.put("postingdate",postingdate);
        result.put("productortitle",productortitle);
        result.put("category",category);
        result.put("price",price);
        result.put("body",body);
        result.put("paiddate",paiddate);
        result.put("ispublic", ispublic);
        result.put("ispaid",ispaid);
        result.put("likeorvotepay",likesorvotepay);
        result.put("votesave",votesave);
        result.put("voteduration",voteduration);
        FieldValue.serverTimestamp();
        return result;
    }
}

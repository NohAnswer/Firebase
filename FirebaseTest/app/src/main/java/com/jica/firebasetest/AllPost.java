package com.jica.firebasetest;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class AllPost {
    public String postingtype,user,postingdate,productortitle,category,body,paiddate;
    public Boolean ispublic,ispaid;
    public Long price,likesorvotepay, votesave,voteduration;

    public AllPost() {}

    public AllPost(String postingtype, String user, String postingdate, String productortitle, String category, String body, String paiddate, Boolean ispublic, Boolean ispaid, Long price, Long likesorvotepay, Long votesave, Long voteduration) {
        this.postingtype = postingtype;
        this.user = user;
        this.postingdate = postingdate;
        this.productortitle = productortitle;
        this.category = category;
        this.body = body;
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
        return result;
    }
}

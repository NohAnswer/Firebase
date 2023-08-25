package com.jica.firebasetest;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Firebasetest1 {
    public String id;
    public String name;
    public String password;
    public String email;
    public Firebasetest1(){

    }

    public Firebasetest1(String id, String name, String password, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }
    @Exclude
    public Map<String,Object> toMap() {
        HashMap<String,Object> result = new HashMap<>();
        result.put("id",id);
        result.put("name",name);
        result.put("password",password);
        result.put("email",email);
        return result;
    }
}

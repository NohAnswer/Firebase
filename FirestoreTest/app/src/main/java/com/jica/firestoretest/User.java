package com.jica.firestoretest;



import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class User {
    public String id;
    public String name;
    public String password;
    public String email;
    public User(){

    }

    public User(String id, String name, String password, String email) {
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

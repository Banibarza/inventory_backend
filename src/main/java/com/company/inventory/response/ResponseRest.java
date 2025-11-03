package com.company.inventory.response;

import java.util.ArrayList;
import java.util.HashMap;

public class ResponseRest {

    private ArrayList<HashMap<String, String>> metadata = new ArrayList<>();

    public ArrayList<HashMap<String, String>> getMetadata() {
        return metadata;
    }

    
    public void setMetadata(String type, String code, String date) {
        HashMap<String, String> map = new HashMap<>();
        map.put("date", date);
        map.put("code", code);
        map.put("type", type);
        metadata.clear(); 
        metadata.add(map);
    }
}

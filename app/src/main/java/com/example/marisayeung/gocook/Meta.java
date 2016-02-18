package com.example.marisayeung.gocook;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by marisayeung on 2/16/16.
 */
public class Meta {
    private Date created;
    private Timestamp last_sync;
    private String url;
    private List<String> tags;

    public Meta(JSONObject meta) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa", Locale.US);
        created = new Date();
        String createdString;

//        Parse date created
        try {
            createdString = meta.getString("date created");
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        try {
            if (!createdString.equals("")) {
                created = dateFormat.parse(createdString);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

//        Create timestamp
        java.util.Date dateNow = new java.util.Date();
        last_sync = new Timestamp(dateNow.getTime());

//        Parse URL
        try {
            url = meta.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

//        Parse tags
        JSONArray jsonArray;
        String tag;
        tags = new ArrayList<>();
        try {
            jsonArray = meta.getJSONArray("tags");
            for(int i=0; i < jsonArray.length(); i++) {
                tag = jsonArray.getString(i);
                if (!tag.equals("")) {
                    tags.add(tag);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
    }
}

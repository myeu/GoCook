package com.example.marisayeung.gocook;

import android.os.Parcel;
import android.os.Parcelable;

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
public class Meta implements Parcelable {

    private Date created;
    private String lastSync;
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
        Timestamp lastSyncTimeStamp = new Timestamp(dateNow.getTime());
        lastSync = lastSyncTimeStamp.toString();

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(created != null ? created.getTime() : -1);
        dest.writeString(this.lastSync);
        dest.writeString(this.url);
        dest.writeStringList(this.tags);
    }

    protected Meta(Parcel in) {
        long tmpCreated = in.readLong();
        this.created = tmpCreated == -1 ? null : new Date(tmpCreated);
        this.lastSync = in.readString();
        this.url = in.readString();
        this.tags = in.createStringArrayList();
    }

    public static final Creator<Meta> CREATOR = new Creator<Meta>() {
        public Meta createFromParcel(Parcel source) {
            return new Meta(source);
        }

        public Meta[] newArray(int size) {
            return new Meta[size];
        }
    };

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Timestamp getLastSync() {
        return Timestamp.valueOf(lastSync);
    }

    public void setLastSync(Timestamp newSyncTime) {
        lastSync = newSyncTime.toString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getTags() {
        return tags;
    }

    public boolean addATag(String tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
            return true;
        }
        return false;
    }

    public boolean removeATag(String tag) {
        if (tags.contains(tag)) {
            tags.remove(tag);
            return true;
        }
        return false;
    }
}

package edu.ucsd.cse110.projects110;

import androidx.annotation.NonNull;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class User {
    /** The title of the note. Used as the primary key for shared notes (even on the cloud). */
    @PrimaryKey
    @SerializedName("UID")
    @NonNull
    public String UID;

    /** The content of the note. */
    @SerializedName("Name")
    @NonNull
    public String name;

    public double Lat;
    public double Long;
    /**
     * When the note was last modified. Used for resolving local (db) vs remote (api) conflicts.
     * Defaults to 0 (Jan 1, 1970), so that if a note already exists remotely, its content is
     * always preferred to a new empty note.
     */
    @SerializedName(value = "version")
    public long version = 0;

    /** General constructor for a note. */
    public User(@NonNull String id, @NonNull String Name, double lat
    ,double Long) {
        this.UID=id;
        this.name = Name;
        this.Long=Long;
        this.Lat=lat;
        this.version = 0;
    }
    /** constructor for the device owner user creation.*/
    public User(@NonNull String Name, double lat
            ,double Long) {
        this.UID=UUID.randomUUID().toString();
        this.name = Name;
        this.Long=Long;
        this.Lat=lat;
        this.version = 0;
    }

    public static User fromJSON(String json) {
        return new Gson().fromJson(json, User.class);
    }

    public String toJSON() {
        return new Gson().toJson(this);
    }
}


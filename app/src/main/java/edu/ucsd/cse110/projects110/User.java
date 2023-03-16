package edu.ucsd.cse110.projects110;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

class TimestampAdapter extends TypeAdapter<Long>{
    @Override
    public void write(com.google.gson.stream.JsonWriter out,Long value) throws IOException {
        var instant = Instant.ofEpochSecond(value);
        out.value(instant.toString());
    }
    @Override
    public Long read(com.google.gson.stream.JsonReader in) throws IOException{
        var instant=Instant.parse(in.nextString());
        return instant.getEpochSecond();
    }
}
@Entity(tableName = "locations")
public class User {
    /** The title of the note. Used as the primary key for shared notes (even on the cloud). */
    @PrimaryKey
    @SerializedName("public_code")
    @NonNull
    public String public_code;

    @NonNull
    public String label;
    public double latitude;
    public double longitude;
    public boolean is_listed_publicly;
    @JsonAdapter(TimestampAdapter.class)
    @SerializedName(value = "created_at",alternate = "createAt")
    public long created_at;
    /**
     * When the note was last modified. Used for resolving local (db) vs remote (api) conflicts.
     * Defaults to 0 (Jan 1, 1970), so that if a user already exists remotely, its content is
     * always preferred to a new empty note.
     */
    @JsonAdapter(TimestampAdapter.class)
    @SerializedName(value = "updated_at",alternate = "updatedAt")
    public long updated_at;


    /** General constructor for a note. */

    @Ignore
    public User(@NonNull String public_code, @NonNull String label, double lat
            ,double Long) {
        this.public_code=public_code;
        this.label = label;
        this.longitude=Long;
        this.latitude=lat;
        this.created_at=0;
        this.updated_at=0;
    }

    /** constructor for the device owner user creation.*/
    public User(@NonNull String public_code, @NonNull String label,
                double latitude,double longitude, boolean is_listed_publicly,long created_at, long updated_at) {
        this.public_code=public_code;
        this.label = label;
        this.longitude=longitude;
        this.latitude=latitude;
        this.is_listed_publicly=false;
        this.created_at=created_at;
        this.updated_at=updated_at;
    }

    public static User fromJSON(String json) {
        return new Gson().fromJson(json, User.class);
    }

    public String toJSON() {
        return new Gson().toJson(this);
    }

    @Override
    @NonNull
    public String toString() {
        return "User{" +
                "public_code='" + public_code + '\'' +
                ", label='" + label + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}


package com.example.fusiontv.models;

import java.util.List;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActorProfile implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("profiles")
    @Expose
    private List<Profile> profiles = null;
    public final static Creator<ActorProfile> CREATOR = new Creator<ActorProfile>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ActorProfile createFromParcel(android.os.Parcel in) {
            return new ActorProfile(in);
        }

        public ActorProfile[] newArray(int size) {
            return (new ActorProfile[size]);
        }

    }
            ;

    protected ActorProfile(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.profiles, (com.example.fusiontv.models.Profile.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public ActorProfile() {
    }

    /**
     *
     * @param profiles
     * @param id
     */
    public ActorProfile(Integer id, List<Profile> profiles) {
        super();
        this.id = id;
        this.profiles = profiles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ActorProfile.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("profiles");
        sb.append('=');
        sb.append(((this.profiles == null)?"<null>":this.profiles));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(profiles);
    }

    public int describeContents() {
        return 0;
    }

}
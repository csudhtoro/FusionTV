package com.example.fusiontv.models;

import java.util.List;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TVCredits implements Parcelable
{

    @SerializedName("cast")
    @Expose
    private List<TVShowModel> cast = null;
    @SerializedName("crew")
    @Expose
    private List<Crew> crew = null;
    @SerializedName("id")
    @Expose
    private Integer id;
    public final static Creator<TVCredits> CREATOR = new Creator<TVCredits>() {


        @SuppressWarnings({
                "unchecked"
        })
        public TVCredits createFromParcel(android.os.Parcel in) {
            return new TVCredits(in);
        }

        public TVCredits[] newArray(int size) {
            return (new TVCredits[size]);
        }

    }
            ;

    protected TVCredits(android.os.Parcel in) {
        in.readList(this.cast, (com.example.fusiontv.models.TVShowModel.class.getClassLoader()));
        in.readList(this.crew, (com.example.fusiontv.models.Crew.class.getClassLoader()));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public TVCredits() {
    }

    /**
     *
     * @param cast
     * @param id
     * @param crew
     */
    public TVCredits(List<TVShowModel> cast, List<Crew> crew, Integer id) {
        super();
        this.cast = cast;
        this.crew = crew;
        this.id = id;
    }

    public List<TVShowModel> getCast() {
        return cast;
    }

    public void TVCredit(List<TVShowModel> cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(TVCredits.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("cast");
        sb.append('=');
        sb.append(((this.cast == null)?"<null>":this.cast));
        sb.append(',');
        sb.append("crew");
        sb.append('=');
        sb.append(((this.crew == null)?"<null>":this.crew));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeList(cast);
        dest.writeList(crew);
        dest.writeValue(id);
    }

    public int describeContents() {
        return 0;
    }

}

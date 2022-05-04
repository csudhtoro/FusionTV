package com.example.fusiontv.models;

import java.util.List;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Credit implements Parcelable
{

    @SerializedName("cast")
    @Expose
    private List<Cast> cast = null;
    @SerializedName("crew")
    @Expose
    private List<Object> crew = null;
    @SerializedName("id")
    @Expose
    private Integer id;
    public final static Creator<Credit> CREATOR = new Creator<Credit>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Credit createFromParcel(android.os.Parcel in) {
            return new Credit(in);
        }

        public Credit[] newArray(int size) {
            return (new Credit[size]);
        }

    }
            ;

    protected Credit(android.os.Parcel in) {
        in.readList(this.cast, (com.example.fusiontv.models.Cast.class.getClassLoader()));
        in.readList(this.crew, (java.lang.Object.class.getClassLoader()));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Credit() {
    }

    /**
     *
     * @param cast
     * @param id
     * @param crew
     */
    public Credit(List<Cast> cast, List<Object> crew, Integer id) {
        super();
        this.cast = cast;
        this.crew = crew;
        this.id = id;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<Object> getCrew() {
        return crew;
    }

    public void setCrew(List<Object> crew) {
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
        sb.append(Credit.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
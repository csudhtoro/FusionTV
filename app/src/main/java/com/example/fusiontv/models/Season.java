package com.example.fusiontv.models;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Season implements Parcelable
{

    @SerializedName("air_date")
    @Expose
    private String airDate;
    @SerializedName("episode_count")
    @Expose
    private Integer episodeCount;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("season_number")
    @Expose
    private Integer seasonNumber;
    public final static Creator<Season> CREATOR = new Creator<Season>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Season createFromParcel(android.os.Parcel in) {
            return new Season(in);
        }

        public Season[] newArray(int size) {
            return (new Season[size]);
        }

    }
            ;

    protected Season(android.os.Parcel in) {
        this.airDate = ((String) in.readValue((String.class.getClassLoader())));
        this.episodeCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.overview = ((String) in.readValue((String.class.getClassLoader())));
        this.posterPath = ((String) in.readValue((String.class.getClassLoader())));
        this.seasonNumber = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Season() {
    }

    /**
     *
     * @param overview
     * @param episodeCount
     * @param airDate
     * @param name
     * @param id
     * @param seasonNumber
     * @param posterPath
     */
    public Season(String airDate, Integer episodeCount, Integer id, String name, String overview, String posterPath, Integer seasonNumber) {
        super();
        this.airDate = airDate;
        this.episodeCount = episodeCount;
        this.id = id;
        this.name = name;
        this.overview = overview;
        this.posterPath = posterPath;
        this.seasonNumber = seasonNumber;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public Integer getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(Integer episodeCount) {
        this.episodeCount = episodeCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Season.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("airDate");
        sb.append('=');
        sb.append(((this.airDate == null)?"<null>":this.airDate));
        sb.append(',');
        sb.append("episodeCount");
        sb.append('=');
        sb.append(((this.episodeCount == null)?"<null>":this.episodeCount));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("overview");
        sb.append('=');
        sb.append(((this.overview == null)?"<null>":this.overview));
        sb.append(',');
        sb.append("posterPath");
        sb.append('=');
        sb.append(((this.posterPath == null)?"<null>":this.posterPath));
        sb.append(',');
        sb.append("seasonNumber");
        sb.append('=');
        sb.append(((this.seasonNumber == null)?"<null>":this.seasonNumber));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(airDate);
        dest.writeValue(episodeCount);
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(overview);
        dest.writeValue(posterPath);
        dest.writeValue(seasonNumber);
    }

    public int describeContents() {
        return 0;
    }

}

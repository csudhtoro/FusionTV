package com.example.fusiontv.models;

import java.util.List;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeasonDetail implements Parcelable
{

    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("air_date")
    @Expose
    private String airDate;
    @SerializedName("episodes")
    @Expose
    private List<Episode> episodes = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("season_number")
    @Expose
    private Integer seasonNumber;
    public final static Creator<SeasonDetail> CREATOR = new Creator<SeasonDetail>() {


        @SuppressWarnings({
                "unchecked"
        })
        public SeasonDetail createFromParcel(android.os.Parcel in) {
            return new SeasonDetail(in);
        }

        public SeasonDetail[] newArray(int size) {
            return (new SeasonDetail[size]);
        }

    }
            ;

    protected SeasonDetail(android.os.Parcel in) {
        this._id = ((String) in.readValue((String.class.getClassLoader())));
        this.airDate = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.episodes, (com.example.fusiontv.models.Episode.class.getClassLoader()));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.overview = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.posterPath = ((String) in.readValue((String.class.getClassLoader())));
        this.seasonNumber = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public SeasonDetail() {
    }

    /**
     *
     * @param overview
     * @param airDate
     * @param name
     * @param id
     * @param seasonNumber
     * @param episodes
     * @param _id
     * @param posterPath
     */
    public SeasonDetail(String _id, String airDate, List<Episode> episodes, String name, String overview, Integer id, String posterPath, Integer seasonNumber) {
        super();
        this._id = _id;
        this.airDate = airDate;
        this.episodes = episodes;
        this.name = name;
        this.overview = overview;
        this.id = id;
        this.posterPath = posterPath;
        this.seasonNumber = seasonNumber;
    }

    public String get_id() {
        return _id;
    }

    public void setCid(String _id) {
        this._id = _id;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        sb.append(SeasonDetail.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("_id");
        sb.append('=');
        sb.append(((this._id == null)?"<null>":this._id));
        sb.append(',');
        sb.append("airDate");
        sb.append('=');
        sb.append(((this.airDate == null)?"<null>":this.airDate));
        sb.append(',');
        sb.append("episodes");
        sb.append('=');
        sb.append(((this.episodes == null)?"<null>":this.episodes));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("overview");
        sb.append('=');
        sb.append(((this.overview == null)?"<null>":this.overview));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
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
        dest.writeValue(_id);
        dest.writeValue(airDate);
        dest.writeList(episodes);
        dest.writeValue(name);
        dest.writeValue(overview);
        dest.writeValue(id);
        dest.writeValue(posterPath);
        dest.writeValue(seasonNumber);
    }

    public int describeContents() {
        return 0;
    }

}
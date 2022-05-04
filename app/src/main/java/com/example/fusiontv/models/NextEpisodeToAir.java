package com.example.fusiontv.models;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NextEpisodeToAir implements Parcelable
{

    @SerializedName("air_date")
    @Expose
    private String airDate;
    @SerializedName("episode_number")
    @Expose
    private Integer episodeNumber;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("production_code")
    @Expose
    private String productionCode;
    @SerializedName("season_number")
    @Expose
    private Integer seasonNumber;
    @SerializedName("still_path")
    @Expose
    private Object stillPath;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    public final static Creator<NextEpisodeToAir> CREATOR = new Creator<NextEpisodeToAir>() {


        @SuppressWarnings({
                "unchecked"
        })
        public NextEpisodeToAir createFromParcel(android.os.Parcel in) {
            return new NextEpisodeToAir(in);
        }

        public NextEpisodeToAir[] newArray(int size) {
            return (new NextEpisodeToAir[size]);
        }

    }
            ;

    protected NextEpisodeToAir(android.os.Parcel in) {
        this.airDate = ((String) in.readValue((String.class.getClassLoader())));
        this.episodeNumber = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.overview = ((String) in.readValue((String.class.getClassLoader())));
        this.productionCode = ((String) in.readValue((String.class.getClassLoader())));
        this.seasonNumber = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.stillPath = ((Object) in.readValue((Object.class.getClassLoader())));
        this.voteAverage = ((Double) in.readValue((Double.class.getClassLoader())));
        this.voteCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public NextEpisodeToAir() {
    }

    /**
     *
     * @param overview
     * @param productionCode
     * @param voteAverage
     * @param airDate
     * @param name
     * @param stillPath
     * @param id
     * @param seasonNumber
     * @param voteCount
     * @param episodeNumber
     */
    public NextEpisodeToAir(String airDate, Integer episodeNumber, Integer id, String name, String overview, String productionCode, Integer seasonNumber, Object stillPath, Double voteAverage, Integer voteCount) {
        super();
        this.airDate = airDate;
        this.episodeNumber = episodeNumber;
        this.id = id;
        this.name = name;
        this.overview = overview;
        this.productionCode = productionCode;
        this.seasonNumber = seasonNumber;
        this.stillPath = stillPath;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
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

    public String getProductionCode() {
        return productionCode;
    }

    public void setProductionCode(String productionCode) {
        this.productionCode = productionCode;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Object getStillPath() {
        return stillPath;
    }

    public void setStillPath(Object stillPath) {
        this.stillPath = stillPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(NextEpisodeToAir.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("airDate");
        sb.append('=');
        sb.append(((this.airDate == null)?"<null>":this.airDate));
        sb.append(',');
        sb.append("episodeNumber");
        sb.append('=');
        sb.append(((this.episodeNumber == null)?"<null>":this.episodeNumber));
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
        sb.append("productionCode");
        sb.append('=');
        sb.append(((this.productionCode == null)?"<null>":this.productionCode));
        sb.append(',');
        sb.append("seasonNumber");
        sb.append('=');
        sb.append(((this.seasonNumber == null)?"<null>":this.seasonNumber));
        sb.append(',');
        sb.append("stillPath");
        sb.append('=');
        sb.append(((this.stillPath == null)?"<null>":this.stillPath));
        sb.append(',');
        sb.append("voteAverage");
        sb.append('=');
        sb.append(((this.voteAverage == null)?"<null>":this.voteAverage));
        sb.append(',');
        sb.append("voteCount");
        sb.append('=');
        sb.append(((this.voteCount == null)?"<null>":this.voteCount));
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
        dest.writeValue(episodeNumber);
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(overview);
        dest.writeValue(productionCode);
        dest.writeValue(seasonNumber);
        dest.writeValue(stillPath);
        dest.writeValue(voteAverage);
        dest.writeValue(voteCount);
    }

    public int describeContents() {
        return 0;
    }

}
package com.example.fusiontv.models;

import java.util.List;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;

import com.example.fusiontv.models.Crew;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Episode implements Parcelable
{

    @SerializedName("air_date")
    @Expose
    private String airDate;
    @SerializedName("episode_number")
    @Expose
    private Integer episodeNumber;
    @SerializedName("crew")
    @Expose
    private List<Crew> crew = null;
    @SerializedName("guest_stars")
    @Expose
    private List<GuestStar> guestStars = null;
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
    @SerializedName("runtime")
    @Expose
    private Integer runtime;
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
    public final static Creator<Episode> CREATOR = new Creator<Episode>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Episode createFromParcel(android.os.Parcel in) {
            return new Episode(in);
        }

        public Episode[] newArray(int size) {
            return (new Episode[size]);
        }

    }
            ;

    protected Episode(android.os.Parcel in) {
        this.airDate = ((String) in.readValue((String.class.getClassLoader())));
        this.episodeNumber = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.crew, (com.example.fusiontv.models.Crew.class.getClassLoader()));
        in.readList(this.guestStars, (com.example.fusiontv.models.GuestStar.class.getClassLoader()));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.overview = ((String) in.readValue((String.class.getClassLoader())));
        this.productionCode = ((String) in.readValue((String.class.getClassLoader())));
        this.runtime = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.seasonNumber = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.stillPath = ((Object) in.readValue((Object.class.getClassLoader())));
        this.voteAverage = ((Double) in.readValue((Double.class.getClassLoader())));
        this.voteCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Episode() {
    }

    /**
     *
     * @param overview
     * @param voteAverage
     * @param airDate
     * @param runtime
     * @param seasonNumber
     * @param episodeNumber
     * @param crew
     * @param productionCode
     * @param name
     * @param guestStars
     * @param stillPath
     * @param id
     * @param voteCount
     */
    public Episode(String airDate, Integer episodeNumber, List<Crew> crew, List<GuestStar> guestStars, Integer id, String name, String overview, String productionCode, Integer runtime, Integer seasonNumber, Object stillPath, Double voteAverage, Integer voteCount) {
        super();
        this.airDate = airDate;
        this.episodeNumber = episodeNumber;
        this.crew = crew;
        this.guestStars = guestStars;
        this.id = id;
        this.name = name;
        this.overview = overview;
        this.productionCode = productionCode;
        this.runtime = runtime;
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

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    public List<GuestStar> getGuestStars() {
        return guestStars;
    }

    public void setGuestStars(List<GuestStar> guestStars) {
        this.guestStars = guestStars;
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

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
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
        sb.append(Episode.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("airDate");
        sb.append('=');
        sb.append(((this.airDate == null)?"<null>":this.airDate));
        sb.append(',');
        sb.append("episodeNumber");
        sb.append('=');
        sb.append(((this.episodeNumber == null)?"<null>":this.episodeNumber));
        sb.append(',');
        sb.append("crew");
        sb.append('=');
        sb.append(((this.crew == null)?"<null>":this.crew));
        sb.append(',');
        sb.append("guestStars");
        sb.append('=');
        sb.append(((this.guestStars == null)?"<null>":this.guestStars));
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
        sb.append("runtime");
        sb.append('=');
        sb.append(((this.runtime == null)?"<null>":this.runtime));
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
        dest.writeList(crew);
        dest.writeList(guestStars);
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(overview);
        dest.writeValue(productionCode);
        dest.writeValue(runtime);
        dest.writeValue(seasonNumber);
        dest.writeValue(stillPath);
        dest.writeValue(voteAverage);
        dest.writeValue(voteCount);
    }

    public int describeContents() {
        return 0;
    }

}
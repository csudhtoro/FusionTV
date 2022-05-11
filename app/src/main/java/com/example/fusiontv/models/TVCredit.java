package com.example.fusiontv.models;

import java.util.List;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TVCredit implements Parcelable
{

    @SerializedName("credit_id")
    @Expose
    private String creditId;
    @SerializedName("original_name")
    @Expose
    private String originalName;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds = null;
    @SerializedName("character")
    @Expose
    private String character;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("episode_count")
    @Expose
    private Integer episodeCount;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("origin_country")
    @Expose
    private List<String> originCountry = null;
    public final static Creator<TVCredit> CREATOR = new Creator<TVCredit>() {


        @SuppressWarnings({
                "unchecked"
        })
        public TVCredit createFromParcel(android.os.Parcel in) {
            return new TVCredit(in);
        }

        public TVCredit[] newArray(int size) {
            return (new TVCredit[size]);
        }

    }
            ;

    protected TVCredit(android.os.Parcel in) {
        this.creditId = ((String) in.readValue((String.class.getClassLoader())));
        this.originalName = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.genreIds, (java.lang.Integer.class.getClassLoader()));
        this.character = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.posterPath = ((String) in.readValue((String.class.getClassLoader())));
        this.voteCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.voteAverage = ((Double) in.readValue((Double.class.getClassLoader())));
        this.popularity = ((Double) in.readValue((Double.class.getClassLoader())));
        this.episodeCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.originalLanguage = ((String) in.readValue((String.class.getClassLoader())));
        this.firstAirDate = ((String) in.readValue((String.class.getClassLoader())));
        this.backdropPath = ((String) in.readValue((String.class.getClassLoader())));
        this.overview = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.originCountry, (java.lang.String.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public TVCredit() {
    }

    /**
     *
     * @param overview
     * @param voteAverage
     * @param episodeCount
     * @param genreIds
     * @param originalLanguage
     * @param originalName
     * @param firstAirDate
     * @param creditId
     * @param character
     * @param popularity
     * @param name
     * @param originCountry
     * @param id
     * @param voteCount
     * @param backdropPath
     * @param posterPath
     */
    public TVCredit(String creditId, String originalName, Integer id, List<Integer> genreIds, String character, String name, String posterPath, Integer voteCount, Double voteAverage, Double popularity, Integer episodeCount, String originalLanguage, String firstAirDate, String backdropPath, String overview, List<String> originCountry) {
        super();
        this.creditId = creditId;
        this.originalName = originalName;
        this.id = id;
        this.genreIds = genreIds;
        this.character = character;
        this.name = name;
        this.posterPath = posterPath;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
        this.episodeCount = episodeCount;
        this.originalLanguage = originalLanguage;
        this.firstAirDate = firstAirDate;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.originCountry = originCountry;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(Integer episodeCount) {
        this.episodeCount = episodeCount;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(TVCredit.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("creditId");
        sb.append('=');
        sb.append(((this.creditId == null)?"<null>":this.creditId));
        sb.append(',');
        sb.append("originalName");
        sb.append('=');
        sb.append(((this.originalName == null)?"<null>":this.originalName));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("genreIds");
        sb.append('=');
        sb.append(((this.genreIds == null)?"<null>":this.genreIds));
        sb.append(',');
        sb.append("character");
        sb.append('=');
        sb.append(((this.character == null)?"<null>":this.character));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("posterPath");
        sb.append('=');
        sb.append(((this.posterPath == null)?"<null>":this.posterPath));
        sb.append(',');
        sb.append("voteCount");
        sb.append('=');
        sb.append(((this.voteCount == null)?"<null>":this.voteCount));
        sb.append(',');
        sb.append("voteAverage");
        sb.append('=');
        sb.append(((this.voteAverage == null)?"<null>":this.voteAverage));
        sb.append(',');
        sb.append("popularity");
        sb.append('=');
        sb.append(((this.popularity == null)?"<null>":this.popularity));
        sb.append(',');
        sb.append("episodeCount");
        sb.append('=');
        sb.append(((this.episodeCount == null)?"<null>":this.episodeCount));
        sb.append(',');
        sb.append("originalLanguage");
        sb.append('=');
        sb.append(((this.originalLanguage == null)?"<null>":this.originalLanguage));
        sb.append(',');
        sb.append("firstAirDate");
        sb.append('=');
        sb.append(((this.firstAirDate == null)?"<null>":this.firstAirDate));
        sb.append(',');
        sb.append("backdropPath");
        sb.append('=');
        sb.append(((this.backdropPath == null)?"<null>":this.backdropPath));
        sb.append(',');
        sb.append("overview");
        sb.append('=');
        sb.append(((this.overview == null)?"<null>":this.overview));
        sb.append(',');
        sb.append("originCountry");
        sb.append('=');
        sb.append(((this.originCountry == null)?"<null>":this.originCountry));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(creditId);
        dest.writeValue(originalName);
        dest.writeValue(id);
        dest.writeList(genreIds);
        dest.writeValue(character);
        dest.writeValue(name);
        dest.writeValue(posterPath);
        dest.writeValue(voteCount);
        dest.writeValue(voteAverage);
        dest.writeValue(popularity);
        dest.writeValue(episodeCount);
        dest.writeValue(originalLanguage);
        dest.writeValue(firstAirDate);
        dest.writeValue(backdropPath);
        dest.writeValue(overview);
        dest.writeList(originCountry);
    }

    public int describeContents() {
        return 0;
    }

}
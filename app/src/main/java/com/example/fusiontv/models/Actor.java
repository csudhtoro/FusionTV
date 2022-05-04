package com.example.fusiontv.models;

import java.util.List;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Actor implements Parcelable
{

    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("known_for_department")
    @Expose
    private String knownForDepartment;
    @SerializedName("deathday")
    @Expose
    private String deathday;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("also_known_as")
    @Expose
    private List<String> alsoKnownAs = null;
    @SerializedName("gender")
    @Expose
    private Integer gender;
    @SerializedName("biography")
    @Expose
    private String biography;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("place_of_birth")
    @Expose
    private String placeOfBirth;
    @SerializedName("profile_path")
    @Expose
    private String profilePath;
    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("imdb_id")
    @Expose
    private String imdbId;
    @SerializedName("homepage")
    @Expose
    private String homepage;
    public final static Creator<Actor> CREATOR = new Creator<Actor>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Actor createFromParcel(android.os.Parcel in) {
            return new Actor(in);
        }

        public Actor[] newArray(int size) {
            return (new Actor[size]);
        }

    }
            ;

    protected Actor(android.os.Parcel in) {
        this.birthday = ((String) in.readValue((String.class.getClassLoader())));
        this.knownForDepartment = ((String) in.readValue((String.class.getClassLoader())));
        this.deathday = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.alsoKnownAs, (java.lang.String.class.getClassLoader()));
        this.gender = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.biography = ((String) in.readValue((String.class.getClassLoader())));
        this.popularity = ((Double) in.readValue((Double.class.getClassLoader())));
        this.placeOfBirth = ((String) in.readValue((String.class.getClassLoader())));
        this.profilePath = ((String) in.readValue((String.class.getClassLoader())));
        this.adult = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.imdbId = ((String) in.readValue((String.class.getClassLoader())));
        this.homepage = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Actor() {
    }

    /**
     *
     * @param birthday
     * @param placeOfBirth
     * @param gender
     * @param imdbId
     * @param knownForDepartment
     * @param biography
     * @param deathday
     * @param alsoKnownAs
     * @param popularity
     * @param name
     * @param id
     * @param profilePath
     * @param adult
     * @param homepage
     */
    public Actor(String birthday, String knownForDepartment, String deathday, Integer id, String name, List<String> alsoKnownAs, Integer gender, String biography, Double popularity, String placeOfBirth, String profilePath, Boolean adult, String imdbId, String homepage) {
        super();
        this.birthday = birthday;
        this.knownForDepartment = knownForDepartment;
        this.deathday = deathday;
        this.id = id;
        this.name = name;
        this.alsoKnownAs = alsoKnownAs;
        this.gender = gender;
        this.biography = biography;
        this.popularity = popularity;
        this.placeOfBirth = placeOfBirth;
        this.profilePath = profilePath;
        this.adult = adult;
        this.imdbId = imdbId;
        this.homepage = homepage;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getKnownForDepartment() {
        return knownForDepartment;
    }

    public void setKnownForDepartment(String knownForDepartment) {
        this.knownForDepartment = knownForDepartment;
    }

    public String getDeathday() {
        return deathday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
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

    public List<String> getAlsoKnownAs() {
        return alsoKnownAs;
    }

    public void setAlsoKnownAs(List<String> alsoKnownAs) {
        this.alsoKnownAs = alsoKnownAs;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Actor.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("birthday");
        sb.append('=');
        sb.append(((this.birthday == null)?"<null>":this.birthday));
        sb.append(',');
        sb.append("knownForDepartment");
        sb.append('=');
        sb.append(((this.knownForDepartment == null)?"<null>":this.knownForDepartment));
        sb.append(',');
        sb.append("deathday");
        sb.append('=');
        sb.append(((this.deathday == null)?"<null>":this.deathday));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("alsoKnownAs");
        sb.append('=');
        sb.append(((this.alsoKnownAs == null)?"<null>":this.alsoKnownAs));
        sb.append(',');
        sb.append("gender");
        sb.append('=');
        sb.append(((this.gender == null)?"<null>":this.gender));
        sb.append(',');
        sb.append("biography");
        sb.append('=');
        sb.append(((this.biography == null)?"<null>":this.biography));
        sb.append(',');
        sb.append("popularity");
        sb.append('=');
        sb.append(((this.popularity == null)?"<null>":this.popularity));
        sb.append(',');
        sb.append("placeOfBirth");
        sb.append('=');
        sb.append(((this.placeOfBirth == null)?"<null>":this.placeOfBirth));
        sb.append(',');
        sb.append("profilePath");
        sb.append('=');
        sb.append(((this.profilePath == null)?"<null>":this.profilePath));
        sb.append(',');
        sb.append("adult");
        sb.append('=');
        sb.append(((this.adult == null)?"<null>":this.adult));
        sb.append(',');
        sb.append("imdbId");
        sb.append('=');
        sb.append(((this.imdbId == null)?"<null>":this.imdbId));
        sb.append(',');
        sb.append("homepage");
        sb.append('=');
        sb.append(((this.homepage == null)?"<null>":this.homepage));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(birthday);
        dest.writeValue(knownForDepartment);
        dest.writeValue(deathday);
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeList(alsoKnownAs);
        dest.writeValue(gender);
        dest.writeValue(biography);
        dest.writeValue(popularity);
        dest.writeValue(placeOfBirth);
        dest.writeValue(profilePath);
        dest.writeValue(adult);
        dest.writeValue(imdbId);
        dest.writeValue(homepage);
    }

    public int describeContents() {
        return 0;
    }

}
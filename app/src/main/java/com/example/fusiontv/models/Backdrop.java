package com.example.fusiontv.models;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Backdrop implements Parcelable
{

    @SerializedName("aspect_ratio")
    @Expose
    private Double aspectRatio;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("iso_639_1")
    @Expose
    private String iso6391;
    @SerializedName("file_path")
    @Expose
    private String filePath;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    @SerializedName("width")
    @Expose
    private Integer width;
    public final static Creator<Backdrop> CREATOR = new Creator<Backdrop>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Backdrop createFromParcel(android.os.Parcel in) {
            return new Backdrop(in);
        }

        public Backdrop[] newArray(int size) {
            return (new Backdrop[size]);
        }

    }
            ;

    protected Backdrop(android.os.Parcel in) {
        this.aspectRatio = ((Double) in.readValue((Double.class.getClassLoader())));
        this.height = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.iso6391 = ((String) in.readValue((String.class.getClassLoader())));
        this.filePath = ((String) in.readValue((String.class.getClassLoader())));
        this.voteAverage = ((Double) in.readValue((Double.class.getClassLoader())));
        this.voteCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.width = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Backdrop() {
    }

    /**
     *
     * @param voteAverage
     * @param filePath
     * @param width
     * @param aspectRatio
     * @param iso6391
     * @param voteCount
     * @param height
     */
    public Backdrop(Double aspectRatio, Integer height, String iso6391, String filePath, Double voteAverage, Integer voteCount, Integer width) {
        super();
        this.aspectRatio = aspectRatio;
        this.height = height;
        this.iso6391 = iso6391;
        this.filePath = filePath;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.width = width;
    }

    public Double getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(Double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Backdrop.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("aspectRatio");
        sb.append('=');
        sb.append(((this.aspectRatio == null)?"<null>":this.aspectRatio));
        sb.append(',');
        sb.append("height");
        sb.append('=');
        sb.append(((this.height == null)?"<null>":this.height));
        sb.append(',');
        sb.append("iso6391");
        sb.append('=');
        sb.append(((this.iso6391 == null)?"<null>":this.iso6391));
        sb.append(',');
        sb.append("filePath");
        sb.append('=');
        sb.append(((this.filePath == null)?"<null>":this.filePath));
        sb.append(',');
        sb.append("voteAverage");
        sb.append('=');
        sb.append(((this.voteAverage == null)?"<null>":this.voteAverage));
        sb.append(',');
        sb.append("voteCount");
        sb.append('=');
        sb.append(((this.voteCount == null)?"<null>":this.voteCount));
        sb.append(',');
        sb.append("width");
        sb.append('=');
        sb.append(((this.width == null)?"<null>":this.width));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(aspectRatio);
        dest.writeValue(height);
        dest.writeValue(iso6391);
        dest.writeValue(filePath);
        dest.writeValue(voteAverage);
        dest.writeValue(voteCount);
        dest.writeValue(width);
    }

    public int describeContents() {
        return 0;
    }

}

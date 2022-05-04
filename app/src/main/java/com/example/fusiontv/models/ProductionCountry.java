package com.example.fusiontv.models;

import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductionCountry implements Parcelable
{

    @SerializedName("iso_3166_1")
    @Expose
    private String iso31661;
    @SerializedName("name")
    @Expose
    private String name;
    public final static Creator<ProductionCountry> CREATOR = new Creator<ProductionCountry>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ProductionCountry createFromParcel(android.os.Parcel in) {
            return new ProductionCountry(in);
        }

        public ProductionCountry[] newArray(int size) {
            return (new ProductionCountry[size]);
        }

    }
            ;

    protected ProductionCountry(android.os.Parcel in) {
        this.iso31661 = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public ProductionCountry() {
    }

    /**
     *
     * @param name
     * @param iso31661
     */
    public ProductionCountry(String iso31661, String name) {
        super();
        this.iso31661 = iso31661;
        this.name = name;
    }

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ProductionCountry.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("iso31661");
        sb.append('=');
        sb.append(((this.iso31661 == null)?"<null>":this.iso31661));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(iso31661);
        dest.writeValue(name);
    }

    public int describeContents() {
        return 0;
    }

}


package com.example.animall.Data.Remote.Models.Home;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category_ implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("category_ar")
    @Expose
    private String categoryAr;
    @SerializedName("category_en")
    @Expose
    private String categoryEn;
    @SerializedName("photo")
    @Expose
    private String photo;

    protected Category_(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        categoryAr = in.readString();
        categoryEn = in.readString();
        photo = in.readString();
    }

    public static final Creator<Category_> CREATOR = new Creator<Category_>() {
        @Override
        public Category_ createFromParcel(Parcel in) {
            return new Category_(in);
        }

        @Override
        public Category_[] newArray(int size) {
            return new Category_[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryAr() {
        return categoryAr;
    }

    public void setCategoryAr(String categoryAr) {
        this.categoryAr = categoryAr;
    }

    public String getCategoryEn() {
        return categoryEn;
    }

    public void setCategoryEn(String categoryEn) {
        this.categoryEn = categoryEn;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(categoryAr);
        dest.writeString(categoryEn);
        dest.writeString(photo);
    }
}

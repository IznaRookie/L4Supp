package com.example.l4supp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Seller implements Parcelable {

    private int id;
    private String name;
    private String description;
    private boolean isFavourite;

    public Seller(int id, String name, String description, boolean isFavourite) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isFavourite = isFavourite;
    }


    protected Seller(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        isFavourite = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeByte((byte) (isFavourite ? 1 : 0));
    }

    public static final Creator<Seller> CREATOR = new Creator<Seller>() {
        @Override
        public Seller createFromParcel(Parcel in) {
            return new Seller(in);
        }

        @Override
        public Seller[] newArray(int size) {
            return new Seller[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}

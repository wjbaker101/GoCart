package com.wjbaker.gocart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TescoProduct implements Parcelable {

    private String image;
    private String superDepartment;
    private long tpnb;

    @SerializedName("ContentsMeasureType")
    private String contentsMeasureType;

    private String name;

    @SerializedName("UnitOfSale")
    private int unitOfSale;

    @SerializedName("AverageSellingUnitWeight")
    private double averageSellingUnitWeight;

    private List<String> description;

    @SerializedName("UnitQuantity")
    private String unitQuantity;

    private long id;

    @SerializedName("ContentsQuantity")
    private double contentsQuantity;

    private String department;
    private double price;

    @SerializedName("unitprice")
    private double unitPrice;

    public TescoProduct() {}

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSuperDepartment() {
        return superDepartment;
    }

    public void setSuperDepartment(String superDepartment) {
        this.superDepartment = superDepartment;
    }

    public long getTpnb() {
        return tpnb;
    }

    public void setTpnb(long tpnb) {
        this.tpnb = tpnb;
    }

    public String getContentsMeasureType() {
        return contentsMeasureType;
    }

    public void setContentsMeasureType(String contentsMeasureType) {
        this.contentsMeasureType = contentsMeasureType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnitOfSale() {
        return unitOfSale;
    }

    public void setUnitOfSale(int unitOfSale) {
        this.unitOfSale = unitOfSale;
    }

    public double getAverageSellingUnitWeight() {
        return averageSellingUnitWeight;
    }

    public void setAverageSellingUnitWeight(double averageSellingUnitWeight) {
        this.averageSellingUnitWeight = averageSellingUnitWeight;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public String getUnitQuantity() {
        return unitQuantity;
    }

    public void setUnitQuantity(String unitQuantity) {
        this.unitQuantity = unitQuantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getContentsQuantity() {
        return contentsQuantity;
    }

    public void setContentsQuantity(double contentsQuantity) {
        this.contentsQuantity = contentsQuantity;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public TescoProduct(Parcel in) {
        this.image = in.readString();
        this.superDepartment = in.readString();
        this.tpnb = in.readLong();
        this.contentsMeasureType = in.readString();
        this.name = in.readString();
        this.unitOfSale = in.readInt();
        this.averageSellingUnitWeight = in.readDouble();
        in.readList(this.getDescription(), this.getClass().getClassLoader());
        this.unitQuantity = in.readString();
        this.id = in.readLong();
        this.contentsQuantity = in.readDouble();
        this.department = in.readString();
        this.price = in.readDouble();
        this.unitPrice = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.image);
        parcel.writeString(this.superDepartment);
        parcel.writeLong(this.tpnb);
        parcel.writeString(this.contentsMeasureType);
        parcel.writeString(this.name);
        parcel.writeInt(this.unitOfSale);
        parcel.writeDouble(this.averageSellingUnitWeight);
        parcel.writeList(this.description);
        parcel.writeString(this.unitQuantity);
        parcel.writeLong(this.id);
        parcel.writeDouble(this.contentsQuantity);
        parcel.writeString(this.department);
        parcel.writeDouble(this.price);
        parcel.writeDouble(this.unitPrice);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<TescoProduct> CREATOR =
            new Parcelable.Creator<TescoProduct>() {

        @Override
        public TescoProduct createFromParcel(Parcel parcel) {
            return new TescoProduct(parcel);
        }

        @Override
        public TescoProduct[] newArray(int size) {
            return new TescoProduct[size];
        }
    };
}

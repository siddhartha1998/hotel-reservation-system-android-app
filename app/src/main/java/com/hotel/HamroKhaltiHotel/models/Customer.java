package com.hotel.HamroKhaltiHotel.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Customer implements Parcelable {

    private Long id;
    private String fullname;
    private String username;
    private  String email;
    private String address;
    private String phone;
    private  String age;
    private String gender;

    public Customer(Long id,String fullname, String username, String email, String address, String phone, String age, String gender) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
    }

    protected Customer(Parcel in) {

        fullname = in.readString();
        username = in.readString();
        email = in.readString();
        address = in.readString();
        phone = in.readString();
        age = in.readString();
        gender = in.readString();
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    public Customer() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fullname);
        parcel.writeString(username);
        parcel.writeString(email);
        parcel.writeString(address);
        parcel.writeString(phone);
        parcel.writeString(age);
        parcel.writeString(gender);
    }
}

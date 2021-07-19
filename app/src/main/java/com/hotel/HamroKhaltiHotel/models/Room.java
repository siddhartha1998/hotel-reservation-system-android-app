package com.hotel.HamroKhaltiHotel.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Room implements Parcelable {

    private Long id;

    private String hotelUsername;

    private Long roomNumber;

    private String roomType;

    private String description;

    private Hotel hotel;


    protected Room(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        hotelUsername = in.readString();
        if (in.readByte() == 0) {
            roomNumber = null;
        } else {
            roomNumber = in.readLong();
        }
        roomType = in.readString();
        description = in.readString();
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHotelUsername() {
        return hotelUsername;
    }

    public void setHotelUsername(String hotelUsername) {
        this.hotelUsername = hotelUsername;
    }

    public Long getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Long roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(id);
        }
        parcel.writeString(hotelUsername);
        if (roomNumber == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(roomNumber);
        }
        parcel.writeString(roomType);
        parcel.writeString(description);
    }
}

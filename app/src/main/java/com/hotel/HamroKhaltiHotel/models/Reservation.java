package com.hotel.HamroKhaltiHotel.models;

public class Reservation {

    private  String hotelName;
    private  String roomNumber;
    private  String reservationTime;
    private  String checkInDate;
    private  String checkOutDate;
    private  String noOfGuest;
    private  String idType;
    private  String idNumber;

    public Reservation(String hotelName, String roomNumber, String reservationTime, String checkInDate, String checkOutDate, String noOfGuest, String idType, String idNumber) {
        this.hotelName = hotelName;
        this.roomNumber = roomNumber;
        this.reservationTime = reservationTime;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.noOfGuest = noOfGuest;
        this.idType = idType;
        this.idNumber = idNumber;
    }

    public Reservation() {

    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getNoOfGuest() {
        return noOfGuest;
    }

    public void setNoOfGuest(String noOfGuest) {
        this.noOfGuest = noOfGuest;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}

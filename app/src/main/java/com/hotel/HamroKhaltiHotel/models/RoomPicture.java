package com.hotel.HamroKhaltiHotel.models;

public class RoomPicture {

    private String fileName;

    private String fileType;

    private String uploadDir;

    private String uploadedBy;

    private String uploadedTime;

    private Room room;



    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public String getUploadedTime() {
        return uploadedTime;
    }

    public void setUploadedTime(String uploadedTime) {
        this.uploadedTime = uploadedTime;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}

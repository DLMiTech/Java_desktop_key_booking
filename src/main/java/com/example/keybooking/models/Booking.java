package com.example.keybooking.models;

public class Booking {
    private int id;
    private int keyId;
    private String studentIndex;
    private String key;
    private String bookingDate;
    private int status;

    public Booking(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentIndex() {
        return studentIndex;
    }

    public void setStudentIndex(String studentIndex) {
        this.studentIndex = studentIndex;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getKeyId() {
        return keyId;
    }

    public void setKeyId(int keyId) {
        this.keyId = keyId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

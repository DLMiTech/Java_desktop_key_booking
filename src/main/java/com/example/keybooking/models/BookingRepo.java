package com.example.keybooking.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingRepo {
    private static final DBAccess dbAccess = new DBAccess();

    public static int insert(Booking booking) {
        int result = 0;

        if (isKeyBooked(booking.getKeyId())) {
            return 2;
        }

        try {
            String query = "INSERT INTO booking(key_id, student_index, kay_name) VALUES(?,?,?);";
            PreparedStatement ps = dbAccess.getConnection().prepareStatement(query);
            ps.setInt(1, booking.getKeyId());
            ps.setString(2, booking.getStudentIndex());
            ps.setString(3, booking.getKey());

            result = ps.executeUpdate();

        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return result;
    }


    public static boolean returnKey(int keyId) {
        boolean result = false;

        try {
            String query = "UPDATE booking SET status = 0 WHERE id = ?;";
            PreparedStatement ps = dbAccess.getConnection().prepareStatement(query);
            ps.setInt(1, keyId);

            int rowsAffected = ps.executeUpdate();
            result = rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return result;
    }

    public static List<Booking> getAllBooking() {
        List<Booking> listBooking = new ArrayList<>();
        Booking booking = null;
        try {
            String query = "SELECT * FROM booking WHERE status = 1 ORDER BY id DESC;";
            PreparedStatement ps = dbAccess.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if(rs != null){
                while(rs.next()){
                    booking = new Booking();
                    booking.setId(rs.getInt("id"));
                    booking.setStudentIndex(rs.getString("student_index"));
                    booking.setKey(rs.getString("kay_name"));
                    booking.setBookingDate(rs.getString("booking_date"));
                    listBooking.add(booking);

                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listBooking;
    }


    public static boolean isKeyBooked(int keyId) {
        boolean isBooked = false;

        try {
            String query = "SELECT COUNT(*) FROM booking WHERE key_id = ? AND status = 1;";
            PreparedStatement ps = dbAccess.getConnection().prepareStatement(query);
            ps.setInt(1, keyId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                isBooked = count > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return isBooked;
    }


    public static int getTotalBookedKeysCount() {
        int totalRows = 0;

        try {
            String query = "SELECT COUNT(*) AS total_rows FROM booking WHERE status = 1;";
            PreparedStatement ps = dbAccess.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                totalRows = rs.getInt("total_rows");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return totalRows;
    }

}

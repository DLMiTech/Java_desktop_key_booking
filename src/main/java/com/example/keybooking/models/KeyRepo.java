package com.example.keybooking.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KeyRepo {
    private static final DBAccess dbAccess = new DBAccess();

    public static int insert(Keys keys) {
        int result = 0;

        try {
            String query = "INSERT INTO keys_tb(block, room, number) VALUES(?,?,?);";
            PreparedStatement ps = dbAccess.getConnection().prepareStatement(query);
            ps.setString(1, keys.getBlock());
            ps.setString(2, keys.getRoom());
            ps.setInt(3, keys.getNumKeys());

            result = ps.executeUpdate();

        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return result;
    }

    public static int getTotalKeysCount() {
        int totalRows = 0;

        try {
            String query = "SELECT COUNT(*) AS total_rows FROM keys_tb;";
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




    public static Keys getKey(int id) {
        Keys keys = null;
        try {
            String query = "SELECT * FROM keys_tb WHERE id=?;";
            PreparedStatement ps = dbAccess.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs != null){
                rs.next();
                keys = new Keys();
                keys.setId(rs.getInt("id"));
                keys.setBlock(rs.getString("block"));
                keys.setRoom(rs.getString("room"));
                keys.setNumKeys(rs.getInt("number"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return keys;
    }

    public static List<Keys> getAllKeys() {
        List<Keys> listKeys = new ArrayList<>();
        Keys keys = null;
        try {
            String query = "SELECT * FROM keys_tb";
            PreparedStatement ps = dbAccess.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if(rs != null){
                while(rs.next()){
                    keys = new Keys();
                    keys.setId(rs.getInt("id"));
                    keys.setBlock(rs.getString("block"));
                    keys.setRoom(rs.getString("room"));
                    keys.setNumKeys(rs.getInt("number"));

                    listKeys.add(keys);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listKeys;
    }

    public static boolean deleteKey(int id) {
        boolean result = false;
        try {
            String query = "DELETE FROM keys_tb WHERE id=?;";
            PreparedStatement ps = dbAccess.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            result = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static boolean updateKey(Keys keys) {
        boolean result = false;

        try {
            String query = "UPDATE keys_tb SET block=?, room=?, number=? WHERE id=?;";
            PreparedStatement ps = dbAccess.getConnection().prepareStatement(query);
            ps.setString(1, keys.getBlock());
            ps.setString(2, keys.getRoom());
            ps.setInt(3, keys.getNumKeys());
            ps.setInt(4, keys.getId());
            result = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}

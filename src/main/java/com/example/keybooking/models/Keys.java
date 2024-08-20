package com.example.keybooking.models;

public class Keys {
    private int id;
    private String block;
    private String room;
    private int numKeys;

    public Keys() {}

    public int getNumKeys() {
        return numKeys;
    }

    public void setNumKeys(int numKeys) {
        this.numKeys = numKeys;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

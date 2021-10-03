package com.isherryforever.museum;

import com.google.gson.annotations.SerializedName;

public class Room {

    //http://47.104.29.237:30032   显示每个房间内的人数

    @SerializedName("room 01")
    private int room1;

    @SerializedName("room 02")
    private int room2;

    @SerializedName("room 03")
    private int room3;

    @SerializedName("room 04")
    private int room4;

    public Room(int room1,int room2,int room3,int room4)
    {
        this.room1=room1;
        this.room2=room2;
        this.room3=room3;
        this.room4=room4;
    }

    public int getRoom1() {
        return room1;
    }

    public int getRoom2() {
        return room2;
    }

    public int getRoom3() {
        return room3;
    }

    public int getRoom4() {
        return room4;
    }

    public void setRoom1(int room1) {
        this.room1 = room1;
    }

    public void setRoom2(int room2) {
        this.room2 = room2;
    }

    public void setRoom3(int room3) {
        this.room3 = room3;
    }

    public void setRoom4(int room4) {
        this.room4 = room4;
    }



}

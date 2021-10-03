package com.isherryforever.museum;

import java.util.List;

public class Sensor {
    private String name;
    private String text;
    private int total;

    public List<UserBean> getRecords() {
        return records;
    }

    private List<UserBean> records;

    public class UserBean
    {
        private float loc_x,loc_y;
        String name,text;
        public UserBean(String name,String text,float loc_x,float loc_y)
        {
            this.loc_x=loc_x;
            this.loc_y=loc_y;
            this.name=name;
            this.text=text;
        }
        public float getLocx() {
            return loc_x;
        }
        public float getLocy() {
            return loc_y;
        }
    }


    public Sensor(String name, String text, int total,List<UserBean> records)
    {
        this.name=name;
        this.text=text;
        this.total=total;
        this.records.addAll(records);

    }

    public String getName() {
        return name;
    }

    public int getTotal() {
        return total;
    }

    public String getText() {
        return text;
    }




}

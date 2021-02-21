package com.icollection.location.Data.Cash;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Cash extends RealmObject {

    @PrimaryKey
    private int id = -1;
    private String type;
    private float sum;
    private String time;
    private boolean isToAccountant;
    private boolean isToDriver;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean getIsToAccountant() {
        return isToAccountant;
    }

    public void setIsToAccountant(boolean isToAccountant) {
        this.isToAccountant = isToAccountant;
    }

    public boolean getIsToDriver() {
        return isToDriver;
    }

    public void setIsToDriver(boolean isToDriver) {
        this.isToDriver = isToDriver;
    }

}

package com.icollection.location.Data.Delivery;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Delivery extends RealmObject {

    @PrimaryKey
    private int id = -1;
    private String date;
    private String day;
    private String driver_name = "Jason";

    private boolean isCar = false;
    private int start_odometer = 0;
    private int end_odometer = 0;
    private boolean isLoad = false;
    private boolean isPoster = false;
    private boolean isEdde = false;
    private boolean isBuy = false;
    private String buy_item;
    private boolean isWarehouseBoss = false;
    private boolean isRepairBoss = false;
    private boolean isBothBox = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public boolean isCar() {
        return isCar;
    }

    public void setCar(boolean car) {
        isCar = car;
    }

    public int getStart_odometer() {
        return start_odometer;
    }

    public void setStart_odometer(int start_odometer) {
        this.start_odometer = start_odometer;
    }

    public int getEnd_odometer() {
        return end_odometer;
    }

    public void setEnd_odometer(int end_odometer) {
        this.end_odometer = end_odometer;
    }

    public boolean isLoad() {
        return isLoad;
    }

    public void setLoad(boolean load) {
        isLoad = load;
    }

    public boolean isPoster() {
        return isPoster;
    }

    public void setPoster(boolean poster) {
        isPoster = poster;
    }

    public boolean isEdde() {
        return isEdde;
    }

    public void setEdde(boolean edde) {
        isEdde = edde;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public void setBuy(boolean buy) {
        isBuy = buy;
    }

    public String getBuy_item() {
        return buy_item;
    }

    public void setBuy_item(String buy_item) {
        this.buy_item = buy_item;
    }

    public boolean isWarehouseBoss() {
        return isWarehouseBoss;
    }

    public void setWarehouseBoss(boolean warehouseBoss) {
        isWarehouseBoss = warehouseBoss;
    }

    public boolean isRepairBoss() {
        return isRepairBoss;
    }

    public void setRepairBoss(boolean repairBoss) {
        isRepairBoss = repairBoss;
    }

    public boolean isBothBox() {
        return isBothBox;
    }

    public void setBothBox(boolean bothBox) {
        isBothBox = bothBox;
    }
}

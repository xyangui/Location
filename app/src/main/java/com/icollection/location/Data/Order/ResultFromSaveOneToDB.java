package com.icollection.location.Data.Order;

public class ResultFromSaveOneToDB {

    private String message;
    private String orderQty; //订单上预定数量
    private String scanQty;  //实际捡货数量

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(String orderQty) {
        this.orderQty = orderQty;
    }

    public String getScanQty() {
        return scanQty;
    }

    public void setScanQty(String scanQty) {
        this.scanQty = scanQty;
    }
}

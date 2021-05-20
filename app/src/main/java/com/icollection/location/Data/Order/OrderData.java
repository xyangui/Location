package com.icollection.location.Data.Order;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class OrderData {

    private int status; // ==0时，没有订单的意思
    private String code_product;
    private String title; //description
    private String max_sold;
    private String stock_shop;
    private String stock_wh;
    private String rec_qty; //建议，相当于原订单的result
    private String shop_actual_order;
    private String actual_send;
    private Object location;  //兼容  没有订单时返回： ...,"location":"null",...
    private String remark;

    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }

    public void setCode_product(String code_product) {
        this.code_product = code_product;
    }
    public String getCodeProduct() {
        return code_product;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setMax_sold(String max_sold) {
        this.max_sold = max_sold;
    }
    public String getMax_sold() {
        return max_sold;
    }

    public void setStock_shop(String stock_shop) {
        this.stock_shop = stock_shop;
    }
    public String getStock_shop() {
        return stock_shop;
    }

    public void setStock_wh(String stock_wh) {
        this.stock_wh = stock_wh;
    }
    public String getStock_wh() {
        return stock_wh;
    }

    public void setRec_qty(String rec_qty) {
        this.rec_qty = rec_qty;
    }
    public String getRec_qty() {
        return rec_qty;
    }

    public void setShop_actual_order(String shop_actual_order) {
        this.shop_actual_order = shop_actual_order;
    }
    public String getShop_actual_order() {
        return shop_actual_order;
    }

    public void setActual_send(String actual_send) {
        this.actual_send = actual_send;
    }
    public String getActual_send() {
        return actual_send;
    }

    public static class LocationBean {

        private List<String> PL;
        private List<String> EB;

        public List<String> getPL() {
            return PL;
        }

        public void setPL(List<String> PL) {
            this.PL = PL;
        }

        public List<String> getEB() {
            return EB;
        }

        public void setEB(List<String> EB) {
            this.EB = EB;
        }

        public String get_PL_Location(){

            if(PL == null){
                return "NONE";
            }

            if(PL.size() == 1){

                if(PL.get(0) == null || PL.get(0).equals("No location")){
                    return "NONE";
                }
                return PL.get(0);
            }

            String strList = new String();
            for(String bean : PL){
                strList = strList + bean;
            }

            if(strList.equals("No location") || strList.isEmpty()){
                return "NONE";
            }
            return strList;
        }

//        public String get_EB_Location(){
//
//            if(code_location.size() == 1 && code_location.get(0).get(0).equals("EB")){
//                return code_location.get(0).get(1);
//            }
//
//            String strList = new String();
//            for(List<String> bean : code_location){
//
//                if(bean.get(0).equals("EB")) {
//                    strList = strList + bean.get(1);
//                }
//            }
//
//            if(strList.equals("No location") || strList.isEmpty()){
//                return "NONE";
//            }
//            return strList;
//        }
    }

    public void setLocation(Object location) {
        this.location = location;
    }
    public Object getLocation() {
        return location;
    }

    public String getLocationString() {

        //取位置   {PL=[Null], EB=[Null]}  String str = "{PL=[Null], EB=[Null]}";
        String str = getLocation().toString();
        Gson gson = new Gson();
        OrderData.LocationBean locationBean = gson.fromJson(str, new TypeToken<LocationBean>() {
        }.getType());

        String location = locationBean.get_PL_Location();

        return location;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getRemark() {
        return remark;
    }
}

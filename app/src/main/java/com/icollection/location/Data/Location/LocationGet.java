package com.icollection.location.Data.Location;

import java.util.List;

public class LocationGet {

    /**
     * bcode : APIPH7PHC129-0
     * description : C0 IPH7/8 Plus Matte Ring Holder Kick Stand Velvet Black
     * location_list : {"code_location":["X43"]}
     * stock : 5
     */

    private String bcode;
    private String description;
    private LocationListBean location_list;
    private String stock;

    public String getBcode() {
        return bcode;
    }

    public void setBcode(String bcode) {
        this.bcode = bcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocationListBean getLocation_list() {
        return location_list;
    }

    public void setLocation_list(LocationListBean location_list) {
        this.location_list = location_list;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    //{"code_location":[["PL","M21"],["PL","Z22"]]}
    public static class LocationListBean {

        private List<List<String>> code_location;

        public List<List<String>> getCode_location() {
            return code_location;
        }

        public void setCode_location(List<List<String>> code_location) {
            this.code_location = code_location;
        }

        public String get_PL_Location(){

            if(code_location.size() == 1 && code_location.get(0).get(0).equals("PL")){

                if(code_location.get(0).get(1).equals("No location")){
                    return "NONE";
                }
                return code_location.get(0).get(1);
            }

            String strList = new String();
            for(List<String> bean : code_location){

                if(bean.get(0).equals("PL")) {
                    strList = strList + bean.get(1);
                }
            }

            if(strList.equals("No location")){
                return "NONE";
            }
            return strList;
        }

        public String get_EB_Location(){

            if(code_location.size() == 1 && code_location.get(0).get(0).equals("EB")){
                return code_location.get(0).get(1);
            }

            String strList = new String();
            for(List<String> bean : code_location){

                if(bean.get(0).equals("EB")) {
                    strList = strList + bean.get(1);
                }
            }

            if(strList.isEmpty()){
                return "NONE";
            }
            return strList;
        }
    }
//    public static class LocationListBean {
//
//        private List<String> code_location;
//
//        public List<String> getCode_location() {
//            return code_location;
//        }
//
//        public void setCode_location(List<String> code_location) {
//            this.code_location = code_location;
//        }
//
//        public String getString(){
//
//            if(code_location.size() == 1){
//                return code_location.get(0);
//            }
//
//            String strList = new String();
//            for(String bean : code_location){
//                strList = strList + "(" + bean + ")";
//            }
//            return strList;
//        }
//    }
}

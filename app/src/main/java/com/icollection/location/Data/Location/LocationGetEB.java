package com.icollection.location.Data.Location;

import java.util.List;

public class LocationGetEB {

    /**
     * bcode : APIPHXRLC103-0
     * description : B0 Mercury IPHXR Rich Diary With Card Pocket Black
     * location_list : {"code_location":[["EB","Z12"]]}
     */

    private String bcode;
    private String description;
    private LocationListBean location_list;
    private int status;    // =0时，添加成功；=1时，添加失败，失败原因在 remark里
    private String remark;

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

            if(strList.equals("No location") || strList.isEmpty()){
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

            if(strList.equals("No location") || strList.isEmpty()){
                return "NONE";
            }
            return strList;
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

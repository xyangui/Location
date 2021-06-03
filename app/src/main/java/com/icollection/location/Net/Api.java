package com.icollection.location.Net;

import com.icollection.location.Data.Delivery.PartsRMAResult;
import com.icollection.location.Data.Location.LocationGet;
import com.icollection.location.Data.Order.OrderData;
import com.icollection.location.Data.Order.ResultFromSaveOneToDB;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {

    //"http://approd9h4leb60v4olh1v.phonecollection.com.au/stock/json-check-location/barcode2/8809745631577"
    @GET("stock/json-check-location/barcode2/{bcode}")
    Flowable<String> getLocation(@Path("bcode") String bcode);

    //"http://approd9h4leb60v4olh1v.phonecollection.com.au/stock/json-edit-location/barcode/APIPH12SC100-13/location/M12/act/edit"
    @GET("stock/json-edit-location/barcode/{bcode}/location/{location}/act/edit")
    Flowable<String> editLocation(@Path("bcode") String bcode, @Path("location") String location);

    //"http://approd9h4leb60v4olh1v.phonecollection.com.au/stock/json-edit-location/barcode/APIPH12SC102-11/location/M12/act/editAll"
    @GET("stock/json-edit-location/barcode/{bcode}/location/{location}/act/editAll")
    Flowable<String> editAllLocation(@Path("bcode") String bcode, @Path("location") String location);

    //"http://approd9h4leb60v4olh1v.phonecollection.com.au/stock/json-edit-location/barcode/APIPHXRLC103-0/location/Z22/act/add"
    @GET("stock/json-edit-location/barcode/{bcode}/location/{location}/act/add")
    Flowable<String> addLocation(@Path("bcode") String bcode, @Path("location") String location);

    //"http://approd9h4leb60v4olh1v.phonecollection.com.au/stock/json-edit-location/barcode/APIPH12SC102-0/location/M12/act/addAll"
    @GET("stock/json-edit-location/barcode/{bcode}/location/{location}/act/addAll")
    Flowable<String> addAllLocation(@Path("bcode") String bcode, @Path("location") String location);

    //"http://approd9h4leb60v4olh1v.phonecollection.com.au/stock/json-edit-location/barcode/OTHAICUNI-100-0/location/J1399/act/edit_Ebay"
    //"http://approd9h4leb60v4olh1v.phonecollection.com.au/stock/json-edit-location/barcode/8809745631577/location/T1222/act/edit_Ebay"
    @GET("stock/json-edit-location/barcode/{bcode}/location/{location}/act/edit_Ebay")
    Flowable<String> editLocationEB(@Path("bcode") String bcode, @Path("location") String location);

    //"http://approd9h4leb60v4olh1v.phonecollection.com.au/stock/json-edit-location/barcode/APIPHXRLC103-0/location/Z222/act/editAll_Ebay"
    @GET("stock/json-edit-location/barcode/{bcode}/location/{location}/act/editAll_Ebay")
    Flowable<String> editAllLocationEB(@Path("bcode") String bcode, @Path("location") String location);

    //"http://approd9h4leb60v4olh1v.phonecollection.com.au/stock/json-edit-location/barcode/APIPHXRLC103-0/location/Z12/act/add_Ebay"
    @GET("stock/json-edit-location/barcode/{bcode}/location/{location}/act/add_Ebay")
    Flowable<String> addLocationEB(@Path("bcode") String bcode, @Path("location") String location);

    //"http://approd9h4leb60v4olh1v.phonecollection.com.au/stock/json-edit-location/barcode/APIPH12SC102-0/location/Z31/act/addAll_Ebay"
    @GET("stock/json-edit-location/barcode/{bcode}/location/{location}/act/addAll_Ebay")
    Flowable<String> addAllLocationEB(@Path("bcode") String bcode, @Path("location") String location);

    //"http://approd9h4leb60v4olh1v.phonecollection.com.au/jsonfunction/transfer-rma/rmaList/75654-54866-44847-448563/p1/123123/p2/2213123"
    @GET("jsonfunction/transfer-rma/rmaList/{rma_list}/p1/{shop_stuff_password}/p2/{driver_password}")
    Flowable<List<PartsRMAResult>> sendPartsRMAList(@Path("rma_list") String rma_list,
                                                    @Path("shop_stuff_password") String shop_stuff_password,
                                                    @Path("driver_password") String driver_password);

    //"http://approd9h4leb60v4olh1v.phonecollection.com.au/jsonfunction/take-order/shop/WBPC/orderno/PDWBPC20210521"
    @GET("jsonfunction/take-order/shop/{shopname}/orderno/{PD_shopname_date}")
    Flowable<List<OrderData>> getOrderData(@Path("shopname") String shopname,
                                           @Path("PD_shopname_date") String PD_shopname_date);

    //测试链接
    //http://approd9h4leb60v4olh1v.phonecollection.com.au/stock/count-kt-warehouse/shop/HPPC/orderno/PDHPPC20160530   //网页版本
    //http://approd9h4leb60v4olh1v.phonecollection.com.au/jsonfunction/take-order/shop/HPPC/orderno/PDHPPC20160530  //json版本
//    @GET("stock/count-kt-warehouse/shop/HPPC/orderno/PDHPPC20160530")
//    Flowable<List<OrderData>> getOrderData_test();

    //http://approd9h4leb60v4olh1v.phonecollection.com.au/jsonfunction/take-order-scan/orderno/PDHPPC20160530/scan_code/660543015192/scan_qty/50
    @GET("jsonfunction/take-order-scan/orderno/{PD_shopname_date}/scan_code/{bcode}/scan_qty/{qty}")
    Flowable<List<ResultFromSaveOneToDB>> saveOneToDB(@Path("PD_shopname_date") String PD_shopname_date,
                                                      @Path("bcode") String bcode,
                                                      @Path("qty") int qty);
}

package com.icollection.location.Net;

import com.icollection.location.Data.Location.LocationGet;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {

    //"http://approd9h4leb60v4olh1v.phonecollection.com.au/stock/json-check-location/barcode2/APIPH12SC102-0"
    @GET("stock/json-check-location/barcode2/{bcode}")
    Flowable<String> getLocation(@Path("bcode") String bcode);

    //"http://approd9h4leb60v4olh1v.phonecollection.com.au/stock/json-edit-location/barcode/APIPH12SC100-13/location/M12/act/edit"
    @GET("stock/json-edit-location/barcode/{bcode}/location/{location}/act/edit")
    Flowable<String> editLocation(@Path("bcode") String bcode, @Path("location") String location);

    @GET("stock/json-edit-location/barcode/{bcode}/location/{location}/act/editAll")
    Flowable<String> editAllLocation(@Path("bcode") String bcode, @Path("location") String location);

    //"http://approd9h4leb60v4olh1v.phonecollection.com.au/stock/json-edit-location/barcode/APIPHXRLC103-0/location/Z22/act/add"
    @GET("stock/json-edit-location/barcode/{bcode}/location/{location}/act/add")
    Flowable<String> addLocation(@Path("bcode") String bcode, @Path("location") String location);

    @GET("stock/json-edit-location/barcode/{bcode}/location/{location}/act/addAll")
    Flowable<String> addAllLocation(@Path("bcode") String bcode, @Path("location") String location);

    //"http://approd9h4leb60v4olh1v.phonecollection.com.au/stock/json-edit-location/barcode/APIPHXRLC103-0/location/Z222/act/edit_Ebay"
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
}

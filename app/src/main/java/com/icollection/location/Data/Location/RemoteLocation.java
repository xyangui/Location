package com.icollection.location.Data.Location;

import android.support.annotation.NonNull;

import com.icollection.location.Net.Http;

import io.reactivex.Flowable;

public class RemoteLocation {

    private static RemoteLocation INSTANCE;
    private RemoteLocation() {
    }
    public static RemoteLocation getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteLocation();
        }
        return INSTANCE;
    }

    public Flowable<String> getLocation(@NonNull String bcode) {

        return Http
                .getInstance()
                .getApi()
                .getLocation(bcode);
    }

    public Flowable<String> editLocation(@NonNull String bcode, String location) {

        return Http
                .getInstance()
                .getApi()
                .editLocation(bcode, location);
    }

    public Flowable<String> editAllLocation(@NonNull String bcode, String location) {

        return Http
                .getInstance()
                .getApi()
                .editAllLocation(bcode, location);
    }

    public Flowable<String> addLocation(@NonNull String bcode, @NonNull String location) {

        return Http
                .getInstance()
                .getApi()
                .addLocation(bcode, location);
    }

    public Flowable<String> addAllLocation(@NonNull String bcode, @NonNull String location) {

        return Http
                .getInstance()
                .getApi()
                .addAllLocation(bcode, location);
    }


    public Flowable<String> editLocationEB(@NonNull String bcode, String location) {

        return Http
                .getInstance()
                .getApi()
                .editLocationEB(bcode, location);
    }

    public Flowable<String> editAllLocationEB(@NonNull String bcode, String location) {

        return Http
                .getInstance()
                .getApi()
                .editAllLocationEB(bcode, location);
    }

    public Flowable<String> addLocationEB(@NonNull String bcode, @NonNull String location) {

        return Http
                .getInstance()
                .getApi()
                .addLocationEB(bcode, location);
    }

    public Flowable<String> addAllLocationEB(@NonNull String bcode, @NonNull String location) {

        return Http
                .getInstance()
                .getApi()
                .addAllLocationEB(bcode, location);
    }

}

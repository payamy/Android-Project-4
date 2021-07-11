package com.example.rhodium.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
//        (primaryKeys = {"cell_id","latitude","longitude","power"})
public class Parameter {

    @PrimaryKey(autoGenerate = true)
    public int id = 0;

    //location
    private double latitude;
    private double longitude;

    //cell info
    private int cell_id;
    private char type;
    private int plmn_id;
    private int lac_rac;
    private int tac;

    //power
    private int power;
    private int quality;
    private int extera_quality;
    private int Strength_level;

    //Qeo param
    private double latency;
    private double jitter;
    private double http_response_time;
    private double multimedia_QoE;
    private int downSpeed;
    private int upSpeed;

    private String current_time;


    @Override
    public String toString() {
        String powerStr = powerToString();
        return "Location: (" + latitude + ", " + longitude + ")" +
                "\nCell Id: " + cell_id +
                "\nPLMN Id: " + plmn_id +
                powerStr +
                "\nStrength level : " + Strength_level +
                "\nLatency: " + latency + "Millisecond" +
                "\nJitter: " + jitter + "Millisecond" +
                "\nHTTP Response Time: " + http_response_time + " Millisecond" +
                "\nMultimedia QoE: " + multimedia_QoE + " Millisecond" +
                "\nDownload: " + downSpeed + " KBPS" +
                "\nUpload:" + upSpeed + " KBPS";
    }


    private String powerToString() {
        String powerStr = "";

        if (type == 'G') {
            powerStr += "\nGSM";
            powerStr += "\nRxLev: " + power;
            powerStr += "\nC1: " + quality;
            powerStr += "\nC2: " + extera_quality;
            powerStr += "\nLAC: " + lac_rac;
        } else {
            if (type == 'U') {
                powerStr += "\nUMTS";
                powerStr += "\nRSCP: " + power;
                powerStr += "\nEC/N0: " + quality;
                powerStr += "\nLAC: " + lac_rac;
                powerStr += "\nRAC: " + tac;
            } else {
                powerStr += "\nLTE";
                powerStr += "\nRSRP: " + power;
                powerStr += "\nRSRQ: " + quality;
                powerStr += "\nCINR: " + extera_quality;
                powerStr += "\nTAC: " + lac_rac;
            }
        }
        return powerStr;
    }


    public Parameter(int cell_id, double latitude, double longitude,
                     char type, int plmn_id, int lac_rac, int tac,
                     int power, int quality, int extera_quality, int Strength_level,
                     double latency, double jitter, double http_response_time, double multimedia_QoE,
                     int downSpeed, int upSpeed, String current_time
    ) {
        this.latitude = latitude;
        this.longitude = longitude;

        this.cell_id = cell_id;
        this.type = type;
        this.plmn_id = plmn_id;
        this.lac_rac = lac_rac;
        this.tac = tac;

        this.power = power;
        this.quality = quality;
        this.extera_quality = extera_quality;
        this.Strength_level = Strength_level;

        this.latency = latency;
        this.jitter = jitter;
        this.http_response_time = http_response_time;
        this.multimedia_QoE = multimedia_QoE;
        this.downSpeed = downSpeed;
        this.upSpeed = upSpeed;

        this.current_time = current_time;
    }


    public  int getId(){return  id;}

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public int getCell_id() {
        return cell_id;
    }

    public void setCell_id(int cell_id) {
        this.cell_id = cell_id;
    }


    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }


    public int getPlmn_id() {
        return plmn_id;
    }

    public void setPlmn_id(int plmn_id) {
        this.plmn_id = plmn_id;
    }


    public int getLac_rac() {
        return lac_rac;
    }

    public void setLac_rac(int lac_rac) {
        this.lac_rac = lac_rac;
    }


    public int getTac() {
        return tac;
    }

    public void setTac(int tac) {
        this.tac = tac;
    }


    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }


    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }


    public int getExtera_quality() {
        return extera_quality;
    }

    public void setExtera_quality(int extera_quality) {
        this.extera_quality = extera_quality;
    }


    public int getStrength_level() {
        return Strength_level;
    }

    public void setStrength_level(int strength_level) {
        Strength_level = strength_level;
    }


    public double getLatency() {
        return latency;
    }

    public void setLatency(double latency) {
        this.latency = latency;
    }


    public double getJitter() {
        return jitter;
    }

    public void setJitter(double jitter) {
        this.jitter = jitter;
    }


    public double getHttp_response_time() {
        return http_response_time;
    }

    public void setHttp_response_time(double http_response_time) {
        this.http_response_time = http_response_time;
    }


    public double getMultimedia_QoE() {
        return multimedia_QoE;
    }

    public void setMultimedia_QoE(double multimedia_QoE) {
        this.multimedia_QoE = multimedia_QoE;
    }


    public int getDownSpeed() {
        return downSpeed;
    }

    public void setDownSpeed(int downSpeed) {
        this.downSpeed = downSpeed;
    }


    public int getUpSpeed() {
        return upSpeed;
    }

    public void setUpSpeed(int upSpeed) {
        this.upSpeed = upSpeed;
    }


    public String getCurrent_time() {
        return current_time;
    }

    public void setCurrent_time(String current_time) {
        this.current_time = current_time;
    }
}

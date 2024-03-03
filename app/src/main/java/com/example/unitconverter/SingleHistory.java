package com.example.unitconverter;

import java.util.Objects;

public class SingleHistory {
    private String key;
    private String fromUnit;
    private String toUnit;
    private String fromValue;
    private String toValue;
    private String time;

    public SingleHistory(String key, String fromUnit, String toUnit, String fromValue, String toValue, String time) {
        this.key = key;
        this.fromUnit = fromUnit;
        this.toUnit = toUnit;
        this.fromValue = fromValue;
        this.toValue = toValue;
        this.time = time;
    }

    public boolean isAllEquals(SingleHistory item){
        return Objects.equals(key,item.key) &&
            Objects.equals(fromUnit,item.fromUnit) &&
            Objects.equals(fromValue,item.fromValue) &&
            Objects.equals(toUnit,item.toUnit) &&
            Objects.equals(toValue,item.toValue) &&
            Objects.equals(time,item.time);
    }

    public String getFromData(){
        return fromValue+"\n"+fromUnit;
    }

    public String getToData(){
        return toValue+"\n"+toUnit;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFromUnit() {
        return fromUnit;
    }

    public void setFromUnit(String fromUnit) {
        this.fromUnit = fromUnit;
    }

    public String getToUnit() {
        return toUnit;
    }

    public void setToUnit(String toUnit) {
        this.toUnit = toUnit;
    }

    public String getFromValue() {
        return fromValue;
    }

    public void setFromValue(String fromValue) {
        this.fromValue = fromValue;
    }

    public String getToValue() {
        return toValue;
    }

    public void setToValue(String toValue) {
        this.toValue = toValue;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

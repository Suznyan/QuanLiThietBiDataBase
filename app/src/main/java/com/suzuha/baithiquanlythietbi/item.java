package com.suzuha.baithiquanlythietbi;

public class item {
    protected String url;
    protected String title;
    protected String Brand;
    protected String Year;
    protected String Detail;

    public item(String url, String Name, String brand, String year, String detail) {
        this.url = url;
        title = Name;
        Brand = brand;
        Year = year;
        Detail = detail;
    }

    public item() {

    }
}
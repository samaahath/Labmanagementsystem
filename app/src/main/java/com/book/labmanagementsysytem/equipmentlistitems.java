package com.book.labmanagementsysytem;

public class equipmentlistitems {
    //Image URL
    public String equipmentid;
    public String name; //Name
    public String code;
    public String location;
    public String img;
    public equipmentlistitems(String equipmentid,String name,String code, String location, String img)
    {
        this.equipmentid = equipmentid;
        this.name = name;
        this.code = code;
        this.location = location;
        this.img = img;
    }

    public String getEquipmentid() {
        return equipmentid;
    }

    public String getName() {
        return name;
    }
    public String getCode(){return code;}
    public String getLocation () {
        return location;
    }
    public String getImg() {
        return img;
    }
}

package com.sdl.sdlarchivesmanager;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "CLIENT".
 */
public class Client {

    private Long id;
    /** Not-null value. */
    private String Client_Num;
    private String Client_Name;
    private String Client_Owner;
    private String Client_Type;
    private String Client_Level;
    private String Client_Uplevel;
    private String Client_Phone;
    private String Client_Province;
    private String Client_City;
    private String Client_Country;
    private String Client_Town;
    private String Client_Address;
    private String Client_LngLat;

    public Client() {
    }

    public Client(Long id) {
        this.id = id;
    }

    public Client(Long id, String Client_Num, String Client_Name, String Client_Owner, String Client_Type, String Client_Level, String Client_Uplevel, String Client_Phone, String Client_Province, String Client_City, String Client_Country, String Client_Town, String Client_Address, String Client_LngLat) {
        this.id = id;
        this.Client_Num = Client_Num;
        this.Client_Name = Client_Name;
        this.Client_Owner = Client_Owner;
        this.Client_Type = Client_Type;
        this.Client_Level = Client_Level;
        this.Client_Uplevel = Client_Uplevel;
        this.Client_Phone = Client_Phone;
        this.Client_Province = Client_Province;
        this.Client_City = Client_City;
        this.Client_Country = Client_Country;
        this.Client_Town = Client_Town;
        this.Client_Address = Client_Address;
        this.Client_LngLat = Client_LngLat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getClient_Num() {
        return Client_Num;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setClient_Num(String Client_Num) {
        this.Client_Num = Client_Num;
    }

    public String getClient_Name() {
        return Client_Name;
    }

    public void setClient_Name(String Client_Name) {
        this.Client_Name = Client_Name;
    }

    public String getClient_Owner() {
        return Client_Owner;
    }

    public void setClient_Owner(String Client_Owner) {
        this.Client_Owner = Client_Owner;
    }

    public String getClient_Type() {
        return Client_Type;
    }

    public void setClient_Type(String Client_Type) {
        this.Client_Type = Client_Type;
    }

    public String getClient_Level() {
        return Client_Level;
    }

    public void setClient_Level(String Client_Level) {
        this.Client_Level = Client_Level;
    }

    public String getClient_Uplevel() {
        return Client_Uplevel;
    }

    public void setClient_Uplevel(String Client_Uplevel) {
        this.Client_Uplevel = Client_Uplevel;
    }

    public String getClient_Phone() {
        return Client_Phone;
    }

    public void setClient_Phone(String Client_Phone) {
        this.Client_Phone = Client_Phone;
    }

    public String getClient_Province() {
        return Client_Province;
    }

    public void setClient_Province(String Client_Province) {
        this.Client_Province = Client_Province;
    }

    public String getClient_City() {
        return Client_City;
    }

    public void setClient_City(String Client_City) {
        this.Client_City = Client_City;
    }

    public String getClient_Country() {
        return Client_Country;
    }

    public void setClient_Country(String Client_Country) {
        this.Client_Country = Client_Country;
    }

    public String getClient_Town() {
        return Client_Town;
    }

    public void setClient_Town(String Client_Town) {
        this.Client_Town = Client_Town;
    }

    public String getClient_Address() {
        return Client_Address;
    }

    public void setClient_Address(String Client_Address) {
        this.Client_Address = Client_Address;
    }

    public String getClient_LngLat() {
        return Client_LngLat;
    }

    public void setClient_LngLat(String Client_LngLat) {
        this.Client_LngLat = Client_LngLat;
    }

}

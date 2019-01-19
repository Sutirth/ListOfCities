package model_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("name")
    @Expose
    private String name;


    @SerializedName("_id")
    @Expose
    private int id;

    @SerializedName("coord")
    private City_Coordinates coordinates;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public City_Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(City_Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}

import java.io.Serializable;

class Grid implements Serializable
{
    public int index;

    private double latitudeSouth;
    private double latitudeNorth;
    private double longitudeWest;
    private double longitudeEast;

    public Grid() {
        index = 0;
        latitudeNorth = 0.0;
        latitudeSouth = 0.0;
        longitudeWest = 0.0;
        longitudeEast = 0.0;
    }

    public Grid(int index, double aNorth, double aSouth, double lWest, double lEast)
    {
        this.index = index;
        latitudeNorth = aNorth;
        latitudeSouth = aSouth;
        longitudeWest = lWest;
        longitudeEast = lEast;

    }

    public void printGrid()
    {
        System.out.println("----------------------------");
        System.out.println("Index: " + index);
        System.out.println("South latitude: " + latitudeSouth);
        System.out.println("North latitude: " + latitudeNorth);
        System.out.println("West longitude: " + longitudeWest);
        System.out.println("East longitude: " + longitudeEast);

    }

    public double getLatitudeSouth() {
        return latitudeSouth;
    }

    public void setLatitudeSouth(double latitudeSouth) {
        this.latitudeSouth = latitudeSouth;
    }


    public double getLatitudeNorth() {
        return latitudeNorth;
    }

    public void setLatitudeNorth(double latitudeNorth) {
        this.latitudeNorth = latitudeNorth;
    }


    public double getLongitudeWest() {
        return longitudeWest;
    }

    public void setLongitudeWest(double longitudeWest) {
        this.longitudeWest = longitudeWest;
    }


    public double getLongitudeEast() {
        return longitudeEast;
    }

    public void setLongitudeEast(double longitudeEast) {
        this.longitudeEast = longitudeEast;
    }




}
package ly.bithive.hsavemeandroid.model;

public class Clink {

    int id;
    String name, phone_number, latitude, longitude, address;
    boolean status, visible;

    public Clink() {
    }

    public Clink(int id, String name, String phone_number, String latitude, String longitude, String address, boolean status, boolean visible) {
        this.id = id;
        this.name = name;
        this.phone_number = phone_number;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.status = status;
        this.visible = visible;
    }

    public int getId() {
        return id;
    }

    public Clink setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Clink setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public Clink setPhone_number(String phone_number) {
        this.phone_number = phone_number;
        return this;
    }

    public double getLatitude() {
        return Double.parseDouble(latitude);
    }

    public Clink setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return Double.parseDouble(longitude);
    }

    public Clink setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Clink setAddress(String address) {
        this.address = address;
        return this;
    }

    public boolean isStatus() {
        return status;
    }

    public Clink setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public boolean isVisible() {
        return visible;
    }

    public Clink setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }
}

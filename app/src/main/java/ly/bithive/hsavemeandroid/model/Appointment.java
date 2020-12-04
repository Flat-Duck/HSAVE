package ly.bithive.hsavemeandroid.model;

public class Appointment {
    int id;
    String drName, aDay, starTime, endTime;
    public Appointment(){


    }
    public Appointment(int id, String drName, String day, String starTime, String endTime) {
        this.id = id;
        this.drName = drName;
        this.aDay = day;
        this.starTime = starTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public Appointment setId(int id) {
        this.id = id;
        return this;
    }

    public String getDrName() {
        return drName;
    }

    public Appointment setDrName(String drName) {
        this.drName = drName;
        return this;
    }

    public String getaDay() {
        return aDay;
    }

    public Appointment setaDay(String aDay) {
        this.aDay = aDay;
        return this;
    }

    public String getStarTime() {
        return starTime;
    }

    public Appointment setStarTime(String starTime) {
        this.starTime = starTime;
        return this;
    }

    public String getEndTime() {
        return endTime;
    }

    public Appointment setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }
}

package ly.bithive.hsavemeandroid.model;

public class Doctor {
    int id,clink_id, specialty_id;
    String name, phone, qualification, clink, specialty;

    public Doctor() {
    }

    public Doctor(int id, int clink_id, int specialty_id, String name, String phone, String qualification, String clink, String specialty) {
        this.id = id;
        this.clink_id = clink_id;
        this.specialty_id = specialty_id;
        this.name = name;
        this.phone = phone;
        this.qualification = qualification;
        this.clink = clink;
        this.specialty = specialty;
    }

    public int getId() {
        return id;
    }

    public Doctor setId(int id) {
        this.id = id;
        return this;
    }

    public int getClink_id() {
        return clink_id;
    }

    public Doctor setClink_id(int clink_id) {
        this.clink_id = clink_id;
        return this;
    }

    public int getSpecialty_id() {
        return specialty_id;
    }

    public Doctor setSpecialty_id(int specialty_id) {
        this.specialty_id = specialty_id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Doctor setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Doctor setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getQualification() {
        return qualification;
    }

    public Doctor setQualification(String qualification) {
        this.qualification = qualification;
        return this;
    }

    public String getClink() {
        return clink;
    }

    public Doctor setClink(String clink) {
        this.clink = clink;
        return this;
    }

    public String getSpecialty() {
        return specialty;
    }

    public Doctor setSpecialty(String specialty) {
        this.specialty = specialty;
        return this;
    }
}

package ly.bithive.hsavemeandroid.model;

public class Specialty {
    int id;
    String name, description;

    public Specialty() {
    }

    public Specialty(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public Specialty setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Specialty setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Specialty setDescription(String description) {
        this.description = description;
        return this;
    }
}

package ly.bithive.hsavemeandroid.model;

public class Test {
    int id;
    String name, description;

    public Test() {
    }

    public Test(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public Test setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Test setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Test setDescription(String description) {
        this.description = description;
        return this;
    }
}

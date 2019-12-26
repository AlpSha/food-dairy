package alpsha;

import java.util.Objects;

public class ServingType {
    private String name;
    private double grams;

    public ServingType(String name, double grams) {
        this.name = name;
        this.grams = grams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null) {
            return;
        }
        this.name = name;
    }

    public double getGrams() {
        return grams;
    }

    public void setGrams(double grams) {
        if(grams <= 0) {
            return;
        }
        this.grams = grams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServingType that = (ServingType) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

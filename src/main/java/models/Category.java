package models;

import lombok.Getter;
import lombok.Setter;
import java.util.Objects;

@Setter
@Getter
public class Category {
    private long id;
    private String name;

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

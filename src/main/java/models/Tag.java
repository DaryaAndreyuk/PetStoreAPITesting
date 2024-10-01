package models;

import lombok.Getter;
import lombok.Setter;
import java.util.Objects;

@Setter
@Getter
public class Tag {
    private long id;
    private String name;

    public Tag(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tag() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id) && Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
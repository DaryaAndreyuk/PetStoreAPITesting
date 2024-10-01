package models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class Pet {
    private long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private String status;

    public Pet(long id, Category category, String name, List<String> photoUrls, List<Tag> tags, String status) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.photoUrls = photoUrls;
        this.tags = tags;
        this.status = status;
    }

    public Pet() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id) && Objects.equals(category, pet.category) && Objects.equals(name, pet.name) && Objects.equals(photoUrls, pet.photoUrls) && Objects.equals(tags, pet.tags) && Objects.equals(status, pet.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, name, photoUrls, tags, status);
    }
}

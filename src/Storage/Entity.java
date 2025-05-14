package Storage;

import java.io.Serializable;
import java.util.UUID;

public abstract class Entity implements Serializable {
  private final UUID id;
  private String name;
  private String description;

  public Entity() {
    this("none", "none");
  }

  public Entity(String name, String description) {
    this.id = UUID.randomUUID();
    this.name = name;
    this.description = description;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Entity other = (Entity) o;
    return id.equals(other.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return name = " (" + description + ")";
  }
}

package at.htl.farm.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@XmlRootElement
@Entity
@NamedQueries({
        @NamedQuery(name = "Farm.findAll", query = "select f from Farm f"),
        @NamedQuery(name = "Farm.findById", query = "select f from Farm f where f.id = ?1")
})
public class Farm {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "farm", cascade = CascadeType.ALL)
    private Set<Animal> animals;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "farm", cascade = CascadeType.ALL)
    private Set<Field> fields;

    //region Constructor
    public Farm() {
    }

    public Farm(String location) {
        this.location = location;
    }
    //endregion

    //region Getter & Setter
    public Long getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void addAnimal(Animal a) {
        if (animals == null)
            animals = new HashSet<>();
        a.setFarm(this);
        animals.add(a);
    }

    public void removeAnimal(Animal a) {
        if (animals != null && animals.contains(a)) {
            animals.remove(a);
            a.setFarm(null);
        }
    }

    public void addField(Field f) {
        if (fields == null)
            fields = new HashSet<>();
        f.setFarm(this);
        fields.add(f);
    }

    public void removeField(Field f) {
        if (fields != null && fields.contains(f)) {
            fields.remove(f);
            f.setFarm(null);
        }
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    public Set<Field> getFields() {
        return fields;
    }
    //endregion
}

package at.htl.farm.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@NamedQueries({
        @NamedQuery(name = "Animal.findAll", query = "select a from Animal a"),
        @NamedQuery(name = "Animal.findByName", query = "select a from Animal a where a.name like ?1"),
        @NamedQuery(name = "Animal.findById", query = "select a from Animal a where a.id = ?1")
})
public class Animal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String name;
    protected int age;

    @ManyToOne
    protected Farm farm;

    //region Constructor
    public Animal() {
    }

    public Animal(String name, Farm farm, int age) {
        this.name = name;
        this.farm = farm;
        this.age = age;
    }

    //endregion

    //region Getter & Setter

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    //endregion
}

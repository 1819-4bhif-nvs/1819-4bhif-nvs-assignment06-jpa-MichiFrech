package at.htl.farm.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@NamedQueries({
        @NamedQuery(name = "Field.findAll", query = "select f from Field f"),
        @NamedQuery(name = "Field.findBySeed", query = "select f from Field f where f.plantedSeeds like ?1"),
        @NamedQuery(name = "Field.findById", query = "select f from Field f where f.id = ?1")
})
public class Field {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer hecatre;
    private String plantedSeeds;

    @ManyToOne
    protected Farm farm;

    //region Constructor
    public Field() {
    }

    public Field(Integer hecatre, String plantedSeeds, Farm farm) {
        this.hecatre = hecatre;
        this.plantedSeeds = plantedSeeds;
        this.farm = farm;
    }
    //endregion

    //region Getter & Setter
    public Long getId() {
        return id;
    }

    public Integer getHecatre() {
        return hecatre;
    }

    public void setHecatre(Integer hecatre) {
        this.hecatre = hecatre;
    }

    public String getPlantedSeeds() {
        return plantedSeeds;
    }

    public void setPlantedSeeds(String plantedSeeds) {
        this.plantedSeeds = plantedSeeds;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }
    //endregion
}

package at.htl.farm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@Entity
@NamedQueries({
        @NamedQuery(name = "Field.findAll", query = "select f from Field f")
})
public class Field {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int hectare;
    private String plantedSeeds;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @XmlTransient
    protected Farm farm;

    //region Constructor
    public Field() {
    }

    public Field(int hectare, String plantedSeeds) {
        this.hectare = hectare;
        this.plantedSeeds = plantedSeeds;
    }
    //endregion

    //region Getter & Setter
    public Long getId() {
        return id;
    }

    public int getHectare() {
        return hectare;
    }

    public void setHectare(int hectare) {
        this.hectare = hectare;
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

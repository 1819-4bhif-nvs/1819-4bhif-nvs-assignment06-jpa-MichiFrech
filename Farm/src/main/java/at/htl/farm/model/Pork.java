package at.htl.farm.model;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@NamedQuery(name="Pork.findAll", query = "select p from Pork p")
public class Pork extends Animal {
    private String fieldOfUse;

    //region Constructor
    public Pork() {
    }

    public Pork(String name, int age, String fieldOfUse) {
        super(name, age);
        this.fieldOfUse = fieldOfUse;
    }
    //endregion

    //region Getter & Setter
    public String getFieldOfUse() {
        return fieldOfUse;
    }

    public void setFieldOfUse(String fieldOfUse) {
        this.fieldOfUse = fieldOfUse;
    }
    //endregion
}

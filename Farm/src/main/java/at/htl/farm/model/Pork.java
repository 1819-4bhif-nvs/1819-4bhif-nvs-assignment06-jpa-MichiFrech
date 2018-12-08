package at.htl.farm.model;

import javax.persistence.Entity;

@Entity
public class Pork extends Animal {
    private String fieldOfUse;

    //region Constructor
    public Pork() {
    }

    public Pork(String name, Farm farm, int age, String fieldOfUse) {
        super(name, farm, age);
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

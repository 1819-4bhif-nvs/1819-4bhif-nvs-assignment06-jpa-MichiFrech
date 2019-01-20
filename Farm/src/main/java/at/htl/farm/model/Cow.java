package at.htl.farm.model;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@XmlRootElement
@Entity
@NamedQuery(name="Cow.findAll", query = "select c from Cow c")
public class Cow extends Animal {
    private LocalDate lastTimeMilk;

    //region Constructor
    public Cow() {
    }

    public Cow(String name, int age, LocalDate lastTimeMilk) {
        super(name, age);
        this.lastTimeMilk = lastTimeMilk;
    }
    //endregion

    //region Getter & Setter
    public LocalDate getLastTimeMilk() {
        return lastTimeMilk;
    }

    public void setLastTimeMilk(LocalDate lastTimeMilk) {
        this.lastTimeMilk = lastTimeMilk;
    }
    //endregion
}

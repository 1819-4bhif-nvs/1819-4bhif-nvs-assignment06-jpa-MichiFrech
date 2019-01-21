package at.htl.farm.model;

import at.htl.farm.converter.XMLAdapter;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement
@Entity
@NamedQuery(name="Cow.findAll", query = "select c from Cow c")
public class Cow extends Animal {
    @XmlJavaTypeAdapter(XMLAdapter.class)
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

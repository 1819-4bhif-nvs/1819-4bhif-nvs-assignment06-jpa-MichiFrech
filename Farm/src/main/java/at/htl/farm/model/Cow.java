package at.htl.farm.model;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Cow extends Animal {
    private LocalDate lastTimeMilk;

    //region Constructor
    public Cow() {
    }

    public Cow(String name, Farm farm, int age, LocalDate lastTimeMilk) {
        super(name, farm, age);
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

package at.htl.farm.model;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@Entity
@NamedQueries({
        @NamedQuery(name = "Farm.findAll", query = "select f from Farm f"),
})
public class Farm {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;

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
    //endregion
}

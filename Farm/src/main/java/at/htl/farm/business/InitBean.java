package at.htl.farm.business;

import at.htl.farm.model.Cow;
import at.htl.farm.model.Farm;
import at.htl.farm.model.Field;
import at.htl.farm.model.Pork;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Startup
@Singleton
public class InitBean {

    @PersistenceContext
    EntityManager em;
    public InitBean() {

    }

    @PostConstruct
    private void init() {
        System.out.println("------init------");

        Farm firstFarm = new Farm("Linz");
        em.persist(firstFarm);
        Farm secondFarm = new Farm("Leonding");
        em.persist(secondFarm);

        Field field1 = new Field(4, "Weizen", firstFarm);
        em.persist(field1);
        Field field2 = new Field(3, "Roggen", firstFarm);
        em.persist(field2);
        Field field3 = new Field(5, "Gerste", secondFarm);
        em.persist(field3);
        Field field4 = new Field(2, "Hirse", secondFarm);
        em.persist(field4);
        Field field5 = new Field(3, "Dinkel", secondFarm);
        em.persist(field5);

        Cow cow1 = new Cow("Peter", firstFarm, 4, LocalDate.of(2018, 11, 22));
        em.persist(cow1);
        Cow cow2 = new Cow("Hans", firstFarm, 5, LocalDate.of(2018, 11, 18));
        em.persist(cow2);
        Cow cow3 = new Cow("Max", firstFarm, 2, LocalDate.of(2018, 11, 19));
        em.persist(cow3);
        Cow cow4 = new Cow("Franz", firstFarm, 6, LocalDate.of(2018, 11, 20));
        em.persist(cow4);
        Cow cow5 = new Cow("Sepp", firstFarm, 3, LocalDate.of(2018, 11, 26));
        em.persist(cow5);

        Pork pork1 = new Pork("Manuel", firstFarm, 3, "schlachten");
        em.persist(pork1);
        Pork pork2 = new Pork("Walter", firstFarm, 1, "züchten");
        em.persist(pork2);
        Pork pork3 = new Pork("Herbert", firstFarm, 2, "züchten");
        em.persist(pork3);

        Cow cow6 = new Cow("Oskar", secondFarm, 4, LocalDate.of(2018, 12, 1));
        em.persist(cow6);
        Cow cow7 = new Cow("Ben", secondFarm, 2, LocalDate.of(2018, 11, 29));
        em.persist(cow7);
        Cow cow8 = new Cow("Leon", secondFarm, 6, LocalDate.of(2018, 12, 3));
        em.persist(cow8);

        Pork pork4 = new Pork("Marco", secondFarm, 1, "züchten");
        em.persist(pork4);
        Pork pork5 = new Pork("Markus", secondFarm, 2, "züchten");
        em.persist(pork5);
        Pork pork6 = new Pork("Fabian", secondFarm, 5, "schlachten");
        em.persist(pork6);
        Pork pork7 = new Pork("Gerhard", secondFarm, 2, "züchten");
        em.persist(pork7);
    }

}

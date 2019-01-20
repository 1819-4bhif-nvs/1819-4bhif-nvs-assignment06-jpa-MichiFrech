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

        Field field1 = new Field(4, "Weizen");
        em.persist(field1);
        Field field2 = new Field(3, "Roggen");
        em.persist(field2);
        Field field3 = new Field(5, "Gerste");
        em.persist(field3);
        Field field4 = new Field(2, "Hirse");
        em.persist(field4);
        Field field5 = new Field(3, "Dinkel");
        em.persist(field5);

        Cow cow1 = new Cow("Peter", 4, LocalDate.of(2018, 11, 22));
        em.persist(cow1);
        Cow cow2 = new Cow("Hans", 5, LocalDate.of(2018, 11, 18));
        em.persist(cow2);
        Cow cow3 = new Cow("Max", 2, LocalDate.of(2018, 11, 19));
        em.persist(cow3);
        Cow cow4 = new Cow("Franz", 6, LocalDate.of(2018, 11, 20));
        em.persist(cow4);
        Cow cow5 = new Cow("Sepp", 3, LocalDate.of(2018, 11, 26));
        em.persist(cow5);

        Pork pork1 = new Pork("Manuel", 3, "schlachten");
        em.persist(pork1);
        Pork pork2 = new Pork("Walter", 1, "züchten");
        em.persist(pork2);
        Pork pork3 = new Pork("Herbert", 2, "züchten");
        em.persist(pork3);

        Cow cow6 = new Cow("Oskar", 4, LocalDate.of(2018, 12, 1));
        em.persist(cow6);
        Cow cow7 = new Cow("Ben", 2, LocalDate.of(2018, 11, 29));
        em.persist(cow7);
        Cow cow8 = new Cow("Leon", 6, LocalDate.of(2018, 12, 3));
        em.persist(cow8);

        Pork pork4 = new Pork("Marco", 1, "züchten");
        em.persist(pork4);
        Pork pork5 = new Pork("Markus", 2, "züchten");
        em.persist(pork5);
        Pork pork6 = new Pork("Fabian", 5, "schlachten");
        em.persist(pork6);
        Pork pork7 = new Pork("Gerhard", 2, "züchten");
        em.persist(pork7);

        Farm firstFarm = new Farm("Linz");
        firstFarm.addField(field1);
        firstFarm.addField(field2);

        firstFarm.addAnimal(cow1);
        firstFarm.addAnimal(cow2);
        firstFarm.addAnimal(cow3);
        firstFarm.addAnimal(cow4);
        firstFarm.addAnimal(cow5);

        firstFarm.addAnimal(pork1);
        firstFarm.addAnimal(pork2);
        firstFarm.addAnimal(pork3);
        em.persist(firstFarm);

        Farm secondFarm = new Farm("Leonding");
        secondFarm.addField(field3);
        secondFarm.addField(field4);
        secondFarm.addField(field5);

        secondFarm.addAnimal(cow6);
        secondFarm.addAnimal(cow7);
        secondFarm.addAnimal(cow8);

        secondFarm.addAnimal(pork4);
        secondFarm.addAnimal(pork5);
        secondFarm.addAnimal(pork6);
        secondFarm.addAnimal(pork7);
        em.persist(secondFarm);
    }

}

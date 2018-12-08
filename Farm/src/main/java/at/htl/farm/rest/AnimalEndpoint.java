package at.htl.farm.rest;

import at.htl.farm.model.Animal;
import at.htl.farm.model.Cow;
import at.htl.farm.model.Pork;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("animal")
@Stateless
public class AnimalEndpoint {

    @PersistenceContext
    EntityManager em;

    @Path("findall")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Animal> findAll(){
        return em.createNamedQuery("Animal.findAll", Animal.class).getResultList();
    }

    @Path("findById/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Animal findById(@PathParam("id") long id){
        return em.createNamedQuery("Animal.findById", Animal.class).setParameter(1, id).getSingleResult();
    }

    @Path("findByName/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Animal findByName(@PathParam("name") String name){
        return em.createNamedQuery("Animal.findByName", Animal.class).setParameter(1, name).getSingleResult();
    }

    @Path("delete/{id}")
    @DELETE
    @Transactional
    public void delete(@PathParam("id") long id){
        Animal a = em.find(Animal.class, id);
        em.remove(a);
    }

    @Path("put/{id}")
    @PUT
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void put(@PathParam("id") long id, Animal animal){
        Animal a = em.find(Animal.class, id);
        a.setAge(animal.getAge());
        a.setFarm(animal.getFarm());
        a.setName(animal.getName());
        em.merge(a);
    }

    @Path("postcow")
    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Long post(Cow cow){
        em.persist(cow);
        em.flush();
        return cow.getId();
    }

    @Path("postpork")
    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Long post(Pork pork){
        em.persist(pork);
        em.flush();
        return pork.getId();
    }

}

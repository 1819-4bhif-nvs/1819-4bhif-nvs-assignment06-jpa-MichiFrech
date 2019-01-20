package at.htl.farm.rest;

import at.htl.farm.model.Animal;
import at.htl.farm.model.Cow;
import at.htl.farm.model.Pork;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("animal")
@Stateless
public class AnimalEndpoint {

    @PersistenceContext
    EntityManager em;

    @Path("findall")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findAll() {
        List<Animal> animalList = em.createNamedQuery("Animal.findAll", Animal.class).getResultList();
        GenericEntity entity = new GenericEntity<List<Animal>>(animalList){};

        return Response.ok(entity).build();
    }

    @Path("findall/cow")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllCow() {
        List<Cow> cowList = em.createNamedQuery("Cow.findAll", Cow.class).getResultList();

        return Response.ok(cowList).build();
    }

    @Path("find/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") long id) {
        Animal a = em.find(Animal.class, id);

        if (a != null) {
            return Response.ok(a).build();
        } else {
            return Response.noContent().build();
        }
    }

    @Path("delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") long id) {
        try {
            Animal a = em.find(Animal.class, id);
            if (a != null) {
                //a.getFarm().removeAnimal(a);
                em.remove(a);
            }
        } catch (Exception ex) {
            //System.err.println(ex.getMessage());
            return Response.status(404).build();
        }

        return Response.ok().build();
    }

    @Path("new/cow")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newCow(Cow cow) {
        em.persist(cow);
        em.flush();
        return Response.created(URI.create("http://localhost:8080/farm/rs/animal/find/" + cow.getId())).build();
    }

    @Path("new/pork")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newPork(Pork pork){
        em.persist(pork);
        em.flush();
        return Response.created(URI.create("http://localhost:8080/farm/rs/animal/find/" + pork.getId())).build();
    }

    @Path("update/pork/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long id, Pork p){
        Pork pork = em.find(Pork.class, id);
        pork.setAge(p.getAge());
        pork.setFarm(p.getFarm());
        pork.setName(p.getName());
        pork.setFieldOfUse(p.getFieldOfUse());
        em.merge(pork);
        return Response.ok().build();
    }
}

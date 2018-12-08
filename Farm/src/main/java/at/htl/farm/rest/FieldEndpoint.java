package at.htl.farm.rest;

import at.htl.farm.model.Field;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("field")
@Stateless
public class FieldEndpoint {

    @PersistenceContext
    EntityManager em;

    @Path("findall")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Field> findAll(){
        return em.createNamedQuery("Field.findAll", Field.class).getResultList();
    }

    @Path("find/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Field findById(@PathParam("id") long id){
        return em.createNamedQuery("Field.findById", Field.class).setParameter(1, id).getSingleResult();
    }

    @Path("find/{seed}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Field findBySeed(@PathParam("seed") String seed){
        return em.createNamedQuery("Field.findBySeed", Field.class).setParameter(1, seed).getSingleResult();
    }

    @Path("delete/{id}")
    @DELETE
    @Transactional
    public void delete(@PathParam("id") long id){
        Field f = em.find(Field.class, id);
        em.remove(f);
    }

    @Path("put/{id}")
    @PUT
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void put(@PathParam("id") long id, Field field){
        Field f = em.find(Field.class, id);
        f.setFarm(field.getFarm());
        f.setHecatre(field.getHecatre());
        f.setPlantedSeeds(field.getPlantedSeeds());
        em.merge(f);
    }

    @Path("post")
    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Long post(Field field){
        em.persist(field);
        em.flush();
        return field.getId();
    }
}

package at.htl.farm.rest;

import at.htl.farm.model.Farm;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("farm")
@Stateless
public class FarmEndpoint {

    @PersistenceContext
    EntityManager em;

    @Path("findall")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Farm> findAll(){
        return em.createNamedQuery("Farm.findAll", Farm.class).getResultList();
    }

    @Path("delete/{id}")
    @DELETE
    @Transactional
    public void delete(@PathParam("id") long id){
        Farm f = em.find(Farm.class, id);
        em.remove(f);
    }

    @Path("put/{id}")
    @PUT
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void put(@PathParam("id") long id, Farm farm){
        Farm f = em.find(Farm.class, id);
        f.setLocation(farm.getLocation());
        em.merge(f);
    }

    @Path("post")
    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Long post(Farm farm){
        em.persist(farm);
        em.flush();
        return farm.getId();
    }

}

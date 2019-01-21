package at.htl.farm.rest;

import at.htl.farm.model.Animal;
import at.htl.farm.model.Farm;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("farm")
@Stateless
public class FarmEndpoint {

    @PersistenceContext
    EntityManager em;

    @Path("findall")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findAll() {
        List<Farm> farmList = em.createNamedQuery("Farm.findAll", Farm.class).getResultList();
        GenericEntity entity = new GenericEntity<List<Farm>>(farmList){};

        if(farmList != null && !farmList.isEmpty())
            return Response.ok(entity).build();
        else
            return Response.noContent().build();
    }

    @Path("find/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response find(@PathParam("id") long id) {
        Farm farm = em.createNamedQuery("Farm.findById", Farm.class).setParameter(1, id).getSingleResult();

        if(farm != null) {
            return Response.ok(farm).build();
        } else {
            return Response.noContent().build();
        }
    }

    @Path("delete/{id}")
    @DELETE
    public Response delete(@PathParam("id") long id) {
        try{
            Farm f = em.find(Farm.class, id);
            if(f != null){
                em.remove(f);
            }
        }catch (Exception e){
            return Response.status(404).build();
        }
        return Response.ok().build();
    }

}

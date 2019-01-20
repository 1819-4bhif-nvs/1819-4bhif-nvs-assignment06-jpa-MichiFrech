package at.htl.farm.rest;

import at.htl.farm.model.Field;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("field")
@Stateless
public class FieldEndpoint {

    @PersistenceContext
    EntityManager em;

    @Path("findall")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findAll() {
        List<Field> fieldList = em.createNamedQuery("Field.findAll", Field.class).getResultList();
        GenericEntity entity = new GenericEntity<List<Field>>(fieldList){};

        if (fieldList != null && !fieldList.isEmpty()) {
            return Response.ok(entity).build();
        } else {
            return Response.noContent().build();
        }
    }

    @Path("find/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findById(@PathParam("id") long id) {
        Field f = em.find(Field.class, id);

        if (f != null) {
            return Response.ok(f).build();
        } else {
            return Response.noContent().build();
        }
    }

    @Path("delete/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") long id) {
        try {
            Field f = em.find(Field.class, id);
            if (f != null) {
                //f.getFarm().removeField(f);
                em.remove(f);
            }
        } catch (Exception ex) {
            return Response.status(404).build();
        }

        return Response.ok().build();
    }

    @Path("new")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newField(Field f) {
        em.persist(f);
        em.flush();
        return Response.created(URI.create("http://localhost:8080/farm/rs/field/find/" + f.getId())).build();
    }

    @Path("update/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long id, Field f){
        Field field = em.find(Field.class, id);
        field.setFarm(f.getFarm());
        field.setHectare(f.getHectare());
        field.setPlantedSeeds(f.getPlantedSeeds());
        em.merge(field);
        return Response.ok().build();
    }
}

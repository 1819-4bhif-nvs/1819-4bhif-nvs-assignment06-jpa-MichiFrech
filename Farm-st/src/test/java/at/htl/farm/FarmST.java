package at.htl.farm;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FarmST {
    private static Client client;
    private static WebTarget target;


    @BeforeClass
    public static void init(){
        client = ClientBuilder.newClient();
        target = client.target("http://localhost:8085/farm/rs");
    }

    @Test
    public void test01_GetAllFarms(){
        Response response = this.target.path("/farm/findall").request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(200));
        JsonArray jsonArray = response.readEntity(JsonArray.class);
        assertThat(jsonArray.getValuesAs(JsonObject.class).size(), greaterThanOrEqualTo(2));
    }


    @Test
    public void test02_GetAnimalByName(){
        JsonObject response = target.path("/animal/findByName/Manuel").request(MediaType.APPLICATION_JSON).get(JsonObject.class);
        assertThat(response.getInt("age"), is(3));
        assertThat(response.getString("fieldOfUse"), is("schlachten"));
    }

    @Test
    public void test03_GetFieldBySeed(){
        JsonObject response = target.path("/field/findBySeed/Weizen").request(MediaType.APPLICATION_JSON).get(JsonObject.class);
        assertThat(response.getInt("hectare"), is(4));
    }

    @Test
    public void test04_CreateAnimal(){
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonObject farm = Json.createObjectBuilder().add("id", 1).add("location", "Linz").build();
        JsonObject newAnimal = builder.add("name", "Musti").add("age", 6).add("fieldOfUse", "züchten").build();
        Response response = this.target.path("animal/postpork").request().post(Entity.json(newAnimal));
        assertThat(response.getStatus(), is(200));

        long id = Long.valueOf(response.readEntity(String.class));
        JsonObject animal = this.target.path("/animal/findById/" + id).request(MediaType.APPLICATION_JSON).get().readEntity(JsonObject.class);
        assertThat(animal.getInt("age"), is(6));
    }

    @Test
    public void test05_UpdateAnimal(){
        JsonObject farm = Json.createObjectBuilder().add("id", 1).add("location", "Linz").build();
        JsonObject animal = Json.createObjectBuilder().add("name", "Ted").add("age", 2).add("farm", farm).build();

        this.target.path("/animal/put/" + 6).request().put(Entity.json(animal));
        JsonObject response = this.target.path("/animal/findById/" + 6).request(MediaType.APPLICATION_JSON).get().readEntity(JsonObject.class);
        assertThat(response.getInt("age"), is(2));
    }

    @Test
    public void test06_DeleteAnimal(){
        Response response = this.target.path("/animal/delete/" + 6).request().delete();
        assertThat(response.getStatus(), is(Response.Status.NO_CONTENT.getStatusCode()));
    }
}

package at.htl.farm;

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

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FarmST {
    private static Client client;
    private static WebTarget target;
    private static int tmpAnimalId = 0;
    private static int tmpFieldId = 0;


    @BeforeClass
    public static void init() {
        client = ClientBuilder.newClient();
        target = client.target("http://localhost:8080/farm/rs");
    }

    @Test
    public void test01_GetAllAnimals() {
        Response response = this.target.path("/animal/findall").request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(200));
        JsonArray jsonArray = response.readEntity(JsonArray.class);
        assertThat(jsonArray.getValuesAs(JsonObject.class).size(), greaterThan(1));
    }

    @Test
    public void test02_GetAnimal() {
        Response response = this.target.path("/animal/find/1").request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(200));
        JsonObject object = response.readEntity(JsonObject.class);
        assertThat(object.getInt("age"), is(4));
        assertThat(object.getString("name"), is("Peter"));
    }

    @Test
    public void test03_CreateAnimal() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonObject newAnimal = builder
                .add("name", "Alex")
                .add("age", 2)
                .add("fieldOfUse", "züchten")
                .build();
        Response response = this.target.path("/animal/new/pork").request().post(Entity.json(newAnimal));
        assertThat(response.getStatus(), is(201));
        JsonObject animal = this.client.target(response.getLocation()).request(MediaType.APPLICATION_JSON).get().readEntity(JsonObject.class);
        assertThat(animal.getInt("age"), is(2));
        assertThat(animal.getString("name"), containsString("Alex"));

        tmpAnimalId = animal.getInt("id");
    }

    @Test
    public void test04_UpdateAnimal() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonObject animal = builder
                .add("name", "Manuel")
                .add("age", 4)
                .add("fieldOfUse", "schlachten")
                .build();
        Response response = this.target.path("/animal/update/pork/6").request().put(Entity.json(animal));
        assertThat(response.getStatus(), is(200));

        JsonObject updatedAnimal = this.target.path("/animal/find/6").request(MediaType.APPLICATION_JSON).get().readEntity(JsonObject.class);
        assertThat(updatedAnimal.getInt("age"), is(4));

        animal = builder
                .add("name", "Manuel")
                .add("age", 3)
                .add("fieldOfUse", "schlachten")
                .build();
        this.target.path("/animal/update/pork/6").request().put(Entity.json(animal));
    }

    @Test
    public void test05_DeleteAnimal() {
        Response response = this.target.path("/animal/delete/" + tmpAnimalId).request().delete();
        assertThat(response.getStatus(), is(200));
        response = this.target.path("/animal/delete/" + tmpAnimalId).request().delete();
        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void test06_GetFarm() {
        Response response = target.path("/farm/find/1").request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(200));
        JsonObject respObj = response.readEntity(JsonObject.class);
        assertThat(respObj.getString("location"), is("Linz"));

        JsonArray animals = respObj.getJsonArray("animals");
        assertThat(animals.size(), greaterThan(1));
    }

    @Test
    public void test07_GetField() {
        Response response = target.path("/field/find/1").request().get();
        assertThat(response.getStatus(), is(200));
        JsonObject field = response.readEntity(JsonObject.class);
        assertThat(field.getInt("hectare"), is(4));
        assertThat(field.getString("plantedSeeds"), is("Weizen"));
    }

    @Test
    public void test08_CreateField() {
        JsonObject field = Json.createObjectBuilder()
                .add("hectare", 3)
                .add("plantedSeeds", "Weizen")
                .build();
        Response response = target.path("/field/new").request().post(Entity.json(field));
        assertThat(response.getStatus(), is(201));
        JsonObject newField = this.client.target(response.getLocation()).request(MediaType.APPLICATION_JSON).get().readEntity(JsonObject.class);
        assertThat(newField.getInt("hectare"), is(3));
        assertThat(newField.getString("plantedSeeds"), is("Weizen"));

        tmpFieldId = newField.getInt("id");
    }

    @Test
    public void test09_UpdateField() {
        JsonObject team = Json.createObjectBuilder()
                .add("hectare", 5)
                .add("plantedSeeds", "Roggen")
                .build();
        Response response = target.path("/field/update/" + tmpFieldId).request().put(Entity.json(team));
        assertThat(response.getStatus(), is(200));
        response = target.path("/field/find/" + tmpFieldId).request().get();
        assertThat(response.getStatus(), is(200));
        JsonObject updatedField = response.readEntity(JsonObject.class);
        assertThat(updatedField.getInt("hectare"), is(5));
    }

    @Test
    public void test10_DeleteField() {
        Response response = this.target.path("/field/delete/" + tmpFieldId).request().delete();
        assertThat(response.getStatus(), is(200));
        response = this.target.path("/field/delete/" + tmpFieldId).request().delete();
        assertThat(response.getStatus(), is(200));
    }
}

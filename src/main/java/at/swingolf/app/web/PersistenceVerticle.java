package at.swingolf.app.web;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.impl.ClusterSerializable;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.eventbus.EventBus;
import org.neo4j.driver.v1.*;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PersistenceVerticle extends AbstractVerticle {
    private Driver driver;

    public void start() throws Exception {
        super.start();
        driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "test"));
        System.out.println("started neo4j");

        initQueries();

    }

    private void initQueries() {
        EventBus eventBus = vertx.eventBus();

        eventBus.consumer("users").handler(message -> message.reply(queryArrayByNodeLabel("User", "firstname", "lastname", "email")));

        String queryUserAndLicense = "MATCH (node1:User) OPTIONAL MATCH (node1)-[r:HAS_LICENSE]->(node2:License) RETURN node1.firstname as firstname,node1.lastname as lastname,node1.email as email,node2.license as license LIMIT 25";
        eventBus.consumer("users-and-license").handler(message -> message.reply(queryArrayWithCypher(queryUserAndLicense, "firstname", "lastname", "email", "license")));
    }

    private ClusterSerializable queryArrayByNodeLabel(String nodeLabel, String... attributes) {
        String query = "MATCH (a:" + nodeLabel + ")  " +
                "RETURN " + Arrays.stream(attributes).map(attribute -> "a." + attribute + " AS " + attribute).collect(Collectors.joining(","));
        System.out.println(query);

        return queryArrayWithCypher(query, attributes);
    }

    private ClusterSerializable queryArrayWithCypher(String query, String... attributes) {
        System.out.println("querying neo4j");
        Session session = driver.session();

        StatementResult result = session.run(query);
        JsonArray jsonArray = new JsonArray();
        while (result.hasNext()) {
            Record record = result.next();
            JsonObject jsonObject = new JsonObject();
            Arrays.stream(attributes).forEach(attribute -> {
                Value attributeValue = record.get(attribute);
                if (!attributeValue.isNull()) {
                    jsonObject.put(attribute, attributeValue.asString());
                }
            });
            jsonArray.add(jsonObject);
            System.out.println("found object " + jsonObject);
        }

        session.close();
        return jsonArray;
    }

    @Override
    public void stop() throws Exception {
        System.out.println("stopping neo4j");
        driver.close();
        super.stop();
    }
}

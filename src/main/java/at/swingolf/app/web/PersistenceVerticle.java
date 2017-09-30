package at.swingolf.app.web;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.impl.ClusterSerializable;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.eventbus.EventBus;
import org.apache.commons.lang3.time.StopWatch;
import org.neo4j.driver.internal.value.IntegerValue;
import org.neo4j.driver.v1.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

public class PersistenceVerticle extends AbstractVerticle {
    private Driver driver;

    SimpleDateFormat parser=new SimpleDateFormat("yyyyMMdd");

    public void start() throws Exception {
        super.start();
        driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "test"));
        System.out.println("started neo4j");

        initQueries();

    }

    private void initQueries() {
        EventBus eventBus = vertx.eventBus();

        eventBus.consumer("users").handler(message -> message.reply(queryArrayByNodeLabel("User", "firstname", "lastname", "email")));
        eventBus.consumer("courses").handler(message -> message.reply(queryArrayByNodeLabel("Course", "name")));
        eventBus.consumer("clubs").handler(message -> message.reply(queryArrayByNodeLabel("Club", "name")));

        String queryUserAndLicense = "MATCH (node1:User) OPTIONAL MATCH (node1)-[r:HAS_LICENSE{}]->(node2:License) RETURN node1.firstname as firstname,node1.lastname as lastname,node1.email as email,node2.license as license LIMIT 25";
        eventBus.consumer("users-and-license").handler(message -> message.reply(queryArrayWithCypher(queryUserAndLicense, "firstname", "lastname", "email", "license")));

        String queryActiveUserAndLicense = "MATCH (node1:User)-[r:HAS_LICENSE]->(node2:License)-[s:IS_ACTIVE]->(node3:Duration) WHERE not exists(node3.to)  RETURN node1.firstname as firstname,node1.lastname as lastname,node1.email as email,node2.license as license,node3.from as from,node3.to as to LIMIT 25";
        eventBus.consumer("active-users-and-license").handler(message -> message.reply(queryArrayWithCypher(queryActiveUserAndLicense, "firstname", "lastname", "email", "license","from","to")));

        String queryTournamentsAndDate = "MATCH (node1:Tournament)-[r:HAS_DATE]->(node2:Duration) RETURN node1.name as name,node2.from as from,node2.to as to LIMIT 25";
        eventBus.consumer("tournaments-and-dates").handler(message -> message.reply(queryArrayWithCypher(queryTournamentsAndDate, "name", "from", "to")));


    }

    private ClusterSerializable queryArrayByNodeLabel(String nodeLabel, String... attributes) {
        String query = "MATCH (a:" + nodeLabel + ")  " +
                "RETURN " + Arrays.stream(attributes).map(attribute -> "a." + attribute + " AS " + attribute).collect(Collectors.joining(","));
        System.out.println(query);

        return queryArrayWithCypher(query, attributes);
    }

    private ClusterSerializable queryArrayWithCypher(String query, String... attributes) {
        StopWatch sw = new StopWatch();
        sw.start();

        System.out.println("querying neo4j with query " + query);
        Session session = driver.session();

        StatementResult result = session.run(query);
        JsonArray jsonArray = new JsonArray();
        while (result.hasNext()) {
            Record record = result.next();
            JsonObject jsonObject = new JsonObject();
            Arrays.stream(attributes).forEach(attribute -> {
                Value attributeValue = record.get(attribute);
                if (!attributeValue.isNull()) {
                    if (attributeValue instanceof IntegerValue) {
                        if (attribute.equals("from") || attribute.equals("to")) {
                            try {
                                jsonObject.put(attribute, parser.parse(new Integer(attributeValue.asInt()).toString()).toInstant());
                            } catch (ParseException e) {
                                throw new RuntimeException(e.getMessage(),e);
                            }
                        } else {
                            jsonObject.put(attribute, attributeValue.asInt());
                        }
                    } else {
                        jsonObject.put(attribute, attributeValue.asString());
                    }
                }
            });
            jsonArray.add(jsonObject);
            System.out.println("found object " + jsonObject);
        }

        System.out.println("query executed in "+sw.getTime()+" ms.");
        if (sw.getTime() > 500) {
            System.out.println("FATAL query took longer than 500ms");
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

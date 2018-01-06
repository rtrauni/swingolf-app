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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PersistenceVerticle extends AbstractVerticle {
    private Driver driver;

    SimpleDateFormat parser=new SimpleDateFormat("yyyyMMdd");

    public void start() throws Exception {
        super.start();
        driver = GraphDatabase.driver(System.getProperty("bolt","bolt://localhost:7687"), AuthTokens.basic(System.getProperty("bolt-user","neo4j"), System.getProperty("bolt-pass","test")));
        System.out.println("started neo4j");

        initQueries();

    }

    private void initQueries() {
        EventBus eventBus = vertx.eventBus();

        eventBus.consumer("users").handler(message -> message.reply(queryArrayByNodeLabel("User", "firstname", "lastname", "email")));
        eventBus.consumer("courses").handler(message -> message.reply(queryArrayByNodeLabel("Course", "name")));
        eventBus.consumer("clubs").handler(message -> message.reply(queryArrayByNodeLabel("Club", "name")));

        String queryUserByLicense = "MATCH (node1:User) MATCH (node1)-[r:HAS_LICENSE{}]->(node2:License {license:\"param1\"}) RETURN node1.firstname as firstname,node1.lastname as lastname,node1.email as email,node2.license as license ORDER BY license ASC LIMIT 10000";
        eventBus.consumer("getDetailsForUser").handler(message -> message.reply(queryArrayWithCypher(queryUserByLicense.replaceAll("param1",message.body().toString()) , "firstname", "lastname", "email", "license")));

        String queryUserAndLicense = "MATCH (node1:User) OPTIONAL MATCH (node1)-[r:HAS_LICENSE{}]->(node2:License) RETURN node1.firstname as firstname,node1.lastname as lastname,node1.email as email,node2.license as license LIMIT 1000";
        eventBus.consumer("users-and-license").handler(message -> message.reply(queryArrayWithCypher(queryUserAndLicense, "firstname", "lastname", "email", "license")));

        String queryActiveUserAndLicense = "MATCH (node1:User)-[r:HAS_LICENSE]->(node2:License)-[s:IS_ACTIVE]->(node3:Duration) WHERE not exists(node3.to)  RETURN node1.firstname as firstname,node1.lastname as lastname,node1.email as email,node2.license as license,node3.from as from,node3.to as to ORDER BY license ASC LIMIT 10000";
        eventBus.consumer("active-users-and-license").handler(message -> message.reply(queryArrayWithCypher(queryActiveUserAndLicense, "firstname", "lastname", "email", "license","from","to")));

        String queryTournamentsAndDate = "MATCH (node1:Tournament)-[r:HAS_DATE]->(node2:Duration) RETURN node1.name as name,node2.from as from,node2.to as to,ID(node1) as id LIMIT 1000";
        eventBus.consumer("tournaments-and-dates").handler(message -> message.reply(queryArrayWithCypher(queryTournamentsAndDate, "name", "from", "to", "id")));


        String tournamentByTournamentId = "MATCH (node1:Tournament) WHERE ID(node1)=param1 RETURN node1.name as name LIMIT 1000";
        eventBus.consumer("getDetailsForTournament").handler(message -> message.reply(queryArrayWithCypher(tournamentByTournamentId.replaceAll("param1",message.body().toString()) , "name")));

        eventBus.consumer("score-sorted").handler(message -> message.reply(scoreSorted(message.body().toString())));

        eventBus.consumer("score-sorted-count").handler(message -> message.reply(scoreSortedCount(message.body().toString())));

    }

    private ClusterSerializable scoreSorted(String tournamentId) {
        Map<Integer, List<Integer>> group = queryIntegerListMap(tournamentId);
        JsonArray jsonArray = new JsonArray(new LinkedList(group.keySet()));
        return jsonArray;
    }
    private ClusterSerializable scoreSortedCount(String tournamentId) {
        Map<Integer, List<Integer>> group = queryIntegerListMap(tournamentId);
        JsonArray jsonArray = new JsonArray();
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("data", new JsonArray(group.values().stream().map(integers -> integers.size()).collect(Collectors.toList())));
        jsonObject.put("label", "Punkte");
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    private Map<Integer, List<Integer>> queryIntegerListMap(String tournamentId) {
        String queryScores = "MATCH (node1:Tournament)-[:HAS_GAME]->(node2:Game),(node3:Score)-[:WAS_PLAYED_IN_GAME]->(node2) WHERE ID(node1)="+tournamentId+" RETURN node3.score as score LIMIT 10000";
        List<Integer> scores = queryScores(queryScores);
        return scores.stream().collect(Collectors.groupingBy(o -> o));
    }

    private List<Integer> queryScores(String query) {
        List<Integer> scores = new LinkedList<>();
        Session session = driver.session();
        StatementResult result = session.run(query);
        while (result.hasNext()) {
            Record record = result.next();
            int score = Integer.parseInt(record.get("score").asString().replaceAll("\"",""));
            System.out.println(score);
            scores.add(score);
        }
        return scores;
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

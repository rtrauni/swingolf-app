package at.swingolf.app.web;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.impl.ClusterSerializable;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.eventbus.EventBus;
import io.vertx.rxjava.core.eventbus.Message;
import io.vertx.rxjava.core.http.HttpClient;
import org.apache.commons.lang3.time.StopWatch;
import org.neo4j.driver.internal.value.IntegerValue;
import org.neo4j.driver.v1.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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

        eventBus.consumer("users").handler(message -> consumeRest("http://localhost:8079/users",message));
        eventBus.consumer("tournaments-previous3").handler(message -> consumeRest("http://localhost:8079/tournaments-previous3",message));
        eventBus.consumer("tournaments-previous3-by-user").handler(message -> consumeRest("http://localhost:8079/tournaments-previous3-by-player?license="+message.body().toString(),message));
        eventBus.consumer("tournaments-next3").handler(message -> consumeRest("http://localhost:8079/tournaments-next3",message));
        eventBus.consumer("users-by-tournament").handler(message -> consumeRest("http://localhost:8079/usersByTournament?tournamentId="+message.body().toString(),message));
//        eventBus.consumer("users").handler(message -> message.reply(queryArrayByNodeLabel("User", "firstname", "lastname", "email")));
//        eventBus.consumer("courses").handler(message -> message.reply(queryArrayByNodeLabel("Course", "name")));
        eventBus.consumer("courses").handler(message -> message.reply(queryCourses()));
//        eventBus.consumer("clubs").handler(message -> message.reply(queryArrayByNodeLabel("Club", "name")));
        eventBus.consumer("clubs").handler(message -> message.reply(queryClubs()));

        String queryUserByLicense = "MATCH (node1:User) MATCH (node1)-[r:HAS_LICENSE{}]->(node2:License {license:\"param1\"}) RETURN node1.firstname as firstname,node1.lastname as lastname,node1.email as email,node2.license as license ORDER BY license ASC LIMIT 10000";
        eventBus.consumer("getDetailsForUser").handler(message -> message.reply(queryArrayWithCypher(queryUserByLicense.replaceAll("param1",message.body().toString()) , "firstname", "lastname", "email", "license")));

        String queryUserAndLicense = "MATCH (node1:User) OPTIONAL MATCH (node1)-[r:HAS_LICENSE{}]->(node2:License) RETURN node1.firstname as firstname,node1.lastname as lastname,node1.email as email,node2.license as license LIMIT 1000";
//        eventBus.consumer("users-and-license").handler(message -> message.reply(queryArrayWithCypher(queryUserAndLicense, "firstname", "lastname", "email", "license")));
        eventBus.consumer("users-and-license").handler(message -> consumeRest("http://localhost:8079/users-and-license",message));

        String queryActiveUserAndLicense = "MATCH (node1:User)-[r:HAS_LICENSE]->(node2:License)-[s:IS_ACTIVE]->(node3:Duration) WHERE not exists(node3.to)  RETURN node1.firstname as firstname,node1.lastname as lastname,node1.email as email,node2.license as license,node3.from as from,node3.to as to ORDER BY license ASC LIMIT 10000";
        eventBus.consumer("active-users-and-license").handler(message -> message.reply(queryArrayWithCypher(queryActiveUserAndLicense, "firstname", "lastname", "email", "license","from","to")));

        String queryTournamentsAndDate = "MATCH (node1:Tournament)-[r:HAS_DATE]->(node2:Duration) RETURN node1.name as name,node2.from as from,node2.to as to,ID(node1) as id LIMIT 1000";
        eventBus.consumer("tournaments-and-dates").handler(message -> message.reply(queryArrayWithCypher(queryTournamentsAndDate, "name", "from", "to", "id")));

        String tournamentByTournamentId = "MATCH (node1:Tournament)-[r:HAS_DATE]->(node2:Duration) WHERE ID(node1)=param1 RETURN node1.name as name,node2.from as from,node2.to as to,ID(node1) as id  LIMIT 1000";
        eventBus.consumer("getDetailsForTournament").handler(message -> message.reply(queryArrayWithCypher(tournamentByTournamentId.replaceAll("param1",message.body().toString()) , "name","from")));

        String playersByTournamentId = "MATCH (node1:Tournament)-[r:HAS_DATE]->(node2:Duration) WHERE ID(node1)=param1 RETURN node1.name as name,node2.from as from,node2.to as to,ID(node1) as id  LIMIT 1000";
        eventBus.consumer("getDetailsForTournament").handler(message -> message.reply(queryArrayWithCypher(tournamentByTournamentId.replaceAll("param1",message.body().toString()) , "name","from")));

        eventBus.consumer("getPlayerCountForTournament").handler(message -> message.reply(queryPlayerCount(message.body().toString())));

        eventBus.consumer("score-sorted").handler(message -> message.reply(scoreSorted(message.body().toString())));

        eventBus.consumer("score-sorted-count").handler(message -> message.reply(scoreSortedCount(message.body().toString())));

    }

    private String consumeRest(String s, Message<Object> message) {
        System.out.println("consuming "+s);
        final HttpClient httpClient = vertx.createHttpClient();

        final String url = s;
        httpClient.getAbs(url, response -> {
            if (response.statusCode() != 200) {
                System.err.println("fail");
            } else {

                response.bodyHandler(b -> message.reply(new JsonArray(b.toString())));
            }
        }).end();
      return s;
    };

    private ClusterSerializable queryCourses() {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(new JsonObject(Collections.singletonMap("name","1. SGC Essen 2010")));
        jsonArray.add(new JsonObject(Collections.singletonMap("name","1. SGC Westenholz")));
        jsonArray.add(new JsonObject(Collections.singletonMap("name","SGC Allgäu-Bodensee")));
        jsonArray.add(new JsonObject(Collections.singletonMap("name","SGC Alling")));
        jsonArray.add(new JsonObject(Collections.singletonMap("name","SGC Brohltal")));
        jsonArray.add(new JsonObject(Collections.singletonMap("name","SGC Linz")));
        jsonArray.add(new JsonObject(Collections.singletonMap("name","SGC Harz")));
        jsonArray.add(new JsonObject(Collections.singletonMap("name","SGC Horbach 08")));
        jsonArray.add(new JsonObject(Collections.singletonMap("name","SGC Iserloy")));
        jsonArray.add(new JsonObject(Collections.singletonMap("name","SGC Paulushofen")));
        jsonArray.add(new JsonObject(Collections.singletonMap("name","SGG Schwansen")));
        jsonArray.add(new JsonObject(Collections.singletonMap("name","SGV Renningen")));
        jsonArray.add(new JsonObject(Collections.singletonMap("name","SGSHG Flensburg")));
        return jsonArray;
    }

    private ClusterSerializable queryClubs() {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(new JsonObject(Collections.singletonMap("name","1. SGC Essen 2010")));
        jsonArray.add(new JsonObject(Collections.singletonMap("name","1. SGC Westenholz")));
        jsonArray.add(new JsonObject(Collections.singletonMap("name","SGC Allgäu-Bodensee")));
        jsonArray.add(new JsonObject(Collections.singletonMap("name","SGC Alling")));
        jsonArray.add(new JsonObject(Collections.singletonMap("name","SGC Brohltal")));
        jsonArray.add(new JsonObject(Collections.singletonMap("name","SGC Linz")));
        jsonArray.add(new JsonObject(Collections.singletonMap("name","SGC Harz")));
        jsonArray.add(new JsonObject(Collections.singletonMap("name","SGC Horbach 08")));
        jsonArray.add(new JsonObject(Collections.singletonMap("name","SGC Iserloy")));
        jsonArray.add(new JsonObject(Collections.singletonMap("name","SGC Paulushofen")));
        jsonArray.add(new JsonObject(Collections.singletonMap("name","SGG Schwansen")));
        jsonArray.add(new JsonObject(Collections.singletonMap("name","SGV Renningen")));
        jsonArray.add(new JsonObject(Collections.singletonMap("name","SGSHG Flensburg")));
        return jsonArray;
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

    private Integer queryPlayerCount(String tournamentId) {
        String queryScores = "MATCH (node1:Tournament)-[:HAS_GAME]->(node2:Game),(node3:Score)-[:WAS_PLAYED_IN_GAME]->(node2) WHERE ID(node1)="+tournamentId+" RETURN count(node3.score) as score LIMIT 10000";
        Session session = driver.session();
        StatementResult result = session.run(queryScores);
        return result.next().get("score").asInt() / 18;
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

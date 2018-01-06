package at.swingolf.app.web;

import io.vertx.rxjava.core.parsetools.RecordParser;
import io.vertx.rx.java.ObservableFuture;
import io.vertx.rx.java.ObservableHandler;
import io.vertx.rx.java.RxHelper;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.buffer.Buffer;
import io.vertx.rxjava.core.eventbus.EventBus;
import one.util.streamex.StreamEx;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.GraphDatabase;
import rx.Observer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ImportVerticle extends AbstractVerticle {
    public void start() throws Exception {
        super.start();
        initEventbusAddresses();
    }

    private void initEventbusAddresses() {
        EventBus eventBus = vertx.eventBus();

//        eventBus.consumer("import").handler(message -> reimportAndApplyData());
//        reimportAndApplyData();
    }

    private void reimportAndApplyData() {
        System.out.println("reading");
        ObservableHandler<Buffer> observable = RxHelper.observableHandler();
        ImportRegistry importRegistry = new ImportRegistry();

        vertx.fileSystem().rxReadFile("import/year2017/players.csv").subscribe(new ImportFileReader(importRegistry,2017));

    }

    private static class ImportFileReader implements Observer<Buffer> {
        private final ImportRegistry importRegistry;
        private final int year;

        public ImportFileReader(ImportRegistry importRegistry, int year) {
            this.importRegistry=importRegistry;
            this.year=year;
        }

        @Override
        public void onCompleted() {
            System.out.println("finished");
        }

        @Override
        public void onError(Throwable throwable) {
            throwable.printStackTrace();
        }

        @Override
        public void onNext(Buffer buffer) {
            String[] players = buffer.toString().split("\n");
            List<String> entries = StreamEx.of(players).skip(7).collect(Collectors.toList());
            List<String> joined = new LinkedList<>();
            for(int i = 0; i < entries.size(); i += 2) {
                joined.add(entries.get(i)+","+entries.get(i+1));
            }
            new Player2Neo4J(joined,year,importRegistry).toNeo4J();
            importRegistry.getPersons().stream().forEach(person->System.out.println(person.toNeo4j()));
            importRegistry.getTournaments().stream().forEach(tournament->System.out.println(tournament.toNeo4j()));

        }
    }
}

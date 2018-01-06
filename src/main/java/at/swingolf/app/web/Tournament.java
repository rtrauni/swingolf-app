package at.swingolf.app.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Tournament {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private final Calendar date;
    private final Location location;

    public Tournament(Calendar date, Location location) {
        this.date=date;
        this.location=location;
    }

    public Calendar getDate() {
        return date;
    }

    public Location getLocation() {
        return location;
    }

    public String toNeo4j() {
        String dateString = sdf.format(date.getTime());
        String key = dateString + location.toString();

        StringBuffer sb =new StringBuffer("CREATE (tournament"+key+":Tournament {name: 'Südliga Linz'})\n" +
                "CREATE (duration"+key+":Duration {from: "+sdf.format(getDate().getTime())+", to: "+sdf.format(getDate().getTime())+"})\n" +
                "CREATE (tournament"+key+")-[:HAS_DATE]->(duration"+key+")\n");
        return sb.toString();
    }
}

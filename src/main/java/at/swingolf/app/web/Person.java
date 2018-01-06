package at.swingolf.app.web;

import java.util.HashMap;
import java.util.Map;

public class Person {
    private final String id;
    Map<Integer, Club> clubPerYear = new HashMap<>();
    private String name;
    private String firstname;

    public Person(String personId) {
        this.id=personId;
    }

    public void addClubForYear(Club c, int year) {
        clubPerYear.put(year,c);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFirstname() {
        return firstname;
    }

    public String toNeo4j() {
        String personId = getId().replace("-", "");
        StringBuffer sb =new StringBuffer( "CREATE (user"+ personId +":User {firstname:'"+getFirstname()+"', lastname:'"+getName()+"'})\n");
        sb.append("CREATE (license"+personId+":License {license:'"+getId()+"'})\n");
        sb.append("CREATE (duration2017toNow"+personId+":Duration {from: 201700101, to: null})\n");
        sb.append("CREATE (license"+personId+")-[:IS_ACTIVE]->(duration2017toNow"+personId+")\n");
        sb.append("CREATE (user"+personId+")-[:HAS_LICENSE]->(license"+personId+")\n");
        return sb.toString();
    }
}

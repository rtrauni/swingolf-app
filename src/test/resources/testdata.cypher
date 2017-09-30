MATCH (n) DETACH DELETE n
CREATE (andrea:User {firstname:'Andrea', lastname:'Traunmüller', email:'andrea.traunmueller@xyz.at'})
CREATE (rudolf:User {firstname:'Rudolf', lastname:'Traunmüller', email:'rudolf.traunmueller@xyz.at'})
CREATE (wolfgang:User {firstname:'Wolfgang', lastname:'Traunmüller', email:'wolfgang.traunmueller@xyz.at'})

CREATE (rudolfLicense:License {license:'007-0021'})

CREATE (rudolf)-[:HAS_LICENSE]->(rudolfLicense)
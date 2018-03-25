MATCH (n) DETACH DELETE n
// ################################ create users ################################
CREATE (andrea:User {firstname:'Andrea', lastname:'Traunmüller', email:'andrea.traunmueller@xyz.at', handicap:'5.4', category:'SF1'})
CREATE (rudolf:User {firstname:'Rudolf', lastname:'Traunmüller', email:'rudolf.traunmueller@xyz.at', handicap:'5.4', category:'SH1'})
CREATE (wolfgang:User {firstname:'Wolfgang', lastname:'Traunmüller', email:'wolfgang.traunmueller@xyz.at', handicap:'5.4', category:'SH1'})
CREATE (inactiveiay²³k  ulp b#ll.< User {firstname:'Inactive User', lastname:'Inactive User', email:'inactive@xyz.at', handicap:'5.4', category:'SH1'})

// ################################ assign licenses ################################
CREATE (andreaLicense:License {license:'007-0001'})
CREATE (duration20100102tonow:Duration {from: 20100102, to: null})
CREATE (andreaLicense)-[:IS_ACTIVE]->(duration20100102tonow)

CREATE (rudolfLicense:License {license:'007-0021'})
CREATE (duration20100103tonow:Duration {from: 20100103, to: null})
CREATE (rudolfLicense)-[:IS_ACTIVE]->(duration20100103tonow)

CREATE (wolfgangLicense:License {license:'007-0002'})
CREATE (duration20100104tonow:Duration {from: 20100104, to: null})
CREATE (wolfgangLicense)-[:IS_ACTIVE]->(duration20100104tonow)

CREATE (inactiveLicense:License {license:'007-0003'})
CREATE (duration20100105toduration20101105:Duration {from: 20100105, to: 20101105})
CREATE (inactiveLicense)-[:IS_ACTIVE]->(duration20100105toduration20101105)

CREATE (andrea)-[:HAS_LICENSE]->(andreaLicense)
CREATE (rudolf)-[:HAS_LICENSE]->(rudolfLicense)
CREATE (wolfgang)-[:HAS_LICENSE]->(wolfgangLicense)
CREATE (inactiveuser)-[:HAS_LICENSE]->(inactiveLicense)

// ################################ create clubs ################################
CREATE (sgclinz:Club {name:'SGC Linz'})
CREATE (duration20130101tonow:Duration {from: 20130101, to: null})
CREATE (sgclinz)-[:IS_ACTIVE]->(duration20130101tonow)

CREATE (inactiveclub:Club {name:'Inactive Club'})
CREATE (duration20121101to20121102:Duration {from: 20121101, to: 20121102})
CREATE (inactiveclub)-[:IS_ACTIVE]->(duration20121101to20121102)

// ################################ create courses ################################
CREATE (swingolflinz:Course {name:'Swingolf Linz'})
CREATE (duration20110101tonow:Duration {from: 20110101, to: null})
CREATE (swingolflinz)-[:IS_ACTIVE]->(duration20100101tonow)
CREATE (inactivecourse:Course {name:'Inactive Course'})
CREATE (duration20110101to20110102:Duration {from: 20110101, to: 20110102})
CREATE (inactivecourse)-[:IS_ACTIVE]->(duration20110101to20110102)

// ################################ create holes ################################
CREATE (hole1:Hole {number:1, par:3})
CREATE (duration20120101tonow:Duration {from: 20120101, to: null})
CREATE (hole1)-[:IS_ACTIVE]->(duration20120101tonow)
CREATE (hole2:Hole {number:2, par:4})
CREATE (duration20120102tonow:Duration {from: 20120102, to: null})
CREATE (hole2)-[:IS_ACTIVE]->(duration20120102tonow)
CREATE (hole3:Hole {number:3, par:5})
CREATE (duration20120103tonow:Duration {from: 20120103, to: null})
CREATE (hole3)-[:IS_ACTIVE]->(duration20120103tonow)
CREATE (hole4:Hole {number:4, par:3})
CREATE (duration20120104tonow:Duration {from: 20120104, to: null})
CREATE (hole4)-[:IS_ACTIVE]->(duration20120104tonow)
CREATE (hole5:Hole {number:5, par:3})
CREATE (duration20120105tonow:Duration {from: 20120105, to: null})
CREATE (hole5)-[:IS_ACTIVE]->(duration20120105tonow)
CREATE (hole6:Hole {number:6, par:3})
CREATE (duration20120106tonow:Duration {from: 20120106, to: null})
CREATE (hole6)-[:IS_ACTIVE]->(duration20120106tonow)
CREATE (hole7:Hole {number:7, par:4})
CREATE (duration20120107tonow:Duration {from: 20120107, to: null})
CREATE (hole7)-[:IS_ACTIVE]->(duration20120107tonow)
CREATE (hole8:Hole {number:8, par:4})
CREATE (duration20120108tonow:Duration {from: 20120108, to: null})
CREATE (hole8)-[:IS_ACTIVE]->(duration20120108tonow)
CREATE (hole9:Hole {number:9, par:5})
CREATE (duration20120109tonow:Duration {from: 20120109, to: null})
CREATE (hole9)-[:IS_ACTIVE]->(duration20120109tonow)
CREATE (hole10:Hole {number:10, par:5})
CREATE (duration20120110tonow:Duration {from: 20120110, to: null})
CREATE (hole10)-[:IS_ACTIVE]->(duration20120110tonow)
CREATE (hole11:Hole {number:11, par:5})
CREATE (duration20120111tonow:Duration {from: 20120111, to: null})
CREATE (hole11)-[:IS_ACTIVE]->(duration20120111tonow)
CREATE (hole12:Hole {number:12, par:4})
CREATE (duration20120112tonow:Duration {from: 20120112, to: null})
CREATE (hole12)-[:IS_ACTIVE]->(duration20120112tonow)
CREATE (hole13:Hole {number:13, par:4})
CREATE (duration20120113tonow:Duration {from: 20120113, to: null})
CREATE (hole13)-[:IS_ACTIVE]->(duration20120113tonow)
CREATE (hole14:Hole {number:14, par:3})
CREATE (duration20120114tonow:Duration {from: 20120114, to: null})
CREATE (hole14)-[:IS_ACTIVE]->(duration20120114tonow)
CREATE (hole15:Hole {number:15, par:4})
CREATE (duration20120115tonow:Duration {from: 20120115, to: null})
CREATE (hole15)-[:IS_ACTIVE]->(duration20120115tonow)
CREATE (hole16:Hole {number:16, par:5})
CREATE (duration20120116tonow:Duration {from: 20120116, to: null})
CREATE (hole16)-[:IS_ACTIVE]->(duration20120116tonow)
CREATE (hole17:Hole {number:17, par:3})
CREATE (duration20120117tonow:Duration {from: 20120117, to: null})
CREATE (hole17)-[:IS_ACTIVE]->(duration20120117tonow)
CREATE (hole18:Hole {number:18, par:3})
CREATE (duration20120118tonow:Duration {from: 20120118, to: null})
CREATE (hole18)-[:IS_ACTIVE]->(duration20120118tonow)
CREATE (hole1inactive:Hole {number:1, par:5})
CREATE (duration20120101toduration20120102:Duration {from: 20120101, to: 20120102})
CREATE (hole1inactive)-[:IS_ACTIVE]->(duration20120101toduration20120102)

CREATE (swingolflinz)-[:HAS_HOLE {number: 1, from:20120101, to:null}]->(hole1)
CREATE (swingolflinz)-[:HAS_HOLE {number: 2, from:20120102, to:null}]->(hole2)
CREATE (swingolflinz)-[:HAS_HOLE {number: 3, from:20120103, to:null}]->(hole3)
CREATE (swingolflinz)-[:HAS_HOLE {number: 4, from:20120104, to:null}]->(hole4)
CREATE (swingolflinz)-[:HAS_HOLE {number: 5, from:20120105, to:null}]->(hole5)
CREATE (swingolflinz)-[:HAS_HOLE {number: 6, from:20120106, to:null}]->(hole6)
CREATE (swingolflinz)-[:HAS_HOLE {number: 7, from:20120107, to:null}]->(hole7)
CREATE (swingolflinz)-[:HAS_HOLE {number: 8, from:20120108, to:null}]->(hole8)
CREATE (swingolflinz)-[:HAS_HOLE {number: 9, from:20120109, to:null}]->(hole9)
CREATE (swingolflinz)-[:HAS_HOLE {number: 10, from:20120110, to:null}]->(hole10)
CREATE (swingolflinz)-[:HAS_HOLE {number: 11, from:20120111, to:null}]->(hole11)
CREATE (swingolflinz)-[:HAS_HOLE {number: 12, from:20120112, to:null}]->(hole12)
CREATE (swingolflinz)-[:HAS_HOLE {number: 13, from:20120113, to:null}]->(hole13)
CREATE (swingolflinz)-[:HAS_HOLE {number: 14, from:20120114, to:null}]->(hole14)
CREATE (swingolflinz)-[:HAS_HOLE {number: 15, from:20120115, to:null}]->(hole15)
CREATE (swingolflinz)-[:HAS_HOLE {number: 16, from:20120116, to:null}]->(hole16)
CREATE (swingolflinz)-[:HAS_HOLE {number: 17, from:20120117, to:null}]->(hole17)
CREATE (swingolflinz)-[:HAS_HOLE {number: 18, from:20120118, to:null}]->(hole18)
CREATE (swingolflinz)-[:HAS_HOLE {number: 1, from:20111111, to:20111112}]->(hole1inactive)

// ################################ create tournaments ################################
CREATE (suedligaLinz:Tournament {name: 'Südliga Linz'})
CREATE (duration20170101:Duration {from: 20170101, to: 20170101})
CREATE (suedligaLinz)-[:HAS_DATE]->(duration20170101)

// ################################ create games ################################
CREATE (suedligaLinzGame:Game {name: 'Südliga Linz',date: '20170101'})

CREATE (suedligaLinz)-[:HAS_GAME]->(suedligaLinzGame)
CREATE (suedligaLinzGame)-[:IS_PLAYED_AT]->(swingolfLinz)

// ################################ create scores ################################
CREATE (score1:Score {score: '3'})
CREATE (score2:Score {score: '4'})
CREATE (score3:Score {score: '5'})
CREATE (score4:Score {score: '4'})
CREATE (score5:Score {score: '3'})
CREATE (score6:Score {score: '2'})
CREATE (score7:Score {score: '1'})
CREATE (score8:Score {score: '5'})
CREATE (score9:Score {score: '4'})
CREATE (score10:Score {score: '5'})
CREATE (score11:Score {score: '4'})
CREATE (score12:Score {score: '2'})
CREATE (score13:Score {score: '8'})
CREATE (score14:Score {score: '7'})
CREATE (score15:Score {score: '5'})
CREATE (score16:Score {score: '3'})
CREATE (score17:Score {score: '3'})
CREATE (score18:Score {score: '3'})

CREATE (score1)-[:WAS_PLAYED_BY]->(andrea)
CREATE (score1)-[:WAS_PLAYED_AT_HOLE]->(hole1)
CREATE (score1)-[:WAS_PLAYED_IN_GAME]->(suedligaLinzGame)
CREATE (score2)-[:WAS_PLAYED_BY]->(andrea)
CREATE (score2)-[:WAS_PLAYED_AT_HOLE]->(hole2)
CREATE (score2)-[:WAS_PLAYED_IN_GAME]->(suedligaLinzGame)
CREATE (score3)-[:WAS_PLAYED_BY]->(andrea)
CREATE (score3)-[:WAS_PLAYED_AT_HOLE]->(hole3)
CREATE (score3)-[:WAS_PLAYED_IN_GAME]->(suedligaLinzGame)
CREATE (score4)-[:WAS_PLAYED_BY]->(andrea)
CREATE (score4)-[:WAS_PLAYED_AT_HOLE]->(hole4)
CREATE (score4)-[:WAS_PLAYED_IN_GAME]->(suedligaLinzGame)
CREATE (score5)-[:WAS_PLAYED_BY]->(andrea)
CREATE (score5)-[:WAS_PLAYED_AT_HOLE]->(hole5)
CREATE (score5)-[:WAS_PLAYED_IN_GAME]->(suedligaLinzGame)
CREATE (score6)-[:WAS_PLAYED_BY]->(andrea)
CREATE (score6)-[:WAS_PLAYED_AT_HOLE]->(hole6)
CREATE (score6)-[:WAS_PLAYED_IN_GAME]->(suedligaLinzGame)
CREATE (score7)-[:WAS_PLAYED_BY]->(andrea)
CREATE (score7)-[:WAS_PLAYED_AT_HOLE]->(hole7)
CREATE (score7)-[:WAS_PLAYED_IN_GAME]->(suedligaLinzGame)
CREATE (score8)-[:WAS_PLAYED_BY]->(andrea)
CREATE (score8)-[:WAS_PLAYED_AT_HOLE]->(hole8)
CREATE (score8)-[:WAS_PLAYED_IN_GAME]->(suedligaLinzGame)
CREATE (score9)-[:WAS_PLAYED_BY]->(andrea)
CREATE (score9)-[:WAS_PLAYED_AT_HOLE]->(hole9)
CREATE (score9)-[:WAS_PLAYED_IN_GAME]->(suedligaLinzGame)
CREATE (score10)-[:WAS_PLAYED_BY]->(andrea)
CREATE (score10)-[:WAS_PLAYED_AT_HOLE]->(hole10)
CREATE (score10)-[:WAS_PLAYED_IN_GAME]->(suedligaLinzGame)
CREATE (score11)-[:WAS_PLAYED_BY]->(andrea)
CREATE (score11)-[:WAS_PLAYED_AT_HOLE]->(hole11)
CREATE (score11)-[:WAS_PLAYED_IN_GAME]->(suedligaLinzGame)
CREATE (score12)-[:WAS_PLAYED_BY]->(andrea)
CREATE (score12)-[:WAS_PLAYED_AT_HOLE]->(hole12)
CREATE (score12)-[:WAS_PLAYED_IN_GAME]->(suedligaLinzGame)
CREATE (score13)-[:WAS_PLAYED_BY]->(andrea)
CREATE (score13)-[:WAS_PLAYED_AT_HOLE]->(hole13)
CREATE (score13)-[:WAS_PLAYED_IN_GAME]->(suedligaLinzGame)
CREATE (score14)-[:WAS_PLAYED_BY]->(andrea)
CREATE (score14)-[:WAS_PLAYED_AT_HOLE]->(hole14)
CREATE (score14)-[:WAS_PLAYED_IN_GAME]->(suedligaLinzGame)
CREATE (score15)-[:WAS_PLAYED_BY]->(andrea)
CREATE (score15)-[:WAS_PLAYED_AT_HOLE]->(hole15)
CREATE (score15)-[:WAS_PLAYED_IN_GAME]->(suedligaLinzGame)
CREATE (score16)-[:WAS_PLAYED_BY]->(andrea)
CREATE (score16)-[:WAS_PLAYED_AT_HOLE]->(hole16)
CREATE (score16)-[:WAS_PLAYED_IN_GAME]->(suedligaLinzGame)
CREATE (score17)-[:WAS_PLAYED_BY]->(andrea)
CREATE (score17)-[:WAS_PLAYED_AT_HOLE]->(hole17)
CREATE (score17)-[:WAS_PLAYED_IN_GAME]->(suedligaLinzGame)
CREATE (score18)-[:WAS_PLAYED_BY]->(andrea)
CREATE (score18)-[:WAS_PLAYED_AT_HOLE]->(hole18)
CREATE (score18)-[:WAS_PLAYED_IN_GAME]->(suedligaLinzGame)


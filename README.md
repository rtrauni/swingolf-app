# swingolf-app

# Docker
docker run --rm --name neo4j --publish=7474:7474 --publish=7687:7687 --volume=%USERPROFILE%/neo4j/data:/data -e NEO4J_dbms_memory_heap_maxSize=8G neo4j:3.3
scp -r graph.db root@db.swingolf.at:/opt/swingolf-app/neo4j/data/databases/

# Slider images
1000x300
mogrify -format jpg -resize "1000x300^" -gravity center -crop 1000x300+0+0 +repage *.jpg

# Handicap Data
copy from http://www.swingolf-dachverband.de/images/hc/Handicap_Lizenzspieler.xls
convert to csv

# Create New Module
ng generate component layout/persondetail

ng build 
scp -r * root@db.swingolf.at:/opt/swingolf-app/jenkins_home/webroot/
# swingolf-app

# Docker

docker run --rm --name neo4j --publish=7474:7474 --publish=7687:7687 neo4j:3.0

# Slider images
1000x300
mogrify -format jpg -resize "1000x300^" -gravity center -crop 1000x300+0+0 +repage *.jpg

run: down build build_image up

down:
	docker-compose down
build:
	./gradlew build
build_image:
	./gradlew jibDockerBuild
up:
	docker-compose up -d
# pp-backend

## Set up environment
```shell
docker-compose -f docker/docker-compose-local.yml up -d
```

## Run Application
```shell
./gradlew clean bootRun --args='--spring.profiles.active=local --PROPERTY_KEY={PROPERTY_VALUE}'
```
# Henesis wallet sample api repo

deploy `contracts/Ownable.sol`

fill `environment/config/application.yaml`

run
```bash
$ ./gradlew build 
$ docker build . -t sample-api:latest
$ cd environment
$ docker-compose up -d
```

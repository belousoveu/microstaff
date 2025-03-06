## Сборка проекта при помощи Maven
```bash
cd api-gateway
mvn clean package
cd ..

cd eureka-server
mvn clean package
cd ..

cd user-service
mvn clean package
cd ..

cd company-service
mvn clean package
cd..
```
## Сборка и запуск контейнеров
```bash
docker-compose build
docker-compose up
```

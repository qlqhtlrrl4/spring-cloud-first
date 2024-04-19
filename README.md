1. .src 있는 directory 위치에서 ./gradlew clean build
2. java -jar -D xxx.jar
3. (1) discovery-serivce.jar start
   (2) apigateway-service.jar start
   (3) first-service or second-service
   (4) another terminal -> (3) 작업 한번더 

4. server.port => 0 랜덤으로 설정해놔서 first-service를 2개이상 띄우고 http://localhost:8000/first-service/check  lb 확인 -> round-robin
   log 로 port 정보 확인 response 에 port 확인

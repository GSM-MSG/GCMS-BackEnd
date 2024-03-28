REPOSITORY=/home/ec2-user
PROJECT_NAME=GCMS-Backend
JAR_PATH=$REPOSITORY/GCMS-Backend/build/libs/gcms-0.0.1-SNAPSHOT.jar

cd $REPOSITORY/$PROJECT_NAME/

echo "> Git Pull"
git pull origin develop

echo "> Project Build"
./gradlew clean GCMS-Backend:build

CURRENT_PID=$(lsof -i tcp:8080)
if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> kill -9 $CURRENT_PID"
    kill -9 $CURRENT_PID
    sleep 5
fi

echo "> 애플리케이션 배포"
JAR_NAME=$(ls -tr $JAR_PATH | tail -n 1)
echo "> JAR NAME: $JAR_NAME"
nohup java -jar $JAR_NAME  --logging.file.path=/home/ec2-user/ --logging.level.org.hibernate.SQL=DEBUG >> /home/ec2-user/deploy.log 2>/home/ec2-user/deploy_err.log &
## Kris私人社区
## 部署
- Git
- JDK
- Maven
- MYSQL
## 步骤
- yum update
- yum install git
- mkdir app
- cd App
- git clone https://github.com/Krissss517/Community.git
- yum install maven
- mvn compile package
- cp src/main/resources/application.properties src/main/resources/applicatio
  n-production.properties
- vim src/main/resources/application-production.properties
- mvn package
- java -jar -Dspring.profiles.active=production /root/App/Community/target/community-0.0.1-SNAPSHOT.jar
- ps -aux | grep java
- git pull
- mvn clean compile flyway:migrate -Pproduction

## 资料
[Spring 文档](https://spring.io/guides)  
[Spring Web](https://spring.io/guides/gs/serving-web-content/)  
[es](http://elasticsearch.cn/explore/)  
[bootstrap 文档](https://v3.bootcss.com/getting-started/)  
[GitHub OAuth](https://docs.github.com/en/developers/apps/building-oauth-apps)  
[菜鸟教程](https://www.runoob.com/java/java-tutorial.html)  
[MarkDown](https://pandao.github.io/editor.md/)
## 工具
[Git](https://git-scm.com/download)  
[FlyWay](https://flywaydb.org/)  
[LomBok](https://projectlombok.org/features/all)  
[UFileSDK](https://cloud.tencent.com/document/product/436/35217)     

## 脚本
```sql
CREATE table USER
(
    id int AUTO_INCREMENT PRIMARY KEY NOT NULL,
    account_id VARCHAR (100),
    name VARCHAR (50),
    token VARCHAR (36),
    gmt_create BIGINT ,
    gmt_modified BIGINT
);
```
```bash
mvn flyway:clean
mvn flyway:migrate
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```

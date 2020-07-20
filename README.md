## Kris私人社区

## 资料
[Spring 文档](https://spring.io/guides)  
[Spring Web](https://spring.io/guides/gs/serving-web-content/)  
[es](http://elasticsearch.cn/explore/)  
[bootstrap 文档](https://v3.bootcss.com/getting-started/)  
[GitHub OAuth](https://docs.github.com/en/developers/apps/building-oauth-apps)
[菜鸟教程](https://www.runoob.com/java/java-tutorial.html)
## 工具
[Git](https://git-scm.com/download)  
[FlyWay](https://flywaydb.org/)
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
```

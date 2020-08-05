# bot

wfb's qq bot 小斌

## 部署

1. 修改`src/main/resources/application.properties`文件，将qq、mysql、redis修改为正确的配置
2. 使用`gradle run bootJar`打包为Jar包，Jar包在`build/libs/`目录下
3. 将Jar包和`src/main/resources/`目录下的全部内容一起复制到服务器
4. 使用`java -jar bot-0.0.1-SNAPSHOT.jar`启动项目


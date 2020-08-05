# bot

wfb's qq bot 小斌

## 运行

1. 使用`idea`添加项目
2. 使用`gradle`进行依赖的下载
3. 修改[application.properties](src/main/resources/application.properties
)文件，将qq、mysql、redis等配置调整正确
4. 运行[BotApplication.kt](src/main/kotlin/com/wfb/bot/BotApplication.kt)

## 部署

1. 修改[application.properties](src/main/resources/application.properties
     )文件，将qq、mysql、redis等配置调整正确
2. 使用`gradle run bootJar`打包为Jar包，Jar包在`build/libs/`目录下
3. 使用`java -jar bot-0.0.1-SNAPSHOT.jar`启动项目
# 更换环境时，需要install整改项目
mvn clean install -f ./pom.xml

# 以下的common指tourShare-Common

# 如果没有修改过facade或者common，单纯修改启动环境，install common即可
# 可以在Maven Project中install，也可以使用下面的命令
clean install -P dev -f tourShare-Common/pom.xml

# 如果修改过facade或者common，先install对应项目
# 此命令install所有Facade和common，-P参数决定了项目启动使用的环境，不加默认使用build
clean install -P build -f pom-facade-install.xml

# All-Timer项目启动环境由common决定
# 但是还需要-P参数指定使用的schedule-*.properties文件，如果不加默认使用schedule-dev.properties
clean -P dev tomcat7:run-war -f tourShare-All-Timer/pom.xml

# 以下项目指定自己的pom文件即可，启动参数完全由common决定
clean tomcat7:run-war -f tourShare-Buyer-Service/pom.xml
clean tomcat7:run-war -f tourShare-Buyer-Web/pom.xml
clean tomcat7:run-war -f tourShare-Cert-Service/pom.xml
clean tomcat7:run-war -f tourShare-Cert-Web/pom.xml
clean tomcat7:run-war -f tourShare-Circle-Service/pom.xml
clean tomcat7:run-war -f tourShare-Circle-Web/pom.xml
clean tomcat7:run-war -f tourShare-Demand-Service/pom.xml
clean tomcat7:run-war -f tourShare-Demand-Web/pom.xml
clean tomcat7:run-war -f tourShare-Frame-Service/pom.xml
clean tomcat7:run-war -f tourShare-Frame-Web/pom.xml
clean tomcat7:run-war -f tourShare-LiveVideo-Service/pom.xml
clean tomcat7:run-war -f tourShare-LiveVideo-Web/pom.xml
clean tomcat7:run-war -f tourShare-Order-Service/pom.xml
clean tomcat7:run-war -f tourShare-Order-Web/pom.xml
clean tomcat7:run-war -f tourShare-Pay-Service/pom.xml
clean tomcat7:run-war -f tourShare-Pay-Web/pom.xml
clean tomcat7:run-war -f tourShare-Product-Service/pom.xml
clean tomcat7:run-war -f tourShare-Product-Web/pom.xml
clean tomcat7:run-war -f tourShare-Seller-Service/pom.xml
clean tomcat7:run-war -f tourShare-Seller-Web/pom.xml

# search-service项目需要指定环境参数-P
clean -P yanshi tomcat7:run-war -f tourShare-Search-Service/pom.xml

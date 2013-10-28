beans = {
    switch (grails.util.GrailsUtil.environment) {
        case "production":
            println "Creating production log4j beans"
            log4jConfigurer(org.springframework.beans.factory.config.MethodInvokingFactoryBean)
            {
               targetClass = "org.springframework.util.Log4jConfigurer"
               targetMethod = "initLogging"
               arguments = ["classpath:log4j.properties"]
            }
            break
        case "development":
            println "Creating development log4j beans"
            log4jConfigurer(org.springframework.beans.factory.config.MethodInvokingFactoryBean)
            {
               targetClass = "org.springframework.util.Log4jConfigurer"
               targetMethod = "initLogging"
               arguments = ["file:log4j.properties"]
            }
            break
    }
}

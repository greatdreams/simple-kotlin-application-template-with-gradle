application {
    mainClassName = "com.greatdreams.learn.java.MainProgram"
    applicationDefaultJvmArgs = listOf("-Dfile.encoding=US-ASCII",
            "-Djavax.net.debug=all",
            "-Djavax.net.ssl.trustStore=src/main/resources/newServerKeystore",
            "-Djavax.net.ssl.trustStorePassword=123456")
}
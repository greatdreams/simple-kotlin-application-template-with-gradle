import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

appender("CONSOLE", ConsoleAppender) {
    append = true
    encoder(PatternLayoutEncoder) {
        pattern = "%level %date %logger - %msg%n"
    }
}
root(DEBUG, ["CONSOLE"])
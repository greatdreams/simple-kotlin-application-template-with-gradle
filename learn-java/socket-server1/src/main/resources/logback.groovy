import ch.qos.logback.core.filter.*
import static ch.qos.logback.classic.Level.*

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%level %date %logger - %msg%n"
    }
}
root(ch.qos.logback.classic.Level.INFO, ["CONSOLE"])

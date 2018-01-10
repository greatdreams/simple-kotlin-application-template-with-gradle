import ch.qos.logback.core.filter.*
import ch.qos.logback.classic.boolex.*
import ch.qos.logback.core.util.FileSize

import static ch.qos.logback.core.spi.FilterReply.NEUTRAL
import static ch.qos.logback.core.spi.FilterReply.DENY
import static ch.qos.logback.classic.Level.*

appPath = System.getProperty("user.dir")

appender("FILE", RollingFileAppender) {
    append =true
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${appPath}/log/application_%d{yyyy-MM-dd}.log"
        maxHistory = 30 // controls the maximum number of archive files to keep, asynchronously deleting older files
        totalSizeCap = FileSize.valueOf("3 gb")
    }
    encoder(PatternLayoutEncoder) {
        pattern = '%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level  %logger - %msg%n'
    }
    filter(EvaluatorFilter) {
        evaluator(GEventEvaluator) {
            expression = 'e.level.toInt() <= INFO.toInt()'
        }
        onMatch = NEUTRAL
        onMismatch = DENY
    }
}

appender("ERROR_FILE", RollingFileAppender) {
    append =true
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${appPath}/log/application_error_%d{yyyy-MM-dd}.log"
        maxHistory = 30 // controls the maximum number of archive files to keep, asynchronously deleting older files
        totalSizeCap = FileSize.valueOf("3 gb")
    }
    encoder(PatternLayoutEncoder) {
        pattern = '%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level  %logger - %msg%n'
    }
    filter(EvaluatorFilter) {
        evaluator(GEventEvaluator) {
            expression = 'e.level.toInt() > INFO.toInt()'
        }
        onMatch = NEUTRAL
        onMismatch = DENY
    }
}

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = '%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level  %logger - %msg%n'
    }
}
root(INFO,["ERROR_FILE", "FILE"])
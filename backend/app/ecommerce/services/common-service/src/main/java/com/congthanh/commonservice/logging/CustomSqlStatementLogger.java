package com.congthanh.commonservice.logging;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.hibernate.engine.jdbc.spi.SqlStatementLogger;
import org.hibernate.resource.jdbc.spi.JdbcSessionContext;
import org.hibernate.stat.spi.StatisticsImplementor;

import java.util.concurrent.TimeUnit;

import static com.congthanh.commonservice.constants.Constants.Logging.DURATION;

@Slf4j
public class CustomSqlStatementLogger extends SqlStatementLogger {

    public CustomSqlStatementLogger(boolean logToStdout, boolean format, boolean highlightSql, int slowQueryThreshold) {
        super(logToStdout, format, highlightSql, slowQueryThreshold);
    }

    //override this function so we do not have to check the slow query threshold, always log the sql
    @SneakyThrows
    @Override
    public void logSlowQuery(String sql, long startTimeNanos, JdbcSessionContext context) {
        Thread.sleep(10);//simulate query time, should be removed on production
        long queryExecutionMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTimeNanos);
        ThreadContext.put(DURATION, String.valueOf(queryExecutionMillis));
        ThreadContext.put("sql", sql);
        logSlowQueryInternal(context, queryExecutionMillis, sql);
        ThreadContext.remove(DURATION);
        ThreadContext.remove("sql");
    }

    private void logSlowQueryInternal(final JdbcSessionContext context, final long queryExecutionMillis, final String sql) {
        final String logData = "Query took " + queryExecutionMillis + " milliseconds [" + sql + "]";
        log.info("[CustomSqlStatementLogger][logSlowQueryInternal] {}", logData);
        if (context != null) {
            final StatisticsImplementor statisticsImplementor = context.getStatistics();
            if (statisticsImplementor != null && statisticsImplementor.isStatisticsEnabled()) {
                statisticsImplementor.slowQuery(sql, queryExecutionMillis);
            }
        }
    }
}

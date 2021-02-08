package com.example.jpa.demo.config;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.stereotype.Component;

@Component
public class SlicedTableStatementInspector implements StatementInspector {
    /**
     * Inspect the given SQL, possibly returning a different SQL to be used instead.  Note that returning {@code null}
     * is interpreted as returning the same SQL as was passed.
     *
     * @param sql The SQL to inspect
     * @return The SQL to use; may be {@code null}
     */
    @Override
    public String inspect(String sql) {
        System.out.println("sql::::::::"+sql);
        return sql;
    }
}

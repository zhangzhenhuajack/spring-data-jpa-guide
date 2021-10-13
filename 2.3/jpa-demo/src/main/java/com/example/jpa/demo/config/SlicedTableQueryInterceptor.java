package com.example.jpa.demo.config;

import com.mysql.cj.MysqlConnection;
import com.mysql.cj.Query;
import com.mysql.cj.interceptors.QueryInterceptor;
import com.mysql.cj.jdbc.interceptors.ServerStatusDiffInterceptor;
import com.mysql.cj.log.Log;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.protocol.ServerSession;

import java.util.Properties;
import java.util.function.Supplier;

public class SlicedTableQueryInterceptor implements QueryInterceptor {
    @Override
    public QueryInterceptor init(MysqlConnection conn, Properties props, Log log) {
        System.out.println(conn);
        System.out.println(props);
        return this;
    }

    @Override
    public <T extends Resultset> T preProcess(Supplier<String> sql, Query interceptedQuery) {
        return null;
    }

    @Override
    public boolean executeTopLevelOnly() {
        return false;
    }

    @Override
    public void destroy() {

    }

    @Override
    public <T extends Resultset> T postProcess(Supplier<String> sql, Query interceptedQuery, T originalResultSet, ServerSession serverSession) {
        return null;
    }

}

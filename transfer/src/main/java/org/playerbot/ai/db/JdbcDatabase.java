package org.playerbot.ai.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;
import org.azeckoski.reflectutils.ReflectUtils;
import org.playerbot.ai.annotation.AnnotationProcessor;
import org.playerbot.ai.annotation.ColumnMeta;
import org.playerbot.ai.config.DatabaseConfiguration;
import org.playerbot.ai.entity.PostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public abstract class JdbcDatabase implements Database {

    public final BasicDataSource dataSource = new BasicDataSource();
    public final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    public final DatabaseConfiguration config;

    public JdbcDatabase(DatabaseConfiguration config) throws DbException {
        try {
            dataSource.setDriverClassName(config.getDriver());
            dataSource.setUrl(config.getUrl());
            dataSource.setUsername(config.getUsername());
            dataSource.setPassword(config.getPassword());
            this.config = config;
        } catch (Exception e) {
            throw new DbException(e);
        }
    }

    public <T> T selectOne(final Class<T> type, Object key) {
        List<T> list = select(type, key);
        if (list.isEmpty()) {
            return null;
        }
        else if (list.size() > 1) {
            throw new UnsupportedOperationException("One record is expected for " + type.getName());
        }
        return list.get(0);
    }

    public <T> List<T> select(final Class<T> type, Object key) {
        return select(type, new Object[] { key }, new QueryBuilder() {
            @Override
            public String build(AnnotationProcessor annotationProcessor) {
                return String.format("SELECT %s FROM %s WHERE %s = ? ORDER BY %s",
                        StringUtils.join(annotationProcessor.getColumnNames(), ","),
                        annotationProcessor.getTableName(),
                        annotationProcessor.getKey(),
                        annotationProcessor.getKey());
            }
        });
    }

    public <T> List<T> selectAll(final Class<T> type) {
        return select(type, new Object[0], new QueryBuilder() {
            @Override
            public String build(AnnotationProcessor annotationProcessor) {
                return String.format("SELECT %s FROM %s",
                        StringUtils.join(annotationProcessor.getColumnNames(), ","),
                        annotationProcessor.getTableName());
            }
        });
    }

    public interface QueryBuilder {
        String build(AnnotationProcessor annotationProcessor);
    }

    public <T> List<T> select(final Class<T> type, Object[] params, QueryBuilder queryBuilder) {
        final AnnotationProcessor annotationProcessor = new AnnotationProcessor(type, config.getVersion());
        if (!annotationProcessor.isEnabled())
            return new ArrayList<T>();

        List<T> list = jdbcTemplate.query(queryBuilder.build(annotationProcessor), params, new RowMapper<T>() {

            private ColumnMeta[] columns = annotationProcessor.getColumns();

            @Override
            public T mapRow(ResultSet rs, int rowNum) throws SQLException {
                T result = ReflectUtils.getInstance().constructClass(type);
                for (ColumnMeta column : columns) {
                    annotationProcessor.apply(result, column, rs.getObject(column.getName()));
                }
                if (result instanceof PostProcessor) {
                    ((PostProcessor)result).postProcess();
                }
                return result;
            }
        });
        return list;
    }

    public <T> void insert(Class<T> type, T object) {
        final AnnotationProcessor annotationProcessor = new AnnotationProcessor(type, config.getVersion());
        if (!annotationProcessor.isEnabled())
            return;

        String[] columns = annotationProcessor.getColumnNames();
        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)",
                annotationProcessor.getTableName(),
                StringUtils.join(columns, ","),
                StringUtils.repeat("?", ",", columns.length));
        jdbcTemplate.update(sql, annotationProcessor.read(object, annotationProcessor.getColumns()));
    }

    @Override
    public void close() throws DbException {
        try {
            dataSource.close();
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    public void update(String sql, Object... params) {
        jdbcTemplate.update(sql, params);
    }

    public Long queryForLong(String sql, Object... params) {
        return jdbcTemplate.queryForLong(sql, params);
    }

    public DatabaseConfiguration getConfig() {
        return config;
    }

    public void delete(Class<?> type, Object key) {
        AnnotationProcessor annotationProcessor = new AnnotationProcessor(type, config.getVersion());
        if (!annotationProcessor.isEnabled())
            return;

        jdbcTemplate.update(String.format("DELETE FROM %s WHERE %s = ?", annotationProcessor.getTableName(),
                annotationProcessor.getKey()), key);
    }
}

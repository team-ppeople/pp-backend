package com.pp.api.util;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class DatabaseCleanUtil {

    private static final Set<String> SYS_TABLES = Set.of(
            "CONSTANTS",
            "ENUM_VALUES",
            "INDEXES",
            "INDEX_COLUMNS",
            "INFORMATION_SCHEMA_CATALOG_NAME",
            "IN_DOUBT",
            "LOCKS",
            "QUERY_STATISTICS",
            "RIGHTS",
            "ROLES",
            "SESSIONS",
            "SESSION_STATE",
            "SETTINGS",
            "SYNONYMS"
    );

    private final EntityManager entityManager;

    private final List<String> tableNames = new ArrayList<>();

    public DatabaseCleanUtil(
            EntityManager entityManager,
            DataSource dataSource
    ) throws SQLException {
        this.entityManager = entityManager;

        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();

            ResultSet resultSet = databaseMetaData.getTables(
                    null,
                    null,
                    "%",
                    new String[]{"TABLE"}
            );

            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");

                if (!SYS_TABLES.contains(tableName)) {
                    tableNames.add(tableName);
                }
            }
        }
    }

    @Transactional
    public void clear() {
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();

        entityManager.flush();

        for (String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
        }

        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
    }

}

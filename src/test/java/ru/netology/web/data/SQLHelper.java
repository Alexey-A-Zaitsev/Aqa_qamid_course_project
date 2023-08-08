package ru.netology.web.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static QueryRunner runner = new QueryRunner();

    private SQLHelper() {

    }

    static String url = System.getProperty("db.url");
    static String user = System.getProperty("db.user");
    static String password = System.getProperty("db.password");

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @SneakyThrows
    public static String getPaymentTransactionStatus() {
        var codeSQL = "SELECT status FROM payment_entity ORDER BY created DESC";
        var connection = getConnection();
        var result = runner.query(connection, codeSQL, new ScalarHandler<String>());
        return result;
    }

    @SneakyThrows
    public static String getLoanTransactionStatus() {
        var codeSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC";
        var connection = getConnection();
        var result = runner.query(connection, codeSQL, new ScalarHandler<String>());
        return result;
    }

    @SneakyThrows
    public static int getPurchaseAmount() {
        var codeSQL = "SELECT amount FROM payment_entity ORDER BY created DESC";
        var connection = getConnection();
        var result = runner.query(connection, codeSQL, new ScalarHandler<Integer>());
        return result;
    }

    @SneakyThrows
    public static void clearDatabase() {
        var connection = getConnection();
        runner.execute(connection, "DELETE FROM credit_request_entity");
        runner.execute(connection, "DELETE FROM order_entity");
        runner.execute(connection, "DELETE FROM payment_entity");
    }

}

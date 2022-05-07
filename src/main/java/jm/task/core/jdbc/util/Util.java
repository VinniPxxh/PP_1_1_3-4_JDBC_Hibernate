package jm.task.core.jdbc.util;

import com.mysql.cj.xdevapi.SessionFactory;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/pp_base";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "!Vinni6325";


    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties properties = new Properties();
                properties.put(Environment.URL, "jdbc:mysql://localhost:3306/pp_base");
                properties.put(Environment.USER, "root");
                properties.put(Environment.PASS, "!Vinni6325");

                properties.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");

                properties.put(Environment.SHOW_SQL, "true");
                properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                properties.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(properties);
                configuration.addAnnotatedClass(UserDaoHibernateImpl.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = (SessionFactory) configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sessionFactory;
    }

}


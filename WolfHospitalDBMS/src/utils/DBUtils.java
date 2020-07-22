package utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Database Utils
 *
 * @author Huang Yuan Min
 */
public class DBUtils {
    private static String sqlName = null;
    private static String url = null;
    private static String name = null;
    private static String password = null;

    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    private static final String PROPERTY_MYSQL = "mysql.properties";
    private static final String PROPERTY_MARIADB = "mariadb.properties";

    /*
     * this static block read in property files to initialize variables for database connection
     */
    static {
        try {
            Properties properties = new Properties();
//            InputStream in = DBUtils.class.getClassLoader().getResourceAsStream(PROPERTY_MYSQL);
            InputStream in = DBUtils.class.getClassLoader().getResourceAsStream(PROPERTY_MARIADB);
            properties.load(in);

//            sqlName = properties.getProperty("sqlName");
//            url = properties.getProperty("url");
//            name = properties.getProperty("name");
//            password = properties.getProperty("password");
            sqlName = "org.mariadb.jdbc.Driver";
            url = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/yhuang64";
            name = "yhuang64";
            password = "quickpass";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This general function encapsulates Update, Insert, and Delete operations
     * using prepared statements and placeholders in SQLs.
     *
     * @param sql sql to execute with placeholders
     * @param args arguments used to replace placeholders
     */
    public static void update(String sql, Object... args) {
        try {
            connection = registerDriverAndGetConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            release(null, preparedStatement, connection);
        }
    }

    /**
     * this general function is used for querying one object from one of the database table
     * it uses reflection to create entities of the given object class type and use setters of entities
     * to assign values to each field of the entity object
     * @param clazz the class type of the entity we want to query
     * @param sql the select statement
     * @param args arguments used to replace placeholders
     * @param <T> the return type of the entity class
     * @return the entity we generated using the information we get from the database
     */
    public static <T> T get(Class<T> clazz, String sql, Object... args) {
        List<T> result = getList(clazz, sql, args);

        if (result.size() > 0) // has result
            return result.get(0);

        // no result
        return null;
    }

//    /**
//     * Get First Value
//     *
//     * @param sql
//     * @param args
//     * @param <E>
//     * @return
//     */
//    public static <E> E getFirstValue(String sql, Object... args) {
//        E value = null;
//        try {
//            // query to get result set
//            connection = registerDriverAndGetConnection();
//            preparedStatement = connection.prepareStatement(sql);
//            for (int i = 0; i < args.length; i++) {
//                preparedStatement.setObject(i + 1, args[i]);
//            }
//            resultSet = preparedStatement.executeQuery();
//
//            if (resultSet.next()) {
//                value = (E) resultSet.getObject(1);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            release(resultSet, preparedStatement, connection);
//        }
//
//        return value;
//    }

    /**
     * this general function is used for querying a list of objects from one of the database table
     * it retrieves a list of all objects information, which contains maps from column names of attributes
     * to values of attributes, using the get list of values method
     * then, it uses the list of information to generate list of entities, using the get list of entities method
     * @param clazz the class type of the entity we want to query
     * @param sql the select statement
     * @param args arguments used to replace placeholders
     * @param <T> the return type of the entity class
     * @return a list of entities we generated using the information we get from the database
     */
    public static <T> List<T> getList(Class<T> clazz, String sql, Object... args) {
        List<T> entities = new ArrayList<>();

        try {
            // query to get result set
            connection = registerDriverAndGetConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            resultSet = preparedStatement.executeQuery();

            List<Map<String, Object>> listOfValues = getListOfValues(resultSet);

            // if has entry in map values, construct object by reflection
            entities = getListOfEntities(clazz, listOfValues);
        } catch (SQLException | InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            release(resultSet, preparedStatement, connection);
        }

        return entities;
    }

    /**
     * get list of entities from list of maps of column names and column values, corresponding to the type of
     * object we want to generate
     * for each entity, we use get entity from map method to generate it
     * @param clazz        Class of entities
     * @param listOfValues list of maps of column names and column values
     * @param <T>          type of entities
     * @throws InstantiationException e
     * @throws IllegalAccessException e
     * @throws NoSuchFieldException   e
     */
    private static <T> List<T> getListOfEntities(Class<T> clazz, List<Map<String, Object>> listOfValues)
            throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        List<T> result = new ArrayList<>();
        if (listOfValues.size() > 0) {
            for (Map<String, Object> values : listOfValues) {
                result.add(getEntityFromMap(clazz, values));
            }
        }
        return result;
    }

    /**
     * this function get an entity object from map of column names and column values
     * we first use the class type to construct a new instance of the entity type
     * for each field of the entity, we use reflection to get the field by field name
     * then we set the value to that field
     * @param clazz  Class of entity
     * @param values map of values
     * @param <T>    type of entity
     * @return entity object
     * @throws InstantiationException e
     * @throws NoSuchFieldException   e
     * @throws IllegalAccessException e
     */
    private static <T> T getEntityFromMap(Class<T> clazz, Map<String, Object> values)
            throws InstantiationException, NoSuchFieldException, IllegalAccessException {
        T entity = clazz.newInstance();
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();

            // set field to value
            Field entityField = entity.getClass().getDeclaredField(field);
            entityField.setAccessible(true);
            entityField.set(entity, value);
        }
        return entity;
    }

    /**
     * this function gets list of maps from result set queried, one map for one entry in database
     * we first use get column labels method to get all column labels in the result set
     * then we use the column labels as the key of the map
     * and set the column value we get from the result set as the value of the map
     * @param resultSet queried
     * @return list of maps
     * @throws SQLException e
     */
    private static List<Map<String, Object>> getListOfValues(ResultSet resultSet) throws SQLException {
        List<String> columnLabels = getColumnLabels(resultSet);

        // get list of Map, key: columnName, value: columnValue
        List<Map<String, Object>> listOfValues = new ArrayList<>();
        while (resultSet.next()) {
            Map<String, Object> values = new HashMap<>();

            for (String columnLabel : columnLabels) {
                Object columnValue = resultSet.getObject(columnLabel);
                values.put(columnLabel, columnValue);
            }

            listOfValues.add(values);
        }
        return listOfValues;
    }

    /**
     * get list of column names in a table from result set queried
     * we use result set meta data to get column names and store them one by one in list of strings
     * @param resultSet queried
     * @return list of column names
     * @throws SQLException e
     */
    private static List<String> getColumnLabels(ResultSet resultSet) throws SQLException {
        List<String> labels = new ArrayList<>();

        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
            labels.add(resultSetMetaData.getColumnLabel(i + 1));
        }

        return labels;
    }

    /**
     * TRANSACTION VERSION OF GET AVAILABLE BED COUNTS
     * scan all the beds in given hospital with given specialty,
     * then use the bed No. to scan in check-in-out records,
     * return the number of beds - the count of those beds with NULL end date
     *
     * @param hospitalId given hospital id
     * @param specialty  given specialty
     * @return the count of those beds with NOT NULL end date
     */
    public static int transactionGetAvailableBedsInHospitalGivenSpecialty(int hospitalId, String specialty) {
        long allBeds = -2, unavailableBeds = -1;

        String sqlAll = "SELECT COUNT(*) FROM Bed WHERE HospitalId = ? AND Specialization = ?";

        String sqlUnavailable = "SELECT COUNT(*) FROM CheckInOut WHERE EndDate IS NULL AND BedNumber IN " +
                "(SELECT BedNumber FROM Bed WHERE HospitalId = ? AND Specialization = ?)";

        try {
            // connect to the database and get prepared statement
            connection = registerDriverAndGetConnection();
            // stop auto commit
            connection.setAutoCommit(false);

            // do things that needed to do
            // get number of all beds in the hospital with given specialty
            preparedStatement = connection.prepareStatement(sqlAll);
            preparedStatement.setObject(1, hospitalId);
            preparedStatement.setObject(2, specialty);

            resultSet = preparedStatement.executeQuery();

            resultSet.next();
            allBeds = (long)resultSet.getObject("COUNT(*)");

            // get number of beds that are unavailable in the hospital with given specialty
            CloseST(preparedStatement);
            CloseRS(resultSet);
            preparedStatement = connection.prepareStatement(sqlUnavailable);
            preparedStatement.setObject(1, hospitalId);
            preparedStatement.setObject(2, specialty);

            resultSet = preparedStatement.executeQuery();

            resultSet.next();
            unavailableBeds = (long)resultSet.getObject("COUNT(*)");

            // all SQLs successfully executed, commit all executions
            connection.commit();
            // start auto commit
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            // if encounters SQLException, there is SQL failed, roll back
            if (connection != null) {
                try {
                    connection.rollback();
                    connection.setAutoCommit(true);
                } catch (SQLException e1) {
                    e.printStackTrace();
                }
            }
        } finally {
            // release connection, statement
            release(resultSet, preparedStatement, connection);
        }

        return (int)(allBeds - unavailableBeds);
    }

    /**
     * METHOD USED BY HOSPITAL_DAO_IMPL
     * first scan the check in/out table to count the number of check in
     * records, of which the end date IS NULL(, which means that the patient is
     * currently in a hospital) then group the numbers by hospital id
     *
     * @return a map of the hospital id and the number of patients currently in
     *         the hospital
     */
    public static Map<Integer, Integer> getAllHospitalUsageStatus() {
        Map<Integer, Integer> map = new HashMap<>();

        String sql = "SELECT HospitalId, COUNT(*) FROM CheckInOut WHERE EndDate IS NULL GROUP BY HospitalId ORDER BY HospitalId";

        try {
            // query to get result set
            connection = registerDriverAndGetConnection();
            preparedStatement = connection.prepareStatement(sql);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                map.put((Integer) resultSet.getObject("HospitalId"), resultSet.getInt("COUNT(*)"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            release(resultSet, preparedStatement, connection);
        }

        return map;
    }

    /**
     * TRANSACTION VERSION OF RELEASE BED
     * use given patient, hospital, bed to find a check in record with NULL end date,
     * update its end date with given end date
     * MODIFY PATIENT STATUS HERE
     *
     * @param patientId  given patient
     * @param hospitalId given hospital
     * @param bedNumber  given bed number
     * @param endDate    given end date
     */
    public static void transactionReleaseBed(int patientId, int hospitalId, int bedNumber, Date endDate) {
        // generate corresponding SQL
        String sqlCheck = "UPDATE CheckInOut SET endDate = ? WHERE PatientId = ? AND HospitalId = ? AND BedNumber = ?";
        String sqlPatient = "UPDATE Patient SET Status = ? WHERE PatientId = ?";

        try {
            // connect to the database and get prepared statement
            connection = registerDriverAndGetConnection();
            // stop auto commit
            connection.setAutoCommit(false);

            // do things that needed to do
            // update check out record
            preparedStatement = connection.prepareStatement(sqlCheck);
            preparedStatement.setObject(1, endDate);
            preparedStatement.setObject(2, patientId);
            preparedStatement.setObject(3, hospitalId);
            preparedStatement.setObject(4, bedNumber);

            preparedStatement.executeUpdate();

            // update patient status
            CloseST(preparedStatement);
            preparedStatement = connection.prepareStatement(sqlPatient);
            preparedStatement.setObject(1, "Treatment complete");
            preparedStatement.setObject(2, patientId);

            preparedStatement.executeUpdate();

            // all SQLs successfully executed, commit all executions
            connection.commit();
            // start auto commit
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            // if encounters SQLException, there is SQL failed, roll back
            if (connection != null) {
                try {
                    connection.rollback();
                    connection.setAutoCommit(true);
                } catch (SQLException e1) {
                    e.printStackTrace();
                }
            }
        } finally {
            // release connection, statement
            release(null, preparedStatement, connection);
        }
    }

    /**
     * use properties for database connections to get a connection
     * @return the generated connection
     */
    private static Connection registerDriverAndGetConnection() {
        try {
            Class.forName(sqlName);
            connection = DriverManager.getConnection(url, name, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * this function is used for releasing result sets, statements and connections
     * @param rs result set to release
     * @param st statements to release
     * @param con connection to release
     */
    private static void release(ResultSet rs, Statement st, Connection con) {
        CloseRS(rs);
        CloseST(st);
        CloseCon(con);
    }

    private static void CloseRS(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            rs = null;
        }
    }

    private static void CloseST(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            st = null;
        }
    }

    private static void CloseCon(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con = null;
        }
    }
}
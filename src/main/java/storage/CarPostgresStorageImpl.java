package storage;

import type.Car;
import type.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarPostgresStorageImpl implements CarStorage {


    static {
        try {
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException ex){
            System.err.println("Server can't find postgresql Driver class: \n" + ex);
        }
    }


    private static String POSTGRESQL_JDBC_URL = "jdbc:postgresql://localhost:5432/budget_of_auto"; //- to jest nasza baza - możemy nazwę zmieniać wg uznania
    private static String POSTGRESQL_DATABASE_USER ="postgres";
    private static String POSTGRESQL_DATABASE_PASS ="Astra123!";

    private final String ID = "id";
    private final String VALUE = "value";
    private final String DATA = "date";

//    private final String GAZ = "gas";
//    private final String BENZYNA = "petrol";
//    private final String CZESCI = "parts";
//    private final String UBEZPIECZENIE = "insurance";
//    private final String SERWIS = "service";
//    private final String OTHER = "other";




    @Override
    public void removeService(long autoIdToDelete) {

        final  String sqlSelectService = "DELETE FROM service WHERE  id= ?;";

        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlSelectService);
            preparedStatement.setLong(1,autoIdToDelete);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query :\n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        }finally {
            closeDataBaseResources(connection, preparedStatement);
        }

    }


    @Override
    public void addService(Service service) {

        final String sqlInsertService = "INSERT INTO service (id, value, date)" +
                "VALUES" +
                "   (?,?, ?);";
        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;


        try {
            preparedStatement = connection.prepareStatement(sqlInsertService);

            preparedStatement.setLong(1,service.getId());
            preparedStatement.setDouble(2,service.getValue());
            preparedStatement.setDate(3, service.getData());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query :\n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        }finally {
            closeDataBaseResources(connection, preparedStatement);
        }

    }

    @Override
    public  Service getService(long id) {
        final  String sqlSeletService = "SELECT * FROM service WHERE id = ?;";
//        final  String sqlSeletBook = "SELECT * FROM books WHERE book_id = ;"+ id;

        Connection connection = initializeDataBaseConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sqlSeletService);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                Service service = new Service();
                service.setId(resultSet.getInt(ID));
                service.setValue(resultSet.getDouble(VALUE));
                service.setData(resultSet.getDate(DATA));

                return service;

            }
        } catch (SQLException e) {
            System.err.println("Error during invoke SQL query :\n" + e.getMessage());
            throw new RuntimeException("Error during invoke SQL query");
        }finally {
            closeDataBaseResources(connection,preparedStatement);
        }
        return null;
    }



    @Override
    public List<Service> getAllService() {

        final  String sqlSelectAllService = "SELECT * FROM service;";

        Connection connection = initializeDataBaseConnection();
        Statement statement = null;
        List<Service> services = new ArrayList<>();

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSelectAllService);

            while (resultSet.next()){
                Service service = new Service();
                service.setId(resultSet.getInt(ID));
                service.setData(resultSet.getDate(DATA));
                service.setValue(resultSet.getDouble(VALUE));

                services.add(service);
            }
        } catch (SQLException e) {

            System.err.println("Error during invoke SQL query: \n" + e.getMessage());
            throw new  RuntimeException("Error during invoke SQL query");
        }
        finally {
            closeDataBaseResources(connection,statement);
        }
        return  services;
    }


    private Connection initializeDataBaseConnection(){

        try {
            return DriverManager.getConnection(POSTGRESQL_JDBC_URL,POSTGRESQL_DATABASE_USER,POSTGRESQL_DATABASE_PASS);
        } catch (SQLException e) {
            System.err.println("Server can't initialize database connection");
            throw new RuntimeException("Server can't initialize database connection");

        }


    }


    private void closeDataBaseResources(Connection connection, Statement statement){
        try{
            if(statement!= null ){
                statement.close();
            }
            if(connection != null){
                connection.close();
            }
        }catch(SQLException ex){
            System.err.println( ex);
            throw new RuntimeException("Error during closing database resources");
        }
    }





    @Override
    public Car getGas(long id) {
        return null;
    }

    @Override
    public Car getPetrol(long id) {
        return null;
    }

    @Override
    public Car getParts(long id) {
        return null;
    }

    @Override
    public Car getInsurance(long id) {
        return null;
    }

    @Override
    public Car getOther(long id) {
        return null;
    }



    @Override
    public List<Car> getAllGas() {
        return null;
    }

    @Override
    public List<Car> getAllPetrol() {
        return null;
    }

    @Override
    public List<Car> getAllParts() {
        return null;
    }

    @Override
    public List<Car> getAllInsurance() {
        return null;
    }

    @Override
    public List<Car> getAllOther() {
        return null;
    }




    @Override
    public void addGas(Car car) {

    }

    @Override
    public void addPetrol(Car car) {

    }

    @Override
    public void addParts(Car car) {

    }

    @Override
    public void addInsurance(Car car) {

    }

    @Override
    public void addOther(Car car) {

    }


    @Override
    public void removeGas(long autoIdToDelete) {

    }

    @Override
    public void removePetrol(long autoIdToDelete) {

    }

    @Override
    public void removeParts(long autoIdToDelete) {

    }

    @Override
    public void removeInsurance(long autoIdToDelete) {

    }

    @Override
    public void removeOther(long autoIdToDelete) {

    }
}

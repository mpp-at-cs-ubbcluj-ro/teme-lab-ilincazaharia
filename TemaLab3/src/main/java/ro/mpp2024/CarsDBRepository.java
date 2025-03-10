package ro.mpp2024;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class CarsDBRepository implements CarRepository{

    private JdbcUtils dbUtils;


    private static final Logger logger= LogManager.getLogger();

    public CarsDBRepository(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public List<Car> findByManufacturer(String manufacturerN) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement("SELECT * FROM cars WHERE manufacturer=?"))
        {
            statement.setString(1, manufacturerN);
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String manufacturer = resultSet.getString("manufacturer");
                    String model = resultSet.getString("model");
                    int year = resultSet.getInt("year");

                    Car car = new Car(manufacturer, model, year);
                    car.setId(id);
                    cars.add(car);
                }
            }
        } catch (SQLException e)
        {
            logger.error(e);
            System.err.println("Error DB "+e);
        }
        logger.traceExit(cars);
        return cars;
    }

    @Override
    public List<Car> findBetweenYears(int min, int max) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement("SELECT * FROM cars WHERE year > ? AND year < ?"))
        {
            statement.setInt(1, min);
            statement.setInt(2, max);
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String manufacturer = resultSet.getString("manufacturer");
                    String model = resultSet.getString("model");
                    int year = resultSet.getInt("year");

                    Car car = new Car(manufacturer, model, year);
                    car.setId(id);
                    cars.add(car);
                }
            }
        } catch (SQLException e)
        {
            logger.error(e);
            System.err.println("Error DB "+e);
        }
        logger.traceExit(cars);
        return cars;
    }

    @Override
    public void add(Car elem) {
        logger.traceEntry("saving task {} ", elem);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("INSERT INTO cars (manufacturer, model, year) VALUES (?,?,?)"))
        {
            preStmt.setInt(3, elem.getYear());
            preStmt.setString(1, elem.getManufacturer());
            preStmt.setString(2, elem.getModel());
            int result=preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);
        }
        catch(SQLException e)
        {
            logger.error(e);
            System.err.println("Error DB "+e);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Car elem) {
        logger.traceEntry("updating task {} with id", integer);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt = con.prepareStatement("UPDATE cars SET manufacturer = ?, model = ?, year = ? WHERE id = ?"))
        {
            preStmt.setInt(3, elem.getYear());
            preStmt.setString(1, elem.getManufacturer());
            preStmt.setString(2, elem.getModel());
            preStmt.setInt(4,integer);
            int result=preStmt.executeUpdate();
            logger.trace("Updated {} instances", result);
        }
        catch(SQLException e)
        {
            logger.error(e);
            System.err.println("Error DB "+e);
        }
        logger.traceExit();
    }

    @Override
    public Iterable<Car> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement("SELECT * FROM cars"))
        {
             try(ResultSet resultSet = statement.executeQuery()) {
                 while (resultSet.next()) {
                     int id = resultSet.getInt("id");
                     String manufacturer = resultSet.getString("manufacturer");
                     String model = resultSet.getString("model");
                     int year = resultSet.getInt("year");

                     Car car = new Car(manufacturer, model, year);
                     car.setId(id);
                     cars.add(car);
                 }
             }
        } catch (SQLException e)
        {
            logger.error(e);
            System.err.println("Error DB "+e);
        }
        logger.traceExit(cars);
        return cars;
    }
}

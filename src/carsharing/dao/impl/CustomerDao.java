package carsharing.dao.impl;

import carsharing.dao.IDao;
import carsharing.entities.Car;
import carsharing.entities.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDao implements IDao<Customer> {
    private final String tableName;
    private final Connection connection;

    public CustomerDao(Connection connection, String tableName) {
        this.connection = connection;
        this.tableName = tableName;
        
        String sql = "CREATE TABLE IF NOT EXISTS CUSTOMER " +
                "(ID INTEGER NOT NULL AUTO_INCREMENT, " +
                "RENTED_CAR_ID INTEGER, " +
                "NAME VARCHAR UNIQUE NOT NULL, " +
                "PRIMARY KEY (ID), " +
                "CONSTRAINT FK_RENTED_CAR FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID));";
        
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Customer> getById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String sql = String.format("SELECT * FROM %s", tableName);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Customer customer = new Customer(rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getInt("RENTED_CAR_ID"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return customers;
    }

    @Override
    public void save(Customer customer) {
        try {
            Statement stmt = connection.createStatement();
            String sql = String.format("INSERT INTO %s (NAME) VALUES (\'%s\')", tableName, customer.getName());
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Customer customer, String[] params) {
        try {
            Statement stmt = connection.createStatement();
            String sql = String.format("UPDATE %s SET RENTED_CAR_ID=%s WHERE ID=%s", tableName, params[0], customer.getId());
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Car> getRentedCar(int id) {
        Car car = null;
        try {
            Statement stmt = connection.createStatement();
            String sql = String.format("SELECT * " +
                            "FROM CAR " +
                            "INNER JOIN CUSTOMER " +
                            "ON CAR.ID=CUSTOMER.RENTED_CAR_ID " +
                            "WHERE CUSTOMER.ID=%s", id);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                car = new Car(
                        rs.getInt("CAR.ID"),
                        rs.getString("CAR.NAME"),
                        rs.getInt("CAR.COMPANY_ID"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return Optional.ofNullable(car);
    }

    public boolean isCarLocked(int id) {
        try {
            Statement stmt = connection.createStatement();
            String sql = String.format("SELECT 1 FROM %s WHERE RENTED_CAR_ID=%s", tableName, id);
            ResultSet rs = stmt.executeQuery(sql);
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void releaseCar(int id) {
        try {
            Statement stmt = connection.createStatement();
            String sql = String.format("UPDATE %s SET RENTED_CAR_ID = NULL WHERE ID=%s", tableName, id);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public void deleteByName(String name) {

    }
}

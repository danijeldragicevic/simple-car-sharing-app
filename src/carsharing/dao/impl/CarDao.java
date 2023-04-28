package carsharing.dao.impl;

import carsharing.dao.IDao;
import carsharing.entities.Car;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDao implements IDao<Car> {
    private final String tableName;
    private final Connection connection;

    public CarDao(Connection connection, String tableName) {
        this.connection = connection;
        this.tableName = tableName;
        
        String sql = "CREATE TABLE IF NOT EXISTS CAR " +
                "(ID INTEGER NOT NULL AUTO_INCREMENT, " +
                "COMPANY_ID INTEGER NOT NULL, " +
                "NAME VARCHAR UNIQUE NOT NULL, " +
                "PRIMARY KEY (ID), " +
                "CONSTRAINT FK_COMPANY FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID));";
        
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Car> getById(int id) {
        Car car = null;
        try {
            Statement stmt = connection.createStatement();
            String sql = String.format("SELECT * FROM %s WHERE ID=%s", tableName, id);
            ResultSet rs = stmt.executeQuery(sql);
            car = new Car(rs.getString("NAME"), rs.getInt("COMPANY_ID"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return Optional.ofNullable(car);
    }

    @Override
    public List<Car> getAll() {
        List<Car> cars = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String sql = String.format("SELECT * FROM %s", tableName);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Car car = new Car(
                        rs.getInt("ID"), 
                        rs.getString("NAME"), 
                        rs.getInt("COMPANY_ID"));
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return cars;
    }

    @Override
    public void save(Car car) {
        try {
            Statement stmt = connection.createStatement();
            String sql = String.format("INSERT INTO %s (NAME, COMPANY_ID) VALUES (\'%s\', %s)",
                    tableName, car.getName(), car.getCompanyId());
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            Statement stmt = connection.createStatement();
            String sql = String.format("DELETE FROM %s WHERE COMPANY_ID=%s", tableName, id);
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void update(Car car, String[] params) {

    }

    @Override
    public void deleteByName(String name) {

    }

    public List<Car> getByCompanyId(int companyId) {
        List<Car> cars = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String sql = String.format("SELECT * FROM %s " + "WHERE COMPANY_ID=%s", tableName, companyId);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Car car = new Car(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getInt("COMPANY_ID"));
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return cars;
    }
}

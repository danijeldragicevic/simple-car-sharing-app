package carsharing.dao.impl;

import carsharing.dao.IDao;
import carsharing.entities.Company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyDao implements IDao<Company> {
    private final String tableName;
    private final Connection connection;

    public CompanyDao(Connection connection, String tableName) {
        this.connection = connection;
        this.tableName = tableName;
        /*
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " " +
                "(ID INTEGER NOT NULL AUTO_INCREMENT," +
                "NAME VARCHAR UNIQUE NOT NULL," +
                "PRIMARY KEY (ID))";
        */
        String sql = String.format(
                "CREATE TABLE IF NOT EXISTS %s(" +
                "ID INTEGER NOT NULL AUTO_INCREMENT, " +
                "NAME VARCHAR UNIQUE NOT NULL, " +
                "PRIMARY KEY(ID))", 
                tableName);
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Company> getById(int id) {
        Company company = null;
        try {
            Statement stmt = connection.createStatement();
            String sql = String.format("SELECT * FROM %s WHERE ID=%s", tableName, id);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                company = new Company(rs.getString("NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(company);
    }

    @Override
    public List<Company> getAll() {
        List<Company> companies = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String sql = String.format("SELECT * FROM %s", tableName);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Company company = new Company(rs.getInt("id"), rs.getString("name"));
                companies.add(company);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return companies;
    }

    @Override
    public void save(Company company) {
        try {
            Statement stmt = connection.createStatement();
            String sql = String.format("INSERT INTO %s (name) VALUES (\'%s\')", tableName, company.getName());
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByName(String name) {
        try {
            Statement stmt = connection.createStatement();
            String sql = String.format("DELETE FROM %s WHERE name='%s'", tableName, name);
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void update(Company company, String[] params) {

    }

    @Override
    public void deleteById(Integer id) {

    }
}

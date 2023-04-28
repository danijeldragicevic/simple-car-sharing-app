package carsharing.controller;

import carsharing.entities.Company;
import carsharing.entities.Customer;

import java.sql.Connection;

public interface IMenuController {
    void printMainMenu(Connection connection);
    void printManagerSubMenu();
    void printCustomerSubMenu();
    void printCompanyChooseMenu();
    void printCarCompanyMenu(Company company) throws InterruptedException;
    void printCustomerChoiceMenu(Customer customer) throws InterruptedException;
}

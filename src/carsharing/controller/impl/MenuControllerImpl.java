package carsharing.controller.impl;

import carsharing.controller.IMenuController;
import carsharing.dao.impl.CarDao;
import carsharing.dao.impl.CompanyDao;
import carsharing.dao.impl.CustomerDao;
import carsharing.entities.Car;
import carsharing.entities.Company;
import carsharing.entities.Customer;
import carsharing.utils.MenuConstants;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MenuControllerImpl implements IMenuController {
    private static final Scanner scanner = new Scanner(System.in);
    private static CompanyDao companyDao;
    private static CarDao carDao;
    private static CustomerDao customerDao;

    @Override
    public void printMainMenu(Connection connection) {
        companyDao = new CompanyDao(connection, "COMPANIES");
        carDao = new CarDao(connection, "CARS", "COMPANIES");
        customerDao = new CustomerDao(connection, "CUSTOMERS", "CARS");
        
        boolean exit = false;
        while (!exit) {
            System.out.println(MenuConstants.LOG_IN_AS_A_MANAGER);
            System.out.println(MenuConstants.LOG_IN_AS_A_CUSTOMER);
            System.out.println(MenuConstants.CREATE_A_CUSTOMER);
            System.out.println(MenuConstants.EXIT_MENU);
            
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    printManagerSubMenu();
                    break;
                case "2":
                    printCustomerSubMenu();
                    break;
                case "3":
                    System.out.println("Enter the customer name:");
                    customerDao.save(new Customer(scanner.nextLine()));
                    System.out.println("The customer was created.\n");
                    break;
                case "0":
                    exit = true;
                    break;
                default:
                    System.out.println("Such value is not supported!");
            }
        }
    }

    @Override
    public void printManagerSubMenu() {
        boolean back = false;
        while (!back) {
            System.out.println(MenuConstants.COMPANY_LIST_MENU);
            System.out.println(MenuConstants.COMPANY_CREATE_MENU);
            System.out.println(MenuConstants.BACK_MENU);
            
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    printCompanyChooseMenu();
                    break;
                case "2":
                    System.out.println("Enter the company name:");
                    String companyName = scanner.nextLine();
                    companyDao.save(new Company(companyName));
                    System.out.println("The company was created.\n");
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Such value is not supported!");
            }
        }
    }
    
    @Override
    public void printCompanyChooseMenu() {
        boolean back = false;
        while (!back) {
            List<Company> companies = companyDao.getAll();
            if (companies.size() > 0) {
                System.out.println("Choose the company:");
                for (int i = 0; i < companies.size(); i++) {
                    System.out.println((i + 1) + ". " + companies.get(i).getName());
                }
                System.out.println(MenuConstants.BACK_MENU);
                String choice = scanner.nextLine();
                if (Integer.valueOf(choice) == 0) {
                    back = true;
                } else {
                    try {
                        printCarCompanyMenu(companies.get(Integer.valueOf(choice) - 1));
                    } catch (InterruptedException e) {
                        back = true;
                    }
                }
            } else {
                System.out.println("The company list is empty!\n");
                back = true;
            }
        }
    }
    
    @Override
    public void printCarCompanyMenu(Company company) throws InterruptedException {
        boolean back = false;
        while (!back) {
            System.out.println("'" + company.getName() + "' company");
            System.out.println(MenuConstants.CAR_LIST_MENU);
            System.out.println(MenuConstants.CAR_CREATE_MENU);
            System.out.println(MenuConstants.BACK_MENU);
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    List<Car> cars = carDao.getByCompanyId(company.getId());
                    if (cars.size() > 0) {
                        System.out.println("Car list:");
                        for (int i = 0; i < cars.size(); i++) {
                            System.out.println((i + 1) + ". " + cars.get(i).getName() + " rented: " + customerDao.isCarLocked(cars.get(i).getId()));
                        }
                    } else {
                        System.out.println(MenuConstants.CAR_LIST_EMPTY);
                    }
                    break;
                case "2":
                    System.out.println("Enter the car name:");
                    String carName = scanner.nextLine();
                    carDao.save(new Car(carName, company.getId()));
                    System.out.println("The car was created.\n");
                    break;
                case "0":
                    throw new InterruptedException("Exit to main menu.");
            }
        }
    }
    
    @Override
    public void printCustomerSubMenu() {
        boolean back = false;
        while (!back) {
            List<Customer> customers = customerDao.getAll();
            if (customers.size() > 0) {
                System.out.println("Choose a customer:");
                for (int i = 0; i < customers.size(); i++) {
                    System.out.println((i + 1) + ". " + customers.get(i).getName());
                }
                System.out.println(MenuConstants.BACK_MENU);
                String choice = scanner.nextLine();
                if (Integer.valueOf(choice) == 0) {
                    back = true;
                } else {
                    try {
                        printCustomerChoiceMenu(customers.get(Integer.valueOf(choice) - 1));
                    } catch (InterruptedException e) {
                        back = true;
                    }
                }
            } else {
                System.out.println("The customer list is empty!\n");
                back = true;
            }
        }
    }
    

    @Override
    public void printCustomerChoiceMenu(Customer customer) throws InterruptedException {
        boolean back = false;
        while (!back) {
            System.out.println(MenuConstants.CUSTOMER_RENT_MENU);
            System.out.println(MenuConstants.CUSTOMER_RETURN_MENU);
            System.out.println(MenuConstants.CUSTOMER_LIST_MENU);
            System.out.println(MenuConstants.BACK_MENU);
            
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": // Rent a car
                    if (customerDao.getRentedCar(customer.getId()).isPresent()) {
                        System.out.println("You've already rented a car!\n");
                        break;
                    }
                    List<Company> companies = companyDao.getAll();
                    if (companies.size() > 0) {
                        System.out.println("Choose a company:");
                        for (int i = 0; i < companies.size(); i++) {
                            System.out.println((i + 1) + ". " + companies.get(i).getName());
                        }
                        System.out.println(MenuConstants.BACK_MENU);
                        
                        String choiceCompany = scanner.nextLine();
                        if (Integer.valueOf(choiceCompany) == 0) {
                            back = true;
                        } else {
                            Integer companyId = companies.get(Integer.valueOf(choiceCompany) - 1).getId();
                            List<Car> cars = carDao.getByCompanyId(companyId)
                                    .stream()
                                    .filter(c -> !customerDao.isCarLocked(c.getId()))
                                    .collect(Collectors.toList());
                            if (cars.size() > 0) {
                                System.out.println("Choose a car:");
                                for (int i = 0; i < cars.size(); i++) {
                                    System.out.println((i + 1) + ". " + cars.get(i).getName());
                                }
                                int index = scanner.nextInt() ;
                                index = index <= 0 ? 1 : index;
                                Integer carId = cars.get(index - 1).getId();
                                String[] params = new String[]{Integer.toString(carId)};
                                customerDao.update(customer, params);
                                System.out.println("You rented '" + cars.get(index - 1).getName() + "'");
                            } else {
                                System.out.println(MenuConstants.CAR_LIST_EMPTY);
                            }
                        }
                    } else {
                        System.out.println("The company list is empty!\n");
                        back = true;
                    }
                    break;

                case "2": // Return a rented car
                    Optional<Car> rentedCar = customerDao.getRentedCar(customer.getId());
                    if (rentedCar.isPresent()) {
                        customerDao.releaseCar(customer.getId());
                        System.out.println("You've returned a rented car: " + rentedCar.get().getName() + ".\n");
                    } else {
                        System.out.println("You didn't rent a car!\n");
                    }
                    break;

                case "3": // My rented car
                    Optional<Car> myCar = customerDao.getRentedCar(customer.getId());
                    if (myCar.isPresent()) {
                        System.out.println("Your rented car:");
                        System.out.println(myCar.get().getName());
                        System.out.println("Company:");
                        System.out.println(companyDao.getById(myCar.get().getCompanyId()).get().getName());
                    } else {
                        System.out.println("You didn't rent a car!\n");
                    }
                    break;
                case "0":
                    throw new InterruptedException("Exit to main menu!\n");
            }
        }
    }
}

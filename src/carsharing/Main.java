package carsharing;

import carsharing.controller.IMenuController;
import carsharing.controller.impl.MenuControllerImpl;
import carsharing.utils.DbUtil;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection connection = DbUtil.openConnection();
        
        IMenuController controller = new MenuControllerImpl();
        controller.printMainMenu(connection);
        
        DbUtil.closeConnection(connection);
    }
}

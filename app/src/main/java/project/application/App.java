/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package project.application;

import project.display.MainDisplay;
import project.logic.ILogic;
import project.logic.Logic;
import project.persistence.Database;
import project.persistence.DatabaseStub;

public class App {
    public static void main(String[] args) {
        Database database = new DatabaseStub();

        // The "nextBarcode" for logic has to be hardcoded based on the test data
        // That's set up when the DatabaseStub is initialized.
        ILogic logic = new Logic(database, 2);
        MainDisplay display = new MainDisplay(logic);
    }
}

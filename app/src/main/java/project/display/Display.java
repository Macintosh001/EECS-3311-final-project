package project.display;

import project.display.views.AdminView;
import project.display.views.EmployeeView;
import project.display.views.LoginView;
import project.display.views.ManagerView;
import project.display.views.StockCheckingView;
import project.logic.StockCheckingLogic;

import javax.swing.*;
import java.util.Stack;

public class Display extends JFrame {
    private final LoginView loginView;

    private final EmployeeView employeeView;
    private final ManagerView managerView;
    private final AdminView adminView;

    private final StockCheckingView stockCheckingView;

    private final Stack<JPanel> viewStack;
    private JPanel currentPanel;

    public Display(
            StockCheckingLogic stockCheckingLogic
    ) {
        // Set up the JFrame
        super("TIM");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700 + 30);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        this.loginView = new LoginView(this);

        this.employeeView = new EmployeeView(this);
        this.managerView = new ManagerView(this);
        this.adminView = new AdminView(this);

        this.stockCheckingView = new StockCheckingView(this, stockCheckingLogic);

        add(loginView);

        add(employeeView);
        add(managerView);
        add(adminView);

        add(stockCheckingView);

        setVisible(true);

        // Setup the Stack
        this.viewStack = new Stack<>();
        currentPanel = loginView;
        currentPanel.setVisible(true);
    }

    public void advanceTo(JPanel panel) {
        currentPanel.setVisible(false);
        viewStack.push(currentPanel);
        currentPanel = panel;
        currentPanel.setVisible(true);
    }

    public void back() {
        currentPanel.setVisible(false);
        currentPanel = viewStack.pop();
        currentPanel.setVisible(true);
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public EmployeeView getEmployeeView() {
        return employeeView;
    }

    public ManagerView getManagerView() {
        return managerView;
    }

    public AdminView getAdminView() {
        return adminView;
    }

    public StockCheckingView getStockCheckingView() {
        return stockCheckingView;
    }
}

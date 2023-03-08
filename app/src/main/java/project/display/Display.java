package project.display;

import project.display.views.*;
import project.logic.StockCheckingLogic;
import project.logic.StockManagingLogic;

import javax.swing.*;
import java.util.Stack;

public class Display extends JFrame {
    private final LoginView loginView;

    private final EmployeeView employeeView;
    private final ManagerView managerView;
    private final AdminView adminView;

    private final StockCheckingView stockCheckingView;
    private final StockManagingView stockManagingView;

    private final Stack<JPanel> viewStack;
    private JPanel currentPanel;

    public Display(
            StockCheckingLogic stockCheckingLogic,
            StockManagingLogic stockManagingLogic
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
        this.stockManagingView = new StockManagingView(this, stockManagingLogic);

        add(loginView);

        add(employeeView);
        add(managerView);
        add(adminView);

        add(stockCheckingView);
        add(stockManagingView);

        setVisible(true);

        // Setup the Stack
        this.viewStack = new Stack<>();
        currentPanel = adminView;
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

    public StockManagingView getStockManagingView() {
        return stockManagingView;
    }
}

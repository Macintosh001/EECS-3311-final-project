package project.display;

import project.display.views.*;
import project.logic.*;

import javax.swing.*;
import java.util.Stack;

public class Display extends JFrame {
    private final LoginView loginView;
    private final ModifierManagerView modView;
    private final EmployeeView employeeView;
    private final ManagerView managerView;
    private final AdminView adminView;

    private final StockCheckingView stockCheckingView;
    private final StockManagingView stockManagingView;
    private final OrderView orderView;
    private final CouponManagerView couponManagerView;
    private final SaleView saleView;
    private final AutomationView autoView;

    private final Stack<JPanel> viewStack;
    private JPanel currentPanel;

    private final AutomationLogic automationLogic;

    /**
     * @param stockCheckingLogic logic object associated with the stock checking view
     * @param stockManagingLogic logic object associated with the stock managing view
     * @param orderLogic logic object associated with the order view
     * @param couponManagerLogic logic object associated with the coupon manager view
     * @param saleLogic logic object associated with the POS view
     */
    public Display(
            StockCheckingLogic stockCheckingLogic,
            StockManagingLogic stockManagingLogic,
            OrderLogic orderLogic,
            CouponManagerLogic couponManagerLogic,
            SaleLogic saleLogic,
            ModifierManagerLogic modLogic,
            AutomationLogic aLogic
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
        this.orderView = new OrderView(this, orderLogic);
        this.couponManagerView = new CouponManagerView(this, couponManagerLogic);
        this.saleView = new SaleView(this, saleLogic);
        this.modView = new ModifierManagerView(this, modLogic);
        this.autoView = new AutomationView(this, aLogic);

        this.automationLogic = aLogic;

        add(loginView);

        add(employeeView);
        add(managerView);
        add(adminView);

        add(stockCheckingView);
        add(stockManagingView);
        add(orderView);
        add(couponManagerView);
        add(saleView);
        add(modView);
        add(autoView);

        setVisible(true);

        // Setup the Stack
        this.viewStack = new Stack<>();
        currentPanel = loginView;
        currentPanel.setVisible(true);
    }

    /**
     * @param panel pass in the JPanel that needs to be updated
     */
    private void refresh(JPanel panel) {
        if (panel instanceof ViewWithTable) {
            ((ViewWithTable) panel).regenTable();
        }
    }

    /**
     * @param panel pass in the next JPanel
     */
    public void advanceTo(JPanel panel) {
        // hide the current panel
        currentPanel.setVisible(false);

        // run automation tasks
        automationLogic.automate();

        // show the next panel
        viewStack.push(currentPanel);
        currentPanel = panel;
        refresh(currentPanel);
        currentPanel.setVisible(true);
    }

    /**
     * Go back to the last JPanel
     */
    public void back() {
        // hide the current panel
        currentPanel.setVisible(false);

        // run automation tasks
        automationLogic.automate();
        if (viewStack.empty()) {
            dispose();
        } else {
            currentPanel = viewStack.pop();
            refresh(currentPanel);
            currentPanel.setVisible(true);
        }
    }

    /**
     * @return the a reference to the Login view
     */
    public LoginView getLoginView() {
        return loginView;
    }

    /**
     * @return a reference to the Employee view
     */
    public EmployeeView getEmployeeView() {
        return employeeView;
    }

    /**
     * @return a reference to the Manager View
     */
    public ManagerView getManagerView() {
        return managerView;
    }

    /**
     * @return a reference to the Admin View
     */
    public AdminView getAdminView() {
        return adminView;
    }

    /**
     * @return a reference to the Stock Checking View
     */
    public StockCheckingView getStockCheckingView() {
        return stockCheckingView;
    }

    /**
     * @return a reference to the Stock Managing View
     */
    public StockManagingView getStockManagingView() {
        return stockManagingView;
    }

    /**
     * @return a reference to the Order View
     */
    public OrderView getOrderView() {
        return orderView;
    }

    /**
     * @return a reference to the Coupon Manager View
     */
    public CouponManagerView getCouponManagerView() {
        return couponManagerView;
    }

    /**
     * @return a reference to the Sale/POS View
     */
    public SaleView getSaleView() {
        return saleView;
    }

    public ModifierManagerView getModView(){ return modView; }
    public AutomationView getAutoView(){return autoView;}
}

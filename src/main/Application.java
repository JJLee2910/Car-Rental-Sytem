package main;

import main.backend.db.Database;
import main.backend.db.repository.*;
import main.backend.model.booking.Booking;
import main.backend.model.car.Car;
import main.backend.model.user.Admin;
import main.backend.model.user.Customer;
import main.backend.service.*;
import main.backend.session.SessionContainer;
import main.backend.session.SessionContainerImpl;
import main.frontend.ui.*;

import javax.swing.*;
import java.util.Optional;

public class Application {

    private AdminRepository adminRepository;

    private CustomerService customerService;
    private AdminService adminService;
    private CarService carService;
    private BookingService bookingService;
    private PaymentService paymentService;
    private LoginService loginService;

    private JFrame jFrame;
    private LoginPage loginPage;
    private CustomerPage customerRegisterPage;
    private CustomerPage customerEditPage;
    private AdminMenuPage adminMenuPage;
    private UserMenuPage userMenuPage;
    private CarMenuPage carMenuPage;
    private CarPage addCarPage;
    private CarPage editCarPage;
    private CarBookingPage bookCarPage;
    private CustomerBookingHistoryPage customerBookingHistoryPage;
    private AdminBookingPage adminBookingPage;
    private PaymentDetailsPage paymentDetailsPage;
    private DailySalesReportPage dailySalesReportPage;
    private MonthlyCarRevenueReportPage monthlyCarRevenuePage;

    private SessionContainer sessionContainer = new SessionContainerImpl();
    private Optional<BasePage> currentPage = Optional.empty();

    public static void main(String[] args) {
        new Application();
    }

    /**
     * 1. initialize database ensure files are ready/created
     * 2. define the UI of each functions
     * 3. check user data
     * 4. defining frames
     * 5. initialization of the system
     */
    public Application() {
        initializeDatabase();
        initializeUI();
        seedDataIfNew();
        initJFrame();
        toLogin();
    }


    private void initializeUI() {

        loginPage = new LoginPage(this, loginService);

        customerRegisterPage = new CustomerPage(this, customerService::registerCustomer, true);

        adminMenuPage = new AdminMenuPage(this);

        carMenuPage = new CarMenuPage(this, carService);

        addCarPage = new CarPage(this, carService, CarPage.Mode.ADD);

        editCarPage = new CarPage(this, carService, CarPage.Mode.EDIT);

        userMenuPage = new UserMenuPage(this);

        bookCarPage = new CarBookingPage(this, carService, bookingService);

        customerBookingHistoryPage = new CustomerBookingHistoryPage(this, bookingService);

        adminBookingPage = new AdminBookingPage(this, bookingService, customerService, paymentService);

        paymentDetailsPage = new PaymentDetailsPage(this, customerService, carService);

        dailySalesReportPage = new DailySalesReportPage(this, paymentService);

        monthlyCarRevenuePage = new MonthlyCarRevenueReportPage(this, bookingService, paymentService);

        customerEditPage = new CustomerPage(this, customerService::updateCustomer, false);
    }

    // direct to login page
    public void toLogin() {
        toPage(loginPage);
    }

    // direct to car menu
    public void toCarMenu() {
        toPage(carMenuPage);
    }

    // direct to add car
    public void toAddCar() {
        toPage(addCarPage);
    }

    // direct to booking history
    public void toCustomerBookingHistory() {
        toPage(customerBookingHistoryPage);
    }

    // direct to booking page
    public void toAdminBookingPage() {
        toPage(adminBookingPage);
    }

    public void toPaymentDetailPage(Booking booking) {
        toPage(paymentDetailsPage);
        paymentDetailsPage.initializeData(booking);
    }

    // direct to daily sales report page
    public void toDailySalesReportPage() {
        toPage(dailySalesReportPage);
    }

    public void toPage(BasePage page) {
        toPage(page, false);
    }

    public void backToPage(BasePage page) {
        toPage(page, true);
    }

    // direct from page to page
    public void toPage(BasePage page, boolean isBack) {
        currentPage.ifPresent(BasePage::close);
        if (!isBack)
            page.setPreviousPage(currentPage.orElse(null));
        currentPage = Optional.of(page);
        jFrame.setContentPane(page.getPanel());
        page.open();
        jFrame.setVisible(true);
    }

    private void initJFrame() {
        jFrame = new JFrame("Car Rental System");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(800,600);
    }

    // check for admin user first
    private void seedDataIfNew() {
        if (adminRepository.getTotalRecord() == 0) {
            Admin admin = new Admin("admin", "admin");
            adminRepository.insert(admin);
        }
    }

    private void initializeDatabase() {
        Database database = new Database("car-rental");
        CustomerRepository customerRepository = new CustomerRepository("customer.txt", database);
        adminRepository = new AdminRepository("admin.txt", database);
        CarRepository carRepository = new CarRepository("car.txt", database);
        BookingRepository bookingRepository = new BookingRepository("booking.txt", database);
        PaymentRepository paymentRepository = new PaymentRepository("payment.txt", database);

        bookingService = new BookingServiceImpl(bookingRepository);
        paymentService = new PaymentServiceImpl(paymentRepository);
        carService = new CarServiceImpl(carRepository);
        customerService = new CustomerServiceImpl(customerRepository, adminRepository);
        adminService = new AdminServiceImpl(adminRepository);
        loginService = new LoginServiceImpl(adminRepository, customerRepository);
    }

    // middle layer validation for users
    public SessionContainer getSessionContainer() {
        return sessionContainer;
    }

    public void toEditCar(Car car) {
        toPage(editCarPage);
        editCarPage.initializeCarPage(car);
    }
/*
* functions diverting to different pages
*/
    public void toBookCar() {
        toPage(bookCarPage);
    }

    public void toCarMonthlyRevenueReportPage() {
        toPage(monthlyCarRevenuePage);
    }

    public void toCustomerRegister() {
        toPage(customerRegisterPage);
    }

    public void toAdminMenu() {
        toPage(adminMenuPage);
    }

    public void toCustomerMenu() {
        toPage(userMenuPage);
    }

    public void toCustomerEdit(Customer customer) {
        toPage(customerEditPage);
        customerEditPage.initializeData(customer);
    }
}

package main.frontend.ui;

import main.Application;
import main.backend.model.car.Car;
import main.backend.service.CarService;
import main.frontend.ui.table.model.CarTableModel;
import main.frontend.util.SwingUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CarMenuPage extends BasePage {
    private JPanel mainPanel;
    private JTable carTable;
    private JButton addNewCarButton;
    private JButton backButton;
    private JButton editCarButton;
    private JButton removeCarButton;

    private CarTableModel tableModel;

    private final CarService carService;

    public CarMenuPage(Application application, CarService carService) {
        super(application);
        this.carService = carService;

        backButton.addActionListener(this::backToPreviousPage);
        addNewCarButton.addActionListener(e -> application.toAddCar());
        removeCarButton.addActionListener(this::removeCar);
        editCarButton.addActionListener(this::editCar);
    }

    /** edit selected car detail
     */
    private void editCar(ActionEvent event) {
        int[] selectedRows = carTable.getSelectedRows();

        if (selectedRows.length != 1) {
            SwingUtils.promptMessageError("Must only select one car!");
            return;
        }

        Car car = tableModel.getModelAt(selectedRows[0]);

        application.toEditCar(car);
    }

    /** remove selected car
     * unable to delete when no car selected
     */
    private void removeCar(ActionEvent event) {
        int[] selectedRows = carTable.getSelectedRows();

        if (selectedRows.length == 0) {
            SwingUtils.promptMessageError("No car selected!");
            return;
        }

        List<Car> carList = tableModel.getModelAt(selectedRows);
        carList.forEach(carService::removeCar);
        carTable.clearSelection();
        SwingUtils.promptMessageInfo("Deleted successfully!");
        refreshData();
    }

    private void createUIComponents() {
        tableModel = new CarTableModel();
        carTable = new JTable(tableModel);
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }

    @Override
    public void refreshData() {
        tableModel.refreshData(carService.getAllCars());
        carTable.addNotify();
    }

}

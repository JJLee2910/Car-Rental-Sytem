package main.frontend.ui.table.model;

import main.backend.model.car.Car;

public class CarTableModel extends ListTableModel<Car> {
    /** return table of car list*/
    private static final String[] COLUMN_NAMES = {"Plate No.", "Brand", "Model", "Seats", "Transmission", "Price"};
    private static final ModelObjectAdapter<Car> ADAPTER = (obj, idx) -> {
        switch (idx) {
            case 0:
                return obj.getCarPlate();
            case 1:
                return obj.getBrand();
            case 2:
                return obj.getModel();
            case 3:
                return obj.getSeats();
            case 4:
                return obj.getTransmission().name();
            case 5:
                return obj.getPrice().toString();
            default:
                throw new IllegalArgumentException("Exceed column size");
        }
    };

    public CarTableModel() {
        super(COLUMN_NAMES, ADAPTER);
    }
}

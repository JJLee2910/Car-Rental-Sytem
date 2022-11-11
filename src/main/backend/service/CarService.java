package main.backend.service;

import main.backend.model.car.Car;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CarService {
    void addCar(Car car);
    /**
     * @return all car list
     */
    List<Car> getAllCars();

    /**
     * @return car list within start date , end date
     */
    List<Car> getCarsWithinDates(LocalDate startDate, LocalDate endDate);

    /**
     * return car plate
     */
    Car getByCarPlate(String carPlate);

    /**
     * filter car plate with unbooked
     */
    List<Car> getAllCarsExcept(Collection<String> bookedCarPlate);

    void updateCar(Car car);

    Optional<Car> findByCarPlate(String text);

    void removeCar(Car car);
}

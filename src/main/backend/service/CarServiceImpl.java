package main.backend.service;

import main.backend.db.repository.CarRepository;
import main.backend.model.car.Car;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    @Override
    public void addCar(Car car) {
        car.setCarPlate(car.getCarPlate().toLowerCase());
        carRepository.insert(car);
    }

    @Override
    public Optional<Car> findByCarPlate(String carPlate) {
        return carRepository.findByPrimaryKey(carPlate.toLowerCase());
    }

    @Override
    public void removeCar(Car car) {
        carRepository.remove(car);
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.getLines().collect(Collectors.toList());
    }

    @Override
    public List<Car> getCarsWithinDates(LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public Car getByCarPlate(String carPlate) {
        return carRepository.getByPrimaryKey(carPlate);
    }

    @Override
    public List<Car> getAllCarsExcept(Collection<String> bookedCarPlate) {
        return carRepository.getLines()
                .filter(car -> !bookedCarPlate.contains(car.getCarPlate()))
                .collect(Collectors.toList());
    }

    @Override
    public void updateCar(Car car) {
        carRepository.update(car);
    }
}

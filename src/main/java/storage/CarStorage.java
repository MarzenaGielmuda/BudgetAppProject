package storage;

import type.Car;
import type.Service;

import java.util.List;

public interface CarStorage {

    Service getService(long id);
    Car getGas(long id);
    Car getPetrol(long id);
    Car getParts(long id);
    Car getInsurance(long id);
    Car getOther(long id);


    List<Service> getAllService();
    List<Car> getAllGas();
    List<Car> getAllPetrol();
    List<Car> getAllParts();
    List<Car> getAllInsurance();
    List<Car> getAllOther();

    void addService(Service service);
    void addGas(Car car);
    void addPetrol(Car car);
    void addParts(Car car);
    void addInsurance(Car car);
    void addOther(Car car);


    void removeService(long autoIdToDelete);
    void removeGas(long autoIdToDelete);
    void removePetrol(long autoIdToDelete);
    void removeParts(long autoIdToDelete);
    void removeInsurance(long autoIdToDelete);
    void removeOther(long autoIdToDelete);
}

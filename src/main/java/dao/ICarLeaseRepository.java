package dao;

import entity.Vehicle;
import entity.Customer;
import entity.Lease;

import java.util.Date;
import java.util.List;

public interface ICarLeaseRepository {
	// Car Management
    void addCar(Vehicle car);

    void removeCar(int carID);

    List<Vehicle> listAvailableCars();

    List<Vehicle> listRentedCars();

    Vehicle findCarById(int vehicleID);
    
    // Customer Management
    void addCustomer(Customer customer);

    void removeCustomer(int customerID);

    List<Customer> listCustomers();

    Customer findCustomerById(int customerID);
    
    // Lease Management
    Lease createLease(int customerID, int carID, Date startDate, Date endDate);

    void returnCar(int leaseID);

    List<Lease> listActiveLeases();

    List<Lease> listLeaseHistory();
    
    Lease findLeaseByID(int LeaseID);
    
    // Payment Handling
    void recordPayment(int lease, double amount);
}

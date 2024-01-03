package main;

//importing entity packages
import entity.Vehicle;
import entity.Customer;
import entity.Lease;
//importing dao packages
import dao.ICarLeaseRepositoryImpl;
import dao.ICarLeaseRepository;
//importing myexceptions
import myexceptions.CarNotFoundException;
import myexceptions.LeaseNotFoundException;
//importing myexceptions
import myexceptions.CarNotFoundException;
import myexceptions.LeaseNotFoundException;
import myexceptions.CustomerNotFoundException;

import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
			
			ICarLeaseRepository carLeaseRepository = new ICarLeaseRepositoryImpl();
			
			int choice;
			do {
				System.out.println("\nCar Rental Application Menu:");
	            System.out.println("1. Add Car");
	            System.out.println("2. Remove Car");
	            System.out.println("3. List Available Cars");
	            System.out.println("4. List Rented Cars");
	            System.out.println("5. Add Customer");
	            System.out.println("6. Remove Customer");
	            System.out.println("7. List Customers");
	            System.out.println("8. Create Lease");
	            System.out.println("9. Return Car");
	            System.out.println("10. List Active Leases");
	            System.out.println("11. List Lease History");
	            System.out.println("12. Find Car by ID");
	            System.out.println("13. Find Customer by ID");
	            System.out.println("14. Find Lease by ID");
	            System.out.println("15. Record Payment");
	            System.out.println("0. Exit");

	            System.out.print("Enter your choice: ");
	            choice = scanner.nextInt();
	            scanner.nextLine(); // Consume the newline character
				
				
				switch(choice) {
					case 1:
						// Add Car
	                    System.out.println("Enter car details:");
	                    System.out.print("Make: ");
	                    String make = scanner.nextLine();
	                    System.out.print("Model: ");
	                    String model = scanner.nextLine();
	                    System.out.print("Year: ");
	                    int year = scanner.nextInt();
	                    System.out.print("Daily Rate: ");
	                    double dailyRate = scanner.nextDouble();
	                    scanner.nextLine(); // Consume the newline character
	                    System.out.print("Passenger Capacity: ");
	                    int passengerCapacity = scanner.nextInt();
	                    System.out.print("Engine Capacity: ");
	                    double engineCapacity = scanner.nextDouble();

	                    Vehicle newCar = new Vehicle();
	                    newCar.setMake(make);
	                    newCar.setModel(model);
	                    newCar.setYear(year);
	                    newCar.setDailyRate(dailyRate);
	                    newCar.setPassengerCapacity(passengerCapacity);
	                    newCar.setEngineCapacity(engineCapacity);
	                    newCar.setStatus("available");

	                    carLeaseRepository.addCar(newCar);
	                    System.out.println("Car added successfully!");
	                    break;
	                case 2:
	                	System.out.print("Enter Car ID to remove: ");
	                    int carIDToRemove = scanner.nextInt();
	                    try
	                    {
	                    	carLeaseRepository.removeCar(carIDToRemove);
		                    System.out.println("Car removed successfully!");
	                    }
	                    catch(CarNotFoundException e)
	                    {
	                        System.out.println("Car not found.");
	                    }
	                    break;
	                case 3:
	                	// List Available Cars
	                    List<Vehicle> availableCars = carLeaseRepository.listAvailableCars();
	                    System.out.println("Available Cars:");
	                    for (Vehicle car : availableCars) {
	                        System.out.println(car);
	                    }
	                    break;
	                case 4:
	                	// List Rented Cars
	                    List<Vehicle> rentedCars = carLeaseRepository.listRentedCars();
	                    System.out.println("Rented Cars:");
	                    for (Vehicle car : rentedCars) {
	                        System.out.println(car);
	                    }
	                    break;
	                case 5:
	                	// Add Customer
	                    System.out.println("Enter customer details:");
	                    System.out.print("First Name: ");
	                    String firstName = scanner.nextLine();
	                    System.out.print("Last Name: ");
	                    String lastName = scanner.nextLine();
	                    System.out.print("Email: ");
	                    String email = scanner.nextLine();
	                    System.out.print("Phone Number: ");
	                    String phoneNumber = scanner.nextLine();

	                    Customer newCustomer = new Customer();
	                    newCustomer.setFirstName(firstName);
	                    newCustomer.setLastName(lastName);
	                    newCustomer.setEmail(email);
	                    newCustomer.setPhoneNumber(phoneNumber);

	                    carLeaseRepository.addCustomer(newCustomer);
	                    System.out.println("Customer added successfully!");
	                    break;
	                case 6:
	                	//Remove Customer
	                	System.out.print("Enter Customer ID to remove: ");
	                    int customerIDToRemove = scanner.nextInt();
	                    try
	                    {
		                    carLeaseRepository.removeCustomer(customerIDToRemove);
		                    System.out.println("Customer removed successfully!");
	                    }
	                    catch(CustomerNotFoundException e)
	                    {
	                    	System.out.println("Customer not found.");
	                    }
	                    break;
	                case 7:
	                	// List Customers
	                    List<Customer> customers = carLeaseRepository.listCustomers();
	                    System.out.println("Customers:");
	                    for (Customer customer : customers) {
	                        System.out.println(customer);
	                    }
	                    break;
	                case 8:
	                	// Create Lease
	                    System.out.println("Enter lease details:");
	                    System.out.print("Customer ID: ");
	                    int customerID = scanner.nextInt();
	                    System.out.print("Car ID: ");
	                    int carID = scanner.nextInt();
	                    scanner.nextLine(); // Consume the newline character
	                    System.out.print("Start Date (yyyy-MM-dd): ");
	                    String startDateString = scanner.nextLine();
	                    System.out.print("End Date (yyyy-MM-dd): ");
	                    String endDateString = scanner.nextLine();
	                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	                    try {
	                        Date startDate = dateFormat.parse(startDateString);
	                        Date endDate = dateFormat.parse(endDateString);

	                        Lease newLease = carLeaseRepository.createLease(customerID, carID, startDate, endDate);
	                        if(newLease==null)
	                        {
	                        	System.out.println("Car unavailabe Lease Not created");
	                        	break;
	                        }
	                        System.out.println("Lease created successfully:\n" + newLease);
	                    } catch (ParseException e) {
	                        System.out.println("Invalid date format. Please enter dates in the format yyyy-MM-dd.");
	                    }
	                    catch(CarNotFoundException e)
	                	{
	            			System.out.println("Car Not Found");
	                	}
	                	catch(CustomerNotFoundException e)
	                	{
	                		System.out.println("Customer Not Found");
	                	}
	                    break;
	                case 9:
	                	 // Return Car
	                    System.out.print("Enter Lease ID to return the car: ");
	                    int leaseIDToReturn = scanner.nextInt();
	                    try
	                    {
	                    	carLeaseRepository.returnCar(leaseIDToReturn);
		                    System.out.println("Car returned successfully!");
	                    }
	                    catch(LeaseNotFoundException e)
	                	{
	            			System.out.println("Lease Not Found");
	                	}
	                    break;
	                case 10:
	                	// List Active Leases
	                    List<Lease> activeLeases = carLeaseRepository.listActiveLeases();
	                    System.out.println("Active Leases:");
	                    for (Lease lease : activeLeases) {
	                        System.out.println(lease);
	                    }
	                    break;
	                case 11:
	                	// List Lease History
	                    List<Lease> leaseHistory = carLeaseRepository.listLeaseHistory();
	                    System.out.println("Lease History:");
	                    for (Lease lease : leaseHistory) {
	                        System.out.println(lease);
	                    }
	                    break;
	                case 12:
	                	// Find Car by ID
	                    System.out.print("Enter Car ID to find: ");
	                    int carIDToFind = scanner.nextInt();
	                    try
	                    {
	                    	Vehicle foundCar = carLeaseRepository.findCarById(carIDToFind);
	                        System.out.println("Found Car:\n" + foundCar);

	                    }
	                    catch(CarNotFoundException e)
	                    {
	                        System.out.println("Car not found.");
	                    }
	                    break;
	                case 13:
	                	// Find Customer by ID
	                    System.out.print("Enter Customer ID to find: ");
	                    int customerIDToFind = scanner.nextInt();
	                    try
	                    {
	                    	Customer foundCustomer = carLeaseRepository.findCustomerById(customerIDToFind);
	                    	System.out.println("Found Customer:\n" + foundCustomer);
	                    }
	                    catch(CustomerNotFoundException e)
	                    {
	                    	System.out.println("Customer not found.");
	                    }
	                    
	                    
	                    break;
	                case 14:
	                	// Find Lease by ID
	                    System.out.print("Enter Lease ID to find: ");
	                    int leaseIDToFind = scanner.nextInt();
	                    try
	                    {
	                    	Lease foundLease = carLeaseRepository.findLeaseByID(leaseIDToFind);
	                    	System.out.println("Found Lease:\n" + foundLease);
	                    }
	                    catch(LeaseNotFoundException e)
	                    {
	                    	System.out.println("Lease not found.");
	                    }
	                    
	                    break;
	                case 15:
	                	// Record Payment
	                    System.out.print("Enter Lease ID for payment: ");
	                    int leaseIDForPayment = scanner.nextInt();
	                    System.out.print("Enter payment amount: ");
	                    double paymentAmount = scanner.nextDouble();
	                    carLeaseRepository.recordPayment(leaseIDForPayment, paymentAmount);
	                    System.out.println("Payment recorded successfully!");
	                    break;
	                case 0:
	                    System.out.println("Exiting the Car Rental System. Goodbye!");
	                    System.exit(0);
	                    break;
	                default:
	                    System.out.println("Invalid choice. Please enter a valid option.");
				}
				
			}while(choice != 0);
			
			
			
	}
}	

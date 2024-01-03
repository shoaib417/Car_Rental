package dao;

import util.DBConnection;

import entity.Vehicle;
import myexceptions.CarNotFoundException;
import myexceptions.CustomerNotFoundException;
import myexceptions.LeaseNotFoundException;
import entity.Customer;
import entity.Lease;

//import myexceptions.CarNotFoundException;
//import myexceptions.CustomerNotFoundException;
//import myexceptions.LeaseNotFoundException;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ICarLeaseRepositoryImpl implements ICarLeaseRepository {
	
	private Connection connection;

    public ICarLeaseRepositoryImpl() {
        this.connection = DBConnection.getConnection();
    }

    // Implement methods for Car Management
    @Override
    public void addCar(Vehicle car) {
    	try (PreparedStatement statement = connection.prepareStatement(
    			"INSERT INTO Vehicle (make, model, year, dailyRate, status, passengerCapacity, engineCapacity) " + 
    					"VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, car.getMake());
            statement.setString(2, car.getModel());
            statement.setInt(3, car.getYear());
            statement.setDouble(4, car.getDailyRate());
            statement.setString(5, car.getStatus());
            statement.setInt(6, car.getPassengerCapacity());
            statement.setDouble(7, car.getEngineCapacity());
            
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating car failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedCarID = generatedKeys.getInt(1);
                    car.setVehicleID(generatedCarID);
                } else {
                    throw new SQLException("Creating car failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
//        cars.add(car);
    }

    @Override
    public void removeCar(int carID) {
    	int result=0;
    	try (PreparedStatement statement = connection.prepareStatement("DELETE FROM Vehicle WHERE vehicleID = ?")) {
            statement.setInt(1, carID);
            result=statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    	if(result==0)
    	{
    		throw new CarNotFoundException("Car not found");
    	}
    	
    }

    @Override
    public List<Vehicle> listAvailableCars() {
        List<Vehicle> availableCars = new ArrayList<>();
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Vehicle WHERE status = 'available'")) {

               while (resultSet.next()) {
                   Vehicle car = new Vehicle();
                   car.setVehicleID(resultSet.getInt("vehicleID"));
                   car.setMake(resultSet.getString("make"));
                   car.setModel(resultSet.getString("model"));
                   car.setYear(resultSet.getInt("year"));
                   car.setDailyRate(resultSet.getDouble("dailyRate"));
                   car.setStatus(resultSet.getString("status"));
                   car.setPassengerCapacity(resultSet.getInt("passengerCapacity"));
                   car.setEngineCapacity(resultSet.getInt("engineCapacity"));

                   availableCars.add(car);
               }
           } catch (SQLException e) {
               e.printStackTrace();
               // Handle exception
           }

           return availableCars;
    }

    @Override
    public List<Vehicle> listRentedCars() {
        List<Vehicle> rentedCars = new ArrayList<>();
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Vehicle WHERE status = 'unavailable'")) {

               while (resultSet.next()) {
                   Vehicle car = new Vehicle();
                   car.setVehicleID(resultSet.getInt("vehicleID"));
                   car.setMake(resultSet.getString("make"));
                   car.setModel(resultSet.getString("model"));
                   car.setYear(resultSet.getInt("year"));
                   car.setDailyRate(resultSet.getDouble("dailyRate"));
                   car.setStatus(resultSet.getString("status"));
                   car.setPassengerCapacity(resultSet.getInt("passengerCapacity"));
                   car.setEngineCapacity(resultSet.getInt("engineCapacity"));

                   rentedCars.add(car);
               }
           } catch (SQLException e) {
               e.printStackTrace();
               // Handle exception
           }

           return rentedCars;
    }

    @Override
    public Vehicle findCarById(int carID) {
    	try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM Vehicle WHERE vehicleID = ?")) {
            statement.setInt(1, carID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Vehicle car = new Vehicle();
                car.setVehicleID(resultSet.getInt("vehicleID"));
                car.setMake(resultSet.getString("make"));
                car.setModel(resultSet.getString("model"));
                car.setYear(resultSet.getInt("year"));
                car.setDailyRate(resultSet.getDouble("dailyRate"));
                car.setStatus(resultSet.getString("status"));
                car.setPassengerCapacity(resultSet.getInt("passengerCapacity"));
                car.setEngineCapacity(resultSet.getInt("engineCapacity"));

                return car;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    	throw new CarNotFoundException("Car not found");
    }
 // Implement methods for Customer Management
    @Override
    public void addCustomer(Customer customer) {
    	 try (PreparedStatement statement = connection.prepareStatement(
    			 "INSERT INTO Customer (firstName, lastName, email, phoneNumber) " +
                         "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
             statement.setString(1, customer.getFirstName());
             statement.setString(2, customer.getLastName());
             statement.setString(3, customer.getEmail());
             statement.setString(4, customer.getPhoneNumber());

             statement.executeUpdate();

             ResultSet generatedKeys = statement.getGeneratedKeys();
             if (generatedKeys.next()) {
                 customer.setCustomerID(generatedKeys.getInt(1));
             }
         } catch (SQLException e) {
             e.printStackTrace();
             // Handle exception
         }
    }

    @Override
    public void removeCustomer(int customerID) {
    	int result=0;
    	 try (PreparedStatement statement = connection.prepareStatement(
                 "DELETE FROM Customer WHERE customerID = ?")) {
             statement.setInt(1, customerID);
             result=statement.executeUpdate();
         } catch (SQLException e) {
             e.printStackTrace();
             // Handle exception
         }
    	 if(result==0)
    	 {
    		 throw new CustomerNotFoundException("Customer Not Found");
    	 }
    }

    @Override
    public List<Customer> listCustomers() {
    	List<Customer> customers = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Customer")) {

            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(resultSet.getInt("customerID"));
                customer.setFirstName(resultSet.getString("firstName"));
                customer.setLastName(resultSet.getString("lastName"));
                customer.setEmail(resultSet.getString("email"));
                customer.setPhoneNumber(resultSet.getString("phoneNumber"));

                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }

        return customers;
    }

    @Override
    public Customer findCustomerById(int customerID) {
    	try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM Customer WHERE customerID = ?")) {
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(resultSet.getInt("customerID"));
                customer.setFirstName(resultSet.getString("firstName"));
                customer.setLastName(resultSet.getString("lastName"));
                customer.setEmail(resultSet.getString("email"));
                customer.setPhoneNumber(resultSet.getString("phoneNumber"));

                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    	throw new CustomerNotFoundException("Customer not found");
    }
 // Implement methods for Lease Management
    @Override
    public Lease createLease(int customerID, int carID, Date startDate, Date endDate) {
    	ICarLeaseRepositoryImpl carLeaseRepository=new ICarLeaseRepositoryImpl();
    	try
    	{
    		Customer customer = carLeaseRepository.findCustomerById(customerID);
    		Vehicle car = carLeaseRepository.findCarById(carID);
    	}
		catch(CarNotFoundException e)
    	{
			throw new CarNotFoundException("Car Not Found");
    	}
    	catch(CustomerNotFoundException e)
    	{
			throw new CustomerNotFoundException("Customer Not Found");
    	}
    	String status="";
    	String query = "SELECT status FROM Vehicle WHERE vehicleID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, carID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    status = resultSet.getString("status");
                    if(status.equals("unavailable"))
                    {
                    	return null;
                    }
                }
            }
        }catch (SQLException e) {
        // Handle any potential SQL exceptions
        e.printStackTrace();
        }
        String query1 = "UPDATE Vehicle SET status = 'unavailable' WHERE vehicleID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query1)) {
            preparedStatement.setInt(1, carID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
        // Handle any potential SQL exceptions
        e.printStackTrace();
        }
    	try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Lease (vehicleID, customerID, startDate, endDate, type) " +
                        "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, carID);
            statement.setInt(2, customerID);
            statement.setDate(3, new java.sql.Date(startDate.getTime()));
            statement.setDate(4, new java.sql.Date(endDate.getTime()));
            statement.setString(5, calculateLeaseType(startDate, endDate));
            //long countDays = ChronoUnit.DAYS.between(startDate.getTime(),endDate.getTime());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Lease lease = new Lease();
                lease.setLeaseID(generatedKeys.getInt(1));
                lease.setVehicleID(carID);
                lease.setCustomerID(customerID);
                lease.setStartDate(startDate);
                lease.setEndDate(endDate);
                lease.setType(calculateLeaseType(startDate, endDate));

                return lease;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }

        return null;
    }

    @Override
    public void returnCar(int leaseID) {
    	ICarLeaseRepositoryImpl carLeaseRepository=new ICarLeaseRepositoryImpl();
    	try
    	{
    		Lease foundLease = carLeaseRepository.findLeaseByID(leaseID);
    	}
		catch(LeaseNotFoundException e)
    	{
			throw new LeaseNotFoundException("Lease Not Found");
    	}
    	try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE Lease SET endDate = CURRENT_DATE WHERE leaseID = ?")) {
            statement.setInt(1, leaseID);
            statement.executeUpdate();
            String query = "UPDATE Vehicle SET status = 'available' WHERE vehicleID = (SELECT vehicleID FROM Lease WHERE leaseID = ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, leaseID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }
    
    private String calculateLeaseType(Date startDate, Date endDate) {
        return (endDate.getTime() - startDate.getTime()) > 30 * 24 * 60 * 60 * 1000 ? "MonthlyLease" : "DailyLease";
    }

    @Override
    public List<Lease> listActiveLeases() {
        List<Lease> activeLeases = new ArrayList<>();
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Lease WHERE endDate >= CURRENT_DATE")) {

               while (resultSet.next()) {
                   Lease lease = new Lease();
                   lease.setLeaseID(resultSet.getInt("leaseID"));
                   lease.setVehicleID(resultSet.getInt("vehicleID"));
                   lease.setCustomerID(resultSet.getInt("customerID"));
                   lease.setStartDate(resultSet.getDate("startDate"));
                   lease.setEndDate(resultSet.getDate("endDate"));
                   lease.setType(resultSet.getString("type"));

                   activeLeases.add(lease);
               }
           } catch (SQLException e) {
               e.printStackTrace();
               // Handle exception
           }

           return activeLeases;
    }
       
    public int generateLeaseID() {
    	return 1;
    	
    }

    @Override
    public List<Lease> listLeaseHistory() {
    	 List<Lease> leaseHistory = new ArrayList<>();
         try (Statement statement = connection.createStatement();
              ResultSet resultSet = statement.executeQuery("SELECT * FROM Lease")) {

             while (resultSet.next()) {
                 Lease lease = new Lease();
                 lease.setLeaseID(resultSet.getInt("leaseID"));
                 lease.setVehicleID(resultSet.getInt("vehicleID"));
                 lease.setCustomerID(resultSet.getInt("customerID"));
                 lease.setStartDate(resultSet.getDate("startDate"));
                 lease.setEndDate(resultSet.getDate("endDate"));
                 lease.setType(resultSet.getString("type"));

                 leaseHistory.add(lease);
             }
         } catch (SQLException e) {
             e.printStackTrace();
             // Handle exception
         }

         return leaseHistory;
    }
    
    public Lease findLeaseByID(int leaseID) {
    	Lease lease=null;
    	try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM Lease WHERE leaseID = ?")) {
            statement.setInt(1, leaseID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                lease = new Lease();
                lease.setLeaseID(resultSet.getInt("leaseID"));
                lease.setVehicleID(resultSet.getInt("vehicleID"));
                lease.setCustomerID(resultSet.getInt("customerID"));
                lease.setStartDate(resultSet.getDate("startDate"));
                lease.setEndDate(resultSet.getDate("endDate"));
                lease.setType(resultSet.getString("type"));

                return lease;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }	
    	throw new LeaseNotFoundException("Lease not found");
    }
    
 // Implement methods for Payment Handling
    @Override
    public void recordPayment(int leaseID, double amount) {
    	 try (PreparedStatement statement = connection.prepareStatement(
                 "INSERT INTO Payment (leaseID, paymentDate, amount) " +
                         "VALUES (?, CURRENT_DATE, ?)")) {
             statement.setInt(1, leaseID);
             statement.setDouble(2, amount);

             statement.executeUpdate();
         } catch (SQLException e) {
             e.printStackTrace();
             // Handle exception
         }
    }
}

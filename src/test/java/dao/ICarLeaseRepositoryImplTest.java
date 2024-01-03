package dao;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import entity.Customer;
import entity.Lease;
import entity.Vehicle;
import myexceptions.CarNotFoundException;
import myexceptions.CustomerNotFoundException;
import myexceptions.LeaseNotFoundException;

class ICarLeaseRepositoryImplTest {
	static ICarLeaseRepositoryImpl repo;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		repo = new ICarLeaseRepositoryImpl();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		repo = null;
	}

	@Test
	void testICarLeaseRepositoryImpl() {
		ICarLeaseRepositoryImpl carLeaseRepository = new ICarLeaseRepositoryImpl();
		assertNotNull(carLeaseRepository,"ICarLeaseRepositoryImpl instance should not be null");
	}
	
	@Test
    public void testCarCreation() {
        ICarLeaseRepository carLeaseRepository = new ICarLeaseRepositoryImpl();
        Vehicle car = new Vehicle(17,"Toyota", "Camry", 2022, 50.0, "available", 5, 2000);
        carLeaseRepository.addCar(car);
    }
	@Test
	public void testLeaseCreation() {
        ICarLeaseRepository carLeaseRepository = new ICarLeaseRepositoryImpl();

        Date startDate=Date.valueOf("2023-11-28");
        Date endDate=Date.valueOf("2023-12-25");
		Lease lease = carLeaseRepository.createLease(3, 4, startDate, endDate);

        // Verify if the lease is successfully created
        assertNotNull(lease);
    }
	@Test
    public void testLeaseRetrieval() {
        ICarLeaseRepository carLeaseRepository = new ICarLeaseRepositoryImpl();
        Lease retrievedLease = carLeaseRepository.listActiveLeases().get(0);

        // Verify if the lease is successfully retrieved
        assertNotNull(retrievedLease);
    }
	@Test
    public void testCarNotFoundException() {
        ICarLeaseRepository carLeaseRepository = new ICarLeaseRepositoryImpl();
        assertThrows(CarNotFoundException.class, () -> {
            carLeaseRepository.findCarById(-1);  
        });
    }

    @Test
    public void testLeaseNotFoundException() {
        ICarLeaseRepository carLeaseRepository = new ICarLeaseRepositoryImpl();
        assertThrows(LeaseNotFoundException.class, () -> {
            carLeaseRepository.findLeaseByID(-1);  
        });
    }

    @Test
    public void testCustomerNotFoundException() {
        ICarLeaseRepository carLeaseRepository = new ICarLeaseRepositoryImpl();
        assertThrows(CustomerNotFoundException.class, () -> {
            carLeaseRepository.findCustomerById(-1);  
        });
    }

}

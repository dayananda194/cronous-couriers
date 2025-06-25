package org.syncron.cronouscouriers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.syncron.cronouscouriers.dispatchservice.DispatchCenter;
import org.syncron.cronouscouriers.entities.Rider;
import org.syncron.cronouscouriers.entities.Package;
import org.syncron.cronouscouriers.enums.PackageType;
import org.syncron.cronouscouriers.enums.RiderStatus;
import org.syncron.cronouscouriers.geo.Location;
import org.syncron.cronouscouriers.entities.Assignment;
import org.syncron.cronouscouriers.logging.PackageDeliveryAudit;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class CronousCouriersApplication {

	private final DispatchCenter dispatchCenter;
	private final Scanner scanner ;

	public CronousCouriersApplication() {
		this.dispatchCenter = new DispatchCenter();
		this.scanner = new Scanner(System.in);
	}

	public void run(){

		System.out.println("---------------------Chronos Couriers Dispatch System--------------------------------\n");
		while(true){

			printMenu();

			String option = scanner.nextLine();
			int optionNumber = Integer.parseInt(option);

			switch(optionNumber){

				case 1:
						addRider();
						break;

				case 2:
						placeOrder();
						break;

				case 3:
						updateRiderStatus();
						break;

				case 4:
						assignPackage();
						break;

				case 5:
						recordPickup();
						break;

				case 6:
						recordDelivery();
						break;

				case 7:
						recordFailure();
						break;

				case 8:
						getPackagesDeliveredByRider();
						break;
				case 9:
						getLateExpressPackages();
						break;
				case 0:  // Added exit option
					System.out.println("Exiting system...");
					return;
				default:
						System.out.println("Invalid option");

			}

		}


	}

	private void printMenu() {
		System.out.print("""
            \nMenu:
            1. Add Rider       2. Place Order
            3. Update Rider Status   4. Assign Package
            5. Record Pickup   6. Record Delivery Success
            7. Record Delivery Failure    8. Get Packages By a Rider
            9. Get Late Express Packages 
            0. Exit
            Enter command:\s""");
	}

	private void addRider() {
		try {
			System.out.print("\nAdd Rider\nID: ");
			String id = scanner.nextLine();

			System.out.print("Reliability (0.0-1.0), Can handle fragile (true/false): ");
			String[] riderSpecs = scanner.nextLine().split(",");
			double rating = Double.parseDouble(riderSpecs[0].trim());
			boolean canHandleFragile = Boolean.parseBoolean(riderSpecs[1].trim());

			System.out.print("Location (lat,long): ");
			String[] coords = scanner.nextLine().split(",");
			Location loc = new Location(
					Double.parseDouble(coords[0].trim()),
					Double.parseDouble(coords[1].trim())
			);

			dispatchCenter.addRider(new Rider(id, rating, canHandleFragile, loc));
		} catch (Exception e) {
			System.out.println(" Error: " + e.getMessage());
		}
	}
	private void placeOrder() {
		try {
			System.out.print("\nNew Package\nID: ");
			String id = scanner.nextLine();

			System.out.print("Type (EXPRESS/STANDARD), Fragile (true/false): ");
			String[] packageSpecs = scanner.nextLine().split(",");
			PackageType type = PackageType.valueOf(packageSpecs[0].trim().toUpperCase());
			boolean fragile = Boolean.parseBoolean(packageSpecs[1].trim());

			System.out.print("Destination, Deadline (mins): ");
			String[] deliveryInfo = scanner.nextLine().split(",");
			String destination = deliveryInfo[0].trim();
			long deadline = System.currentTimeMillis() +
					(Long.parseLong(deliveryInfo[1].trim()) * 60 * 1000);

			dispatchCenter.placeOrder(new Package(id, type, System.currentTimeMillis(),
					deadline, fragile, destination));

		} catch (Exception e) {
			System.out.println("! Error: " + e.getMessage());
		}
	}


	private void updateRiderStatus() {
		try {
			System.out.print("\nUpdate Status\nRider ID, New_Status (AVAILABLE/BUSY/OFFLINE): ");
			String[] input = scanner.nextLine().split(",");
			dispatchCenter.updateRiderStatus(
					input[0].trim(),
					RiderStatus.valueOf(input[1].trim().toUpperCase())
			);

		} catch (Exception e) {
			System.out.println("! Error: " + e.getMessage());
		}
	}

	private void assignPackage() {
		try {
			Optional<Assignment> assignment = dispatchCenter.assignPackage();
			System.out.println(assignment.isPresent()
					? " Assigned: " + assignment.toString()
					: "No riders / Packages available");
		} catch (Exception e) {
			System.out.println("! Error: " + e.getMessage());
		}
	}


	private void recordPickup() {
		try {
			System.out.print("\nRecord Pickup\nAssignment ID: ");
			dispatchCenter.recordPickup(scanner.nextLine());
		} catch (Exception e) {
			System.out.println("! Error: " + e.getMessage());
		}
	}


	private void recordDelivery() {
		try {
			System.out.print("\nRecord Delivery\nAssignment ID, On Time? (true/false): ");
			String[] input = scanner.nextLine().split(",");
			dispatchCenter.recordDelivery(input[0].trim(), Boolean.parseBoolean(input[1].trim()));
		} catch (Exception e) {
			System.out.println("! Error: " + e.getMessage());
		}
	}

	private void recordFailure() {

		try{
			System.out.print("\nRecord Delivery Failure\nAssignment ID : ");
			String assigmentID = scanner.nextLine();
			dispatchCenter.recordDeliveryFailure(assigmentID);

		}catch (Exception e){
			System.out.println(" Error : " + e.getMessage());
		}

	}

	private void getPackagesDeliveredByRider(){

		System.out.println("Available Drivers are : " + dispatchCenter.getRiders().keySet().stream().peek((rider)->{System.out.println(rider.toString());}));
		System.out.println("Enter the driver ID : ");
		String driverID = scanner.nextLine();
		List<String> packageList = PackageDeliveryAudit.getPackagesDeliveredByRider(driverID,System.currentTimeMillis()-24*60*60*1000); // 24 hrs ago
		System.out.println( "Packages Delivered by the Driver " + driverID + " are : " +  packageList);

	}

	private void getLateExpressPackages(){

		System.out.println("Enter the last Hours H to find late express packages : ");
		long hours = scanner.nextLong();
		System.out.println("Express Late Express Packages are : " + PackageDeliveryAudit.getLateExpressPackages(System.currentTimeMillis()-24*hours*60*1000));

	}


	public static void main(String[] args) {


		SpringApplication.run(CronousCouriersApplication.class, args);

		new CronousCouriersApplication().run();

	}

}

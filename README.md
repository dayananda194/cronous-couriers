# Chronos Couriers

🚚📦 Smart, Fast, Reliable Delivery

An intelligent dispatch system for time-sensitive package deliveries, prioritizing urgent packages and optimizing rider assignments.

Jump to:
- [Local SetUp](https://github.com/dayananda194/cronous-couriers?tab=readme-ov-file#local-setup)

  
## 📌 Features

- **Priority-based dispatch**:
  - Express packages always take precedence over Standard
  - Time-sensitive deadline enforcement
  - First-in-first-out handling for equal priority packages

- **Rider management**:
  - Availability tracking (online/offline/busy)
  - Special handling capabilities (fragile items)
  - Reliability rating consideration(0 to 1)

- **Real-time operations**:
  - Dynamic package assignment
  - Immediate response to new high-priority packages
  - Status updates throughout delivery lifecycle

- **Reporting & tracking**:
  - Delivery audit trails
  - Performance analytics
  - Deadline compliance monitoring
 
  
## 🚀 Getting Started

### Prerequisites
- Java 21 JDK
  - Download the Java 21 at [Oracle](https://www.oracle.com/java/technologies/downloads/) 
- Gradle 8.x
  - Download the [Gradle build system](https://gradle.org/releases/)
- IDE  

## TechStack Used 
- Java , Spring Boot

### Local Setup
1. Clone the repository:
   
   ```bash
   cd chronos-couriers
   git clone https://github.com/your-repo/chronos-couriers
   
2. Build the Project
   
   ```bash
    ./gradlew build
   
3. Run the Project
   ```bash
   ./gradlew bootRun
       or
   java -jar build/libs/CronousCouriers-0.0.1-SNAPSHOT.jar 



## 🧭 **Project Walkthrough**

Once we start the Server , we will get a prompt to select the option to perform . 

<img width="1589" alt="Screenshot 2025-06-25 at 9 43 39 PM" src="https://github.com/user-attachments/assets/0431cc72-9477-4e60-a251-577b3caa8252" />

ADD RIDER 

 - If we want to add a rider , we can select the option 1 , and once we provide the required details , it will add the new rider and gives us the confirmation and displays the newly added rider . Since Rider is available , it will automatically tries to assign the packages .( Here we are storing the co-ordinates of the driver location , so that we can use it if we want to assign the riders based on the location in future ) .
   
<img width="1591" alt="image" src="https://github.com/user-attachments/assets/82d89afb-87ed-4fe8-96b8-973ca943e22e" />

Place Order

- We can place the order by selecting the option 2 , and after placing the order , it will automatically tries to assign the package and if assigned displays the assigment details as well .

<img width="1584" alt="image" src="https://github.com/user-attachments/assets/46bb0e01-54fe-4e31-a351-8dee0bb27b6a" />

UPDATE RIDER STATUS 

 - Rider status can be updated to either AVAILABLE , BUSY or OFFLINE . If the Rider is OFFLINE , he will not be assigned to any other incoming packages in until he is available . 

<img width="1517" alt="image" src="https://github.com/user-attachments/assets/2c4bef6d-259d-42d2-87bd-90c9f87d303e" />

RECORD PACKAGE PICK UP 

 - We can update the pick up time of the package for the particular assigment . For better user experience we are showing the list of assignments were there in the system . 

 <img width="1490" alt="image" src="https://github.com/user-attachments/assets/e674acca-2523-49b2-9666-c5a942265314" />

RECORD DELIVERY SUCCESS 

- Once the package is delivered successfully , we can update that status through this option . If the package is delivered , then it means that the Rider is AVAILABLE . The
  system automatically tries to assign the packages .

  <img width="1614" alt="image" src="https://github.com/user-attachments/assets/99d7f696-1d39-49ba-a08d-85e4760be607" />

GET PACKAGES DELIVERED BY A RIDER 

- If we want to see the list of packages that are delivered by the rider , then we can select this command to run .

  <img width="1524" alt="image" src="https://github.com/user-attachments/assets/3fc38dd4-9528-42a3-8d80-1804f0367e79" />

[Contact Me](dayanandaredd7993163@gmail.com)


   
   



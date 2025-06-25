# Chronos Couriers

🚚📦 Smart, Fast, Reliable Delivery

An intelligent dispatch system for time-sensitive package deliveries, prioritizing urgent packages and optimizing rider assignments.

Jump to:
- [Installation](https://github.com/dayananda194/cronous-couriers/new/main?filename=README.md#local-setup)

  
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

  <img width="1469" alt="image" src="https://github.com/user-attachments/assets/6fca5cfa-ab36-4e7e-add6-d43e650f57df" />

[Contact Me](dayanandaredd7993163@gmail.com)


   
   



# Shira Runtime Assignment
# Overview
A Runtime Policy Assignment.

This service includes
1. controler to create/update/delete runtime policy in DB
2. service to update enforcer in runtime
   
![schema](https://github.com/ShiraMinHahar/shira-runtime-assignment/assets/55111910/71892fce-1482-48f3-b3aa-3d888f83a5fa)

# Installation
1. make sure that you have PostgreSQL installed in your computer. if not download it [here](https://www.postgresql.org/download/) 
2. Clone the repository
3. Open your project in IntelliJ, and do the following:
    * Open your new project as spring application and set 'Project SDK' in the Project Structure to Java 11
    * Enable auto gradle import (IntelliJ will suggest that in the Event Log box)
      
4. Environment setup - local
    * Create a database with name facebook-feeds
    * run the following commands in the CLI:
      ```psql postgres```
      ```CREATE DATABASE runtimeassignment;```
      ```ALTER DATABASE runtimeassignment OWNER TO shira;```
    * run this command un the CLI (in the root project - 'shira-runtime-assignment') for the tables and credtionals will be create
      ```./gradlew flywayMigrate```
    * Run the ApiApplication 
    * Test the application via api 


# Most common questions
### How to send the API requests?
1. dowanload postman [here](https://www.postman.com/downloads/)
2. Create - PUT ```http://127.0.0.1:8082/runtimePolicy/Create```
3. Update - POST ```http://127.0.0.1:8082/runtimePolicy/Update```
4. Delete - DELETE ```http://127.0.0.1:8082/runtimePolicy/Delete```
5. Body for all the requests shuld looks:
```{"policyName": "PolicyName1","author": "admin","controls": "{\"block_cryptomining\": {\"enabled\": true,\"action\": \"delete\"}}"}```

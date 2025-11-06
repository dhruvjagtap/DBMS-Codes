-- Problem Statement 2 (DDL USING MYSQL)
-- Create following tables using a given schema and insert appropriate data into the same:
-- Customer (CustID, Name, Cust_Address, Phone_no, Email_ID, Age)
-- Branch (Branch ID, Branch_Name, Address)
-- Account (Account_no, Branch ID, CustID, open_date, Account_type, Balance)

-- 1. Create the tables with referential integrity.
CREATE TABLE Customer (
    CustID INT PRIMARY KEY,
    Name VARCHAR(50),
    Cust_Address VARCHAR(100),
    Phone_no VARCHAR(15),
    Email_ID VARCHAR(50),
    Age INT
);

CREATE TABLE Branch (
    Branch_ID INT PRIMARY KEY,
    Branch_Name VARCHAR(50),
    Address VARCHAR(100)
);

CREATE TABLE Account (
    Account_no INT PRIMARY KEY,
    Branch_ID INT,
    CustID INT,
    open_date DATE,
    Account_type VARCHAR(50),
    Balance INT,
    FOREIGN KEY (Branch_ID) REFERENCES Branch(Branch_ID),
    FOREIGN KEY (CustID) REFERENCES Customer(CustID)
);

-- 2. Draw the ER diagram for the same.

-- 3. Create a View as Saving account displaying the customer details with the open date as 16/8/2018.
CREATE VIEW Saving_account AS
SELECT c.CustID, c.Name, c.Cust_Address, c.Phone_no, c.Email_ID, c.Age FROM Customer c
JOIN Account a ON a.CustID = c.CustID
WHERE a.open_date = '2018-08-16';

-- 4. Update the View with Cust_Address as Pune for CustID =103.
UPDATE Saving_account SET Cust_Address = 'Pune' WHERE CustID = 103;

-- 5. Create a View as Loan account displaying the customer details with the open date as 16/2/2018.
CREATE VIEW Loan_account AS
SELECT c.CustID, c.Name, c.Cust_Address, c.Phone_no, c.Email_ID, c.Age FROM Customer c
JOIN Account a ON a.CustID = c.CustID
WHERE a.open_date = '2018-02-16';


-- 6. Create an Index on primary key column of table Customer.
CREATE INDEX cust_index ON Customer(CustID);

-- 7. Create an Index on primary key column of table Branch.
CREATE INDEX branch_index ON Branch(Branch_ID);

-- 8. Create a sequence on Customer Table.
ALTER TABLE Branch MODIFY Branch_ID INT AUTO_INCREMENT;

-- 9. Create synonym ‘Cust_info’ for branch table.
CREATE VIEW Branch_info AS
SELECT * FROM Branch;

------------------------------
-- Problem Statement 5 (DML USING MYSQL)
-- Create following tables using a given schema and insert appropriate data into the same:
-- Customer (CustID, Name, Cust_Address, Phone_no, Age)
-- Branch (Branch ID, Branch_Name, Address)
-- Account (Account_no, Branch ID, CustID, date_open, Account_type, Balance)

CREATE TABLE Customer (CustID INT PRIMARY KEY, Name VARCHAR(50), Cust_Address VARCHAR(100), Phone_no VARCHAR(12), Age INT);
CREATE TABLE Branch (Branch_ID INT PRIMARY KEY, Branch_Name VARCHAR(50), Address VARCHAR(100));
CREATE TABLE Account (Account_no INT PRIMARY KEY, Branch_ID INT, CustID INT, date_open DATE, Account_type VARCHAR(10), Balance INT, FOREIGN KEY (Branch_ID) REFERENCES Branch(Branch_ID));

-- 1. Add the column “Email_Address” in Customer table.
ALTER TABLE Customer ADD Email_Address VARCHAR(20);

-- 2. Change the name of column “Email_Address” to “Email_ID” in Customer table.
ALTER TABLE Customer CHANGE Email_Address Email_ID VARCHAR(50);

-- 3. Display the customer details with highest balance in the account.
SELECT c.CustID, c.Name, a.Balance FROM Customer c
JOIN Account a ON a.CustID =  c.CustID
WHERE a.Balance = (SELECT MAX(Balance) FROM Account);

-- 4. Display the customer details with lowest balance for account type= “Saving Account”.
SELECT c.CustID, c.Name, a.Balance
FROM Customer c
JOIN Account a ON a.CustID = c.CustID
WHERE a.Account_type = 'Saving Account'
AND a.Balance = (
    SELECT MIN(Balance) FROM Account WHERE Account_type = 'Saving Account'
);


-- 5. Display the customer details that live in Pune and have age greater than 35.
SELECT * FROM Customer WHERE Cust_Address LIKE "%Pune%" AND AGE > 35;

-- 6. Display the CustD, Name and Age of the customer in ascending order of their age.
SELECT CustID,Name, Age FROM Customer ORDER BY Age;

-- 7. Display the Name and Branch ID of the customer group by the Account_type. 
SELECT a.Account_type, b.Branch_ID, c.Name
FROM Customer c
JOIN Account a ON c.CustID = a.CustID
JOIN Branch b ON b.Branch_ID = a.Branch_ID
GROUP BY a.Account_type, b.Branch_ID, c.Name;

-------------------------------------

-- Problem Statement 8 (JOINS & SUBQUERIES USING MYSQL)
-- Consider Following Schema
-- Employee (Employee_id, First_name, last_name , hire_date, salary, Job_title, manager_id, department_id)
-- Departments(Department_id, Department_name, Manager_id, Location_id)
-- Locations(location_id ,street_address ,postal_code, city, state, country_id)
-- Manager(Manager_id, Manager_name)

-- Create the tables with referential integrity. Solve following queries using joins and subqueries.
CREATE TABLE Manager(Manager_id INT PRIMARY KEY, Manager_name VARCHAR(50));
CREATE TABLE Locations(location_id INT ,street_address VARCHAR (50),postal_code INT, city VARCHAR(10), state VARCHAR(10), country_id INT);
CREATE TABLE Departments(Department_id INT PRIMARY KEY, Department_name VARCHAR(50), Manager_id INT, Location_id INT, FOREIGN KEY (Location_id) REFERENCES Locations(Location_id), FOREIGN KEY Manager_id REFERENCES Manager(Manager_id));
CREATE TABLE Employee (Employee_id INT PRIMARY KEY, First_name VARCHAR(10), last_name VARCHAR(10), hire_date DATE, salary INT, Job_title VARCHAR(10), manager_id INT, department_id INT, FOREIGN KEY Manager_id REFERENCES Manager(Manager_id), FOREIGN KEY Department_id REFERENCES Departments(Department_id));

-- 1. Write a query to find the names (first_name, last_name) and the salaries of the employees who have a
-- higher salary than the employee whose last_name=''Singh”.
SELECT First_name, Last_name, salary
FROM Employee
WHERE salary > (SELECT salary FROM Employee WHERE Last_name = 'Singh');

-- 2. Write a query to find the names (first_name, last_name) of the employees who have a manager and
-- work for a department based in the United States.
SELECT e.First_name, e.Last_name
FROM Employee e
JOIN Departments d ON e.Department_id = d.Department_id
JOIN Locations l ON d.Location_id = l.Location_id
WHERE e.Manager_id IS NOT NULL
  AND l.country_id = 'US';

-- 2. Write a query to find the names (first_name, last_name), the salary of the employees whose salary is
-- greater than the average salary.
SELECT First_name, last_name, salary FROM Employee
WHERE salary > (SELECT AVG(salary) FROM Employee);

-- 3. Write a query to find the employee id, name (last_name) along with their manager_id, manager name
-- (last_name).
SELECT e.Employee_id, e.First_name, e.last_name, e.Manager_id, m.Manager_name FROM Employee e
JOIN Manager m ON e.Manager_id = m.Manager_id;

-- 4. Find the names and hire date of the employees who were hired after 'Jones'.
SELECT First_name, last_name, hire_date FROM Employee 
WHERE hire_date > (SELECT hire_date FROM Employee WHERE First_name = 'Jones');

--------------------------
-- Problem Statement 14 (DML USING MYSQL)
-- Create following tables using a given schema and insert appropriate data into the same:
-- Customer (CustID, Name, Cust_Address, Phone_no, Email_ID, Age)
-- Branch (Branch ID, Branch_Name, Address)
-- Account (Account_no, Branch ID, CustID, date_open, Account_type, Balance)

CREATE TABLE Customer (CustID INT PRIMARY KEY, Name VARCHAR(50), Cust_Address VARCHAR(100), Phone_no VARCHAR(12), Email_ID VARCHAR(30), Age INT);
CREATE TABLE Branch (Branch_ID INT PRIMARY KEY, Branch_Name VARCHAR(10), Address VARCHAR(100));
CREATE TABLE Account (Account_no INT PRIMARY KEY, Branch_ID INT, CustID INT, date_open DATE, Account_type VARCHAR(10), Balance INT, FOREIGN KEY (Branch_ID) REFERENCES Branch(Branch_ID),FOREIGN KEY (CustID) REFERENCES Customer(CustID));

-- 1. Modify the size of column “Email_Address” to 20 in Customer table.
ALTER TABLE Customer MODIFY Email_ID VARCHAR(20);

-- 2. Change the column “Email_Address” to Not Null in Customer table.
ALTER TABLE Customer MODIFY Email_ID VARCHAR(20) NOT NULL;

-- 3. Display the total customers with the balance >50, 000 Rs.
SELECT COUNT(*) AS TOTAL_CUSTOMERS_BALANCE_GT_50K FROM Customer c 
JOIN Account a ON a.CustID = c.CustID
WHERE a.Balance > 50000;

-- 4. Display average balance for account type=”Saving Account”.
SELECT AVG(Balance) AS Avg_Saving_Balance
FROM Account
WHERE Account_type = 'Saving Account';


-- 5. Display the customer details that lives in Pune or name starts with ’A’.
SELECT *
FROM Customer
WHERE Cust_Address LIKE '%PUNE%'
   OR Name LIKE 'A%';


-- 6. Create a table Saving_Account with (Account_no, Branch ID, CustID, date_open, Balance) using
-- Account Table.
CREATE TABLE Saving_Account AS
SELECT Account_no, Branch_ID, CustID, date_open, Balance
FROM Account
WHERE Account_type = 'Saving Account';


-- 7. Display the customer details Age wise with balance>=20,000Rs
SELECT c.*, a.Balance
FROM Customer c
JOIN Account a ON a.CustID = c.CustID
WHERE a.Balance >= 20000
ORDER BY c.Age;

------------------------------------------

-- Problem Statement 17 (JOINS & SUBQUERIES USING MYSQL)

-- Consider Following Schema
-- Employee (Employee_id, First_name, Last_name , Hire_date, Salary, Job_title, Manager_id, department_id)
-- Departments(Department_id, Department_name, Manager_id, Location_id)
-- Locations(Location_id , Street_address , Postal_code, city, state, Country_id)
-- Manager(Manager_id, Manager_name)

-- Create the tables with referential integrity.Solve following queries using joins and subqueries.
CREATE TABLE Manager(Manager_id INT PRIMARY KEY, Manager_name VARCHAR(50));
CREATE TABLE Locations(location_id INT ,street_address VARCHAR (50),postal_code INT, city VARCHAR(10), state VARCHAR(10), country_id INT);
CREATE TABLE Departments(Department_id INT PRIMARY KEY, Department_name VARCHAR(50), Manager_id INT, Location_id INT, FOREIGN KEY (Location_id) REFERENCES Locations(Location_id), FOREIGN KEY Manager_id REFERENCES Manager(Manager_id));
CREATE TABLE Employee (Employee_id INT PRIMARY KEY, First_name VARCHAR(10), last_name VARCHAR(10), hire_date DATE, salary INT, Job_title VARCHAR(10), manager_id INT, department_id INT, FOREIGN KEY Manager_id REFERENCES Manager(Manager_id), FOREIGN KEY Department_id REFERENCES Departments(Department_id));

-- 1. Write a query to find the names (first_name, last_name), the salary of the employees who earn more than
-- the average salary and who works in any of the IT departments.
SELECT e.First_name, e.Last_name, e.Salary FROM Employee e
JOIN Departments d ON d.Department_id = e.Department_id
WHERE Department_name LIKE '%IT%' AND e.Salary > (SELECT AVG(Salary) FROM Employee);

-- 2. Write a query to find the names (first_name, last_name), the salary of the employees who earn the same
-- salary as the minimum salary for all departments.
SELECT e.First_name, e.Last_name, e.Salary
FROM Employee e
WHERE e.Salary = (
  SELECT MIN(Salary)
  FROM Employee
  WHERE Department_id = e.Department_id
);


-- 3. Write a query to display the employee ID, first name, last names, salary of all employees whose salary is
-- above average for their departments.
SELECT e.Employee_id, e.First_name, e.Last_name, e.Salary
FROM Employee e
WHERE e.Salary > (
  SELECT AVG(Salary)
  FROM Employee
  WHERE Department_id = e.Department_id
);

-- 4. Write a query to display the department name, manager name, and city.
SELECT d.Department_name, m.Manager_name, l.City
FROM Departments d
JOIN Manager m ON d.Manager_id = m.Manager_id
JOIN Locations l ON d.Location_id = l.Location_id;

-- 5. Write a query to display the name (first_name, last_name), hire date, salary of all managers whose
-- experience is more than 15 years.
SELECT e.First_name, e.Last_name, e.Hire_date, e.Salary
FROM Employee e
JOIN Manager m ON e.Manager_id = m.Manager_id
WHERE TIMESTAMPDIFF(YEAR, e.Hire_date, CURDATE()) > 15;


# Air Companies Management System

Welcome to the Air Companies Management System project! This system is designed to manage companies, flights and airplanes efficiently, providing tools for scheduling, tracking, and managing various aspects of airline operations.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)

## Introduction

This Air Companies Management System provides a comprehensive solution for airlines to handle various tasks related to flight scheduling, airplane and air companies' management.

## Features

- **Flight Scheduling**: Easily schedule flights, manage routes, and allocate resources efficiently.
- **Airplane Management**: Keep track of airplane information, maintenance schedules, and availability.
- **AirCompanies Management**: Create, update, delete, get all information about companies.
- **Integration**: Easily integrate with other systems such as airport databases, reservation systems, and financial software.

## Installation
### First option:

To run the Air Companies Management System locally, follow these steps:

1. Clone the repository:


2. Install maven dependencies:


3. Configure the database:

Modify the database configuration in `application.yml` according to your setup.

4. Start the application:


5. Run `data.sql` file to fill sql tables with test data



### Second option:

1. Unzip `Air_Companies_Managemenet_System.zip`:


2. Go to unzipped folder and run `cmd` in that directory:


3. Write `docker compose up --build` to start the application:


4. Run `data.sql` file to fill sql tables with test data


## Usage

Once the application is running, you can start using the Flight Management System to manage airlines, airplanes and flights. 

### Postman Collection:
1. Import Postman collection `Air Companies Management System.postman_collection.json` into your Postman app to get all endpoints:


2. Send requests by using Postman app:

### API Endpoints:
| HTTP Method | Endpoint                 | Request Parameters                                    | Action                               |
|-------------|--------------------------|-------------------------------------------------------|--------------------------------------|
| POST        | `/airCompany`            | `@RequestBody AirCompany`                             | To create a new air company          |
| GET         | `/airCompany`            |                                                       | To get all companies                 |
| GET         | `/airCompany`            | `@RequestParam companyName`                           | To get a company by name             |
| PUT         | `/airCompany`            | `@RequestParam companyName`                           | To update a company info by name     |
| DELETE      | `/airCompany`            | `@RequestParam companyName`                           | To delete a company by name          |
| POST        | `/airplane`              | `(Optional) @RequestParam companyName`,`@RequestBody Airplane`   | To create an airplane by airplane's serial number        |
| PUT         | `/airplane`              | `@RequestParam companyName`,`@RequestParam serialNum` | To update an airplane's owner company by airplane's serial number        |
| POST        | `/flight`                | `@RequestParam planeSerialNum`,`@RequestBody Flight`  | To create a flight                   |
| PUT         | `/flight`                | `@RequestParam id`,`@RequestParam status`             | To update a flight                   |
| GET         | `/flight`                | `@RequestParam companyName`,`@RequestParam status`    | To get all company flights by status |
| GET         | `/flight`                | `@RequestParam status`    | To get all flights by status         |
| GET         | `/flight/timeUnderrated` |     | To get time underrated flights       |


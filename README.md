# Event Platform Api.

## Description

The application allows for the creation of two types of users: Producers and Consumers. Producers are the entities that create events. They have the ability to send events to the platform. On the other hand, Consumers, as the name suggests, are the entities that consume these events.
The core functionality of the Event Platform API is to manage the distribution of these events. When a Producer sends an event, the platform takes over and ensures the event is efficiently delivered to the appropriate Consumers.
This setup allows for a seamless interaction between Producers and Consumers, making the management and consumption of events a hassle-free process.

## Technologies Used

- Java: Programming language used to develop the service.
- Spring Boot: Framework used to simplify the application development.
- SQL: Structured Query Language used to manage the data stored in the database.
- Gradle: Build automation tool used to manage dependencies and build the project.
- Amazon SQS: Fully managed message queues service used for reliable communication among independent components of a distributed system.

## How to Run the Project

1. Ensure Docker is installed on your machine.
2. Clone the repository.
3. Navigate to the project directory.
4. Run `docker-compose up` to start up the Amazon SQS and PostgreSQL services and the service itself.
5. After the Docker services are up and running, execute `./gradlew bootRun` (for Unix-based systems) or `gradlew bootRun` (for Windows) to start the application.

Please note that the application won't run correctly if the Docker services are not up and running.

## To see the full documentation:

You will need the npm and docsify installed in your machine.
To install docsify run `npm i docsify-cli -g`
after installing dosify run `docsify serve ./docs/guide` and click [here](http://localhost:3000)

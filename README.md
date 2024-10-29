# Webhook Notifications Project

This project is a Java-based plugin for Rundeck that allows flexible HTTP notifications. It supports various HTTP methods and allows customization of request body and content type.

## Features

- Supports GET, POST, PUT, DELETE HTTP methods.
- Allows custom headers and body content.
- Uses Apache HttpClient for HTTP requests.

## Requirements

- Java 11
- Maven

## Building the Project

To build the project, run the following command:

```bash
mvn clean install
```

## Running Tests

To execute the tests, use:

```bash
mvn test
```

## Usage

Include the generated JAR in your Rundeck plugins directory to enable the HTTP Notification Plugin.

## Contributing

Contributions are welcome! Please submit a pull request or open an issue for any improvements or bug fixes.
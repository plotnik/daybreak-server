# DayBreak Server

A Spring Boot REST API application that serves poems from markdown files.

## Overview

DayBreak Server is a Spring Boot application that provides a REST API for accessing poems stored in markdown files. The application automatically discovers and parses `.md` files from a designated folder and exposes their content through RESTful endpoints.

## Features

- 📖 Automatic discovery and parsing of markdown poem files
- 🔍 REST API endpoints for browsing poems
- 📋 Swagger UI documentation
- 🏠 Configurable poem directory location
- ⚡ Fast in-memory caching of parsed poems

## Quick Start

### Prerequisites

- Java 21 or higher
- Maven 3.6+ or wrapper included
- IDE (VS Code, IntelliJ IDEA, etc.)

### Setup

1. **Create poem directory**: Create a `daybreak` folder in your Documents directory:
   ```
   ~/Documents/daybreak/     (Linux/macOS)
   C:\Users\{User}\Documents\daybreak\  (Windows)
   ```

2. **Add sample poems**: Create markdown files with the following structure:
   ```markdown
   # Section Title
   
   ## Poem Title 1
   
   > First line of the poem
   > Second line of the poem
   > Third line of the poem
   
   ## Poem Title 2
   
   > Another poem starts here
   > With multiple lines
   ```

3. **Run the application**:
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the API**: Open http://localhost:8080/swagger-ui/index.html

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/poems` | List all available poem files |
| GET | `/poems/{filename}` | Get poem count for a specific file |
| GET | `/poems/{filename}/{number}` | Get a specific poem by number |


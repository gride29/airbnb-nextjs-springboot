# Airbnb Next.js/Spring Boot Project
This project is a small application built with Next.js, Spring Boot, and MongoDB. It allows users to browse and book accommodations similar to Airbnb. The application is hosted on Google Cloud Run using Docker containers, and the CI/CD pipeline is set up with GitHub Actions.

## Features
- User Registration and Authentication: Users can sign up and log in to the application using their email address and password or via GitHub.
- Accommodation Listings: Users can browse through a list of available accommodations with details such as location, price, amenities and create their own listings.
- Booking Reservations: Users can select a desired accommodation and make reservations for specific dates.
- User Favorites: Users can save their favorite accommodations for future reference.
- Search Functionality: Users can search for accommodations based on location, price range, and other criteria.

## Technology Stack
The project uses the following technologies:

- Frontend:
  - Next.js: A React framework for server-side rendering and building static websites.
  - Tailwind CSS: A utility-first CSS framework for styling the application.
  - Cloudinary: A cloud-based image management service for storing and retrieving media files.
  
- Backend:
  - Spring Boot: A Java framework for building enterprise-level applications.
  - MongoDB: A NoSQL document database for storing and retrieving data.
  - Spring Security: A Spring framework for securing the application.

- Deployment:
  - Google Cloud Run: A serverless compute platform for deploying containerized applications.
  - Docker: A containerization platform used to package the application and its dependencies.
  - GitHub Actions: CI/CD pipeline automation tool integrated with GitHub.

## Getting Started
To run the application locally, follow these steps:

1. Clone the repository: `git clone https://github.com/your-username/airbnb-nextjs-springboot.git`
2. To install required dependencies for:
   - frontend: run `npm install` in the frontend directory.
   - backend: run `mvn install` in the backend directory.
3. Set up a local MongoDB database and update the configuration in the `application.properties`.
4. Run the frontend and backend in separate terminals using `npm run dev` and `mvn spring-boot:run`, respectively.
5. Access the application in your web browser on `localhost:3000`

### Important Notes:
- The application uses GitHub OAuth for user authentication. To run the application locally, you will need to create a GitHub OAuth app and update the `clientId` and `clientSecret` in the `application.properties`.
- Tests require a running MongoDB instance. To run the tests, you will need to update `application.properties` in `test` directory to point to your local MongoDB instance.

## License
This project is licensed under the [MIT License](https://opensource.org/licenses/MIT). Feel free to use, modify, and distribute the code as per the terms of the license.
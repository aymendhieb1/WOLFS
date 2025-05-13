
## Overview
WOLFS is a comprehensive travel and hospitality management system built with Java and JavaFX. It provides a robust platform for managing hotels, rooms, vehicles, contracts, forums, and user interactions.

## Features
- **User Management**
  - User registration and authentication
  - Role-based access control
  - Profile management
  - Password reset functionality

- **Hotel Management**
  - Hotel registration and details
  - Room management (Standard, Deluxe, Suite, Executive)
  - Room availability tracking
  - Pricing management

- **Vehicle Management**
  - Vehicle registration
  - Contract management
  - Status tracking
  - Pricing management

- **Forum System**
  - Forum creation and management
  - Post creation and interaction
  - Comment system
  - Voting mechanism
  - File attachments

- **Additional Features**
  - Weather integration
  - Calendar management
  - QR code generation
  - PDF generation
  - SMS notifications
  - Payment processing

## Technical Stack
- **Backend**
  - Java 17
  - JavaFX 20.0.2
  - MySQL 8.0.33
  - Maven

- **Dependencies**
  - BCrypt for password hashing
  - Jakarta Mail for email functionality
  - iText for PDF generation
  - ZXing for QR code generation
  - Twilio for SMS
  - Stripe for payments
  - Google APIs for calendar and authentication

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── wolfs/
│   │           ├── controllers/
│   │           ├── models/
│   │           ├── services/
│   │           └── utils/
│   └── resources/
│       ├── images/
│       ├── styles/
│       └── videos/
└── test/
    └── java/
```

## Models
- **User**: Base user entity with authentication details
- **Client**: Extended user entity with additional client-specific attributes
- **Hotel**: Hotel management with location and contact details
- **Chambre**: Room management with types and availability
- **Vehicule**: Vehicle management with status and pricing
- **Contrat**: Contract management for vehicle rentals
- **Forum**: Forum management with privacy settings
- **Post**: Post management with voting and file attachments

## Getting Started
1. Clone the repository
2. Configure MySQL database
3. Update database credentials in `DataSource.java`
4. Run `mvn clean install`
5. Launch the application

## Contributing
Please read CONTRIBUTING.md for details on our code of conduct and the process for submitting pull requests.

## License
This project is licensed under the MIT License - see the LICENSE.md file for details.

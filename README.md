# Maia - Stores App for Android

Maia is a native Android application designed for stores to efficiently manage their inventory and interact with customers. It leverages Jetpack Compose for a modern, intuitive interface and integrates advanced backend technologies to ensure seamless operation and scalability.

## Features

### Product Management
- Create, read, update, and delete (CRUD) operations for inventory management.
- Customize product details such as descriptions, prices, and availability.

### Real-Time Communication
- Integrated with Firebase Firestore for instant messaging with customers.
- Receive and respond to customer inquiries in real time.

### Store Profiles
- Manage personalized store profiles, including details like contact information and operating hours.
- Automatically synchronize updates across all linked products and interactions.

### Security
- API Key-based authentication for secure access.
- Encrypted data storage for robust protection of sensitive information.

### Scalability and Maintainability
- Implements Clean Architecture and MVVM patterns.
- Ensures smooth scalability and efficient code management.

## Technologies Used

- **Frontend:** Jetpack Compose for declarative UI design.
- **Backend:** Ktor server for handling operations and secure communications.
- **Database:** MongoDB for scalable and reliable data storage.
- **Messaging:** Firebase Firestore for real-time communication.

## Getting Started

### Prerequisites
- Android Studio Bumblebee or later.
- Kotlin 1.6 or later.
- Firebase account for integration.

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/ldgomm/maia-android.git
   ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files to install dependencies.

### Configuration
1. Set up Firebase:
   - Download your `google-services.json` file from the Firebase Console.
   - Add it to the `app` directory of the project.
2. Configure API Keys in the `BuildConfig` file or a secure location.

### Running the App
1. Build and run the app on your emulator or device:
   ```bash
   Shift + F10
   ```

## Contributing

We welcome contributions! To contribute:
1. Fork the repository.
2. Create a new branch for your feature or bugfix.
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes.
   ```bash
   git commit -m "Description of your changes"
   ```
4. Push to your fork.
   ```bash
   git push origin feature-name
   ```
5. Submit a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

For questions or support, please reach out to:
- **Email:** support@maiaapp.com
- **GitHub Issues:** [Issue Tracker](https://github.com/ldgomm/maia-android/issues)

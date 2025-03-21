# Fluffy App

Fluffy App is an Android application that fetches and displays a list of cat breeds. The app is built using a simple MVVM architecture.

## App Structure

### Data Layer
Consists of the following packages:
- **Local:**  
   - Contains the Data Access Objects (DAO), Entity classes, and Room Database.
- **Mapper:**  
  - Contains functions that map data between different data classes.
- **Remote:**  
  - Contains API endpoints, API DTOs, and the `RemoteMediator` which loads data from the network into the database.
- **Repository:**  
  - Implements the repository interface and contains the data logic.
- **DI Layer:**  
  - Handles dependency injection (using Hilt) and contains dependency modules.

### Domain Layer
Serves as an interface between the Data Layer and the UI Layer. It consists of:
- **Model:**  
  - Contains model or data classes used in the UI layer.
- **Repository:**  
   - Contains methods that are implemented by the Repository in the Data Layer.

> **Note:** This layer does not include a dedicated use-case layer.

### UI Layer
Handles everything related to the user interface:
- Contains screens, ViewModels, navigation, and other UI-related tasks.
- Each screen is organized into its own package along with related classes.

### Test
Contains test classes that validate the logic in the Data Layer.
- Uses JUnit, MockK, and Truth for unit testing.

## Libraries

- **Caching:**  
  Uses Room library to cache network responses locally, ensuring the app is usable offline and that data persists across screens.

- **Pagination:**  
  Uses the Paging3 library to retrieve data in pages to optimize API calls and improve app performance.

- **Remote Mediator:**  
  Uses a `RemoteMediator` from the Paging library to load data from the network into the database. This ensures a single source of truth for data and handles data refreshes, appends, and prepends.

- **Flows and Coroutines:**  
  Uses Kotlin Coroutines and Flows for asynchronous data handling and to achieve concurrency in a lifecycle-aware manner.

- **Dependency Injection:**  
  Uses the Hilt library for dependency injection, providing and injecting dependencies where needed.

- **Jetpack Navigation:**  
  Uses Jetpack Navigation for navigating between screens.

- **Coil:**  
  Uses the Coil library for efficient loading of network images into views.

- **Swipe Refresh:**  
  Implements the SwipeRefresh library to allow users to refresh data by swiping down on the screen.

- **Retrofit:**  
  Uses Retrofit as the HTTP client to handle network requests.

- **Mockk:**  
  Uses the Mockk library in tests to mock and stub interfaces for testing business logic.

- **JUnit:**  
  Uses JUnit as the testing framework to run unit tests.

- **Truth:**  
  Uses the Truth library for test assertions.

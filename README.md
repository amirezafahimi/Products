## FakeStore Products

FakeStore Products is an Android application developed using MVVM architecture, Kotlin Flow, and Jetpack Compose. It allows users to browse products fetched from the [FakeStore API](https://fakestoreapi.com/products) and search for products by title. The application also supports offline mode by caching the last fetched products.

### Features

- Display a list of products fetched from the FakeStore API.
- Allow users to search for products by title.
- Cache the last fetched products for offline mode.
- CLEAN architecture for separation of concerns.
- Jetpack Compose for building the UI.


### Dependencies

- Kotlin Coroutines for asynchronous programming.
- Retrofit for networking.
- Room Database for local caching.
- Jetpack Compose for building the UI.
- Turbine for testing Kotlin StateFlows.

### Usage

- Upon launching the application, users will see a list of products fetched from the FakeStore API.
- Users can search for products by typing the product title in the search box at the top of the screen.
- The application will cache the last fetched products for offline viewing. 

### Testing

The application includes unit tests for the data layer and ViewModel using Turbine for testing Kotlin Flows. To run the tests:

1. Open the project in Android Studio.
2. Navigate to the `test` directory.
3. Right-click on the test file and select "Run".

### Acknowledgments

- FakeStore API for providing the product data.
- Jetpack Compose and Kotlin Flow for simplifying UI development and asynchronous programming.
- Turbine for testing Kotlin Flows.

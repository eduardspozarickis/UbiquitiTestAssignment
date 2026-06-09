Application to view list of Ubiquiti products from public API with filtering by category via tabs: All, Cameras, IoT. Detailed view of each product by clicking on it.
Data stored to local Room database with 1 day of data storage, after will refetch from API.

In folder "materials" is drawn initial architecture, which was iterated to keep and try out Compose way of navigation between screens in same activity.

Libraries:
- Koin - simple Kotlin oriented library for dependency insenction with minimum boilerplate code;
- Retrofit - usual library for communication with API;
- Room - usual library to store data locally;
- Coil - recommended library in AndroidDevelopers for image loading in Compose, tried it out to see how it works.

Potential improvements that could be done:
- Firebase - analytics on issues/crashes, app performace, user engagement;
- detekt - Kotlin code analysis: detect code smells.

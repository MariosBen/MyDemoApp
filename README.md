This app is designed with the assumption that it is a small part of a much larger application. As such, it follows a modular structure, utilizes the MVI (Model-View-Intent) pattern, and is built using Clean Architecture. Otherwise, applying such a setup to a small app like this would be an over-engineering effort.
I have left references to the Shared module in the app to demonstrate how I would have implemented it. Even though I eventually decided to switch the data storage to Room due to the large number of events that need to be saved.

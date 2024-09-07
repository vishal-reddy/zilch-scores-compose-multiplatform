# Zilch Score - Dice scorekeeping app

* Experimental Compose multiplatform app built by my boys and I with an MVP in about 2 hours
* Boilerplate dependencies taken from my other projects
* Colors are designed by pre-teen boys and it shows.
* We will continue to clean it up over time but it was fun to see how excited they got just to see it work when we had just thought of it 2 hours prior

## Technologies

* Compose multiplatform
* Cupertino for iOS rendering of some components
* Flows
* ViewModel
* Material 3

This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
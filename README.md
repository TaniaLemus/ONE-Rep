# Welcome to ONE Max Rep!

This is my interview sample app where I utilized the newest technologies available for Android:

 - Compose
 - Hilt for dependency injection.
 - Material 3
 - Flow
 - MVI as a Desing pattern
 - I've also decided to stick with MPAndroidChart as recommended.

# How to setup

Clone the repository and open it with Android Studio Iguana (Build #AI-232.10300.40.2321.11668458).

 - Let the pakages to sync.
 - Click the play/debug button.

In case you encounter an error, simply invalidate the caches and restart (File > Invalidate Caches).

## How to add new workout data

In the assets folder of the Android app, you'll find the same text file (workoutData.txt) shared in the assessment. **Simply replace the content of the file** or replace it in the file location with the same name. 

If you want to change the name of the file, just update the constant `WORKOUT_FILE_NAME` located in the `WorkoutDataSource`:

    private const val WORKOUT_FILE_NAME = "NewName.txt"

## Architecture

As requested, I used MVI, which is MVVM-like but works with intents (States). It involves Views and ViewModels. I chose this pattern because it integrates better with Jetpack Compose. We follow the common Android Architecture:

-   Hilt
    -   Manages the injection of viewmodels, data sources, and repositories.
-   DataSource
    -   Handles data reading. The app could be improved by adding `Room` to implement some ADO (Android Data Objects).
-   Repository
    -   Defines the interface to implement the `WorkoutRepository`. This allows us to use an API or health data from services like Google Fit, Health Connect, etc.
-   ViewModel
    -   Creates UI States and reads data from the Repository.
    -   Also handles route navigation from Compose by reading the required data using parameters.
-   Screens
    -   Renders the views in Compose.
-   Navigation
    -   Utilizes a List Pane. If you're using a tablet or a device with a large screen, the list will render in landscape, next to the selected RM.
   - Coroutines
	   - Using flow to read the data as states and also to prevent the UI to get locked.

## Missing Features

-   Android App Links
    -   Even though routes are implemented, we can further improve the app by adding hosts to the manifest and implementing Universal Linking. Since we don't have a server to upload asset links, this is not necessary for now.
-   Modularization
    -   I created a single module (`App`), but internally structured the different folders for future modularization if needed.
-   Synchronization
    -   Since there aren't many data sources, synchronization isn't necessary.
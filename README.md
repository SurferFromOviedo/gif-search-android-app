<p align="center">
  <img src="https://github.com/user-attachments/assets/014cc540-2f84-4e13-b6d8-cd400ff80f89" alt="immge" width="300"/>
</p>

# Express yourself using GIFs!
Sometimes text or emojis are not enough to express the storm of emotions you are experiencing. GIFSearch will help you find the right GIF and share it with your closest ones! 

## Features
### Techincal
- MVVM architecture
- Single-acivity architecture
- Manual dependency injection
- Auto search - search requests performed after a 500 milliseconds after the user stops typing
- Pagination - loading more results when scrolling
- Vertical & horizontal orientation support
- Network availablity handling
- Error handling
- Unit tests

### UI
- GIFs sourced by GIPHY (2 sizes of each GIF)
- GIFs are displayed in a grid
- Clicking on a GIF in the grid opens the GIF in its original size
- Loading indicators
- Error displays
- Custom "Send GIF" button drawn in Canva
  
### Technologies used 
- Android Studio
- Kotlin
- Jetpack Compose
- Asynchrony - Coroutines, Flows, LiveData
- Retrofit with Kotlin serialization Converter
- Coil
- Paging Library
- ConnectivityManager class
- Junit, Mockito, Robolectric - for tests

## Getting Started

To run this project locally, follow these steps:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/SurferFromOviedo/gif-search-android-app.git

2. **Open the project in Android Studio**:
    - Launch Android Studio.
    - Go to File > Open and choose the directory where the project was cloned.
      
3. **Synchronize the project with Gradle**:
    - Android Studio will automatically sync the project.

4. **Add GIPHY API key**:
   ```kotlin
   // To make the app work, replace "YOUR_KEY" with your GIPHY API key in the file GiphyApiService.kt
   private const val API_KEY = "YOUR_KEY"

6. **Build and run the project**:
    - Press the Run button or go to Build > Build APK to generate the APK.


## Screenshots 
> GIF grid with "funny dancing cats" search query.
> In the lower left corner "Powered By GIPHY" banner is located at the request of GIPHY.
<img src="https://github.com/user-attachments/assets/e5e3e55e-07ed-43d5-90b6-54ebee93636d" alt="immge" width="200"/>

> Clicking on a GIF in the grid opens GIF in its original size.
<img src="https://github.com/user-attachments/assets/59fb420d-34cb-4207-9a87-0df23f75f064" alt="immge" width="200"/>

> Sharing GIF with your contacts.
<img src="https://github.com/user-attachments/assets/d7250db4-a384-4581-a870-1b1cdbb99e35" alt="immge" width="200"/>

> GIF grid in landscape mode.
<img src="https://github.com/user-attachments/assets/b51a3651-3fd2-48a7-a839-f8aedbe4eac2" alt="immge" width="375"/>

> Network availablity handling. When the network connection returns content downloading will resume automatically.
<img src="https://github.com/user-attachments/assets/e3db3a32-07e0-4fa1-9430-e5e4c0eee43e" alt="immge" width="200"/>
<img src="https://github.com/user-attachments/assets/f1f8392b-16b5-4ec8-b066-a4f52016ee73" alt="immge" width="200"/>


> Error screen.
<img src="https://github.com/user-attachments/assets/b9d312c3-3479-429d-a2b2-e8c65f9c401f" alt="immge" width="200"/>

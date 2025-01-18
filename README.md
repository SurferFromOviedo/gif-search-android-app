<p align="center">
  <img src="https://github.com/user-attachments/assets/838e71ab-798b-46be-b98d-9246b98d30c8" alt="immge" width="300"/>
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
<img src="https://github.com/user-attachments/assets/833e41c5-9300-44f8-9d48-5e21814c044b" alt="immge" width="200"/>

> Clicking on a GIF in the grid opens GIF in its original size.
<img src="https://github.com/user-attachments/assets/4778a34e-1de2-4377-8447-1636c8d8a09c" alt="immge" width="200"/>

> Sharing GIF with your contacts.
<img src="https://github.com/user-attachments/assets/82b438b4-26ce-4aec-a840-dbd29dedaae1" alt="immge" width="200"/>

> GIF grid in landscape mode.
<img src="https://github.com/user-attachments/assets/2031751b-05c0-4428-ad9c-0c26b3c34624" alt="immge" width="375"/>

> Network availablity handling. When the network connection returns content downloading will resume automatically.
<img src="https://github.com/user-attachments/assets/cf9f7f1a-94a1-4f18-a25d-c8d7978184ad" alt="immge" width="200"/>
<img src="https://github.com/user-attachments/assets/4c41e9b6-c104-43cb-a326-3685da06603b" alt="immge" width="200"/>


> Error screen.
<img src="https://github.com/user-attachments/assets/b9d312c3-3479-429d-a2b2-e8c65f9c401f" alt="immge" width="200"/>

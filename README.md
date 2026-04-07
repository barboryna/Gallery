# Gallery

An Android photo gallery app built with **Jetpack Compose** that fetches photos from the [Lorem Picsum](https://picsum.photos/) API.
Users can browse a scrollable photo grid, view full photo details, and manage a list of favourite photos stored locally.

---

## Features

- **Gallery screen** – paginated grid of photos loaded from the Picsum API (30 photos per page).
- **Photo detail screen** – full-resolution photo with author, dimensions, and favourite toggle. Slides up from the bottom with an animated transition.
- **Favourites screen** – locally persisted list of favourited photos (in progress).
- **Offline-first favourites** – favourite state is stored in a Room database and merged with remote data on every fetch.
- **Error handling** – each screen exposes a loading/error state and a retry button.

---

## Tech Stack

| Layer | Technology |
|---|---|
| UI | Jetpack Compose, Material 3, Compose Navigation |
| Image loading | Glide (Compose extension) |
| Networking | Retrofit 3 + OkHttp 5 (with logging interceptor), Gson converter |
| Local storage | Room 2 |
| Dependency injection | Hilt |
| Async | Kotlin Coroutines |
| Serialization | Kotlin Serialization (type-safe nav args) |
| Testing | JUnit 4, MockK, Kotlin Coroutines Test |

---

## Architecture

The app follows a **single-Activity, multi-screen** pattern with a clean layered architecture:

```
MainActivity
└── HomeScreen (NavHost)
    ├── GalleryScreen       ← photo grid
    ├── PhotoDetailScreen   ← single photo view
    └── FavoritesScreen     ← saved favourites
```

### Layer breakdown

```
app/
├── core/
│   ├── photo/
│   │   ├── Photo.kt                    – domain model
│   │   ├── PhotoRepository.kt          – repository interface + implementation
│   │   └── network/
│   │       ├── PicsumApiService.kt     – Retrofit API definition
│   │       ├── PicsumRemoteDataSource  – remote data source
│   │       └── PhotoResponse.kt        – network DTO
│   ├── database/
│   │   ├── GalleryDatabase.kt          – Room database
│   │   ├── FavoriteDao.kt              – DAO for favourite photos
│   │   └── FavoriteEntity.kt           – Room entity
│   └── di/                             – Hilt modules (network, database, repository)
│
├── home/
│   ├── HomeScreen.kt / HomeViewModel.kt – navigation host & nav events
│   ├── gallery/
│   │   ├── GalleryScreen.kt            – photo grid UI
│   │   ├── GalleryViewModel.kt
│   │   └── GetPhotosUseCase.kt
│   ├── detail/
│   │   ├── PhotoDetailScreen.kt        – photo detail UI
│   │   ├── PhotoDetailViewModel.kt
│   │   └── GetPhotoDetailUseCase.kt
│   └── favorites/
│       ├── FavoritesScreen.kt          – favourites UI (WIP)
│       ├── FavoritesViewModel.kt
│       ├── GetFavoritesUseCase.kt
│       └── ToggleFavoriteUseCase.kt
│
└── util/
    ├── compose/                        – shared Composables (AppImage, GalleryImage, …)
    │   └── navigation/ScreenConfig.kt  – sealed type-safe nav destination marker
    └── state/Effect.kt                 – one-shot state helper (StateTriggeredEffect)
```

### Navigation

Navigation is driven by `HomeViewModel` which exposes two one-shot `MutableState` effects:

| Effect | Purpose |
|---|---|
| `navigate` | Push a new `ScreenConfig` destination onto the back stack |
| `navigateBack` | Pop the current destination |

`StateTriggeredEffect` (a custom `LaunchedEffect` wrapper) consumes these values exactly once, preventing re-delivery on recomposition.

---

## API

Photos are served by the public **Lorem Picsum** API — no API key required.

| Endpoint | Usage |
|---|---|
| `GET https://picsum.photos/v2/list?page=&limit=` | Paginated photo list |
| `GET https://picsum.photos/id/{id}/info` | Single photo metadata |
| `https://picsum.photos/id/{id}/{width}/{height}` | Thumbnail / full image CDN URL |

---

## Known Limitations / TODOs

- `FavoritesScreen` UI is not yet implemented (placeholder).
- `PhotoRepository.getFavorites()` and `toggleFavorite()` are stubbed out with `TODO` comments.
- No pagination beyond the first page in the gallery.
- No offline caching of photo metadata (only favourites are persisted locally).


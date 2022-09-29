# GoTown
**Android Native** project with Jetpack Compose, Retrofit REST API client framework along with Kotlin Coroutines. Can run on a device with Android Lollipop and above.

GoTown is an app that makes use of [An API of Ice And Fire](https://anapioficeandfire.com/) to show list of house in Game of Thrones and details of a house, once it's tapped.

### Structure
Application aspires to use the MVI architecture with viewmodels and observable streams of data.

There is a viewmodel in the core directory that uses MutableStateFlow to hold state of the app and communicate changes through a stream of data. It also exposes functions for the UI to initiate a change of state upon an event. The UI is built with Jetpack compose and observes relevant data streams from the viewmodel then updates itself. The UI for list of houses and house detail is in the houses directory.

The logic for the app is handled in a repository class. The repository class uses the network client to make calls to the REST API endpoints and returns either an error or response in an [Either monad](https://arrow-kt.io/docs/apidocs/arrow-core/arrow.core/-either/).

### Building
App can be built with Android Studio versions 4.x and above.


### Screenshots

**List of houses**
<br/>
![Screenshot_20220920_183214](https://user-images.githubusercontent.com/45716888/191337323-094640ac-e594-42d4-8abe-637f577cd6e9.png)


**House details**
<br/>
![Screenshot_20220920_183152](https://user-images.githubusercontent.com/45716888/191337379-1e9c2a45-5bc2-4b74-bafb-aa6db1dcb101.png)

**Splashscreen**
<br/>
![Screenshot_20220920_183256](https://user-images.githubusercontent.com/45716888/191337488-6d079af6-6247-46b2-8606-102817e84e3c.png)


### Languages, libraries and tools used

* [Kotlin](https://kotlinlang.org/)
* [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
* [Retrofit client library](https://github.com/ktorio/ktor](https://square.github.io/retrofit/))
* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html)
* [Jetpack Compose](https://developer.android.com/jetpack/compose)


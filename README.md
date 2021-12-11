MARVEL CHARACTERS
---------------------
Para dar solución a la prueba se ha desarrollado una aplicación con arquitectura MVVM (CLEAN)
que contiene tres principales módulos: data, domain, ui.
Para la inyección de dependencias se utiliza DaggerHilt.

Módulo data
----------------------
En este módulo se encuentra todo lo relacionado con las fuentes de datos, bases de datos, consultas a base de datos,
llamadas a servicios, modelos de datos, modelos de las respuestas a los servicios…
Para la base de datos local se utiliza ROOM y para las llamadas a los servicios se está usando Retrofit.
También contienen la implementación de los repositorios que son llamados mediante las interfaces de la capa domain por
los casos de uso donde está la lógica de negocio.

Módulo domain
------------------------
Este modulo contiene la lógica de negocio de la aplicación, aquí se encuentran los usecases,
los modelos de datos con los que se va a trabajar en la capa de UI y las interfaces a la implementación
de los repositorios del módulo data, permitiendo obtener datos sin conocer la fuente origen.

Módulo UI
---------------------------
Aquí se encuentra toda la parte que pertenece a la vista, ViewModels, Fragments, Adapters…
Los Adapters permiten que se muestre la información obtenida en los recyclerViews
Los fragments son con los que el usuario interacciona directamente a través de eventos, 
permitiendo la manipulación de la aplicación y obteniendo los datos a través de los viewModels.
Los viewModel tienen el fin de almacenar y administrar datos relacionados con la IU de manera optimizada 
para los ciclos de vida, actualiza los LiveData que son escuchados por los observers situados en los fragments
para realizar los cambios oportunos en la vista.

TESTS
---------
Se han añadido test unitarios en todos los módulos de la aplicación:

Modulo data
CharactersDaoTest: Contiene tests de todas las funciones del fichero CharactersDao.

Módulo domain
CharactersRepositoryTest: Contiene tests de todas las funciones del repositorio que obtiene datos del servicio, probando así toda la parte de las llamadas a los servicios.
FavoritesRepositoryTest: Contiene tests de todas las funciones del repositorio que interactúa con la base de datos, probando toda la parte de las llamadas a los métodos que interactúan con la base de datos.

Modulo ui
Contiene test de todos los viewmodels, quedando probados todos su métodos y los usecases que utiliza.
ViewModels
-	DetailFavoritesViewModelTest
     DetailtViewModelTest
     FavoritesViewModelTest
     HomeViewModelTest

MainActivityTest
También con tiene un test para la parte de UI usando espresso que inicia la aplicación, hace una búsqueda de un personaje y lo añade a favoritos. Después va a la pantalla de favoritos y lo elimina, cubriendo toda la funcionalidad de la aplicación.

Librerías añadidas
-------------------------------
-	Kotlin
-	Rxjava2
-	RxRelay
-	Dagger2
-	ViewModel (jetpak)
-	LiveData(jetpak)
-	Glide(cargar imágenes)
-	Navegación (jetpak)
-	ViewBinding (jetpak)
-	Corutinas
-	PagedList
-	Room -DataBase
-	Retrofit2 (para llamadas de servicio)
-	okHttp (agregar apiKey alos parametros)
-	Espresso (UI Tests)
-	DaggerHilt (para pruebas unitarias)
# RepositorioMarvel

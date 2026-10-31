# MartiniChef - Grupo 14

Aplicación móvil desarrollada para la asignatura Desarrollo de Aplicaciones Móviles (DSY1105) de Duoc UC.

## Integrantes

- Matías Prieto
- Tomás Gutiérrez

## Descripción

MartiniChef es una aplicación móvil desarrollada en Kotlin con Jetpack Compose. Su objetivo es ofrecer una interfaz simple para registrar usuarios, administrar información de perfil y consultar recetas de cocina.

El proyecto utiliza arquitectura MVVM, navegación entre pantallas, validación de formularios, almacenamiento local, animaciones y acceso a recursos del dispositivo.

## Funcionalidades

### Navegación

La aplicación permite navegar entre las siguientes pantallas:

- Pantalla principal.
- Registro de usuario.
- Resumen del registro.
- Perfil.
- Configuración.
- Catálogo de recetas.
- Detalle de receta.

### Registro y validaciones

- Validación de nombre.
- Validación de correo electrónico.
- Validación de contraseña.
- Validación de dirección.
- Aceptación de términos y condiciones.
- Mensajes visuales e íconos de error.
- Lógica administrada desde `UsuarioViewModel`.

### Catálogo de recetas

- Listado de recetas MartiniChef.
- Búsqueda por nombre o categoría.
- Información de categoría y tiempo de preparación.
- Detalle con ingredientes y pasos.
- Navegación entre catálogo y detalle.

### Recetas favoritas

- Permite agregar y quitar recetas favoritas.
- Animación de color y tamaño del corazón.
- Mensaje visual de confirmación.
- Persistencia local de favoritos mediante DataStore.
- Las favoritas permanecen guardadas al cerrar y abrir la aplicación.

### Recursos nativos

- Acceso a la cámara.
- Selección de imágenes desde la galería.
- Manejo de permisos.
- Uso de FileProvider.

### Diseño y animaciones

- Jetpack Compose y Material Design 3.
- Diseño adaptable para distintos tamaños de pantalla.
- `animateColorAsState`.
- `animateFloatAsState`.
- `AnimatedVisibility`.
- Retroalimentación visual durante la interacción.

## Arquitectura

El proyecto utiliza MVVM para separar la interfaz de la lógica.

```text
model/
    Receta.kt
    UsuarioUiState.kt

data/
    EstadoDataStore.kt
    FavoritosDataStore.kt

viewmodel/
    EstadoViewModel.kt
    MainViewModel.kt
    PerfilViewModel.kt
    RecetasViewModel.kt
    UsuarioViewModel.kt

ui/screens/
    PantallaPrincipal.kt
    RegistroScreen.kt
    ResumenScreen.kt
    ProfileScreen.kt
    SettingsScreen.kt
    RecetasScreen.kt
    DetalleRecetaScreen.kt

navigation/
    AppNavigation.kt
```

## Tecnologías utilizadas

- Kotlin.
- Android Studio.
- Jetpack Compose.
- Material Design 3.
- Navigation Compose.
- ViewModel.
- StateFlow.
- DataStore.
- Activity Result API.
- FileProvider.
- Git y GitHub.
- Trello.

## Requisitos

- Android Studio.
- JDK incluido con Android Studio.
- Emulador o dispositivo Android API 24 o superior.
- Conexión a internet para descargar dependencias la primera vez.

## Cómo ejecutar el proyecto

1. Clonar el repositorio:

```bash
git clone https://github.com/MatiasPrietoc/MartiniChef_grupo14.git
```

2. Abrir Android Studio.

3. Seleccionar **Open**.

4. Abrir la carpeta `MartiniChef_grupo14`.

5. Esperar que finalice la sincronización de Gradle.

6. Seleccionar un emulador o dispositivo físico.

7. Presionar el botón verde **Run app**.

## Distribución del trabajo

### Matías Prieto

- Estructura inicial del proyecto.
- Formulario de registro y validaciones.
- Navegación base.
- Cámara y galería.
- Vistas adaptativas.
- Estado y animaciones iniciales.

### Tomás Gutiérrez

- Modelo y catálogo de recetas.
- Buscador por nombre y categoría.
- Pantalla de detalle.
- Navegación entre catálogo y detalle.
- Animación de recetas favoritas.
- Persistencia de favoritos mediante DataStore.
- Actualización de documentación.

## Control de versiones

El desarrollo se realizó mediante ramas de funcionalidades, commits técnicos y Pull Requests para integrar los cambios a la rama principal.
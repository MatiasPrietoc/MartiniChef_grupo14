# MartiniChef_grupo14

## Información del Encargo
* **Asignatura:** Desarrollo de Aplicaciones Móviles (DSY1105)
* **Institución:** Duoc UC
* **Integrantes:**
    * Matias Prieto
    * Tomas Gutierrez

---

## Descripción del Proyecto
**MartiniChef_grupo14** es una aplicación móvil desarrollada en Android Studio con **Jetpack Compose** y **Kotlin**. Integra navegación estructurada entre 5 pantallas, validación visual de formularios en tiempo real, persistencia local de estado, arquitectura MVVM y acceso a recursos nativos del dispositivo.

---

## Funcionalidades Implementadas

* **Navegación e Interfaz (NavHost):** Flujo continuo entre 5 pantallas (`PantallaPrincipal`, `RegistroScreen`, `ProfileScreen`, `ResumenScreen` y `SettingsScreen`).
* **Validación de Formularios:** Control de errores por campo (nombre, correo con expresión regular y contraseña con mínimo de caracteres) con retroalimentación visual (íconos de alerta, bordes rojos y texto explicativo).
* **Arquitectura MVVM:** Lógica de negocio desacoplada de la interfaz mediante ViewModels (`EstadoViewModel`, `UsuarioViewModel`, `MainViewModel`, `PerfilViewModel`).
* **Persistencia Local:** Almacenamiento local del estado de activación mediante **Jetpack DataStore**.
* **Recursos Nativos:** Integración con la **Cámara** y **Galería** utilizando `FileProvider` para captura y selección de imágenes.
* **Animaciones Visuales:** Transiciones dinámicas de estado con `animateColorAsState` y visibilidad animada con `AnimatedVisibility`.

---

## Requisitos de Ejecución
* **Android Studio:** Versión Ladybug / Hedgehog o superior.
* **JDK:** Java 17 o superior.
* **Dispositivo / Emulador:** Android 8.0 (API 26) o superior.

---

## Pasos para Clonar y Ejecutar
1. Clonar el repositorio público:
   ```bash
   git clone https://github.com/MatiasPrietoc/MartiniChef_grupo14
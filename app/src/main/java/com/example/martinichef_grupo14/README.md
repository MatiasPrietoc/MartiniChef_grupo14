# MartiniChef - Grupo 14

Proyecto académico de Desarrollo de Aplicaciones Móviles
(DSY1105), Duoc UC.

## Integrantes

- Matías Prieto
- Tomás Gutiérrez

## Descripción

MartiniChef es una aplicación móvil de cocina desarrollada
con Kotlin y Jetpack Compose.

Permite consultar recetas ilustradas, explorar contenidos
educativos simulados, guardar un perfil local y personalizar
la apariencia de la aplicación.

El proyecto integra formularios validados, navegación,
gestión de estado, almacenamiento local, animaciones y
recursos nativos del dispositivo.

## Alcance académico

La aplicación es una simulación académica.

- El login utiliza credenciales fijas de demostración.
- No existe autenticación de servidor.
- El registro guarda un perfil local, no una cuenta de acceso nueva.
- Las recetas y los contenidos educativos están incluidos en el código.
- Las imágenes de recetas y el logo fueron generados con IA.
- Los videos se representan mediante fichas y guías de lectura.
- No hay reproducción de archivos de video ni certificación de cursos.

## Acceso de demostración

Correo: demo@martinichef.cl

Contraseña: Martini123

Las credenciales son ficticias y aparecen en la pantalla de login.

La contraseña escrita en Registro no modifica estas credenciales
ni se guarda en el dispositivo.

## Funcionalidades

### Presentación y navegación

- Presentación con logo de MartiniChef.
- Desvanecimiento del logo antes del login.
- Login simulado con validación de entrada.
- Navegación inferior entre Inicio, Recetas, Aprender, Perfil y Ajustes.
- Navegación a detalles mediante identificadores.
- Regreso desde Resumen según el recorrido de ingreso.

### Registro y perfil

- Nombre, correo, contraseña, dirección y aceptación de términos.
- Validaciones administradas desde UsuarioViewModel.
- Mensajes e íconos de error.
- Resumen de los datos.
- Guardado local de nombre, correo y dirección.
- Recuperación del perfil al volver a abrir la aplicación.

### Foto de perfil

- Selección desde la galería.
- Captura con la cámara.
- Solicitud del permiso de cámara.
- Uso de Activity Result API y FileProvider.
- Estado de imagen compartido entre Perfil e Inicio.
- Copia de la foto al almacenamiento interno.
- Recuperación de la foto después de cerrar y abrir.
- Indicadores de carga, guardado y error.

### Recetas

Catálogo ilustrado de cuatro recetas:

1. Pasta al tomate.
2. Ensalada fresca.
3. Crema de zapallo y zanahoria.
4. Panqueques de avena y plátano.

Cada receta incluye descripción, tiempo estimado,
ingredientes, fotografía ilustrativa y preparación.

### Aprender

- Filtros Todos, Videos y Cursos.
- Fichas con descripción y duración estimada.
- Pantalla de detalle con lecciones.

Contenidos de demostración:

- Organización de ingredientes.
- Cortes básicos de verduras.
- Primeros pasos en la cocina.
- Desayunos fáciles con avena.

### Apariencia y animaciones

- Paleta de cocina con verde, terracota y crema.
- Modos día y noche.
- Tamaños de texto pequeño, normal y grande.
- Ajuste de texto relativo a la configuración de Android.
- Persistencia de las preferencias de apariencia.
- Consejos de cocina que pueden mostrarse u ocultarse.
- Persistencia de la preferencia de consejos.
- Animaciones de color y visibilidad.
- Confirmación visual después de guardar el estado.
- Diseños de Inicio para anchos compacto, mediano y expandido.

## Arquitectura y organización

El proyecto separa interfaz, lógica de presentación,
modelos y acceso a datos.

- model: estructuras de datos.
- viewmodel: estado, validaciones y coordinación.
- data: almacenamiento local con DataStore.
- repository: recetas y contenidos educativos simulados.
- navigation: rutas y navegación.
- ui/screens: pantallas.
- ui/components: componentes visuales reutilizables.
- ui/theme: colores y tipografía.
- ui/utils: utilidades de tamaño de ventana.

RegistroUiState está declarado en UsuarioViewModel.kt.

El catálogo y el contenido educativo son listas estáticas
proporcionadas por repositorios.

## Almacenamiento

DataStore Preferences conserva:

- Nombre, correo y dirección del perfil.
- Nombre del archivo de foto de perfil.
- Preferencia para mostrar consejos.
- Modo día/noche.
- Escala del texto.

La foto se almacena como archivo privado de la aplicación.
Sus bytes no se guardan en DataStore.

La contraseña del registro y la aceptación de términos
no se conservan.

Borrar los datos de la aplicación o desinstalarla puede
eliminar los datos locales.

## Tecnologías

- Kotlin.
- Android Studio.
- Jetpack Compose.
- Material 3.
- Navigation Compose.
- ViewModel y StateFlow.
- Corrutinas.
- DataStore Preferences.
- Coil.
- Activity Result API.
- FileProvider.
- Git y GitHub.
- Trello.

## Requisitos

La configuración del proyecto utiliza:

- compileSdk y targetSdk 37.
- minSdk 24.
- Gradle 9.6.0.
- JDK 25 para el daemon de Gradle.

Se necesita una versión de Android Studio compatible
con la configuración del proyecto.

La primera sincronización requiere internet para
descargar herramientas y dependencias.

## Ejecución

1. Clonar o descargar el repositorio.
2. Abrir la carpeta principal en Android Studio.
3. Configurar el SDK y JDK requeridos.
4. Sincronizar Gradle.
5. Seleccionar un emulador o dispositivo compatible.
6. Ejecutar el módulo app.
7. Ingresar con la cuenta de demostración.

```bash
git clone https://github.com/MatiasPrietoc/MartiniChef_grupo14.git
```

## Lista de verificación

Marcar únicamente después de realizar cada prueba:

- [ ] Presentación, desvanecimiento y login.
- [ ] Login válido, inválido y campos vacíos.
- [ ] Registro válido y errores por campo.
- [ ] Recuperación del perfil al reabrir.
- [ ] Regreso desde Resumen a Login o Inicio.
- [ ] Navegación inferior.
- [ ] Cuatro recetas con sus imágenes y detalles.
- [ ] Filtros y detalles de Aprender.
- [ ] Consejos, animaciones y persistencia.
- [ ] Modo día/noche y tamaño de texto.
- [ ] Recuperación de preferencias al reabrir.
- [ ] Galería, cámara y cancelación.
- [ ] Recuperación de una captura tras recreación.
- [ ] Foto persistente y compartida con Inicio.
- [ ] Legibilidad con texto grande y teléfono horizontal.
- [ ] Vista en tablet.

## Referencia visual

Se utilizó https://www.gourmet.cl/ como referencia
de ideas de organización y presentación culinaria.

El logo y las fotografías ilustrativas de esta versión
fueron generados para el proyecto.

## Repositorio y planificación

GitHub:
https://github.com/MatiasPrietoc/MartiniChef_grupo14

Trello:
https://trello.com/b/f2L94EoY/master-martini-grupo-14

Las tareas, responsables y evidencias deben reflejar
el trabajo real del equipo.
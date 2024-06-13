# Trabajo Práctico Integrador – Laboratorio V

Este repositorio contiene el desarrollo de una aplicación para Android que permite listar las noticias de un diario mediante la lectura de su RSS. La aplicación se desarrolló como parte del Trabajo Práctico Integrador del Laboratorio V.

## Funcionalidades

- Conexión a Internet para descargar y visualizar un RSS de noticias.
- Listado vertical de noticias ordenadas por fecha en un RecyclerView.
- Cada noticia se muestra en una CardView, incluyendo título, descripción, fecha, foto y fuente de la noticia. La foto se descarga solo cuando la vista es visible.
- Sistema de búsqueda en la toolbar para filtrar las noticias con más de 3 letras.
- Configuración de visualización de RSS de noticias a través de un menú, con opción de seleccionar los RSS deseados mediante un Dialog. La configuración se almacena mediante SharedPreferences.
- Posibilidad para el usuario de agregar nuevos RSS para futuras selecciones.
- Al hacer clic en una noticia, se abre una segunda Activity donde se muestra la noticia completa. Esta Activity incluye un floating action button para compartir la noticia y el botón de retroceso en la toolbar para volver a la Activity principal.

## Tecnologías utilizadas

- Android SDK
- Java
- RecyclerView
- CardView
- SharedPreferences

## Instalación

Para probar la aplicación, sigue estos pasos:

1. Clona este repositorio en tu máquina local.
2. Abre el proyecto en Android Studio.
3. Ejecuta la aplicación en un emulador o dispositivo Android.

## Autores

- [Pablo Alberto Cugliari](https://github.com/pacugliari) - Desarrollador principal

## Licencia

Este proyecto está licenciado bajo la [Licencia MIT](https://opensource.org/licenses/MIT).


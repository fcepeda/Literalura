# Proyecto de Gestión de Libros y Autores

Este proyecto es una aplicación en Java que gestiona libros y autores utilizando una base de datos PostgreSQL. Permite realizar búsquedas de libros a través de una API externa (Gutendex), almacenarlos en la base de datos, y gestionar autores, incluyendo la capacidad de ver estadísticas sobre los libros según el idioma.

## Descripción

Este proyecto es una aplicación Java que permite gestionar libros y autores, utilizando Spring Boot y JPA para interactuar con una base de datos PostgreSQL. A través de una API externa (Gutendex), el sistema permite buscar libros por título exacto o parcial, guardarlos en la base de datos y asociarlos con los autores correspondientes. Además, la aplicación ofrece funcionalidades para consultar estadísticas sobre los libros en diferentes idiomas, como inglés, español y francés, así como ver información sobre autores vivos en un año específico. Este proyecto forma parte de un curso de Backend de Alura, cuyo objetivo es enseñar a integrar bases de datos, trabajar con APIs y construir aplicaciones robustas en Java.

### Funcionalidades

Este proyecto ofrece las siguientes funcionalidades:

1. **Búsqueda de libros por título**: El usuario puede buscar libros por título exacto o parcial. La búsqueda se realiza a través de la API de Gutendex y los resultados se guardan en la base de datos.

2. **Mostrar libros guardados**: Los libros que se encuentren guardados en la base de datos pueden ser consultados, mostrando detalles como el título, autor e idioma.

3. **Mostrar autores**: Se puede visualizar una lista de autores que han sido asociados a los libros guardados en la base de datos. La lista incluye detalles como nombre, fecha de nacimiento y fecha de fallecimiento.

4. **Filtrar autores vivos según un año**: Permite filtrar y mostrar los autores que estaban vivos en un año determinado. Se basa en la fecha de fallecimiento de cada autor.

5. **Estadísticas de idiomas**: Muestra la cantidad de libros guardados en diferentes idiomas, incluyendo inglés, español, francés, portugués y otros. Además, muestra el total de libros guardados en la base de datos.

### Cómo funciona

El proyecto está basado en el framework **Spring Boot**, que facilita la conexión con la base de datos y la implementación de servicios RESTful. La aplicación utiliza **Spring Data JPA** para interactuar con la base de datos PostgreSQL.

1. **Búsqueda de libros**: 
   - Al ingresar un título exacto o parcial, el sistema consulta una API externa (Gutendex) para obtener información sobre el libro.
   - Si el libro no está en la base de datos, se guarda de forma automática.
   - Si ya existe, no se guarda de nuevo.

2. **Autores y libros en la base de datos**:
   - Los autores son almacenados en la base de datos y asociados a los libros.
   - Si un libro tiene un autor que ya existe, el sistema reutiliza el autor en lugar de crear uno nuevo.

3. **Estadísticas**:
   - El usuario puede consultar cuántos libros están disponibles en diferentes idiomas.
   - Los idiomas se muestran de forma legible (por ejemplo, "es" se convierte en "Español").


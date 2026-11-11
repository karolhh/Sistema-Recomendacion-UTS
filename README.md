# Sistema de Recomendación de Docentes Evaluadores para Trabajos de Grado

**Autores:**
* Karol Dayanna Hernandez Herreño
* Julieth Alexandra León Hernández
* **Director:** Ing. Diego Álvarez Zárate

**Institución:** Unidades Tecnológicas de Santander (UTS) - Tecnología en Desarrollo de Sistemas Informáticos.

---

## Descripción del Proyecto
Este sistema de software permite al Comité de Trabajos de Grado gestionar la asignación de directores y evaluadores de manera equitativa y automatizada. Utiliza un algoritmo de recomendación basado en el perfil académico del docente y su carga laboral (créditos).

## Arquitectura
*   **Backend:** Java + Spring Boot (API REST, JWT, PostgreSQL en Neon).
*   **Frontend:** React + Vite + TypeScript + Tailwind CSS.

---

## Requisitos Previos para Ejecución
*   Java JDK 17 o superior.
*   Node.js 18 o superior.
*   Maven instalado.

---

## Instrucciones de Ejecución

### 1. Configurar y ejecutar el Backend
1. Abre la carpeta `backend` en tu IDE (IntelliJ, Eclipse o VS Code).
2. Asegúrate de que las credenciales de la base de datos en `src/main/resources/application.properties` estén correctas (conexión a Neon).
3. Ejecuta la clase principal `RecomendadorDocentesApplication.java`.
4. El servidor se iniciará en `http://localhost:8080`.

### 2. Configurar y ejecutar el Frontend
1. Abre una terminal en la carpeta `frontend-recomendador`.
2. Ejecuta `npm install` para descargar las dependencias.
3. Ejecuta `npm run dev` para iniciar el servidor de desarrollo.
4. Abre tu navegador en `http://localhost:5173`.

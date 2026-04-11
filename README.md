# Sistema Batch de Reprocesamiento Inteligente 🚀

Sistema backend de alto rendimiento desarrollado con **Java 21**, **Spring Boot 3.4** y **Spring Batch 5**, diseñado para el procesamiento masivo de operaciones con resiliencia, manejo de estados e idempotencia.

## 🏗️ Arquitectura y Decisiones Técnicas

Este proyecto no es solo un proceso batch; aplica patrones de diseño de sistemas empresariales:

*   **Chunk-Oriented Processing:** Procesamiento por bloques (chunks) de 10 registros para optimizar el uso de memoria y garantizar la transaccionalidad.
*   **Idempotencia:** El sistema garantiza que una operación procesada exitosamente no sea duplicada ni alterada en ejecuciones posteriores.
*   **Optimistic Locking:** Implementado mediante `@Version` en JPA para manejar la concurrencia y evitar conflictos de escritura en entornos paralelos.
*   **Estrategia de Reintentos:** Manejo inteligente de fallos temporales (como errores de red) con una transición de estados de `PENDING` -> `RETRY` -> `FAILED`.

## 🛠️ Stack Tecnológico
*   **Java 21:** Uso de las últimas mejoras del lenguaje.
*   **Spring Boot 3.4 / Spring Batch 5:** El estándar de la industria para microservicios y procesos batch.
*   **Spring Data JPA:** Abstracción de persistencia con Hibernate.
*   **H2 Database:** Base de datos en memoria para desarrollo ágil y tests.
*   **GitHub Actions:** Pipeline de Integración Continua (CI) configurado para validación automática de cada commit.

## 📊 Ciclo de Vida de una Operación
1.  **PENDING:** Estado inicial.
2.  **PROCESSING:** El registro está siendo transformado.
3.  **SUCCESS:** Procesamiento completado con éxito.
4.  **RETRY:** Fallo controlado; el registro será procesado nuevamente.
5.  **FAILED:** Se alcanzó el máximo de reintentos (3) o error crítico.

## 🚀 Cómo ejecutar el proyecto

1. Clonar el repositorio: `git clone ...`
2. Compilar y pasar tests: `./mvnw clean install`
3. Ejecutar la aplicación: `./mvnw spring-boot:run`
4. Disparar el proceso manualmente:
   ```bash
   curl -X POST http://localhost:8080/batch/run

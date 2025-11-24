# 🧬 Mutant Detector API - Examen MercadoLibre

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-8.x-blue.svg)](https://gradle.org/)

> API REST para detectar mutantes basándose en secuencias de ADN. Proyecto implementado para el examen técnico de MercadoLibre.

---

## 📋 Descripción del Proyecto

Magneto quiere reclutar mutantes para luchar contra los X-Men. Esta API detecta si un humano es mutante analizando su secuencia de ADN.

**Un humano es mutante si:**
- Su ADN contiene **más de una secuencia** de cuatro letras iguales (A, T, C, G)
- Las secuencias pueden estar en dirección:
  - Horizontal (→)
  - Vertical (↓)
  - Diagonal (↘)
  - Anti-diagonal (↙)

---

## 🎯 Niveles Implementados

### ✅ Nivel 1: Algoritmo de Detección
- [x] Función `boolean isMutant(String[] dna)` implementada
- [x] Búsqueda optimizada en todas las direcciones
- [x] Early termination para mejor rendimiento
- [x] Complejidad O(N²) con optimizaciones

### ✅ Nivel 2: API REST
- [x] Endpoint `POST /mutant` implementado
- [x] Responde 200 OK si es mutante
- [x] Responde 403 Forbidden si es humano
- [x] Validaciones completas del formato de ADN

### ✅ Nivel 3: Persistencia y Estadísticas
- [x] Base de datos H2 integrada
- [x] Un registro único por ADN (usando hash SHA-256)
- [x] Endpoint `GET /stats` implementado
- [x] Retorna estadísticas de verificaciones

---

## 🚀 Inicio Rápido

### Prerequisitos

- Java 17 o superior
- Gradle (incluido wrapper en el proyecto)

### Clonar el Repositorio

```bash
git clone <URL_DEL_REPOSITORIO>
cd ExamenMercado
```

### Ejecutar la Aplicación

#### Windows:
```powershell
.\gradlew.bat bootRun
```

#### Linux/Mac:
```bash
./gradlew bootRun
```

La aplicación estará disponible en: `http://localhost:8080`

---

## 📡 Endpoints de la API

### 1. POST /mutant - Verificar Mutante

**Request:**
```bash
curl -X POST http://localhost:8080/mutant \
  -H "Content-Type: application/json" \
  -d '{
    "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
  }'
```

**Respuestas:**
- `200 OK` - Es mutante
- `403 Forbidden` - Es humano
- `400 Bad Request` - ADN inválido

**Ejemplo de ADN Mutante:**
```json
{
  "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}
```

**Ejemplo de ADN Humano:**
```json
{
  "dna": ["ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"]
}
```

### 2. GET /stats - Obtener Estadísticas

**Request:**
```bash
curl http://localhost:8080/stats
```

**Response:**
```json
{
  "count_mutant_dna": 40,
  "count_human_dna": 100,
  "ratio": 0.4
}
```

---

## 📚 Documentación Swagger

Accede a la documentación interactiva de la API:

```
http://localhost:8080/swagger-ui.html
```

Desde ahí puedes:
- Ver todos los endpoints disponibles
- Probar la API directamente desde el navegador
- Ver los modelos de datos y validaciones

---

## 🧪 Ejecutar Tests

### Todos los tests:
```bash
.\gradlew.bat test
```

### Ver reporte de cobertura:
```bash
.\gradlew.bat test jacocoTestReport
```

El reporte HTML estará en: `build/reports/jacoco/test/html/index.html`

### Tests incluidos:
- **17 tests** - MutantDetectorTest (algoritmo)
- **5 tests** - MutantServiceTest (lógica de negocio)
- **6 tests** - StatsServiceTest (estadísticas)
- **8 tests** - MutantControllerTest (endpoints REST)

**Total: 36 tests** | **Cobertura: >80%**

---

## 🏗️ Arquitectura del Proyecto

```
src/main/java/org/example/
├── MutantDetectorApplication.java  ← Clase principal
├── controller/
│   └── MutantController.java       ← REST endpoints
├── dto/
│   ├── DnaRequest.java             ← Request body
│   ├── StatsResponse.java          ← Response stats
│   └── ErrorResponse.java          ← Error handling
├── entity/
│   └── DnaRecord.java              ← JPA entity
├── repository/
│   └── DnaRecordRepository.java    ← Data access
├── service/
│   ├── MutantDetector.java         ← Algoritmo core
│   ├── MutantService.java          ← Lógica de negocio
│   └── StatsService.java           ← Estadísticas
├── validation/
│   ├── ValidDnaSequence.java       ← Anotación custom
│   └── ValidDnaSequenceValidator.java
├── exception/
│   ├── GlobalExceptionHandler.java ← Manejo global de errores
│   └── DnaHashCalculationException.java
└── config/
    └── SwaggerConfig.java          ← Configuración OpenAPI
```

---

## 🔧 Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Java | 17 | Lenguaje de programación |
| Spring Boot | 3.2.0 | Framework backend |
| Spring Data JPA | 3.2.0 | Persistencia de datos |
| H2 Database | Runtime | Base de datos en memoria |
| Lombok | Latest | Reducir boilerplate |
| SpringDoc OpenAPI | 2.3.0 | Documentación Swagger |
| JUnit 5 | Latest | Testing framework |
| Mockito | Latest | Mocking framework |
| Jacoco | 0.8.11 | Cobertura de código |

---

## 🐳 Despliegue con Docker

### Construir la imagen:
```bash
docker build -t mutant-detector .
```

### Ejecutar el contenedor:
```bash
docker run -p 8080:8080 mutant-detector
```

---

## 📊 Validaciones Implementadas

El endpoint `/mutant` valida automáticamente:

✅ ADN no puede ser null o vacío  
✅ Debe ser una matriz cuadrada NxN  
✅ Tamaño mínimo 4x4  
✅ Solo caracteres válidos: A, T, C, G  
✅ Todas las filas deben tener el mismo largo  

**Ejemplos de ADN inválido:**

```json
// ❌ No es cuadrada
{"dna": ["ATG", "CAGT", "TTA"]}

// ❌ Caracteres inválidos
{"dna": ["ATXC", "CAGT", "TTAT", "AGAC"]}

// ❌ Muy pequeña
{"dna": ["AT", "CG"]}
```

---

## 🎓 Algoritmo de Detección

### Optimizaciones Implementadas:

1. **Early Termination**: Detiene la búsqueda al encontrar >1 secuencia
2. **Conversión a char[][]**: Acceso O(1) en lugar de String.charAt()
3. **Boundary Checking**: Solo busca donde cabe la secuencia completa
4. **Comparaciones Directas**: Sin loops innecesarios

### Complejidad:
- **Tiempo**: O(N²) peor caso, ~O(N) promedio con early termination
- **Espacio**: O(N²) para la matriz de caracteres

---

## 📦 Construir JAR Ejecutable

```bash
.\gradlew.bat bootJar
```

El JAR estará en: `build/libs/Mutantes-1.0-SNAPSHOT.jar`

### Ejecutar el JAR:
```bash
java -jar build/libs/Mutantes-1.0-SNAPSHOT.jar
```

---

## 🗄️ Base de Datos H2

### Acceder a la consola H2:

```
http://localhost:8080/h2-console
```

**Credenciales:**
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: *(vacío)*

**Tabla creada automáticamente:**
```sql
CREATE TABLE dna_records (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    dna_hash VARCHAR(64) UNIQUE NOT NULL,
    is_mutant BOOLEAN NOT NULL
);
```

---

## 📝 Ejemplos de Uso

### PowerShell (Windows):

```powershell
# Verificar mutante
$body = @{
    dna = @("ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG")
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/mutant" `
    -Method Post `
    -ContentType "application/json" `
    -Body $body

# Obtener estadísticas
Invoke-RestMethod -Uri "http://localhost:8080/stats"
```

### Bash (Linux/Mac):

```bash
# Verificar mutante
curl -X POST http://localhost:8080/mutant \
  -H "Content-Type: application/json" \
  -d '{"dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]}'

# Obtener estadísticas
curl http://localhost:8080/stats
```

---

## 👨‍💻 Autor

Proyecto desarrollado como solución al examen técnico de MercadoLibre para la posición de Backend Developer.

---

## 📄 Licencia

Este proyecto es de uso educativo.

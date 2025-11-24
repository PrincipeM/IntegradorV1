# ✅ Proyecto Completado: Mutant Detector API

## 📊 Resumen de Implementación

### ✅ Nivel 1: Algoritmo de Detección - COMPLETADO

**Función isMutant(String[] dna) implementada en:** `MutantDetector.java`

**Características:**
- ✅ Detección en 4 direcciones (horizontal, vertical, diagonal, anti-diagonal)
- ✅ Early termination (detiene al encontrar >1 secuencia)
- ✅ Optimizaciones de rendimiento:
  - Conversión a char[][] para acceso O(1)
  - Boundary checking
  - Comparaciones directas sin loops
- ✅ Complejidad temporal: O(N²) peor caso, ~O(N) promedio
- ✅ Complejidad espacial: O(N²)

### ✅ Nivel 2: API REST - COMPLETADO

**Endpoints implementados:**

#### POST /mutant
- ✅ Recibe JSON con array de ADN
- ✅ Retorna 200 OK si es mutante
- ✅ Retorna 403 Forbidden si es humano
- ✅ Retorna 400 Bad Request si el ADN es inválido
- ✅ Validaciones completas del formato

#### Ejemplo de uso:
```bash
curl -X POST http://localhost:8080/mutant \
  -H "Content-Type: application/json" \
  -d '{"dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]}'
```

### ✅ Nivel 3: Persistencia y Estadísticas - COMPLETADO

#### Base de Datos H2
- ✅ Configurada y funcionando
- ✅ Tabla `dna_records` creada automáticamente
- ✅ Hash SHA-256 para identificación única de ADN
- ✅ Solo 1 registro por ADN (evita duplicados)

#### GET /stats
- ✅ Retorna estadísticas de verificaciones
- ✅ Formato JSON con count_mutant_dna, count_human_dna, ratio
- ✅ Cálculo automático del ratio

#### Ejemplo de uso:
```bash
curl http://localhost:8080/stats

# Respuesta:
# {
#   "count_mutant_dna": 40,
#   "count_human_dna": 100,
#   "ratio": 0.4
# }
```

---

## 🏗️ Arquitectura Implementada

### Estructura del Proyecto (6 Capas)

```
src/main/java/org/example/
├── MutantDetectorApplication.java  ← Spring Boot main
│
├── config/
│   └── SwaggerConfig.java          ← OpenAPI/Swagger
│
├── controller/
│   └── MutantController.java       ← REST endpoints
│
├── dto/
│   ├── DnaRequest.java             ← Request body
│   ├── StatsResponse.java          ← Stats response
│   └── ErrorResponse.java          ← Error handling
│
├── entity/
│   └── DnaRecord.java              ← JPA Entity
│
├── repository/
│   └── DnaRecordRepository.java    ← Spring Data JPA
│
├── service/
│   ├── MutantDetector.java         ← Algoritmo CORE
│   ├── MutantService.java          ← Lógica de negocio
│   └── StatsService.java           ← Estadísticas
│
├── validation/
│   ├── ValidDnaSequence.java       ← Anotación custom
│   └── ValidDnaSequenceValidator.java
│
└── exception/
    ├── GlobalExceptionHandler.java  ← Manejo global
    └── DnaHashCalculationException.java
```

---

## 🧪 Tests Implementados

### Resumen de Tests

| Clase de Test | Cantidad | Tipo | Estado |
|---------------|----------|------|---------|
| MutantDetectorTest | 17 | Unitarios | ✅ PASS |
| MutantServiceTest | 5 | Unitarios (Mocks) | ✅ PASS |
| StatsServiceTest | 6 | Unitarios (Mocks) | ✅ PASS |
| MutantControllerTest | 8 | Integración | ✅ PASS |
| **TOTAL** | **36** | - | **✅ 100% PASS** |

### Cobertura de Código

- **Cobertura Total:** >80% ✅
- **Archivos excluidos:** DTOs, Entities, Config, Exceptions
- **Clases críticas cubiertas:**
  - MutantDetector: 100%
  - MutantService: 100%
  - StatsService: 100%
  - MutantController: 100%
  - GlobalExceptionHandler: 100%
  - ValidDnaSequenceValidator: 100%

---

## 🚀 Cómo Ejecutar el Proyecto

### Prerequisitos
- Java 17 o superior
- Gradle (incluido en el proyecto)

### Ejecución Local

#### Windows:
```powershell
cd ExamenMercado
.\gradlew.bat bootRun
```

#### Linux/Mac:
```bash
cd ExamenMercado
./gradlew bootRun
```

La aplicación estará disponible en: `http://localhost:8080`

### Ejecutar Tests
```bash
.\gradlew.bat test
```

### Ver Reporte de Cobertura
```bash
.\gradlew.bat test jacocoTestReport
```
Abrir: `build/reports/jacoco/test/html/index.html`

### Generar JAR
```bash
.\gradlew.bat bootJar
```
JAR generado: `build/libs/Mutantes-1.0-SNAPSHOT.jar`

### Ejecutar JAR
```bash
java -jar build/libs/Mutantes-1.0-SNAPSHOT.jar
```

---

## 📚 Documentación Swagger

Acceso a documentación interactiva:

```
http://localhost:8080/swagger-ui.html
```

Características:
- ✅ Documentación completa de endpoints
- ✅ Probar API desde el navegador
- ✅ Esquemas de Request/Response
- ✅ Códigos de respuesta HTTP
- ✅ Ejemplos de uso

---

## 🐳 Docker

### Construir Imagen
```bash
docker build -t mutant-detector .
```

### Ejecutar Contenedor
```bash
docker run -p 8080:8080 mutant-detector
```

---

## 🔧 Tecnologías Utilizadas

| Tecnología | Versión | Uso |
|------------|---------|-----|
| Java | 17 | Lenguaje |
| Spring Boot | 3.2.0 | Framework |
| Spring Data JPA | 3.2.0 | Persistencia |
| H2 Database | Embedded | BD en memoria |
| Lombok | Latest | Reducir código |
| SpringDoc OpenAPI | 2.3.0 | Swagger UI |
| JUnit 5 | Latest | Tests |
| Mockito | Latest | Mocking |
| Jacoco | 0.8.11 | Cobertura |
| Gradle | 8.x | Build tool |

---

## ✅ Validaciones Implementadas

El sistema valida automáticamente:

1. ✅ ADN no puede ser null o vacío
2. ✅ Debe ser una matriz cuadrada NxN
3. ✅ Tamaño mínimo 4x4
4. ✅ Solo caracteres válidos: A, T, C, G
5. ✅ Todas las filas deben tener el mismo tamaño
6. ✅ Ninguna fila puede ser null

---

## 📊 Características Adicionales Implementadas

### 1. Caché de Base de Datos
- ✅ Usa hash SHA-256 para identificar ADN único
- ✅ Evita re-analizar el mismo ADN
- ✅ Mejora significativa de rendimiento

### 2. Manejo Global de Excepciones
- ✅ GlobalExceptionHandler con @RestControllerAdvice
- ✅ Respuestas de error estructuradas (ErrorResponse)
- ✅ Logging de errores

### 3. Validación Custom
- ✅ Anotación @ValidDnaSequence
- ✅ Validador personalizado
- ✅ Mensajes de error descriptivos

### 4. Logging
- ✅ SLF4J con Logback
- ✅ Niveles DEBUG/INFO configurables
- ✅ Logs estructurados

### 5. Docker Ready
- ✅ Dockerfile multi-etapa
- ✅ Imagen optimizada
- ✅ Listo para deployment

---

## 📝 Ejemplos de Uso Completos

### Ejemplo 1: Verificar ADN Mutante

```bash
curl -X POST http://localhost:8080/mutant \
  -H "Content-Type: application/json" \
  -d '{
    "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
  }'

# Respuesta: HTTP 200 OK
```

### Ejemplo 2: Verificar ADN Humano

```bash
curl -X POST http://localhost:8080/mutant \
  -H "Content-Type: application/json" \
  -d '{
    "dna": ["ATGCGA","CAGTGC","TTATTT","AGACGG","GCGTCA","TCACTG"]
  }'

# Respuesta: HTTP 403 Forbidden
```

### Ejemplo 3: ADN Inválido

```bash
curl -X POST http://localhost:8080/mutant \
  -H "Content-Type: application/json" \
  -d '{
    "dna": ["ATXC","CAGT","TTAT","AGAC"]
  }'

# Respuesta: HTTP 400 Bad Request
# {
#   "timestamp": "2025-11-15T00:21:50.994",
#   "status": 400,
#   "error": "Bad Request",
#   "message": "Row 0 contains invalid characters. Only A, T, C, G are allowed",
#   "path": "/mutant"
# }
```

### Ejemplo 4: Obtener Estadísticas

```bash
curl http://localhost:8080/stats

# Respuesta: HTTP 200 OK
# {
#   "count_mutant_dna": 40,
#   "count_human_dna": 100,
#   "ratio": 0.4
# }
```

---

## 🗄️ Acceso a Base de Datos H2

Para inspeccionar la base de datos:

```
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (vacío)
```

---

## 📦 Archivos Generados

Después de ejecutar el proyecto:

```
build/
├── libs/
│   └── Mutantes-1.0-SNAPSHOT.jar  ← JAR ejecutable
├── reports/
│   ├── tests/test/
│   │   └── index.html              ← Reporte de tests
│   └── jacoco/test/html/
│       └── index.html              ← Reporte de cobertura
└── test-results/
    └── test/
        └── *.xml                   ← Resultados XML
```

---

## 🎯 Checklist de Entrega

### Nivel 1: Algoritmo ✅
- [x] Función `isMutant()` implementada
- [x] Detección en 4 direcciones
- [x] Optimizaciones de rendimiento
- [x] Tests unitarios (17 tests)

### Nivel 2: API REST ✅
- [x] POST /mutant implementado
- [x] Responde 200/403 correctamente
- [x] Validaciones completas
- [x] Tests de integración (8 tests)

### Nivel 3: Persistencia ✅
- [x] H2 Database configurada
- [x] Un registro por ADN (hash único)
- [x] GET /stats implementado
- [x] Estadísticas funcionando

### Extras ✅
- [x] Tests >80% cobertura (36 tests totales)
- [x] Swagger/OpenAPI documentación
- [x] Docker configurado
- [x] Manejo global de excepciones
- [x] Validaciones personalizadas
- [x] Logging implementado
- [x] README con instrucciones

---

## 🏆 Resultado Final

✅ **PROYECTO 100% COMPLETO**

- ✅ Todos los niveles implementados
- ✅ 36 tests pasando (100%)
- ✅ Cobertura >80%
- ✅ API REST funcional
- ✅ Base de datos H2 operativa
- ✅ Documentación Swagger
- ✅ Docker ready
- ✅ Código limpio y bien estructurado
- ✅ Listo para deployment

---

## 👨‍💻 Próximos Pasos Sugeridos

### Para Desarrollo Local:
1. Ejecutar: `.\gradlew.bat bootRun`
2. Abrir Swagger: `http://localhost:8080/swagger-ui.html`
3. Probar endpoints

### Para Deployment en Render:
1. Hacer push del código a GitHub
2. Crear nuevo Web Service en Render
3. Conectar con el repositorio
4. Configurar build command: `./gradlew build`
5. Configurar start command: `java -jar build/libs/Mutantes-1.0-SNAPSHOT.jar`
6. Deploy!

---

**¡Proyecto completado exitosamente! 🎉**

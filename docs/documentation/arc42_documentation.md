# arc42 Architektur-Dokumentation: Thesis is coming!

## 1. Einführung und Ziele

### 1.1 Aufgabenstellung

**Thesis is coming!** ist eine Webanwendung zur Vermittlung von Studierenden und Betreuenden für Abschlussarbeiten (
Bachelor- und Masterarbeiten). Die Plattform ermöglicht es:

- **Studierenden**, ihr Profil mit abgeschlossenen Kursen und Interessensgebieten anzulegen und passende Betreuende
  sowie Themen zu finden
- **Betreuenden**, ihr Profil mit Fachgebieten und Themenvorschlägen zu pflegen und interessierte Studierende zu
  identifizieren
- **Administratoren**, Benutzerrollen zu verwalten und das System zu administrieren

### 1.2 Qualitätsziele

| Priorität | Qualitätsziel       | Beschreibung                                                                                                 |
|-----------|---------------------|--------------------------------------------------------------------------------------------------------------|
| 1         | **Sicherheit**      | Authentifizierung über GitHub OAuth2, rollenbasierte Zugriffskontrolle, Schutz personenbezogener Daten       |
| 2         | **Wartbarkeit**     | Klare Architekturstruktur (Onion Architecture), Trennung von Fachlichkeit und Infrastruktur, testbarer Code  |
| 3         | **Benutzbarkeit**   | Intuitive Benutzeroberfläche, einfache Profilverwaltung, übersichtliche Darstellung von Matching-Ergebnissen |
| 4         | **Zuverlässigkeit** | Datenkonsistenz durch transaktionale Datenbankoperationen, robuste Fehlerbehandlung                          |

### 1.3 Stakeholder

| Rolle                | Erwartungshaltung                                                                           |
|----------------------|---------------------------------------------------------------------------------------------|
| **Studierende**      | Einfache Registrierung, Profilverwaltung, Suche nach passenden Betreuenden und Themen       |
| **Betreuende**       | Verwaltung von Fachgebieten und Themenvorschlägen, Übersicht über interessierte Studierende |
| **Administratoren**  | Benutzerverwaltung, Rollenzuweisung, Systemüberwachung                                      |
| **Entwicklungsteam** | Klare Architektur, gute Testbarkeit, einfache Erweiterbarkeit                               |
| **Hochschule**       | Datenschutzkonformität, Integration in bestehende Infrastruktur (GitHub)                    |

---

## 2. Randbedingungen

### 2.1 Technische Randbedingungen

| Randbedingung            | Beschreibung                                                        |
|--------------------------|---------------------------------------------------------------------|
| **Programmiersprache**   | Java 21 (LTS)                                                       |
| **Framework**            | Spring Boot 4.0.1 mit Spring MVC, Spring Security, Spring Data JDBC |
| **Datenbank**            | PostgreSQL 15 (Produktion), H2 (Tests)                              |
| **Template Engine**      | Thymeleaf mit Thymeleaf Security Extras                             |
| **Build-Tool**           | Gradle                                                              |
| **Containerisierung**    | Docker mit Docker Compose                                           |
| **Datenbankmigrationen** | Flyway                                                              |

### 2.2 Organisatorische Randbedingungen

| Randbedingung           | Beschreibung                                               |
|-------------------------|------------------------------------------------------------|
| **Entwicklungskontext** | Hochschulprojekt (Programmierpraktikum)                    |
| **Authentifizierung**   | Nutzung der universitären GitHub-Infrastruktur über OAuth2 |
| **Datenschutz**         | DSGVO-konforme Verarbeitung personenbezogener Daten        |
| **Deployment**          | Containerbasiertes Deployment mit Docker Compose           |

### 2.3 Konventionen

| Konvention               | Beschreibung                                                     |
|--------------------------|------------------------------------------------------------------|
| **Architekturstil**      | Onion Architecture (Hexagonal Architecture Variante)             |
| **Dependency Injection** | Spring Framework Dependency Injection                            |
| **Persistenz**           | Spring Data JDBC (bewusst kein JPA/Hibernate für mehr Kontrolle) |
| **Code-Qualität**        | ArchUnit-Tests zur Sicherstellung der Architekturregeln          |

---

## 3. Kontextabgrenzung

### 3.1 Fachlicher Kontext

Das System **Thesis is coming!** ist eine eigenständige Webanwendung, die zwischen Benutzern und externen
Authentifizierungsdiensten vermittelt.

**Externe Schnittstellen:**

- **GitHub OAuth2**: Authentifizierung und Autorisierung der Benutzer über ihre GitHub-Accounts
- **Benutzer**: Interaktion über Webbrowser (HTTP/HTTPS)

**Systemgrenzen:**

Das System verwaltet ausschließlich:

- Benutzerprofile (Studierende, Betreuende)
- Fachgebiete und Interessensgebiete
- Themenvorschläge
- Matching-Logik (in Entwicklung)

Das System verwaltet **nicht**:

- GitHub-Benutzerdaten (werden nur zur Authentifizierung genutzt)
- Externe Kursdatenbanken
- E-Mail-Versand (aktuell nicht implementiert)

### 3.2 Kontextdiagramm

```
┌─────────────────────────────────────────────────────────────────────┐
│                        Externe Akteure                              │
├─────────────────────────────────────────────────────────────────────┤
│  👤 Studierende    👨‍🏫 Betreuende    👨‍💼 Administratoren           │
│       │                  │                    │                      │
│       │                  │                    │                      │
│       └──────────────────┴────────────────────┘                      │
│                          │                                           │
│                          ▼                                           │
│       ┌──────────────────────────────────────────────┐              │
│       │   Thesis is coming! System                   │              │
│       │   (Thesis Matching Platform)                 │              │
│       └──────────────────┬───────────────────────────┘              │
│                          │                                           │
│                          │ Authentifizierung                         │
│                          ▼                                           │
│       ┌──────────────────────────────────────────────┐              │
│       │   🔐 GitHub OAuth2                           │              │
│       │   (Externes System)                          │              │
│       └──────────────────────────────────────────────┘              │
└─────────────────────────────────────────────────────────────────────┘
```

**Interaktionen:**

- **Studierende** → System: Profil verwalten, Themen suchen
- **Betreuende** → System: Themen anbieten, Profile einsehen
- **Administratoren** → System: Rollen zuweisen, System verwalten
- **System** ↔ **GitHub OAuth2**: Authentifizierung, Benutzer-Token, Profildaten

### 3.3 Externe Schnittstellen

#### GitHub OAuth2

- **Zweck**: Authentifizierung und Autorisierung
- **Protokoll**: OAuth 2.0
- **Datenfluss**:
    - Benutzer wird zu GitHub weitergeleitet
    - Nach erfolgreicher Anmeldung erhält das System einen Access Token
    - Benutzerdaten (GitHub-ID, Name, E-Mail) werden abgerufen
    - Benutzer wird im System registriert oder aktualisiert

---

## 4. Lösungsstrategie

### 4.1 Architekturentscheidungen

| Entscheidung                          | Begründung                                                                                                                  |
|---------------------------------------|-----------------------------------------------------------------------------------------------------------------------------|
| **Onion Architecture**                | Klare Trennung von Fachlogik (Domain Core) und technischen Details (Infrastruktur). Abhängigkeiten zeigen immer nach innen. |
| **Spring Data JDBC statt JPA**        | Explizitere Kontrolle über SQL-Queries, weniger "Magic", bessere Performance, einfacheres Debugging                         |
| **Server-Side Rendering (Thymeleaf)** | Einfachere Implementierung, keine separate Frontend-Anwendung nötig, SEO-freundlich                                         |
| **GitHub OAuth2**                     | Nutzung bestehender Hochschul-Infrastruktur, keine eigene Benutzerverwaltung nötig                                          |
| **Flyway Migrationen**                | Versionskontrolle für Datenbankschema, reproduzierbare Deployments                                                          |
| **Docker Compose**                    | Einfaches lokales Setup, konsistente Entwicklungsumgebung                                                                   |

### 4.2 Technologie-Stack

**Schichtenmodell (von außen nach innen):**

```
┌─────────────────────────────────────────────────────────────┐
│  Presentation Layer                                         │
│  • Thymeleaf Templates                                      │
│  • CSS/JavaScript                                           │
└────────────────────────┬────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────┐
│  Application Layer                                          │
│  • Spring MVC                                               │
│  • Spring Security + OAuth2                                 │
└────────────────────────┬────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────┐
│  Business Layer                                             │
│  • Application Services                                     │
│  • Domain Models (KERN)                                     │
└────────────────────────┬────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────┐
│  Data Layer                                                 │
│  • Spring Data JDBC                                         │
│  • Flyway Migrations                                        │
└────────────────────────┬────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────┐
│  Infrastructure                                             │
│  • PostgreSQL 15 (Datenbank)                                │
│  • Docker Container                                         │
└─────────────────────────────────────────────────────────────┘
```

### 4.3 Sicherheitskonzept

- **Authentifizierung**: GitHub OAuth2 (kein lokales Passwort-Management)
- **Autorisierung**: Rollenbasierte Zugriffskontrolle (RBAC) mit Spring Security
- **Rollen**:
    - `ROLE_USER`: Neu registrierte Benutzer (können sich als Student oder Supervisor registrieren)
    - `ROLE_STUDENT`: Studierende mit Zugriff auf Student-Funktionen
    - `ROLE_SUPERVISOR`: Betreuende mit Zugriff auf Supervisor-Funktionen
    - `ROLE_ADMIN`: Administratoren mit vollständigem Systemzugriff

---

## 5. Bausteinsicht

### 5.1 Ebene 1: Gesamtsystem (Onion Architecture)

**Onion Architecture - Schichtenmodell:**

```
╔═══════════════════════════════════════════════════════════════════════════╗
║                    ADAPTER LAYER (Outside - Infrastruktur)                ║
╠═══════════════════════════════════════════════════════════════════════════╣
║                                                                           ║
║  ┌─────────────────────────────────┐  ┌──────────────────────────────┐   ║
║  │  Adapter In - Web/API           │  │  Adapter Out - Persistence   │   ║
║  ├─────────────────────────────────┤  ├──────────────────────────────┤   ║
║  │  • StudentController            │  │  • StudentRepository         │   ║
║  │  • SupervisorController         │  │  • SupervisorRepository      │   ║
║  │  • AdminController              │  │  • UserAccountRepository     │   ║
║  │  • MatchingController           │  │                              │   ║
║  │  • SecurityConfig               │  │  • StudentJDBCEntity         │   ║
║  │  • OAuthService                 │  │  • SupervisorJDBCEntity      │   ║
║  │  • MethodSecurityConfig         │  │  • UserAccountJDBCEntity     │   ║
║  │  • GlobalExceptionHandler       │  │                              │   ║
║  └─────────────┬───────────────────┘  │  • StudentMapper             │   ║
║                │                      │  • SupervisorMapper          │   ║
║                │                      │  • UserAccountMapper         │   ║
║                │                      └──────────┬───────────────────┘   ║
║                │                                 │                       ║
╠════════════════▼═════════════════════════════════▼═══════════════════════╣
║                        APPLICATION LAYER                                 ║
╠══════════════════════════════════════════════════════════════════════════╣
║                                                                          ║
║  • Application Services: UserAccountService, StudentService,            ║
║                          SupervisorService                               ║
║  • Use Case Interfaces: RegisterStudentUseCase, LoadStudentUseCase,     ║
║                         UpdateProfileUseCase                             ║
║  • Commands: RegisterStudentCommand, UpdateStudentProfileCommand        ║
║  • Ports: UserAccountRepositoryPort, StudentRepositoryPort              ║
║                                                                          ║
╠══════════════════════════════════════════════════════════════════════════╣
║                    DOMAIN CORE (Inside - Fachlogik)                      ║
╠══════════════════════════════════════════════════════════════════════════╣
║                                                                          ║
║  • Domain Models: Student, Supervisor, UserAccount, Topic,              ║
║                   Course, Interest                                       ║
║  • Value Objects: StudentId, Name, Contact, Email, PublicId             ║
║                                                                          ║
╚══════════════════════════════════════════════════════════════════════════╝
```

**Abhängigkeitsrichtung:** Außen → Innen (Adapter → Application → Domain)

**Wichtige Prinzipien:**

- Domain Core hat **keine** Abhängigkeiten zu äußeren Schichten
- Application Layer kennt nur Domain Core
- Adapter implementieren Ports (Dependency Inversion)

### 5.2 Baustein-Beschreibungen

#### 5.2.1 Domain Core (Kern)

**Verantwortlichkeit**: Enthält die gesamte Geschäftslogik und Fachlichkeit. Keine Abhängigkeiten zu äußeren Schichten.

**Wichtige Bausteine**:

- **`Student`**: Aggregat-Root für Studierende
    - Verwaltet Kurse (`Course`) und Interessen (`Interest`)
    - Methoden: `addCourse()`, `removeInterest()`, `updateProfile()`

- **`Supervisor`**: Aggregat-Root für Betreuende
    - Verwaltet Fachgebiete (`FieldTag`) und Themen (`Topic`)
    - Methoden: `addTopic()`, `updateTopic()`, `removeTopic()`

- **`UserAccount`**: Repräsentiert einen authentifizierten Benutzer
    - Verwaltet Rollen (`UserRole`)
    - Verbindung zwischen GitHub-Account und internem System

- **Value Objects**: `StudentId`, `SupervisorId`, `Name`, `Contact`, `Email`, `PublicId`
    - Unveränderliche Objekte zur Typsicherheit

#### 5.2.2 Application Layer (Anwendungsschicht)

**Verantwortlichkeit**: Orchestrierung von Use Cases, Koordination zwischen Domain und Infrastruktur.

**Wichtige Bausteine**:

- **Application Services**:
    - `UserAccountService`: Registrierung, Rollenverwaltung
    - `StudentService`: Student-Profilverwaltung
    - `SupervisorService`: Supervisor-Profilverwaltung

- **Use Case Interfaces** (Ports In):
    - Definieren die Schnittstellen für Anwendungsfälle
    - Beispiel: `RegisterStudentUseCase`, `AddCourseUseCase`

- **Commands**:
    - DTOs für eingehende Anfragen
    - Beispiel: `RegisterStudentCommand`, `UpdateStudentProfileCommand`

- **Repository Ports** (Ports Out):
    - Abstrakte Schnittstellen für Datenzugriff
    - Beispiel: `UserAccountRepositoryPort`, `StudentRepositoryPort`

#### 5.2.3 Adapter In - Web/API Layer

**Verantwortlichkeit**: Entgegennahme von HTTP-Requests, Validierung, Weiterleitung an Application Services.

**Wichtige Bausteine**:

- **Controllers**:
    - `StudentController`: Endpunkte für Student-Funktionen (`/student/**`)
    - `SupervisorController`: Endpunkte für Supervisor-Funktionen (`/supervisor/**`, `/topic/**`)
    - `AdminController`: Endpunkte für Admin-Funktionen (`/admin/**`)
    - `MatchingController`: Matching-Funktionalität (**noch in Entwicklung**)

- **Security Configuration**:
    - `SecurityConfig`: Spring Security Konfiguration, URL-basierte Autorisierung
    - `OAuthService`: GitHub OAuth2 Integration
    - `MethodSecurityConfig`: Method-Level Security

- **Exception Handling**:
    - `GlobalExceptionHandler`: Zentrale Fehlerbehandlung

#### 5.2.4 Adapter Out - Persistence Layer

**Verantwortlichkeit**: Implementierung der Repository Ports, Datenbankzugriff über Spring Data JDBC.

**Wichtige Bausteine**:

- **Persistence Adapters**:
    - `UserAccountPersistenceAdapter`: Implementiert `UserAccountRepositoryPort`
    - `StudentPersistenceAdapter`: Implementiert `StudentRepositoryPort`
    - `SupervisorPersistenceAdapter`: Implementiert `SupervisorRepositoryPort`

- **Spring Data JDBC Repositories**:
    - `UserAccountRepository`, `StudentRepository`, `SupervisorRepository`
    - Erben von `CrudRepository`

- **JDBC Entities**:
    - `UserAccountJDBCEntity`, `StudentJDBCEntity`, `SupervisorJDBCEntity`
    - Annotiert mit `@Table`, `@Id` für Spring Data JDBC Mapping

- **Mappers**:
    - Konvertierung zwischen Domain Models und JDBC Entities
    - Beispiel: `StudentMapper`, `SupervisorMapper`

### 5.3 Modul-Übersicht

| Modul             | Beschreibung                                               | Status                |
|-------------------|------------------------------------------------------------|-----------------------|
| **`accounts`**    | Benutzerverwaltung, Authentifizierung, Rollenzuweisung     | ✅ Implementiert       |
| **`students`**    | Student-Profilverwaltung, Kurse, Interessen                | ✅ Implementiert       |
| **`supervisors`** | Supervisor-Profilverwaltung, Fachgebiete, Themen           | ✅ Implementiert       |
| **`matching`**    | Matching-Algorithmus zwischen Studierenden und Betreuenden | 🚧 **In Entwicklung** |
| **`common`**      | Gemeinsame Utilities, Exception Handling                   | ✅ Implementiert       |

---

## 9. Architekturentscheidungen

### 9.1 Onion Architecture (Hexagonal Architecture)

**Entscheidung**: Das System folgt der Onion Architecture mit klarer Schichtentrennung.

**Begründung**:

- **Dependency Inversion**: Abhängigkeiten zeigen immer nach innen (zum Domain Core)
- **Testbarkeit**: Domain-Logik kann isoliert ohne Infrastruktur getestet werden
- **Austauschbarkeit**: Infrastruktur-Komponenten (z.B. Datenbank) können ausgetauscht werden
- **Wartbarkeit**: Klare Verantwortlichkeiten, einfache Navigation im Code

**Konsequenzen**:

- Mehr Boilerplate-Code (Mapper, Ports, Adapters)
- Höhere initiale Komplexität
- Langfristig bessere Wartbarkeit und Erweiterbarkeit

### 9.2 Spring Data JDBC statt JPA/Hibernate

**Entscheidung**: Verwendung von Spring Data JDBC anstelle von JPA/Hibernate.

**Begründung**:

- **Explizite Kontrolle**: Kein "Magic" durch Lazy Loading, Dirty Checking, etc.
- **Einfacheres Debugging**: SQL-Queries sind nachvollziehbar
- **Performance**: Keine unerwarteten N+1 Query-Probleme
- **Aggregate-Orientierung**: Passt besser zur Domain-Driven Design Philosophie

**Konsequenzen**:

- Mehr manuelle Arbeit bei komplexen Queries
- Kein automatisches Lazy Loading (bewusst gewählt)
- Explizites Mapping zwischen Entities und Domain Models nötig

### 9.3 GitHub OAuth2 für Authentifizierung

**Entscheidung**: Authentifizierung ausschließlich über GitHub OAuth2.

**Begründung**:

- **Keine Passwort-Verwaltung**: Sicherheitsrisiken werden minimiert
- **Hochschul-Integration**: Studierende und Mitarbeitende haben bereits GitHub-Accounts
- **Single Sign-On**: Benutzer müssen sich nicht extra registrieren
- **Wartungsaufwand**: Kein eigenes User-Management nötig

**Konsequenzen**:

- Abhängigkeit von GitHub-Verfügbarkeit
- Benutzer ohne GitHub-Account können das System nicht nutzen
- GitHub-Profildaten werden als Basis für interne Profile verwendet

### 9.4 Flyway für Datenbankmigrationen

**Entscheidung**: Verwendung von Flyway für Datenbank-Versionierung.

**Begründung**:

- **Versionskontrolle**: Datenbankschema ist Teil des Git-Repositories
- **Reproduzierbarkeit**: Gleicher Zustand in allen Umgebungen (Dev, Test, Prod)
- **Automatisierung**: Migrationen laufen automatisch beim Deployment
- **Rollback-Fähigkeit**: Änderungen können nachvollzogen werden

**Konsequenzen**:

- Migrationen müssen sorgfältig geplant werden
- Rückwärtskompatibilität muss beachtet werden
- Entwickler müssen SQL-Kenntnisse haben

### 9.5 Matching-Modul: Zukünftige Entwicklung

**Status**: Das Matching-Modul ist aktuell nur als Platzhalter vorhanden.

**Geplante Funktionalität**:

- Algorithmus zum Abgleich von Student-Interessen mit Supervisor-Fachgebieten
- Empfehlungssystem für passende Themen
- Bewertung von Matching-Qualität basierend auf abgeschlossenen Kursen

**Architektur-Vorbereitung**:

- Modul-Struktur ist bereits angelegt (`matching` Package)
- `MatchingController` existiert als Platzhalter
- Domain Models (`Student`, `Supervisor`) sind für Matching vorbereitet

---

## Anhang

### Glossar

| Begriff          | Beschreibung                                                                   |
|------------------|--------------------------------------------------------------------------------|
| **Aggregat**     | Cluster von Domain-Objekten mit einem Aggregat-Root (z.B. `Student`)           |
| **Port**         | Schnittstelle zwischen Application Layer und Adaptern (Hexagonal Architecture) |
| **Adapter**      | Implementierung eines Ports (z.B. `StudentPersistenceAdapter`)                 |
| **Value Object** | Unveränderliches Objekt ohne eigene Identität (z.B. `Email`, `Name`)           |
| **Use Case**     | Anwendungsfall aus Benutzersicht (z.B. "Student registrieren")                 |
| **Command**      | DTO für eingehende Anfragen an Use Cases                                       |

### Technologie-Versionen

- **Java**: 21 (LTS)
- **Spring Boot**: 4.0.1
- **PostgreSQL**: 15
- **Gradle**: (via Wrapper)
- **Flyway**: (via Spring Boot Dependency Management)
- **Thymeleaf**: (via Spring Boot Dependency Management)

### Weiterführende Dokumentation

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JDBC Reference](https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/)
- [arc42 Template](https://arc42.org/)
- [Onion Architecture](https://jeffreypalermo.com/2008/07/the-onion-architecture-part-1/)

### Hinweis

Wenn Sie bis hierhin gelesen haben, herzlichen Glückwunsch, wir kamen nicht so weit.

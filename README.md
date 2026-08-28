# 🗺️ German Accident Atlas Integration Platform

An end-to-end data integration and analytics platform that combines official German open datasets — **Unfallatlas and Regionalatlas** — into a unified, harmonized, and analytics-ready PostgreSQL database.

The platform provides a reproducible **ETL pipeline**, a **Spring Boot REST API**, and an interactive **React dashboard** for exploring accident data and cross-dataset analytics.

---

## 📌 Overview

The **German Accident Atlas Integration Platform** integrates heterogeneous German open-data sources into a common data model.

The system follows a reproducible data-processing workflow:

```text
Official Open Data Sources
        │
        ▼
┌───────────────────────┐
│      Extract          │
│ Download / Update     │
└───────────┬───────────┘
            │
            ▼
┌───────────────────────┐
│        Parse          │
│ CSV / GeoJSON         │
└───────────┬───────────┘
            │
            ▼
┌───────────────────────┐
│       Transform       │
│ Harmonize / Map       │
│ Regional Identifiers  │
└───────────┬───────────┘
            │
            ▼
┌───────────────────────┐
│         Load          │
│      PostgreSQL       │
└───────────┬───────────┘
            │
            ▼
┌───────────────────────┐
│      Aggregate        │
│ Cross-Dataset         │
│ Analytics             │
└───────────┬───────────┘
            │
       ┌────┴─────┐
       ▼          ▼
 Spring Boot    React
 REST API       Dashboard
```

All processed data is stored locally in PostgreSQL. **API requests do not query the external data sources live.**

---

## ✨ Key Features

### 🔄 Reproducible ETL Pipeline

* Automated Unfallatlas data download
* Detection of newly available Unfallatlas releases
* CSV and GeoJSON parsing
* Data cleaning and transformation
* Harmonization of regional identifiers
* Database schema creation
* Automated loading into PostgreSQL
* Aggregation for analytics
* Dataset provenance tracking

### 🗄️ Unified PostgreSQL Database

The platform provides a canonical database structure for combining information from multiple German open-data sources.

The database stores:

* Accident records
* Population indicators
* Car-density indicators
* Regional information
* Dataset metadata
* Data provenance

### 🚀 Spring Boot REST API

The backend exposes the integrated data through a RESTful API.

It provides endpoints for:

* Accident data
* Population density
* Car density
* Metadata
* Cross-dataset analytics
* Provenance information

### 📊 Interactive React Dashboard

The frontend provides an interactive interface for exploring the integrated datasets.

Available functionality includes:

* Accident exploration
* State/year filtering
* Accident category filtering
* Cross-dataset comparison
* Population-density analysis
* Car-density analysis
* Dataset metadata inspection

### 🔐 Data Provenance & Integrity

The platform maintains provenance information for source datasets, including:

* Dataset name
* Source URL
* Download date
* License
* SHA-256 checksum

This makes it possible to track **where data came from, when it was downloaded, and whether the source file has changed**.

---

## 📈 Supported Analytics

The platform supports analytical queries such as:

### Accidents by State and Year

Determine the number of accidents recorded for a specific German state and year.

```http
GET /api/v1/accidents?state=14&year=2023
```

### Earliest Accident Year

Find the earliest accident year available in the integrated dataset.

```http
GET /api/v1/accidents/earliest-year
```

### Accident-to-Population-Density Ratio

Compare accident counts with population-density indicators across states.

```http
GET /api/v1/analytics/accident-population-ratio/2024
```

### Accident-to-Car-Density Ratio

Compare accident data with car-density indicators.

```http
GET /api/v1/analytics/accident-car-density-ratio/2025
```

### Dataset Metadata

Retrieve information about the integrated data sources.

```http
GET /api/v1/metadata/sources
```

---

## 🏗️ Architecture

The project consists of three main application layers:

```text
┌──────────────────────────────────────────────────────┐
│                   React Frontend                     │
│                                                      │
│  Accident Explorer │ Analytics │ Metadata Viewer     │
└───────────────────────┬──────────────────────────────┘
                        │
                        │ REST API
                        ▼
┌──────────────────────────────────────────────────────┐
│                 Spring Boot Backend                   │
│                                                      │
│  REST Controllers │ Services │ Repositories           │
│  Analytics API    │ Metadata │ Accident API           │
└───────────────────────┬──────────────────────────────┘
                        │
                        │ SQL / JDBC
                        ▼
┌──────────────────────────────────────────────────────┐
│                    PostgreSQL                         │
│                                                      │
│  Accidents │ Regions │ Indicators │ Metadata          │
│  Provenance │ Analytics Data                          │
└───────────────────────▲──────────────────────────────┘
                        │
                        │ ETL
                        │
┌───────────────────────┴──────────────────────────────┐
│                  Python ETL Pipeline                  │
│                                                      │
│ Extract → Parse → Transform → Load → Aggregate       │
└───────────────────────▲──────────────────────────────┘
                        │
                        │
        ┌───────────────┼────────────────┐
        │               │                │
        ▼               ▼                ▼
   Unfallatlas     Regionalatlas     GENESIS
```

---

## 📂 Project Structure

```text
german-accident-atlas-integration/
│
├── backend/
│   └── # Spring Boot REST API
│
├── frontend/
│   └── # React analytics dashboard
│
├── etl/
│   ├── extract/
│   │   └── # Download and update source datasets
│   │
│   ├── parse/
│   │   └── # Parse CSV / GeoJSON files
│   │
│   ├── transform/
│   │   └── # Clean, harmonize and map datasets
│   │
│   ├── load/
│   │   └── # Load transformed data into PostgreSQL
│   │
│   ├── db/
│   │   └── # Database schema and SQL scripts
│   │
│   └── run_etl.py
│       └── # ETL pipeline orchestrator
│
├── data/
│   └── # Local source datasets
│
├── USER_MANUAL.md
│   └── # Detailed installation and usage instructions
│
├── requirements.txt
│   └── # Python dependencies
│
└── README.md
```

---

## 🛠️ Technology Stack

| Component         | Technology              |
| ----------------- | ----------------------- |
| ETL               | Python 3.10+            |
| Database          | PostgreSQL 14+          |
| Backend           | Java 21 / Spring Boot 3 |
| API               | REST                    |
| API Documentation | Swagger / OpenAPI       |
| Frontend          | React                   |
| Frontend Runtime  | Node.js 18+             |
| Data Formats      | CSV, GeoJSON            |
| Data Integrity    | SHA-256                 |
| Architecture      | ETL + REST + SPA        |

---

## 📋 Requirements

Before running the project, install:

* **Python 3.10+**
* **PostgreSQL 14+**
* **Java 21+**
* **Node.js 18+**
* **npm**

### Python Dependencies

Install the Python dependencies with:

```bash
pip install -r requirements.txt
```

---

# 🚀 Getting Started

## 1. Clone the Repository

```bash
git clone https://github.com/SujanKarna/german-accident-atlas-integration.git
cd german-accident-atlas-integration
```

---

## 2. Configure PostgreSQL

Create the database:

```sql
CREATE DATABASE unfallatlas;
```

Configure the database connection according to the project's ETL configuration.

Example:

```text
Host: localhost
Port: 5433
Database: unfallatlas
Username: <your-username>
Password: <your-password>
```

> Make sure the PostgreSQL port matches the configuration used by the project.

---

## 3. Install Python Dependencies

```bash
pip install -r requirements.txt
```

---

## 4. Download Unfallatlas Data

Run:

```bash
python -m etl.extract.download_unfallatlas
```

This downloads the official Unfallatlas dataset into:

```text
data/unfallatlas/
```

---

## 5. Check for New Data Releases

To check whether new Unfallatlas datasets are available:

```bash
python -m etl.extract.update_unfallatlas
```

The update process checks the official source and downloads newly available releases.

---

## 6. Run the Complete ETL Pipeline

Run:

```bash
python etl/run_etl.py
```

The pipeline performs:

1. Database schema creation
2. Source dataset parsing
3. Data transformation
4. Regional identifier harmonization
5. Data loading
6. Aggregation
7. Provenance metadata storage

After successful execution, PostgreSQL contains the integrated data required by the backend.

---

# 🔌 Backend

Navigate to the backend:

```bash
cd backend
```

Start the Spring Boot application:

```bash
./mvnw spring-boot:run
```

On Windows, you can use:

```bash
mvnw.cmd spring-boot:run
```

The API is available at:

```text
http://localhost:8080
```

### Swagger / OpenAPI

Interactive API documentation is available through Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

> If your deployment uses a reverse proxy or different port configuration, adjust the URL accordingly.

---

# 🎨 Frontend

Open a new terminal and navigate to the frontend:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Start the development server:

```bash
npm run dev
```

The frontend will be available at:

```text
http://localhost
```

---

# 📊 Frontend Features

## Accident Explorer

Explore accident records using filters such as:

* German state
* Year
* Accident category

This provides a convenient way to inspect the underlying accident dataset.

---

## Cross-Dataset Analytics Explorer

The analytics dashboard combines accident information with regional indicators.

It provides comparisons including:

* Accident-to-population-density ratio
* Accident-to-car-density ratio
* State-level comparisons
* Year-specific analysis
* Dynamic explanations of selected metrics

---

## Metadata Viewer

The metadata interface provides transparency into the datasets used by the platform.

For each source, users can inspect:

| Metadata      | Description                    |
| ------------- | ------------------------------ |
| Dataset       | Name of the source dataset     |
| Download Date | Date the dataset was retrieved |
| License       | Applicable dataset license     |
| SHA-256       | File integrity checksum        |
| Source URL    | Original data source           |

---

# 🧪 Example API Requests

### Get accidents for a state and year

```http
GET http://localhost:8080/api/v1/accidents?state=14&year=2023
```

### Get earliest accident year

```http
GET http://localhost:8080/api/v1/accidents/earliest-year
```

### Accident / population-density ratio

```http
GET http://localhost:8080/api/v1/analytics/accident-population-ratio/2024
```

### Accident / car-density ratio

```http
GET http://localhost:8080/api/v1/analytics/accident-car-density-ratio/2025
```

### Get dataset sources

```http
GET http://localhost:8080/api/v1/metadata/sources
```

---

# 🔄 Data Pipeline

The ETL pipeline is designed to be reproducible and updateable.

```text
             Official Data Sources
                       │
                       ▼
                  ┌─────────┐
                  │ Extract │
                  └────┬────┘
                       │
                       ▼
                  ┌─────────┐
                  │  Parse  │
                  └────┬────┘
                       │
                       ▼
                  ┌─────────────┐
                  │  Transform  │
                  │             │
                  │ • Cleaning  │
                  │ • Mapping   │
                  │ • Harmonize │
                  └──────┬──────┘
                         │
                         ▼
                    ┌────────┐
                    │  Load  │
                    └────┬───┘
                         │
                         ▼
                  ┌────────────┐
                  │ PostgreSQL │
                  └──────┬─────┘
                         │
                         ▼
                   ┌───────────┐
                   │ Aggregate │
                   └─────┬─────┘
                         │
              ┌──────────┴──────────┐
              ▼                     ▼
        Spring Boot API       React Dashboard
```

---

# 🔐 Data Provenance

A key aspect of the platform is maintaining traceability for external datasets.

For each downloaded source, provenance information can include:

```text
Dataset
   │
   ├── Source URL
   ├── Download Date
   ├── License
   └── SHA-256 Checksum
```

The SHA-256 checksum provides an integrity mechanism that can be used to identify changes to downloaded source files.

---

# 🐛 Troubleshooting

## Backend CORS Error

If the frontend reports a CORS or API error, verify that frontend requests use the expected API path:

```text
/api/v1/...
```

Also verify that the Spring Boot backend is running.

---

## ETL Pipeline Fails

Check:

### Database configuration

Verify that the PostgreSQL credentials and connection settings are correct.

### Source files

Ensure required CSV datasets exist under:

```text
data/
```

### Python dependencies

Reinstall the dependencies:

```bash
pip install -r requirements.txt
```

---

## Frontend Shows a Blank Page

Restart the development server:

```bash
npm run dev
```

Then verify that:

* The frontend dependencies are installed.
* The backend API is running.
* The configured API endpoint is correct.

---

# 📖 Documentation

For detailed installation, configuration, pipeline execution, API usage, frontend functionality, and troubleshooting instructions, see:

**[USER_MANUAL.md](USER_MANUAL.md)**

---

# 🎯 Project Goals

The project demonstrates how heterogeneous public datasets can be transformed into a unified analytics platform through:

* Reproducible ETL workflows
* Data integration and schema harmonization
* Relational database design
* REST API development
* Cross-dataset analytics
* Interactive data visualization
* Data provenance and integrity tracking

The architecture separates **data ingestion**, **data storage**, **business logic**, and **data presentation**, making the system easier to maintain and extend.

---

# 🌍 Data Sources

The platform integrates official German open-data sources including:

* **Unfallatlas** — German accident data
* **Regionalatlas** — Regional statistical data

The project stores processed datasets locally and uses the PostgreSQL database as the primary source for application queries.

---

# 👨‍💻 Author

**Sujan Karna**

M.Sc. Web Engineering
Technical University of Chemnitz

GitHub: [@SujanKarna](https://github.com/SujanKarna)

---

# 📄 License

Please refer to the individual licenses of the integrated datasets and the license specified by this repository.

---

⭐ If you find this project useful, consider giving the repository a star.

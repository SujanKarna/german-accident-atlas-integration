# 🗺️ German Accident Atlas Integration Platform
### User Manual

---

## Table of Contents

1. [Overview](#1-overview)
2. [Project Structure](#2-project-structure)
3. [Requirements](#3-requirements)
4. [Database Setup](#4-database-setup)
5. [Running the ETL Pipeline](#5-running-the-etl-pipeline)
6. [Starting the Backend API](#6-starting-the-backend-api)
7. [Starting the Frontend](#7-starting-the-frontend)
8. [Example API Queries](#8-example-api-queries)
9. [Frontend Features](#9-frontend-features)
10. [Troubleshooting](#10-troubleshooting)

---

## 1. Overview

This platform integrates official German open datasets — **Unfallatlas**, **Regionalatlas**, and **GENESIS/Regionalstatistik** — into a unified, analytics-ready data system.

### What It Provides

| Component | Description |
|-----------|-------------|
| **ETL Pipeline** | Reproducible Python pipeline: Extract → Parse → Transform → Load |
| **PostgreSQL Database** | Canonical schema with harmonized regional data |
| **Spring Boot API** | RESTful backend with Swagger documentation |
| **React Frontend** | Interactive dashboard for analytics and visualization |

### Supported Analytics Queries

- How many accidents occurred in a given state and year
- Earliest accident year in the dataset
- Accident-to-population-density ratio per state
- Accident-to-car-density ratio per state
- Multi-dataset integrated analytics, and more.

> **Note:** All data is stored locally in the database. No live external queries are made during API requests.

---

## 2. Project Structure

### Top-Level Layout

```
GERMAN-ACCIDENT-ATLAS/
│
├── backend/          # Spring Boot REST API
├── frontend/         # React analytics dashboard
├── etl/              # Full ETL pipeline
├── data/             # Raw datasets (Regionalatlas, GENESIS, etc.)
└── USER_MANUAL.md    # This document
```

### ETL Pipeline Layout

```
etl/
│
├── extract/          # Download & update Unfallatlas data
├── parse/            # Parse CSV/GeoJSON source files
├── transform/        # Harmonize schemas and map region keys
├── load/             # Insert transformed data into PostgreSQL
├── db/               # Schema creation SQL scripts
└── run_etl.py        # Orchestrator — runs the full pipeline
```

The ETL pipeline follows the required DBW pattern:

> **Extract → Parse → Map → Load → Aggregate**

---

## 3. Requirements

### Software Dependencies

| Tool | Minimum Version |
|------|----------------|
| Python | 3.10+ |
| PostgreSQL | 14+ |
| Java (Spring Boot 3) | 21+ |
| Node.js | 18+ |

### Python Package Installation

```bash
pip install -r requirements.txt
```

---

## 4. Database Setup

**Step 1 — Create an empty PostgreSQL database:**

```sql
CREATE DATABASE unfallatlas;
```


**Step 2 — Configure database credentials:**

Edit `etl/db/connection.py`:

```
database:
  host: localhost
  port: 5433
  name: unfallatlas
  user: 
  password:
```

---

## 5. Running the ETL Pipeline

The ETL pipeline is fully reproducible and handles the following automatically:

- Automated Unfallatlas download and update detection
- Parsing and harmonizing
- Canonical schema mapping
- Database loading, aggregation, and cross-dataset analytics
- Provenance metadata storage (license, source URL, SHA256, download date)

---

### Step 1 — Download Unfallatlas Data

```bash
python -m etl.extract.download_unfallatlas
```

Downloads the official Unfallatlas dataset for the current year into `data/unfallatlas/`.

---

### Step 2 — Check for New Unfallatlas Releases

```bash
python -m etl.extract.update_unfallatlas
```

This script:
- Checks the official Unfallatlas source
- Compares available years against local data
- Automatically downloads any new releases

---

### Step 3 — Run the Full Pipeline

```bash
python etl/run_etl.py
```

This single command will:

1. Create the database schema
2. Parse all datasets
3. Harmonize region keys (AGS → state/district)
4. Load accidents, indicators, and metadata
5. Store provenance records

Once complete, the database is fully populated and ready for API queries.

---

## 6. Starting the Backend API

```bash
cd backend
./mvnw spring-boot:run
```

| Endpoint | URL |
|----------|-----|
| **API Base URL** | `http://localhost:8080` |
| **Swagger UI** | `http://localhost/swagger-ui/index.html` |

### Exposed API Resources

- Accidents
- Population Density
- Car Density
- Metadata
- Cross-dataset analytics
- Provenance information

---

## 7. Starting the Frontend

```bash
cd frontend
npm install
npm run dev
```

Frontend available at: **`http://localhost`**

---

## 8. Example API Queries

### Accident-to-Population-Density Ratio

```http
GET /api/v1/analytics/accident-population-ratio/2024
```

### Accident-to-Car-Density Ratio

```http
GET /api/v1/analytics/accident-car-density-ratio/2025
```

### Accidents in a Specific State and Year

```http
GET /api/v1/accidents?state=14&year=2023
```

### Earliest Accident Year in the Dataset

```http
GET /api/v1/accidents/earliest-year
```

### Source Metadata

```http
GET /api/v1/metadata/sources
```

---

## 9. Frontend Features

### Cross-Dataset Analytics Explorer

Displays side-by-side comparative analytics including:
- Accident-to-population-density ratio
- Accident-to-car-density ratio
- Dynamic explanations based on selected metric
- Year-aware descriptions
- State-level comparison table

### Accident Explorer

Filter and explore accident records by:
- State
- Year
- Accident category

### Metadata Viewer

Inspect data provenance for each source dataset:

| Field | Description |
|-------|-------------|
| Dataset name | Human-readable source name |
| Download date | When the data was last fetched |
| License | Usage license of the source |
| SHA256 hash | Integrity checksum |
| Source URL | Original data location |

---

## 10. Troubleshooting

### Backend Returns a CORS Error

**Cause:** Incorrect API endpoint path in the frontend configuration.  
**Fix:** Ensure all frontend requests target `/api/v1/...`.

---

### ETL Pipeline Fails

Check the following:

1. **Database credentials** — verify `etl/config.yaml` matches your PostgreSQL setup
2. **Missing source files** — confirm CSV/GeoJSON files are present in `data/`
3. **Python dependencies** — re-run `pip install -r requirements.txt`

---

### Frontend Shows a Blank Page

Restart the Vite development server:

```bash
npm run dev
```

---

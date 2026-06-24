# 🗺️ German Accident Atlas Integration Platform

> A unified data platform integrating **Unfallatlas**, **Regionalatlas**, and **GENESIS/Regionalstatistik** for reproducible accident analytics across German regions.

---

## What Is This?

This project fuses three official German open datasets into a single, queryable system. Data is ingested once via ETL and served locally; no live external calls happen at query time.

**Stack:** Python ETL · PostgreSQL · Spring Boot · React

---

## Quick Start

```bash
# 1. Install Python dependencies
pip install -r requirements.txt

# 2. Create the database (tables are auto-created by the ETL)
psql -c "CREATE DATABASE accident_db;"

# 3. Configure DB credentials
nano etl/config.yaml

# 4. Run the full ETL pipeline
python etl/run_etl.py

# 5. Start the backend
cd backend && ./mvnw spring-boot:run

# 6. Start the frontend
cd frontend && npm install && npm run dev
```

| Service | URL |
|---------|-----|
| Frontend | `http://localhost` |
| API | `http://localhost:8080` |
| Swagger UI | `http://localhost/swagger-ui/index.html` |

---

## Data Sources

| Dataset | Purpose |
|---------|---------|
| **Unfallatlas** | Accident records by location, year, category, and participant type |
| **Regionalatlas** | Regional indicators (population density, car ownership, etc.) |
| **GENESIS / Regionalstatistik** | Official statistical aggregates by state and district |

All sources are mapped to a canonical schema using AGS (Amtlicher Gemeindeschlüssel) region codes.

---

## ETL Pipeline

```
Extract → Parse → Map (AGS) → Load → Aggregate
```

The pipeline is fully reproducible:

```bash
# Download latest Unfallatlas
python -m etl.extract.download_unfallatlas

# Check for and pull new releases
python -m etl.extract.update_unfallatlas

# Run everything end-to-end
python etl/run_etl.py
```

Provenance (SHA256, license, source URL, download date) is stored in the database alongside the data.

---

## API Highlights

```http
# Accidents by state and year
GET /api/v1/accidents?state=SN&year=2023

# Cross-dataset ratios
GET /api/v1/analytics/accident-population-ratio/2024
GET /api/v1/analytics/accident-car-density-ratio/2024

# Dataset provenance
GET /api/v1/metadata/sources
```

Full endpoint reference: **Swagger UI** at `http://localhost/swagger-ui/index.html`

---

## Frontend Modules

| Module | What it does |
|--------|-------------|
| **Accident Explorer** | Filter accidents by state, year, category, and participant flags |
| **Cross-Dataset Analytics** | Compare accident rates against population and car density |
| **Metadata Viewer** | Inspect source provenance — license, hash, download date |

---

## Requirements

| Tool | Version |
|------|---------|
| Python | 3.10+ |
| PostgreSQL | 14+ |
| Java | 21+ |
| Node.js | 18+ |

---

## Documentation

For step-by-step setup, detailed ETL configuration, and troubleshooting, see [`USER_MANUAL.md`](./USER_MANUAL.md).
import axios from "axios";

const API_BASE = "http://localhost:8080/api/accidents";

export const getAccidentsByYear = (year) =>
  axios.get(`${API_BASE}/year/${year}`);

export const getAccidentsByState = (stateCode) =>
  axios.get(`${API_BASE}/state/${stateCode}`);

export const getAccidentsByStateYearAndType = (stateCode, year, type) =>
  axios.get(`${API_BASE}/filter`, {
    params: { stateCode, year, type },
  });

// -----------------------------
// SUMMARY
// -----------------------------
export const getAccidentSummary = (year) =>
  axios.get(`${API_BASE}/summary`, {
    params: { year },
  });

// -----------------------------
// MUNICIPALITY LEVEL
// -----------------------------
export const getMunicipalities = (stateCode) =>
  axios.get(`${API_BASE}/municipalities`, {
    params: { stateCode },
  });

export const getMunicipalitiesByYear = (stateCode, year) =>
  axios.get(`${API_BASE}/municipalities/year`, {
    params: { stateCode, year },
  });

  // -----------------------------
// CROSS-DATASET ANALYTICS
// -----------------------------
export const getAccidentsPer100kCars = (stateCode, year) =>
  axios.get(`${API_BASE}/rate/car-density`, {
    params: { stateCode, year },
  });

export const getAccidentsPerKm2 = (stateCode, year) =>
  axios.get(`${API_BASE}/rate/pop-density`, {
    params: { stateCode, year },
  });

export const getAccidentsPerCapita = (stateCode, year) =>
  axios.get(`${API_BASE}/rate/capita`, {
    params: { stateCode, year },
  });

// -----------------------------
// ADVANCED ANALYTICS
// -----------------------------
export const getTopFatalAccidents = (year, limit = 5) =>
  axios.get(`${API_BASE}/top-fatal`, {
    params: { year, limit },
  });
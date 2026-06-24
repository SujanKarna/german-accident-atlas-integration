import axios from "axios";

const API_BASE = "http://localhost:8080/api/v1/accidents";

// ------------------------------------------------------------
// METADATA
// ------------------------------------------------------------
export const getMetadata = (year) =>
  axios.get(`${API_BASE}/metadata/${year}`);

// ------------------------------------------------------------
// MANDATORY QUESTIONS
// ------------------------------------------------------------
export const getEarliestYear = () =>
  axios.get(`${API_BASE}/earliest-year`);

export const getEarliestYearForState = (stateCode) =>
  axios.get(`${API_BASE}/earliest-year/state`, {
    params: { stateCode },
  });

export const countByStateAndYear = (stateCode, year) =>
  axios.get(`${API_BASE}/count/state`, {
    params: { stateCode, year },
  });

export const countPedestrianAccidents = (stateCode, year) =>
  axios.get(`${API_BASE}/count/pedestrian`, {
    params: { stateCode, year },
  });

export const countPersonalInjury = (stateCode, year) =>
  axios.get(`${API_BASE}/count/personal-injury`, {
    params: { stateCode, year },
  });

// ------------------------------------------------------------
// TRENDS
// ------------------------------------------------------------
export const getTrendsForState = (stateCode) =>
  axios.get(`${API_BASE}/analytics/trends/state/${stateCode}`);

// ------------------------------------------------------------
// FILTER
// ------------------------------------------------------------
export const filterAccidents = (stateCode, year, type, page = 0, size = 500) =>
  axios.get(`${API_BASE}/filter`, {
    params: { stateCode, year, type, page, size },
  });

// ------------------------------------------------------------
// MUNICIPALITIES
// ------------------------------------------------------------
export const getMunicipalitiesByYear = (stateCode, year) =>
  axios.get(`${API_BASE}/municipalities/year`, {
    params: { stateCode, year },
  });

// ------------------------------------------------------------
// SUMMARY (Dashboard)
// ------------------------------------------------------------
export const getAccidentSummary = (year) =>
  axios.get(`${API_BASE}/summary`, {
    params: { year },
  });

// ------------------------------------------------------------
// ADVANCED ANALYTICS
// ------------------------------------------------------------
export const getTopFatalAccidents = (year, limit = 5) =>
  axios.get(`${API_BASE}/top-fatal`, {
    params: { year, limit },
  });


  // ------------------------------------------------------------
// Filter Page
// ------------------------------------------------------------
export const getFilteredAccidents = (stateCode, year, type, page = 0) =>
  axios.get(`${API_BASE}/filter`, {
    params: {
      state: stateCode,
      year: Number(year),
      type: Number(type),   
      page: Number(page),
      size: 10,
    }
  });


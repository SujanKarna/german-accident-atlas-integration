import axios from "axios";

const BASE = "http://localhost:8080/api/v1/analytics";

export const getAccidentPopulationRatio = (year) =>
  axios.get(`${BASE}/accident-population-ratio/${year}`);

export const getAccidentCarDensityRatio = (year) =>
  axios.get(`${BASE}/accident-car-density-ratio/${year}`);

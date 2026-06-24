import axios from "axios";
import { API_BASE_URL } from "../config/api";

export const getMunicipalitiesByYear = (stateCode, year) =>
  axios.get(`${API_BASE_URL}/municipalities/year`, {
    params: {
      stateCode,
      year
    }
  });

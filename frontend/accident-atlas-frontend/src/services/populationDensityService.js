import axios from "axios";
import { API_BASE_URL } from "../config/api";

export const getPopulationDensityByYear = (year) =>
  axios.get(`${API_BASE_URL.replace("/accidents", "")}/population-density/year/${year}`);

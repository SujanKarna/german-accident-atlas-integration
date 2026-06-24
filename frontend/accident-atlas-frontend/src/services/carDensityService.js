import axios from "axios";
import { API_BASE_URL } from "../config/api";



export const getCarDensityByYear = (year) =>
  axios.get(`${API_BASE_URL.replace("/accidents", "")}/car-density/year/${year}`);

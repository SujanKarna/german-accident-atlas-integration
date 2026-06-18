import axiosClient from "../api/axiosClient";

export const getPopulationByYear = (year) =>
  axiosClient.get(`/population-density/year/${year}`);

export const getPopulationByState = (stateCode) =>
  axiosClient.get(`/population-density/state/${stateCode}`);

export const getCarDensityByYear = (year) =>
  axiosClient.get(`/car-density/year/${year}`);

export const getCarDensityByState = (stateCode) =>
  axiosClient.get(`/car-density/state/${stateCode}`);

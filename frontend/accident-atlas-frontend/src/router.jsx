import { BrowserRouter, Routes, Route } from "react-router-dom";

import Summary from "./pages/Accidents/Summary";
import Navbar from "./components/layout/Navbar";
import Trends from "./pages/Accidents/Trends";
import Filter from "./pages/Accidents/Filter";
import MunicipalityExplorer from "./pages/Accidents/MunicipalityExplorer";
import PopulationDensityExplorer from "./pages/Population/PopulationDensityExplorer";
import CarDensityExplorer from "./pages/CarDensity/CarDensityExplorer";
import CrossAnalyticsExplorer from "./pages/CrossAnalytics/CrossAnalyticsExplorer";


export default function AppRouter() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        <Route path="/" element={<Summary />} />
        <Route path="/summary" element={<Summary />} />
        <Route path="/trends" element={<Trends />} />
        <Route path="/filter" element={<Filter />} />
        <Route path="/municipalities" element={<MunicipalityExplorer />} />
        <Route path="/population-density" element={<PopulationDensityExplorer />} />
        <Route path="/car-density" element={<CarDensityExplorer />} />
        <Route path="/cross-analytics" element={<CrossAnalyticsExplorer />} />

      </Routes>
    </BrowserRouter>
  );
}

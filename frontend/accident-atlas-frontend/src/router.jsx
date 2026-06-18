import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/layout/Navbar";
import Home from "./pages/Home";
import PopulationPage from "./pages/PopulationPage";
import CarDensityPage from "./pages/CarDensityPage";

export default function AppRouter() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/population" element={<PopulationPage />} />
        <Route path="/car-density" element={<CarDensityPage />} />
      </Routes>
    </BrowserRouter>
  );
}

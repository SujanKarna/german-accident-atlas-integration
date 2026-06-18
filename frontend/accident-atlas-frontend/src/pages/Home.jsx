// src/pages/Home.jsx
import React, { useEffect, useState } from "react";
import { getAccidentSummary } from "../services/accidentService";
import AccidentSummaryView from "../components/accident/AccidentSummaryView";

export default function Home() {
  const [selectedYear, setSelectedYear] = useState(2024); // default latest year
  const [accidentSummary, setAccidentSummary] = useState(null);
  const [loading, setLoading] = useState(false);

  const years = [
    2016, 2017, 2018, 2019,
    2020, 2021, 2022, 2023, 2024
  ];

  useEffect(() => {
    setLoading(true);
    getAccidentSummary(selectedYear)
      .then((res) => setAccidentSummary(res.data.data))
      .finally(() => setLoading(false));
  }, [selectedYear]);

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold">Germany Data Dashboard</h1>

      {/* Accident Summary Section */}
      <h2 className="text-2xl font-semibold mt-8">Accident Summary</h2>

      {/* Year Selector */}
      <div className="mt-4">
        <label className="mr-3 font-medium">Select Year:</label>
        <select
          value={selectedYear}
          onChange={(e) => setSelectedYear(Number(e.target.value))}
          className="border px-3 py-2 rounded"
        >
          {years.map((y) => (
            <option key={y} value={y}>
              {y}
            </option>
          ))}
        </select>
      </div>


      {/* Summary */}
      {loading ? (
        <p className="mt-4 text-gray-500">Loading accident summary…</p>
      ) : (
        <AccidentSummaryView summary={accidentSummary} />
      )}

    </div>
  );
}

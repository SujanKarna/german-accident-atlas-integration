// src/pages/AccidentPage.jsx
import React, { useEffect, useState } from "react";
import {
    getAccidentSummary,
    getMunicipalities,
} from "../services/accidentService";
import AccidentSummaryView from "../components/accident/AccidentSummaryView";
import AccidentMunicipalityView from "../components/accident/AccidentMunicipalityView";

export default function AccidentPage() {
    const [summary, setSummary] = useState(null);
    const [municipalities, setMunicipalities] = useState(null);

    useEffect(() => {
        getAccidentSummary().then((res) => setSummary(res.data));
        getMunicipalities("14").then((res) => setMunicipalities(res.data)); // Saxony example
    }, []);

    return (
        <div className="p-6">
            <h1 className="text-3xl font-bold">Accident Data</h1>

            <AccidentSummaryView summary={summary} />
            <AccidentMunicipalityView data={municipalities} />
        </div>
    );
}

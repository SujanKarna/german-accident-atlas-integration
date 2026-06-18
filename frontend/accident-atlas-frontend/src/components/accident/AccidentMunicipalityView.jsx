// src/components/accident/AccidentMunicipalityView.jsx
import React from "react";
import AccidentTable from "./AccidentTable";

export default function AccidentMunicipalityView({ data }) {
    if (!data) return null;

    const rows = data.map((item) => ({
        municipalityCode: item[0],
        accidents: item[1],
    }));

    return (
        <div className="mt-6">
            <h3 className="text-xl font-semibold mb-2">Accidents by Municipality</h3>
            <AccidentTable rows={rows} />
        </div>
    );
}

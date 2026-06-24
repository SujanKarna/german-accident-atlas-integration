import React, { useEffect, useState } from "react";
import { getTrendsForState } from "../../services/accidentService";
import TrendChart from "../../components/charts/TrendChart";

const STATES = [
    { code: "01", name: "Schleswig-Holstein" },
    { code: "02", name: "Hamburg" },
    { code: "03", name: "Lower Saxony" },
    { code: "04", name: "Bremen" },
    { code: "05", name: "North Rhine-Westphalia" },
    { code: "06", name: "Hesse" },
    { code: "07", name: "Rhineland-Palatinate" },
    { code: "08", name: "Baden-Württemberg" },
    { code: "09", name: "Bavaria" },
    { code: "10", name: "Saarland" },
    { code: "11", name: "Berlin" },
    { code: "12", name: "Brandenburg" },
    { code: "13", name: "Mecklenburg-Vorpommern" },
    { code: "14", name: "Saxony" },
    { code: "15", name: "Saxony-Anhalt" },
    { code: "16", name: "Thuringia" }
];

export default function Trends() {
    const [stateCode, setStateCode] = useState("14"); // Saxony default
    const [trends, setTrends] = useState([]);
    const [loading, setLoading] = useState(false);
    const [metadata, setMetadata] = useState(null);

    const loadTrends = async () => {
        try {
            setLoading(true);
            const res = await getTrendsForState(stateCode);
            setTrends(res.data.data);
            setMetadata(res.data.sourceMetadata);
        } catch (err) {
            console.error("Trend fetch error:", err);
            setTrends([]);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadTrends();
    }, [stateCode]);

    return (
        <div>
            <h2 className="mb-3">Accident Trends (2016–2024)</h2>

            <div className="mb-4" style={{ maxWidth: 300 }}>
                <label className="form-label">Select State</label>
                <select
                    className="form-select"
                    value={stateCode}
                    onChange={(e) => setStateCode(e.target.value)}
                >
                    {STATES.map((s) => (
                        <option key={s.code} value={s.code}>
                            {s.name}
                        </option>
                    ))}
                </select>
            </div>

            {loading && <p>Loading trend data...</p>}

            {!loading && <TrendChart trends={trends} />}

            {/* ⭐ THIS MUST BE INSIDE THE RETURN */}
            {metadata && (
                <div className="mt-4 p-3 bg-light border rounded">
                    <h6 className="text-muted">Dataset Metadata</h6>
                    <p className="mb-1"><strong>Dataset:</strong> {metadata.dataset}</p>
                    <p className="mb-1"><strong>Downloaded:</strong> {metadata.downloadedAt}</p>
                    <p className="mb-1"><strong>License:</strong> {metadata.license}</p>
                    <p className="mb-1"><strong>SHA256:</strong> {metadata.sha256}</p>
                    <a href={metadata.sourceUrl} target="_blank" rel="noreferrer">
                        Source Download Link
                    </a>
                </div>
            )}
        </div>
    );



}


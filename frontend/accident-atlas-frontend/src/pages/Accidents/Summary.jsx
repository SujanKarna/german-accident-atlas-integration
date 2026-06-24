import React, { useEffect, useState } from "react";
import { getAccidentSummary } from "../../services/accidentService";
import SummaryCard from "../../components/cards/SummaryCard";

export default function Summary() {
    const [year, setYear] = useState(2023);
    const [summary, setSummary] = useState(null);
    const [loading, setLoading] = useState(false);
    const [metadata, setMetadata] = useState(null);

    const years = Array.from({ length: 9 }, (_, i) => 2016 + i); // 2016–2024

    useEffect(() => {
        fetchSummary();
    }, [year]);

    const fetchSummary = async () => {
        try {
            setLoading(true);
            const res = await getAccidentSummary(year);
            // ApiResponseDto: { message, data, metadata }
            setSummary(res.data.data);
            setMetadata(res.data.sourceMetadata);
        } catch (err) {
            console.error("Error loading accident summary:", err);
            setSummary(null);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div>
            <div className="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h2 className="mb-1">Accident Summary</h2>
                    <p className="text-muted mb-0">
                        High‑level KPIs for Unfallatlas {year}.
                    </p>
                </div>

                <div style={{ minWidth: 160 }}>
                    <label className="form-label mb-1">Select year</label>
                    <select
                        className="form-select"
                        value={year}
                        onChange={(e) => setYear(parseInt(e.target.value, 10))}
                    >
                        {years.map((y) => (
                            <option key={y} value={y}>
                                {y}
                            </option>
                        ))}
                    </select>
                </div>
            </div>

            {loading && <p>Loading summary...</p>}

            {!loading && !summary && (
                <p className="text-danger">No summary data available for {year}.</p>
            )}

            {!loading && summary && (
                <div className="row">
                    <SummaryCard
                        title="Total accidents"
                        value={summary.total}
                        icon="🚗"
                    />

                    <SummaryCard
                        title="Fatal accidents"
                        value={summary.fatal}
                        icon="☠️"
                    />

                    <SummaryCard
                        title="Personal injury accidents"
                        value={summary.injury}
                        icon="🩺"
                    />

                    <SummaryCard
                        title="Bicycle accidents"
                        value={summary.bicycle}
                        icon="🚴"
                    />

                    <SummaryCard
                        title="Car accidents"
                        value={summary.car}
                        icon="🚘"
                    />

                    <SummaryCard
                        title="Pedestrian accidents"
                        value={summary.pedestrian}
                        icon="🚶"
                    />
                </div>
            )}
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

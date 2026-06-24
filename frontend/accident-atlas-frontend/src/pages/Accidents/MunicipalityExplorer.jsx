import React, { useEffect, useState } from "react";
import { getMunicipalitiesByYear } from "../../services/municipalityService";
import MunicipalityTable from "../../components/tables/MunicipalityTable";
import STATES from "../../constants/states";

const YEARS = Array.from({ length: 2024 - 2016 + 1 }, (_, i) => 2016 + i);

export default function MunicipalityExplorer() {
    const [stateCode, setStateCode] = useState("14");
    const [year, setYear] = useState(2023);
    const [sortByMunicipalityAsc, setSortByMunicipalityAsc] = useState(false);

    const [data, setData] = useState([]);
    const [metadata, setMetadata] = useState(null);
    const [loading, setLoading] = useState(false);

    const loadData = async () => {
        try {
            setLoading(true);
            const res = await getMunicipalitiesByYear(stateCode, year);
            let sorted = [...res.data.data];
            if (sortByMunicipalityAsc) {
                // Sort by municipality code ascending
                sorted.sort((a, b) => a[0].localeCompare(b[0]));
            } else {
                // Default: sort by accident count descending (rank)
                sorted.sort((a, b) => b[1] - a[1]);
            }
            setData(sorted);
            setMetadata(res.data.sourceMetadata);
        } catch (err) {
            console.error("Municipality fetch error:", err);
            setData([]);
        } finally {
            setLoading(false);
        }
    };
    const toggleMunicipalitySort = () => {
        setSortByMunicipalityAsc(!sortByMunicipalityAsc);
    };

    useEffect(() => {
        loadData();
    }, [stateCode, year, sortByMunicipalityAsc]);

    return (
        <div>
            <h2 className="mb-3">Municipality Explorer</h2>

            {/* Filters */}
            <div className="row mb-4">
                <div className="col-md-3">
                    <label className="form-label">State</label>
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

                <div className="col-md-3">
                    <label className="form-label">Year</label>
                    <select
                        className="form-select"
                        value={year}
                        onChange={(e) => setYear(Number(e.target.value))}
                    >
                        {YEARS.map((y) => (
                            <option key={y} value={y}>
                                {y}
                            </option>
                        ))}
                    </select>
                </div>
            </div>

            {/* Table */}
            {loading && <p>Loading municipalities...</p>}
            <MunicipalityTable
                data={data}
                sortByMunicipalityAsc={sortByMunicipalityAsc}
                onToggleMunicipalitySort={toggleMunicipalitySort}
            />


            {/* Metadata */}
            {metadata && (
                <div className="mt-4 p-3 bg-light border rounded">
                    <h6 className="text-muted">Dataset Metadata</h6>
                    <p><strong>Dataset:</strong> {metadata.dataset}</p>
                    <p><strong>Downloaded:</strong> {metadata.downloadedAt}</p>
                    <p><strong>License:</strong> {metadata.license}</p>
                    <p><strong>SHA256:</strong> <code>{metadata.sha256}</code></p>
                    <a href={metadata.sourceUrl} target="_blank" rel="noreferrer">
                        Source Download Link
                    </a>
                </div>
            )}
        </div>
    );
}

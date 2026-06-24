import React, { useEffect, useState } from "react";
import { getCarDensityByYear } from "../../services/carDensityService";
import CarDensityTable from "../../components/tables/CarDensityTable";
import STATES from "../../constants/states";

const YEARS = [2025]; // Only 2025 available

export default function CarDensityExplorer() {
    const [year, setYear] = useState(2025);
    const [data, setData] = useState([]);
    const [metadata, setMetadata] = useState(null);
    const [loading, setLoading] = useState(false);

    const [sortAsc, setSortAsc] = useState(false);
    const toggleSort = () => setSortAsc(!sortAsc);

    const loadData = async () => {
        try {
            setLoading(true);

            const res = await getCarDensityByYear(year);

            const transformed = res.data.data.map((item) => ({
                stateCode: item.id.stateCode,
                stateName: STATES.find((s) => s.code === item.id.stateCode)?.name || "Unknown",
                carDensity: item.carDensity
            }));

            const sorted = [...transformed].sort((a, b) =>
                sortAsc ? a.carDensity - b.carDensity : b.carDensity - a.carDensity
            );

            setData(sorted);
            setMetadata(res.data.sourceMetadata);
        } catch (err) {
            console.error("Car density fetch error:", err);
            setData([]);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadData();
    }, [year, sortAsc]);

    return (
        <div>
            <h2 className="mb-3">Car Density Explorer</h2>

            {/* Info box */}
            <div className="alert alert-info mt-3">
                <strong>About this data:</strong><br />
                Car density represents the number of registered passenger cars per 1,000 inhabitants
                (<em>Pkw je 1.000 Einwohner</em>) in each German federal state.
            </div>

            {/* Filters */}
            <div className="row mb-4">
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
            {loading && <p>Loading car density...</p>}

            {!loading && (
                <CarDensityTable
                    data={data}
                    sortAsc={sortAsc}
                    onToggleSort={toggleSort}
                />
            )}

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

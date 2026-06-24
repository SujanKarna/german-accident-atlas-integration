import React, { useEffect, useState } from "react";
import { getPopulationDensityByYear } from "../../services/populationDensityService";
import PopulationDensityTable from "../../components/tables/PopulationDensityTable";
import STATES from "../../constants/states";

const YEARS = [2024]; // Only 2024 available

export default function PopulationDensityExplorer() {
    const [year, setYear] = useState(2024);
    const [data, setData] = useState([]);
    const [metadata, setMetadata] = useState(null);
    const [loading, setLoading] = useState(false);

    const [sortAsc, setSortAsc] = useState(false);

    const toggleSort = () => setSortAsc(!sortAsc);

    const loadData = async () => {
        try {
            setLoading(true);

            const res = await getPopulationDensityByYear(year);

            // Transform backend response into frontend-friendly structure
            const transformed = res.data.data.map((item) => ({
                stateCode: item.id.stateCode,
                stateName: STATES.find((s) => s.code === item.id.stateCode)?.name || "Unknown",
                populationDensity: item.populationDensity
            }));

            // Sorting logic
            const sorted = [...transformed].sort((a, b) =>
                sortAsc
                    ? a.populationDensity - b.populationDensity
                    : b.populationDensity - a.populationDensity
            );

            setData(sorted);
            setMetadata(res.data.sourceMetadata);
        } catch (err) {
            console.error("Population density fetch error:", err);
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
            <h2 className="mb-3">Population Density Explorer</h2>
            <div className="alert alert-info mt-3">
                <strong>About this data:</strong><br />
                Population density represents the number of people living per square kilometer
                (<em>Einwohner pro km²</em>) in each German federal state.
                Higher values indicate more densely populated regions such as Berlin or Hamburg,
                while lower values correspond to more rural states.
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
            {loading && <p>Loading population density...</p>}

            {!loading && (
                <PopulationDensityTable
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

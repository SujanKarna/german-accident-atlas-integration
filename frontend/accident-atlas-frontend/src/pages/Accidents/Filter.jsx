import React, { useEffect, useState } from "react";
import { getFilteredAccidents } from "../../services/accidentService";

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

const YEARS = Array.from({ length: 2024 - 2016 + 1 }, (_, i) => 2016 + i);

const TYPES = [
    { value: 1, label: "Driving Accident" },
    { value: 2, label: "Turning Off the Road" },
    { value: 3, label: "Turning Into / Crossing" },
    { value: 4, label: "Crossing the Road" },
    { value: 5, label: "Stationary Traffic Accident" },
    { value: 6, label: "Same-Direction Traffic Accident" },
    { value: 7, label: "Other Accident Type" }
];



export default function Filter() {
    const [stateCode, setStateCode] = useState("14");
    const [year, setYear] = useState(2023);
    const [type, setType] = useState(1);


    const [accidents, setAccidents] = useState({ content: [], totalPages: 0 });
    const [page, setPage] = useState(0);

    const [metadata, setMetadata] = useState(null);
    const [loading, setLoading] = useState(false);

    const loadData = async () => {
        try {
            setLoading(true);
            const res = await getFilteredAccidents(stateCode, year, type, page);
            setAccidents(res.data.data);
            setMetadata(res.data.sourceMetadata);
        } catch (err) {
            console.error("Filter fetch error:", err);
            setAccidents([]);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadData();
    }, [stateCode, year, type, page]);

    return (
        <div>
            <h2 className="mb-3">Accident Explorer</h2>

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
                        onChange={(e) => setYear(e.target.value)}
                    >
                        {YEARS.map((y) => (
                            <option key={y} value={y}>
                                {y}
                            </option>
                        ))}
                    </select>
                </div>

                <div className="col-md-3">
                    <label className="form-label">Accident Type</label>
                    <select
                        className="form-select"
                        value={type}
                        onChange={(e) => setType(Number(e.target.value))}
                    >
                        {TYPES.map((t) => (
                            <option key={t.value} value={t.value}>
                                {t.label}
                            </option>
                        ))}
                    </select>
                </div>
            </div>

            {/* Table */}
            {loading && <p>Loading accidents...</p>}

            {!loading && (
                <table className="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Date</th>
                            <th>Time</th>
                            <th>State</th>
                            <th>Category</th>
                            <th>Kind</th>
                            {type === 0 && <th>Type</th>}
                            <th>Light</th>
                            <th>Road</th>
                            <th>Coordinates</th>
                            <th>Participants</th>
                        </tr>
                    </thead>

                    <tbody>
                        {accidents.content?.map((a) => (
                            <tr key={a.id}>
                                <td>{a.id}</td>

                                {/* Date */}
                                <td>{a.year}-{String(a.month).padStart(2, "0")}</td>

                                {/* Time */}
                                <td>{a.hour}:00</td>

                                {/* State */}
                                <td>{a.stateName}</td>

                                {/* Labels */}
                                <td>{a.accidentCategoryLabel}</td>
                                <td>{a.accidentKindLabel}</td>
                                {type === 0 && <td>{a.accidentTypeLabel}</td>}

                                {/* Conditions */}
                                <td>{a.lightConditionLabel}</td>
                                <td>{a.roadConditionLabel}</td>

                                {/* Coordinates */}
                                <td>
                                    {a.latitude.toFixed(5)}, {a.longitude.toFixed(5)}
                                </td>

                                {/* Participants */}
                                <td>
                                    {a.isCar && "🚗 "}
                                    {a.isBicycle && "🚴 "}
                                    {a.isPedestrian && "🚶 "}
                                    {a.isMotorcycle && "🏍️ "}
                                    {a.isGoodsVehicle && "🚚 "}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>


            )}
            {/* ⭐ Pagination must be OUTSIDE the table */}
            <div className="d-flex justify-content-between mt-3">
                <button
                    className="btn btn-outline-primary"
                    disabled={page === 0}
                    onClick={() => setPage(page - 1)}
                >
                    Previous
                </button>

                <span>
                    Page {page + 1} of {accidents.totalPages ?? 1}
                </span>

                <button
                    className="btn btn-outline-primary"
                    disabled={page + 1 >= (accidents.totalPages ?? 1)}
                    onClick={() => setPage(page + 1)}
                >
                    Next
                </button>
            </div>
            {/* Metadata */}
            {metadata && (
                <div className="mt-4 p-3 bg-light border rounded">
                    <h6 className="text-muted">Dataset Metadata</h6>
                    <p className="mb-1"><strong>Dataset:</strong> {metadata.dataset}</p>
                    <p className="mb-1"><strong>Downloaded:</strong> {metadata.downloadedAt}</p>
                    <p className="mb-1"><strong>License:</strong> {metadata.license}</p>
                    <p className="mb-1"><strong>SHA256:</strong> <code>{metadata.sha256}</code></p>
                    <a href={metadata.sourceUrl} target="_blank" rel="noreferrer">
                        Source Download Link
                    </a>
                </div>
            )}
        </div>
    );
}

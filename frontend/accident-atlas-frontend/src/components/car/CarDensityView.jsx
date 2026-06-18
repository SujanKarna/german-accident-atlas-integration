import React, { useState } from 'react';
import {
    getCarDensityByYear,
    getCarDensityByState,
} from '../../services/densityService';
import { STATES } from "../../constants/states";

const CarDensityView = () => {
    const [year, setYear] = useState('');
    const [stateCode, setStateCode] = useState('');
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    const handleSearchByYear = async () => {
        console.log("Selected year:", year);
        if (!year) return;
        setLoading(true);
        setError('');

        try {
            const res = await getCarDensityByYear(year);
            console.log("Population response:", res.data);
            setData(res.data);
        } catch (e) {
            setError('Failed to load car density by year.');
        } finally {
            setLoading(false);
        }
    };

    const handleSearchByState = async () => {
        console.log("Selected state code:", stateCode);
        if (!stateCode) return;
        setLoading(true);
        setError('');

        try {
            const res = await getCarDensityByState(stateCode);
            setData(res.data);
        } catch (e) {
            setError('Failed to load car density by state.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="container mt-4">
            <h2>Car Density</h2>
            <p className="text-muted">
                Query car density by year or by state code.
            </p>

            <div className="row g-3 mb-3">
                <div className="col-md-3">
                    <label className="form-label">Year</label>
                    <select
                        className="form-select"
                        value={year}
                        onChange={(e) => setYear(e.target.value)}
                    >
                        <option value="">Select Year</option>
                        <option value="2025">2025</option>
                    </select>
                </div>
                <div className="col-md-3 d-flex align-items-end">
                    <button className="btn btn-primary w-100" onClick={handleSearchByYear}>
                        Search by Year
                    </button>
                </div>

                <div className="col-md-3">
                    <label className="form-label">State Code</label>
                    <select
                        className="form-select"
                        value={stateCode}
                        onChange={(e) => setStateCode(e.target.value)}
                    >
                        <option value="">Select State</option>
                        {STATES.map((s) => (
                            <option key={s.code} value={s.code}>
                                {s.name}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="col-md-3 d-flex align-items-end">
                    <button className="btn btn-secondary w-100" onClick={handleSearchByState}>
                        Search by State
                    </button>
                </div>
            </div>

            {loading && <div>Loading...</div>}
            {error && <div className="alert alert-danger">{error}</div>}

            {data.length > 0 && (
                <div className="table-responsive mt-3">
                    <table className="table table-striped table-bordered">
                        <thead>
                            <tr>
                                <th>State Code</th>
                                <th>Year</th>
                                <th>Car Density</th>
                            </tr>
                        </thead>
                        <tbody>
                            {data.map((row, idx) => (
                                <tr key={idx}>
                                    <td>{row.id.stateCode}</td>
                                    <td>{row.id.year}</td>
                                    <td>{row.carDensity}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}

            {!loading && !error && data.length === 0 && (
                <p className="text-muted mt-3">No data yet. Try a query above.</p>
            )}
        </div>
    );
};

export default CarDensityView;

import React, { useEffect, useState } from "react";
import {
    getAccidentPopulationRatio,
    getAccidentCarDensityRatio
} from "../../services/crossAnalyticsService";


import CrossAnalyticsTable from "../../components/tables/CrossAnalyticsTable";
import STATES from "../../constants/states";

const DATASETS = {
    POP_DENSITY_RATIO: "Accident-to-Population-Density Ratio",
    CAR_DENSITY_RATIO: "Accident-to-Car-Density Ratio"
};



export default function CrossAnalyticsExplorer() {
    const [dataset, setDataset] = useState("POP_DENSITY_RATIO");
    const [data, setData] = useState([]);
    const [metadata, setMetadata] = useState(null);
    const [loading, setLoading] = useState(false);
    const [year, setYear] = useState(2024);

    const loadData = async () => {
        setLoading(true);

        try {
            let res;
            switch (dataset) {
                case "POP_DENSITY_RATIO":
                    res = await getAccidentPopulationRatio(year);
                    break;

                case "CAR_DENSITY_RATIO":
                    res = await getAccidentCarDensityRatio(year);
                    break;
            }



            const transformed = res.data.data.map((item) => ({
                stateCode: item.stateCode,
                stateName: item.stateName,
                accidents: item.accidents,
                populationDensity: item.populationDensity,
                ratio: item.ratio
            }));



            setData(transformed);
            setMetadata(res.data.sourceMetadata);
        } catch (err) {
            console.error("Cross analytics fetch error:", err);
            setData([]);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        loadData();
    }, [dataset]);

    const getColumns = () => {
        switch (dataset) {
            case "POP_DENSITY_RATIO":
                return [
                    { key: "stateCode", label: "State Code" },
                    { key: "stateName", label: "State Name" },
                    { key: "accidents", label: "Accidents" },
                    { key: "populationDensity", label: "Population Density" },
                    { key: "ratio", label: "Accident-to-Population-Density Ratio" }
                ];

            case "CAR_DENSITY_RATIO":
                return [
                    { key: "stateCode", label: "State Code" },
                    { key: "stateName", label: "State Name" },
                    { key: "accidents", label: "Accidents" },
                    { key: "carDensity", label: "Car Density" },
                    { key: "ratio", label: "Accident-to-Car-Density Ratio" }
                ];
        }
    };

    return (
        <div>
            <h2 className="mb-3">Cross Analytics Explorer</h2>
            {/* Dynamic description */}
            <div className="alert alert-info mt-3">
                {dataset === "POP_DENSITY_RATIO" && (
                    <>
                        <strong>Accident-to-Population-Density Ratio</strong>
                        <p>
                            This ratio shows how many accidents occur relative to how densely
                            populated a state is. A higher ratio means the state has more accidents
                            per unit of population density, indicating higher accident intensity
                            even after adjusting for population.
                        </p>
                        <p>
                            These values are calculated for the selected year <strong>{year}</strong>,
                            ensuring fair comparison across all states.
                        </p>
                    </>
                )}

                {dataset === "CAR_DENSITY_RATIO" && (
                    <>
                        <strong>Accident-to-Car-Density Ratio</strong>
                        <p>
                            This ratio measures how many accidents occur relative to the number of
                            registered cars in each state. A higher ratio means more accidents per
                            unit of car density, which may indicate a riskier or more congested
                            driving environment.
                        </p>
                        <p>
                            These values are calculated for the selected year <strong>2025</strong>,
                            using consistent accident and vehicle density data.
                        </p>
                    </>
                )}
            </div>


            {/* Dataset selector */}
            <div className="mb-4">
                <label className="form-label">Select Analytics Dataset</label>
                <select
                    className="form-select"
                    value={dataset}
                    onChange={(e) => setDataset(e.target.value)}
                >
                    {Object.entries(DATASETS).map(([key, label]) => (
                        <option key={key} value={key}>
                            {label}
                        </option>
                    ))}
                </select>
            </div>

            {/* Table */}
            {loading && <p>Loading analytics...</p>}

            {!loading && (
                <CrossAnalyticsTable columns={getColumns()} data={data} />
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

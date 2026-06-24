import React from "react";
import { Line } from "react-chartjs-2";
import {
    Chart as ChartJS,
    LineElement,
    CategoryScale,
    LinearScale,
    PointElement,
    Tooltip,
    Legend
} from "chart.js";

ChartJS.register(
    LineElement,
    CategoryScale,
    LinearScale,
    PointElement,
    Tooltip,
    Legend
);

export default function TrendChart({ trends }) {
    if (!trends || trends.length === 0) return <p>No trend data available.</p>;

    const years = trends.map(t => t.year);
    const counts = trends.map(t => t.accidents); // FIXED

    const data = {
        labels: years,
        datasets: [
            {
                label: "Accidents",
                data: counts,
                borderColor: "#0d6efd",
                backgroundColor: "rgba(13,110,253,0.1)",
                tension: 0.3,
                pointRadius: 4
            }
        ]
    };

    return <Line data={data} />;
}


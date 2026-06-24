import React from "react";

export default function MunicipalityTable({ data, sortByMunicipalityAsc, onToggleMunicipalitySort }) {
    if (!data || data.length === 0) {
        return <p>No municipality data available.</p>;
    }

    return (
        <table className="table table-striped table-bordered">
            <thead>
                <tr>
                    <th
                        onClick={onToggleMunicipalitySort}
                        style={{ cursor: "pointer" }}
                    >
                        Municipality Code {sortByMunicipalityAsc ? "↑" : "↕"}
                    </th>
                    <th>Accidents</th>
                    <th>Rank</th>
                </tr>
            </thead>

            <tbody>
                {data.map((row, index) => (
                    <tr key={index}>
                        <td>{row[0]}</td>
                        <td>{row[1]}</td>
                        <td>{index + 1}</td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
}

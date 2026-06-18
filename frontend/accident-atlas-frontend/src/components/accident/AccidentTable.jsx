// src/components/accident/AccidentTable.jsx
import React from "react";

export default function AccidentTable({ rows }) {
    if (!rows || rows.length === 0)
        return <p className="text-gray-500">No accident data available.</p>;

    return (
        <table className="min-w-full border border-gray-300 mt-4">
            <thead className="bg-gray-100">
                <tr>
                    {Object.keys(rows[0]).map((col) => (
                        <th key={col} className="px-4 py-2 border">
                            {col}
                        </th>
                    ))}
                </tr>
            </thead>
            <tbody>
                {rows.map((row, idx) => (
                    <tr key={idx} className="hover:bg-gray-50">
                        {Object.values(row).map((val, i) => (
                            <td key={i} className="px-4 py-2 border">
                                {val}
                            </td>
                        ))}
                    </tr>
                ))}
            </tbody>
        </table>
    );
}

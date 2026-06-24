export default function CarDensityTable({
    data,
    sortAsc,
    onToggleSort
}) {
    if (!data || data.length === 0) {
        return <p>No car density data available.</p>;
    }

    return (
        <table className="table table-striped table-bordered">
            <thead>
                <tr>
                    <th>State Code</th>
                    <th>State Name</th>

                    <th onClick={onToggleSort} style={{ cursor: "pointer" }}>
                        Car Density {sortAsc ? "↑" : "↓"}
                    </th>
                </tr>
            </thead>

            <tbody>
                {data.map((row, index) => (
                    <tr key={index}>
                        <td>{row.stateCode}</td>
                        <td>{row.stateName}</td>
                        <td>{row.carDensity}</td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
}

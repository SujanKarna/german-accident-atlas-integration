export default function CrossAnalyticsTable({ columns, data }) {
    if (!data || data.length === 0) {
        return <p>No analytics data available.</p>;
    }

    return (
        <table className="table table-striped table-bordered">
            <thead>
                <tr>
                    {columns.map((col) => (
                        <th key={col.key}>{col.label}</th>
                    ))}
                </tr>
            </thead>

            <tbody>
                {data.map((row, index) => (
                    <tr key={index}>
                        {columns.map((col) => (
                            <td key={col.key}>{row[col.key]}</td>
                        ))}
                    </tr>
                ))}
            </tbody>
        </table>
    );
}

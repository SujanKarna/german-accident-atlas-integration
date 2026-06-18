export default function AccidentSummaryView({ summary }) {
    if (!summary) return null;

    const items = [
        { label: "Total Accidents", value: summary.totalAccidents ?? 0 },
        { label: "Fatal Accidents", value: summary.fatalAccidents ?? 0 },
        { label: "Injury Accidents", value: summary.injuryAccidents ?? 0 },
        { label: "Bicycle Accidents", value: summary.bicycleAccidents ?? 0 },
        { label: "Car Accidents", value: summary.carAccidents ?? 0 },
        { label: "Pedestrian Accidents", value: summary.pedestrianAccidents ?? 0 },
    ];

    return (
        <div className="container mt-4">
            <div className="row g-3">
                {items.map((item) => (
                    <div key={item.label} className="col-6 col-md-4 col-lg-2">
                        <div className="card text-center shadow-sm">
                            <div className="card-body p-3">
                                <h6 className="card-title text-muted">{item.label}</h6>
                                <h4 className="fw-bold text-primary mt-2">{item.value}</h4>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}

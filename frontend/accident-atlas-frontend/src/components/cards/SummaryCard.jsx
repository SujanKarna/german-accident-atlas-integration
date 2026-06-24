import React from "react";

export default function SummaryCard({ title, value, icon }) {
    return (
        <div className="col-md-3 mb-3">
            <div className="card shadow-sm border-0">
                <div className="card-body">
                    <div className="d-flex align-items-center">
                        <div
                            className="me-3"
                            style={{
                                fontSize: "2rem",
                                opacity: 0.7
                            }}
                        >
                            {icon}
                        </div>

                        <div>
                            <h6 className="text-muted text-uppercase mb-1">{title}</h6>
                            <h3 className="fw-bold mb-0">
                                {value?.toLocaleString?.() ?? value}
                            </h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

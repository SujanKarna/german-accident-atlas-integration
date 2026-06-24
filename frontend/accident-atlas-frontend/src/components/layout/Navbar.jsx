import { NavLink } from "react-router-dom";

export default function Navbar() {
  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark px-3">
      <NavLink className="navbar-brand" to="/">
        Accident Atlas
      </NavLink>

      <div className="collapse navbar-collapse">
        <ul className="navbar-nav ms-auto">
          <li className="nav-item">
            <NavLink className="nav-link" to="/trends">
              Trends
            </NavLink>
          </li>
          <li className="nav-item">
            <NavLink className="nav-link" to="/filter">
              Filter
            </NavLink>
          </li>
          <li className="nav-item">
            <NavLink className="nav-link" to="/municipalities">
              Municipalities
            </NavLink>
          </li>
          <li className="nav-item">
            <NavLink className="nav-link" to="/population-density">
              Population Density
            </NavLink>
          </li>
          <li className="nav-item">
            <NavLink className="nav-link" to="/car-density">
              Car Density
            </NavLink>
          </li>
          <li className="nav-item">
            <NavLink className="nav-link" to="/cross-analytics">
              Cross Analytics
            </NavLink>
          </li>
        </ul>
      </div>
    </nav>
  );
}

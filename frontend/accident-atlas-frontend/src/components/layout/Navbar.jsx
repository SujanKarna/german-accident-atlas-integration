import { Link } from "react-router-dom";

export default function Navbar() {
  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark px-3">
      <Link className="navbar-brand" to="/">Accident Atlas</Link>

      <div className="collapse navbar-collapse">
        <ul className="navbar-nav ms-auto">
          <li className="nav-item">
            <Link className="nav-link" to="/population">Population Density</Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="/car-density">Car Density</Link>
          </li>
        </ul>
      </div>
    </nav>
  );
}

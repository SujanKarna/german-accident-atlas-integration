import requests
from pathlib import Path
import zipfile
import hashlib
import datetime

BASE_URL = "https://www.opengeodata.nrw.de/produkte/transport_verkehr/unfallatlas"
DATA_DIR = Path("data/unfallatlas")
DATA_DIR.mkdir(parents=True, exist_ok=True)


def download(url, dest):
    response = requests.get(url, stream=True)
    response.raise_for_status()
    with open(dest, "wb") as f:
        for chunk in response.iter_content(chunk_size=8192):
            f.write(chunk)


def sha256sum(path):
    h = hashlib.sha256()
    with open(path, "rb") as f:
        for chunk in iter(lambda: f.read(4096), b""):
            h.update(chunk)
    return h.hexdigest()


def file_exists_on_server(year):
    url = f"{BASE_URL}/Unfallorte{year}_EPSG25832_CSV.zip"
    response = requests.head(url)
    return response.status_code == 200


def local_years():
    return {int(p.name) for p in DATA_DIR.iterdir() if p.is_dir() and p.name.isdigit()}


def download_year(year):
    zip_name = f"Unfallorte{year}_EPSG25832_CSV.zip"
    url = f"{BASE_URL}/{zip_name}"
    dest_zip = DATA_DIR / zip_name

    print(f"Downloading accident data for {year}...")
    download(url, dest_zip)

    checksum = sha256sum(dest_zip)
    print(f"Downloaded {zip_name} (SHA256: {checksum})")

    extract_dir = DATA_DIR / str(year)
    extract_dir.mkdir(exist_ok=True)

    with zipfile.ZipFile(dest_zip, "r") as z:
        for member in z.namelist():
            z.extract(member, extract_dir)
            if member.lower().endswith((".csv", ".txt")):
                extracted_path = extract_dir / member
                final_path = extract_dir / Path(member).name
                extracted_path.rename(final_path)

    provenance_file = DATA_DIR / f"{year}_provenance.txt"
    with open(provenance_file, "w") as f:
        f.write(f"source_url={url}\n")
        f.write(f"downloaded_at={datetime.datetime.now(datetime.timezone.utc).isoformat()}Z\n")
        f.write(f"sha256={checksum}\n")
        f.write("licence=Datenlizenz Deutschland - Namensnennung - Version 2.0\n")
        f.write("licence_url=https://www.govdata.de/dl-de/by-2-0\n")


def update_unfallatlas():
    print("Checking for new Unfallatlas data...")

    existing = local_years()
    print("Local years:", sorted(existing))

    possible_years = range(2016, datetime.datetime.now().year + 1)

    for year in possible_years:
        if year in existing:
            continue

        if file_exists_on_server(year):
            print(f"New dataset found for {year}! Downloading...")
            download_year(year)
        else:
            print(f"No dataset available for {year} on server.")

    print("Update complete.")


if __name__ == "__main__":
    update_unfallatlas()

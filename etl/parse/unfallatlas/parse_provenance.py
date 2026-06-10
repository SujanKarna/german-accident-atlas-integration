from pathlib import Path

def parse_provenance_file(path: Path):
    meta = {}

    with open(path, "r", encoding="utf-8") as f:
        for line in f:
            if "=" not in line:
                continue

            key, value = line.split("=", 1)
            meta[key.strip()] = value.strip()

    return meta

from pathlib import Path
from etl.parse.common.load_population_raw import list_population_files

def inspect_raw_population():
    """
    Print the first few lines of each raw population CSV file.
    Useful to verify header structure and metadata lines.
    """
    for path in list_population_files():
        print("=" * 80)
        print(f"FILE: {path.name}")
        print("- First 5 lines -")

        with open(path, "r", encoding="utf-8") as f:
            for _ in range(5):
                print(f.readline().rstrip())

        print("=" * 80)
        print()
        

if __name__ == "__main__":
    inspect_raw_population()

from etl.transform.regionalatlas_population.combine_population_indicators import combine_population_indicators

def inspect_transformed_population():
    """
    Show the combined population DataFrame after parsing + transformation.
    """
    df = combine_population_indicators()

    print("\n=== Combined Population Indicators ===")
    print(df.head(20))

    print("\n=== Unique indicator_ids ===")
    print(df["indicator_id"].unique())

    print("\n=== Unique years ===")
    print(df["year"].unique())

    print("\n=== DataFrame shape ===")
    print(df.shape)


if __name__ == "__main__":
    inspect_transformed_population()

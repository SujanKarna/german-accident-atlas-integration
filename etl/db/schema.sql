-- dim_state
CREATE TABLE IF NOT EXISTS dim_state (
    state_code VARCHAR(2) PRIMARY KEY,
    state_name TEXT NOT NULL
);


-- dim_accident_type
CREATE TABLE IF NOT EXISTS dim_accident_type (
    type_code INT PRIMARY KEY,
    label TEXT NOT NULL
);

-- dim_accident_category
CREATE TABLE IF NOT EXISTS dim_accident_category (
    category_code INT PRIMARY KEY,
    label TEXT NOT NULL
);

-- dim_accident_kind
CREATE TABLE IF NOT EXISTS dim_accident_kind (
    kind_code INT PRIMARY KEY,
    label TEXT NOT NULL
);

-- dim_light_condition
CREATE TABLE IF NOT EXISTS dim_light_condition (
    light_condition_code INT PRIMARY KEY,
    label TEXT NOT NULL
);

-- dim_plausibility_level
CREATE TABLE IF NOT EXISTS dim_plausibility_level (
    plausibility_code INT PRIMARY KEY,
    label TEXT NOT NULL
);

-- dim_road_condition
CREATE TABLE IF NOT EXISTS dim_road_condition (
    road_condition_code INT PRIMARY KEY,
    label TEXT NOT NULL
);



-- fact_accident
CREATE TABLE IF NOT EXISTS fact_accident (
    id BIGSERIAL PRIMARY KEY,

    accident_category_code INT REFERENCES dim_accident_category(category_code),
    accident_kind_code INT REFERENCES dim_accident_kind(kind_code),
    accident_type_code INT REFERENCES dim_accident_type(type_code),
    light_condition_code INT REFERENCES dim_light_condition(light_condition_code),
    plausibility_code INT REFERENCES dim_plausibility_level(plausibility_code),
    road_condition_code INT REFERENCES dim_road_condition(road_condition_code),
    municipality_code VARCHAR(8),
    state_code VARCHAR(2) REFERENCES dim_state(state_code),

    week_day VARCHAR(10),

    year INT NOT NULL,
    month INT NOT NULL,
    hour INT,

    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    utm_x DOUBLE PRECISION,
    utm_y DOUBLE PRECISION,

    is_car BOOLEAN,
    is_motorcycle BOOLEAN,
    is_bicycle BOOLEAN,
    is_pedestrian BOOLEAN,
    is_goods_vehicle BOOLEAN,
    is_others BOOLEAN
);

-- source_metadata
CREATE TABLE IF NOT EXISTS source_metadata (
    dataset VARCHAR(100) PRIMARY KEY,
    source_url TEXT,
    license TEXT,
    license_url TEXT,
    downloaded_at TEXT,
    sha256 TEXT
);


CREATE TABLE dim_population_density (
    state_code VARCHAR(2) NOT NULL REFERENCES dim_state(state_code),
    population_density DECIMAL(10,2) NOT NULL,
    year INT NOT NULL,
    PRIMARY KEY (state_code, year)
);

CREATE TABLE dim_car_density (
    state_code VARCHAR(2) NOT NULL REFERENCES dim_state(state_code),
    car_density DECIMAL(10,2) NOT NULL,
    year INT NOT NULL,
    PRIMARY KEY (state_code, year)
);



CREATE TABLE coordinates (
                             id BIGINT generated BY DEFAULT AS IDENTITY PRIMARY KEY,
                             longitude DOUBLE PRECISION,
                             latitude DOUBLE PRECISION
);

CREATE TABLE locations (
                           id BIGINT generated BY DEFAULT AS IDENTITY PRIMARY KEY,
                           coordinates_id BIGINT REFERENCES coordinates(id),
                           municipality VARCHAR(250),
                           address VARCHAR(250)
);

CREATE TABLE users (
                            id BIGINT generated BY DEFAULT AS IDENTITY PRIMARY KEY,
                            first_name VARCHAR(100),
                            last_name VARCHAR(100),
                            email VARCHAR(250),
                            password VARCHAR(100),
                            user_type VARCHAR(30),
                            phone_number VARCHAR(100),
                            activated BOOLEAN,
                            device_token TEXT
);

CREATE TABLE lost_pets (
                           id BIGINT generated BY DEFAULT AS IDENTITY PRIMARY KEY,
                           name VARCHAR(100),
                           pet_type VARCHAR(30),
                           photo VARCHAR(255),
                           additional_information VARCHAR(255),
                           pet_owner_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
                           lost_at_time TIMESTAMP,
                           lost_at_location_id BIGINT REFERENCES locations(id),
                           status VARCHAR(30)
);

CREATE TABLE seen_pets (
                           id BIGINT generated BY DEFAULT AS IDENTITY PRIMARY KEY,
                           lost_pet_id BIGINT REFERENCES lost_pets(id),
                           seen_at_time TIMESTAMP,
                           seen_at_location_id BIGINT REFERENCES locations(id),
                           reported_by_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
                           photo VARCHAR(255)
);

CREATE TABLE messages (
                          id BIGINT generated BY DEFAULT AS IDENTITY PRIMARY KEY,
                          sender_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
                          recipient_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
                          content TEXT,
                          sent_at TIMESTAMP
);
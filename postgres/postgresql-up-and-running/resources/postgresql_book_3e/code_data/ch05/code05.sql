--- JSON examples --

DROP TABLE IF EXISTS persons;

CREATE TABLE persons (id serial PRIMARY KEY, person json);

INSERT INTO persons (person) 
VALUES (
    '{
        "name":"Sonia",
        "spouse":
        {
            "name":"Alex",
            "parents":
            {
                "father":"Rafael",
                "mother":"Ofelia"
            },
            "phones":
            [
                {
                    "type":"work",
                    "number":"619-722-6719"
                },
                {
                    "type":"cell",
                    "number":"619-852-5083"
                }
            ]
        },
        "children": 
        [
            {
                "name":"Brandon",
                "gender":"M"
            },
            {
                "name":"Azaleah",
                "girl":true,
                "phones": []
            }
        ]
    }'
);

SELECT person->'name' FROM persons;

SELECT person->'spouse'->'parents'->'father' FROM persons;
SELECT person#>array['spouse','parents','father'] FROM persons;

SELECT person->'children'->0->'name' FROM persons;
SELECT person#>array['children','0','name'] FROM persons;


SELECT person->'spouse'->'parents'->>'father' FROM persons;
SELECT person#>>array['children','0','name'] FROM persons;



SELECT json_array_elements(person->'children')->>'name' FROM persons;



CREATE TABLE persons (id serial PRIMARY KEY, person json);

INSERT INTO persons_b (person) 
VALUES (
    '{
        "name":"Sonia",
        "spouse":
        {
            "name":"Alex",
            "parents":
            {
                "father":"Rafael",
                "mother":"Ofelia"
            },
            "phones":
            [
                {
                    "type":"work",
                    "number":"619-722-6719"
                },
                {
                    "type":"cell",
                    "number":"619-852-5083"
                }
            ]
        },
        "children": 
        [
            {
                "name":"Brandon",
                "gender":"M"
            },
            {
                "name":"Azaleah",
                "girl":true,
                "phones": []
            }
        ]
    }'
);

-- FULL text examples
--first load the film table

ALTER TABLE film ADD COLUMN fts tsvector;
UPDATE film SET fts = setweight( to_tsvector(COALESCE(title,'')), 'A') ||
    setweight( to_tsvector(COALESCE(description,'')), 'B');
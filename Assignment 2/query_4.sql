SELECT name FROM "Beers" b
    WHERE NOT EXISTS(
        SELECT * FROM "Beers"
            WHERE manf = b.manf 
            AND name <> b.name
);
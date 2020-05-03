SELECT DISTINCT drinker FROM "Frequents" 
    WHERE drinker NOT IN(
        SELECT f.drinker FROM "Frequents" f 
        WHERE f.bar NOT IN (
            SELECT bar FROM "Sells", "Likes"
            WHERE "Sells".beer = "Likes".beer 
            AND "Likes".drinker = f.drinker));

SELECT DISTINCT "Frequents".drinker
    FROM "Likes","Sells","Frequents"
    where "Likes".drinker = "Frequents".drinker and
    "Sells".beer = "Likes".beer and
    "Frequents".bar = "Sells".bar;

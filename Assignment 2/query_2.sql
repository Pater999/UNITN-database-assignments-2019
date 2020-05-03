SELECT Bar1.bar
FROM "Sells" Bar1, "Sells" Bar2
WHERE Bar1.beer = 'Miller' AND Bar2.beer = 'Bud'
   AND round(Bar1.price::numeric,2) = round(Bar2.price::numeric,2)
   AND Bar1.bar = Bar2.bar;
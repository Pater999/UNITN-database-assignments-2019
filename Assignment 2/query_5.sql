select distinct beer 
from "Sells" 
where round(price::numeric,2) = (SELECT Max(round(price::numeric,2)) FROM "Sells");
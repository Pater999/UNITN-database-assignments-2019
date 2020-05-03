select "Beers".name, manf 
from "Beers", "Likes"
where "Likes".beer = "Beers".name and "Likes".drinker = 'Fred';
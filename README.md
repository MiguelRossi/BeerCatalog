The app search for a random beer in the [PUNK API v2](https://punkapi.com/documentation/v2) (the end 
point gives the chance of listing the catalog, search a beer,..., this is why the app is called 
"BeerCatalog")

The app is implemented following the MVVM architectural pattern with a view, viewmodel and a model (
_domain_, _network_ and _repository_ packages).

The app is organized by features with a _main_ package (ideally a module) with all common objects and 
two features: _randombeer_ and _findbeer_ (the last is only a proof of concept of how to scalate the 
app)

Things to improve would be:
- _main_, _randombeer_ and _findbeer_ should be modules. 
- navigation, in order to add more screens (ideally with Navigation Component)
- improve error handling (it should be split in layers and with proper user messages)
- extract all gradle versions (needed for creating different modules) 

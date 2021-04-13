const cache = new Map();

export async function parseBeersAsync(fetchBeerData, name) {

    if (cache.has(name)) {
        return 'From JS Cache! ' + cache.get(name);
    }

    // wait for Java HTTP client
    var data = await fetchBeerData;
    if (!data) {
        return '{}';
    }

    var beers = JSON.parse(data);
    var results = [];
    for (var b of beers) {
        if (b.name.indexOf(name) != -1) {
            results.push({name: b.name, desc: b.description});
        }
    }
    var result = "From ES module! " + JSON.stringify(results);
    cache.set(name, result);
    return result;
}

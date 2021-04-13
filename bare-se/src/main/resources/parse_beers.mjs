export function parseBeers(data, name) {
    var beers = JSON.parse(data);
    var results = [];
    for (var b of beers) {
        if (b.name.indexOf(name) != -1) {
            results.push({name: b.name, desc: b.description});
        }
    }
    return "Hello from mjs! " + JSON.stringify(results);
}
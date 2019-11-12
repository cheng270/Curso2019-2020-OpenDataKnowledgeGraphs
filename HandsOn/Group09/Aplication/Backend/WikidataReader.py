

#obtiene de wikidata
import requests
from flask import jsonify


def obtainFromWikidata(data):
    url = 'https://query.wikidata.org/sparql'
    queryq = "SELECT ?population ?image ?area WHERE {wd:"+data+" wdt:P1082 ?population . wd:Q18419 wdt:P18 ?image . wd:Q18419 wdt:P2046 ?area . SERVICE wikibase:label { bd:serviceParam wikibase:language \"en\" . } } ORDER BY DESC(?population)"
    print(queryq)
    r = requests.get(url, params={'format': 'json', 'query': queryq})
    data = r.json()
    population = data['results']['bindings'][0]['population']['value']
    image = data['results']['bindings'][0]['image']['value']
    area = data['results']['bindings'][0]['area']['value']
    print(data)
    print(population)
    print(image)
    print(area)

    wikidata = {"pop": population, "img": image, "area": area}

    return wikidata

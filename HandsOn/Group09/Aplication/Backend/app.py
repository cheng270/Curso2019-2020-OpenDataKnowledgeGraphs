import json
import rdflib
import re
import requests
from flask import Flask, jsonify, request, Response
from rdflib import Graph, plugin, RDF
from rdflib.namespace import FOAF, Namespace
import WikidataReader
from flask_cors import CORS

g = Graph()
g.parse("with_links_ttl.ttl", format="turtle")
id = 8641

# implementacion del servicio
app = Flask(__name__)
CORS(app)


@app.route('/')
def hello_world():
    return "Hello world"


@app.route('/printinmap', methods=['GET'])
def printinmap():
    dataformap = '{ "id": id, "longitude": longitude, "latitude": latitude }'
    return jsonify(data=obtainHostpotsTTL())


# obatain all Hotspots
def obtainHostpotsTTL():
    listHotspot = []
    query = "SELECT DISTINCT ?s WHERE { ?s ?p ?o }"
    response = g.query(query)
    i = 0
    for datar in response:
        try:
            if "Boroname" not in str(datar) and "Provider" not in str(datar) and "BoroName" not in str(datar):
                i = i + 1
                print(datar)
                hotspot_item = obtainHostpotsInformationTTL(datar)
                listHotspot.append(hotspot_item)
                print(listHotspot)
                print("aaaaaaaaaaaaaaaaaa")
                print(i)
                if i == 100:
                    break;
        except:
            print("Algo Ocurrio")
    return listHotspot


# obatain each Hotspot information
def obtainHostpotsInformationTTL(a):
    row = str(a)
    url = row.split('(rdflib.term.URIRef(\'')[1].split('\'')[0]
    query = "SELECT DISTINCT ?property ?hasValue " \
            "WHERE {" \
            "{ <" + url + "> ?property ?hasValue } }" \
                          "ORDER BY (!BOUND(?hasValue)) ?property ?hasValue ?isValueOf"
    response = g.query(query)
    hotspot = {}
    for datar in response:
        property = datar.property
        value = datar.hasValue
        pr = property.split('#')
        print(property)
        if (pr[1] == "hasBoroName"):
            hotspot.__setitem__("BoroData", obtainIndiceWiki(value))
        print(pr[1])
        print(value)
        print("")
        hotspot.__setitem__(pr[1], str(value))
    return hotspot


def obtainIndiceWiki(distrito):
    wikidata = {}
    query = "SELECT DISTINCT ?hasValue " \
            "WHERE { " \
            "{ <"+ distrito + "> ?property ?hasValue } } " \
                              "ORDER BY (!BOUND(?hasValue)) " \
                              "?property ?hasValue ?isValueOf"
    response = g.query(query)
    for datar in response:
        print(datar[0])
        if "wiki" in str(datar):
            indiceWiki = datar[0].split('wiki/')[1]
            wikidata = WikidataReader.obtainFromWikidata(indiceWiki)
    return wikidata


if __name__ == '__main__':
    app.run(host='0.0.0.0',
            debug=True,
            port=9200)

import rdflib

graph = rdflib.Graph()

graph.parse("Existing_Bike_Network.ttl", format='n3')

query_res = graph.query(
    """SELECT DISTINCT ?street ?name ?imp
        WHERE {
            ?street a bbn:StreetSegment .
            ?street bbn:streetName ?name .
            ?street bbn:medianLatitude ?lat .
            ?street bbn:medianLongitude ?lon .
            ?street bbn:importance ?imp
            FILTER (
                ?lat > 42.3 &&
                ?lat < 42.31 &&
                ?lon < -71 &&
                ?lon > -71.1
            )
        }
        ORDER BY DESC(?imp) 
    """
)

i = 0
for row in query_res:
    i += 1
    print(str(i) + ". %s\n name: %s, importance: %s\n" % row)

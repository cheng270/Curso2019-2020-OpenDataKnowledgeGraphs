import rdflib

graph = rdflib.Graph()

graph.parse("Existing_Bike_Network.ttl", format='n3')

is_divided = "no"

query_res = graph.query(
    """SELECT DISTINCT ?street ?name ?imp
        WHERE {
            ?street a bbn:StreetSegment .
            ?street bbn:streetName ?name .
            ?street bbn:importance ?imp .
            ?street bbn:divided "no" .
        }
        ORDER BY DESC(?imp) LIMIT 1
    """
)

i = 0
for row in query_res:
    i += 1
    print(str(i) + ". %s\n name: %s, importance: %s\n" % row)

import rdflib

graph = rdflib.Graph()

graph.parse("Existing_Bike_Network.ttl", format='n3')


# params
min_imp = "0.4"
coordinates = (42.305, -71.05)
epsilons = (0.005, 0.05)


query_res = graph.query(
    """SELECT DISTINCT ?street ?name ?imp
        WHERE {
            ?street a bbn:StreetSegment .
            ?street bbn:streetName ?name .
            ?street bbn:medianLatitude ?lat .
            ?street bbn:medianLongitude ?lon .
            ?street bbn:importance ?imp .
            FILTER (
                ?lat > """+str(coordinates[0] - epsilons[0])+""" &&
                ?lat < """+str(coordinates[0] + epsilons[0])+""" &&
                ?lon > """+str(coordinates[1] - epsilons[1])+""" &&
                ?lon < """+str(coordinates[1] + epsilons[1])+""" &&
                ?imp > """ + min_imp + """
            )
        }
        ORDER BY DESC(?imp) 
    """
)

i = 0
for row in query_res:
    i += 1
    print(str(i) + ". %s\n name: %s, importance: %s\n" % row)

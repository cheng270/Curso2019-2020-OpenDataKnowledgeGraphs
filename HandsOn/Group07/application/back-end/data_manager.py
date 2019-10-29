from rdflib import Graph, plugin
from rdflib.serializer import Serializer


class DataRetriever:

    def __init__(self):
        self.data = Graph()
        self.data.parse("./test.ttl", format="turtle")

    '''
    print(list(data.query("""SELECT DISTINCT ?Concept
                         WHERE {
                         ?x a ?Concept .
                         } LIMIT 1
                         """))[0].Concept)
    '''

    def get_pollutants(self):
        res = self.data.query("""select distinct ?x 
                            WHERE 
                            {
                              ?x a <http://www.airqualitymadrid.es/ontologies/airqualitymadrid#Pollutant> .
                            }
                            """)
        return res

    def get_stations(self):
        res = self.data.query("""select distinct ?x 
                            WHERE 
                            {
                              ?x a <http://www.airqualitymadrid.es/ontologies/airqualitymadrid#PollutionSensor> .
                            }
                            """)
        tmp = list()
        for row in res:
            d = str(row['x'].toPython())
            tmp.append(d)
        return tmp




from rdflib import Graph


class DataRetriever:

    def __init__(self):
        self.data = Graph()
        self.data.parse("./../../rdf/rdf.ttl", format="turtle")

    def get_pollutants(self):
        res = self.data.query("""select distinct ?x 
                            WHERE 
                            {
                              ?x a <http://www.airqualitymadrid.es/ontologies/airqualitymadrid#Pollutant> .
                            }
                            """)
        return res

    def get_stations(self):
        res = self.data.query("""
            PREFIX aqm: <http://www.airqualitymadrid.es/ontologies/airqualitymadrid#>
            select ?station_id ?station_name ?latitude ?longitude ?address ?elevation 
            WHERE {
                ?station_id a aqm:PollutionSensor ;
                    aqm:placedIn ?station_name ;
                    aqm:placedInNeighbourhood ?neigh_entity ;
                    aqm:hasSensorElevation ?elevation ;
                    aqm:hasLatitude ?latitude ;
                    aqm:hasLongitude ?longitude ;
                    aqm:hasAddress ?address .
                ?station_name a aqm:Address .
                ?neigh_entity a aqm:Neighbourhood
            } 
            """)
        print(res)
        print(len(res))
        tmp = list()
        for row in res:
            print(row)
            print("----------------------------------------------------------------")
            for el in row:
                print(el.toPython())
            print("*****************************************************************")
        return tmp

    def get_measurements_by_id_and_date(self, station_id="28079011",
                                        start_time="2018-01-01T01:00Z",
                                        end_time="2018-10-10T06:00Z"):
        res = self.data.query("""
            PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
            PREFIX sens: <http://www.airqualitymadrid.es/resource/pollution_sensor/>
            PREFIX aqm: <http://www.airqualitymadrid.es/ontologies/airqualitymadrid#>
            select ?pollutant_id ?pollutant_name (AVG(?value) as ?avg) (MIN(?value) as ?min) (MAX(?value) as ?max) 
            WHERE {
                sens:""" + station_id + """ a aqm:PollutionSensor ;
                    aqm:hasCollected ?measurement .
                ?measurement a aqm:Measurement ;
                    aqm:hasPollutant ?pollutant_id ;
                    aqm:hasMeasurementDate ?date ;
                    aqm:hasMeasurementValue ?value . 
                ?pollutant a aqm:Pollutant ;
                    aqm:hasPollutantName ?pollutant_name .
                FILTER(?date >= \"""" + start_time + """\"^^xsd:dateTime)
                FILTER(?date <= \"""" + end_time + """\"^^xsd:dateTime)
            } 
            GROUP BY ?pollutant_id
            """)
        tmp = list()
        for row in res:
            print(row)
            print("----------------------------------------------------------------")
            for el in row:
                print(el.toPython())
            print("*****************************************************************")
        return tmp

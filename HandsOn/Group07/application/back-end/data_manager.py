from rdflib import Graph
from .struct_builder import StructBuilder
import json


class DataRetriever:

    def __init__(self):
        self.data = Graph()
        self.data.parse("./test.ttl", format="turtle")

    def get_pollutant_data(self, pollutant_id):
        res = self.data.query("""
            PREFIX poll: <http://www.airqualitymadrid.es/resource/pollutant/>
            PREFIX aqm: <http://www.airqualitymadrid.es/ontologies/airqualitymadrid#>
            PREFIX owl: <http://www.w3.org/2002/07/owl#>
            select distinct ?link
            WHERE {
                poll:""" + pollutant_id + """ a aqm:Pollutant ;
                    owl:sameAs ?link .
            }
            """)
        tmp = list()
        for row in res:
            el = StructBuilder.get_wikidata_link(row)
            tmp.append(el)
        return json.dumps(tmp)

    def get_stations(self):
        res = self.data.query("""
            PREFIX aqm: <http://www.airqualitymadrid.es/ontologies/airqualitymadrid#>
            select ?station_id ?station_name ?latitude ?longitude ?address ?elevation ?neighbourhood_name
            WHERE {
                ?station_id a aqm:PollutionSensor ;
                    aqm:placedIn ?station_name ;
                    aqm:hasSensorElevation ?elevation ;
                    aqm:hasLatitude ?latitude ;
                    aqm:hasLongitude ?longitude .
                ?station_name aqm:hasAddress ?address ;
                    aqm:placedInNeighbourhood ?neigh_entity .
                ?neigh_entity aqm:hasNeighbourhoodName ?neighbourhood_name .    
            } 
            """)
        tmp = list()
        for row in res:
            el = StructBuilder.get_station_struct(row)
            tmp.append(el)
        return json.dumps(tmp)

    def get_measurements_by_id_and_date(self, station_id="28079011",
                                        start_time="2018-01-01T01:00Z",
                                        end_time="2018-10-10T06:00Z"):
        res = self.data.query("""
            PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
            PREFIX sens: <http://www.airqualitymadrid.es/resource/pollution_sensor/>
            PREFIX aqm: <http://www.airqualitymadrid.es/ontologies/airqualitymadrid#>
            select ?pollutant_id ?pollutant_name (MIN(?value) as ?min) (avg(xsd:float(xsd:string(?value))) as ?avg) (MAX(?value) as ?max) 
            WHERE {
                sens:""" + station_id + """ a aqm:PollutionSensor ;
                    aqm:hasCollected ?measurement .
                ?measurement a aqm:Measurement ;
                    aqm:hasPollutant ?pollutant_id ;
                    aqm:hasMeasurementDate ?date ;
                    aqm:hasMeasurementValue ?value . 
                ?pollutant_id a aqm:Pollutant ;
                    aqm:hasPollutantName ?pollutant_name .
                FILTER(?date >= \"""" + start_time + """\"^^xsd:dateTime)
                FILTER(?date <= \"""" + end_time + """\"^^xsd:dateTime)
            } 
            GROUP BY ?pollutant_id
            """)
        tmp = list()
        for row in res:
            el = StructBuilder.get_pollutant_struct(row)
            tmp.append(el)
        return json.dumps(tmp)

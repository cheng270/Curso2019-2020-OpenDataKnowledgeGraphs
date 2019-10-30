
class StructBuilder:

    @staticmethod
    def extract_info_from_uri(uri):
        tmp = uri.split('/')
        return tmp[len(tmp) - 1]

    @staticmethod
    def get_station_struct(rdf_el):
        element = dict()
        element['station_id'] = StructBuilder.extract_info_from_uri(rdf_el['station_id'].toPython())
        element['station_name'] = StructBuilder.extract_info_from_uri(rdf_el['station_name'].toPython())
        element['coordinates'] = {"latitude": rdf_el['latitude'].toPython(), "longitude": rdf_el['longitude'].toPython()}
        element['address'] = rdf_el['address'].toPython()
        element['neighbourhood_name'] = rdf_el['neighbourhood_name'].toPython()
        element['elevation'] = rdf_el['elevation'].toPython()
        return element

    @staticmethod
    def get_pollutant_struct(rdf_el):
        element = dict()
        element['pollutant_id'] = StructBuilder.extract_info_from_uri(rdf_el['pollutant_id'].toPython())
        element['pollutant_name'] = rdf_el['pollutant_name'].toPython()
        element['avg'] = rdf_el['avg'].toPython()
        element['min'] = rdf_el['min'].toPython()
        element['max'] = rdf_el['max'].toPython()
        return element

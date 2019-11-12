import numpy as np
import pandas as pd
from OSMPythonTools.nominatim import Nominatim

nom = Nominatim(waitBetweenQueries=2)
file_name = 'Existing_Bike_Network.csv'

df = pd.read_csv(file_name)

print(df.head())

osm_type_array = []
osm_id_array = []
osm_lat_array = []
osm_lon_array = []
for name in df['STREET_NAM']:
    res = nom.query(name + " Boston").toJSON()
    if len(res) > 0:
        osm_type_array.append(res[0]['osm_type'])
        osm_id_array.append(res[0]['osm_id'])
        osm_lat_array.append(res[0]['lat'])
        osm_lon_array.append(res[0]['lon'])
    else:
        osm_type_array.append("")
        osm_id_array.append("")
        osm_lat_array.append("")
        osm_lon_array.append("")

df['osm_type'] = np.array(osm_type_array).tolist()
df['osm_id'] = np.array(osm_id_array).tolist()
df['lat'] = np.array(osm_lat_array).tolist()
df['lon'] = np.array(osm_lon_array).tolist()

df.to_csv('EBN_final.csv')

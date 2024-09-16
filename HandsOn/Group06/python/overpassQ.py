import overpy
from OSMPythonTools.api import Api
from OSMPythonTools.overpass import Overpass

api = Api()
op = Overpass()

way = api.query('way/290083775')

res = op.query('way(id:29008377);')

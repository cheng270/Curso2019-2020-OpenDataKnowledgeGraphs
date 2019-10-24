from flask import Flask
from flask import render_template
from flask import make_response
from rdflib import Graph

app = Flask(__name__, template_folder="../front-end/templates", static_folder="../front-end/static")
foaf = Graph()
foaf.parse("../../rdf/rdf-with-links.ttl", format="turtle")
print(list(foaf.query("""SELECT DISTINCT ?Concept
                     WHERE {
                     ?x a ?Concept .
                     } LIMIT 1
                     """))[0].Concept)

def query():
    res = foaf.query("""select distinct ?x 
                        WHERE 
                        {
                          ?x a <http://www.airqualitymadrid.es/ontologies/airqualitymadrid#Pollutant> .
                        } LIMIT 100
                        """)
    tmp = list()
    for row in res:
        d = str(row['x'].toPython())
        tmp.append(d)
    return tmp

@app.route('/')
def show_home_page():
    return render_template('index.html')


@app.route("/<test>")
def show_test_page(test):
    data = query()
    print(data)
    return render_template(test + ".html", result=data)


@app.errorhandler(404)
def not_found():
    """404 template."""
    return make_response(render_template("404.html"), 404)


#todo: check how to display errors
@app.errorhandler(500)
def not_found():
    """500 template."""
    return make_response(render_template("404.html"), 500)


if __name__ == '__main__':
    app.run()

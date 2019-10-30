from flask import Flask
from flask import render_template
from flask import make_response
from .data_manager import DataRetriever

app = Flask(__name__, template_folder="../front-end/templates", static_folder="../front-end/static")
retriever = DataRetriever()


@app.route('/')
def show_home_page():
    return render_template('index.html')


@app.route('/stations')
def send_stations():
    return retriever.get_stations()


@app.route('/station_id/<station_id>/start_date/<start_date>/end_date/<end_date>')
def send_measurements_by_id_and_date(station_id, start_date, end_date):
    return retriever.get_measurements_by_id_and_date(station_id, start_date, end_date)


@app.route("/pollutant/<pollutant_id>")
def show_test_page(pollutant_id):
    return retriever.get_pollutant_data(pollutant_id)


# todo: check how to display errors
@app.errorhandler(404)
def not_found():
    """404 template."""
    return make_response(render_template("404.html"), 404)


@app.errorhandler(500)
def not_found():
    """500 template."""
    return make_response(render_template("404.html"), 500)


if __name__ == '__main__':
    app.run()

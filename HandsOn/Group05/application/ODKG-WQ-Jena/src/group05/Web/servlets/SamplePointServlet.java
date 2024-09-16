package group05.Web.servlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;

import group05.Web.model.Determinand;
import group05.Web.model.Measurement;
import group05.Web.model.SamplePoint;


@WebServlet("/SamplePointServlet")
public class SamplePointServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String SP = req.getParameter("SP");

		String wq = "http://group05.com/ontology/Water-Quality#";
		
		String filename = "/Users/xiaoluo/Desktop/ODKG-WQ-Jena/resources/HNL-2018-csv-with-links.ttl";
		
		Model model=ModelFactory.createDefaultModel();
	    model.read(new FileInputStream(filename),null,"TTL");
		
		
		String queryString = 
				"PREFIX wq: <" + wq + "> " +
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
				"SELECT ?Subject ?label (str(?easting) as ?east) (str(?northing) as ?north) "+
				"WHERE {" +
				"?Subject a wq:SamplingPoint ." +
				"OPTIONAL {?Subject rdfs:label ?label} ." +
				"OPTIONAL {?Subject wq:hasEasting ?easting} ." +
				"OPTIONAL {?Subject wq:hasNorthing ?northing} ." +
				"} ";
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
		ResultSet results = qexec.execSelect();
		
		SamplePoint aux = new SamplePoint();
		
		while (results.hasNext())
		{
			QuerySolution binding = results.nextSolution();
			Resource subj = (Resource) binding.get("Subject");
			if (subj.getURI().toString().contentEquals("http://group05.com/resource/sampling-point/"+SP)) {
				String sp = subj.getURI().toString().replace("http://group05.com/resource/sampling-point/", "");
				String label = binding.getLiteral("label").toString();
				int easting = Integer.parseInt(binding.getLiteral("east").toString());
				int northing = Integer.parseInt(binding.getLiteral("north").toString());

				aux.setSP(sp);
				aux.setLabel(label);
				aux.setEasting(easting);
				aux.setNorthing(northing);
			}
		}
		
		queryString = 
				"PREFIX wq: <" + wq + "> " +
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				"PREFIX sp: <http://group05.com/resource/sampling-point/> " +
				"SELECT ?Subject (str(?result) as ?resul) ?materialType ?purpose (str(?dateTime) as ?time) (str(?isCompliance) as ?boole) ?determinand "+
				"WHERE {" +
				"?Subject a wq:Measurement ." +
				"?Subject wq:hasSamplingPoint sp:"+ SP +" ." +
				"?Subject wq:hasResult ?result ." +
				"?Subject wq:hasMaterialType ?materialType ." +
				"?Subject wq:hasPurpose ?purpose ." +
				"?Subject wq:hasDateTime ?dateTime ." +
				"?Subject wq:isComplianceSample ?isCompliance ." +
				"?Subject wq:hasDeterminand ?determinand ." +
				"} ";
		
		query = QueryFactory.create(queryString);
		qexec = QueryExecutionFactory.create(query, model) ;
		results = qexec.execSelect() ;
		
		Collection<Measurement> msmts = new ArrayList<Measurement>();
		
		while (results.hasNext())
		{
			QuerySolution binding = results.nextSolution();
			Resource subj = (Resource) binding.get("Subject");
			String measurement = subj.getURI().replace("http://group05.com/resource/measurement/", "");
			Measurement meas = new Measurement();
			double result = Double.parseDouble(binding.getLiteral("resul").toString());
			String materialType = binding.getLiteral("materialType").toString();
			String purpose = binding.getLiteral("purpose").toString();
			String dateTime = binding.getLiteral("time").toString();
			boolean isCompliance = Boolean.parseBoolean(binding.getLiteral("boole").toString());
			Resource Det = (Resource) binding.get("determinand");
			int det = Integer.parseInt(Det.getURI().replace("http://group05.com/resource/determinand/", ""));
			
			queryString = 
					"PREFIX wq: <" + wq + "> " +
					"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
					"SELECT ?Subject ?label "+
					"WHERE {" +
					"?Subject a wq:Determinand ." +
					"?Subject wq:determinandLabel ?label ." +
					"} ";
			
			String label = null;
			query = QueryFactory.create(queryString);
			qexec = QueryExecutionFactory.create(query, model) ;
			ResultSet aux_results = qexec.execSelect();
			while (aux_results.hasNext()) {
				QuerySolution bind = aux_results.nextSolution();
				Resource sub = (Resource) bind.get("Subject");
				if (sub.getURI().toString().contentEquals("http://group05.com/resource/determinand/"+det)) {
					label = bind.getLiteral("label").toString();
				}
			}
			
			meas.setMeas(measurement);
			meas.setResult(result);
			meas.setMaterialType(materialType);
			meas.setPurpose(purpose);
			meas.setDateTime(dateTime);
			meas.setIsCompliance(isCompliance);
			Determinand dt = new Determinand();
			dt.setNotation(det);
			dt.setLabel(label);
			meas.setDeterminand(dt);
			msmts.add(meas);
		}

		req.setAttribute("SP", aux.getSP());
		req.setAttribute("label", aux.getLabel());
		req.setAttribute("easting", aux.getEasting());
		req.setAttribute("northing", aux.getNorthing());
		req.setAttribute("measurement_list", msmts);
		
		getServletContext().getRequestDispatcher( "/SamplePointView.jsp" ).forward( req, resp );
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}

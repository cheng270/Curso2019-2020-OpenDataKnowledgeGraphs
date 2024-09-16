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


@WebServlet("/DeterminandServlet")
public class DeterminandServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String notation = req.getParameter("notation");
		
		String wq = "http://group05.com/ontology/Water-Quality#";
		
		String filename = "/Users/xiaoluo/Desktop/ODKG-WQ-Jena/resources/HNL-2018-csv-with-links.ttl";
		
		Model model=ModelFactory.createDefaultModel();
	    model.read(new FileInputStream(filename),null,"TTL");
		
		
		String queryString = 
				"PREFIX wq: <" + wq + "> " +
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				"SELECT ?Subject ?label ?definition ?unit ?sameAs "+
				"WHERE {" +
				"?Subject a wq:Determinand ." +
				"OPTIONAL {?Subject wq:determinandLabel ?label} ." +
				"OPTIONAL {?Subject wq:hasDefinition ?definition} ." +
				"OPTIONAL {?Subject wq:hasUnit ?unit} ." +
				"OPTIONAL {?Subject wq:sameAs ?sameAs} ." +
				"} ";
		
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
		ResultSet results = qexec.execSelect() ;
		
		Determinand aux = new Determinand();
		
		while (results.hasNext())
		{
			QuerySolution binding = results.nextSolution();
			Resource subj = (Resource) binding.get("Subject");
			if (subj.getURI().toString().contentEquals("http://group05.com/resource/determinand/"+notation)) {
				String label = binding.getLiteral("label").toString();
				String definition = binding.getLiteral("definition").toString();
				String unit = binding.getLiteral("unit").toString();
				if (binding.getLiteral("sameAs") != null) {
					String sameAs = binding.getLiteral("sameAs").toString();
					aux.setSameAs(sameAs);
				}
				aux.setLabel(label);
				aux.setDefinition(definition);
				aux.setUnit(unit);
				aux.setNotation(Integer.parseInt(notation));
			}
		}
		
		queryString = 
				"PREFIX wq: <" + wq + "> " +
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				"PREFIX dt: <http://group05.com/resource/determinand/> " +
				"SELECT ?Subject (str(?result) as ?resul) ?materialType ?purpose (str(?dateTime) as ?time) (str(?isCompliance) as ?boole) ?determinand ?sp "+
				"WHERE {" +
				"?Subject a wq:Measurement ." +
				"?Subject wq:hasDeterminand dt:"+ notation +" ." +
				"?Subject wq:hasResult ?result ." +
				"?Subject wq:hasMaterialType ?materialType ." +
				"?Subject wq:hasPurpose ?purpose ." +
				"?Subject wq:hasDateTime ?dateTime ." +
				"?Subject wq:isComplianceSample ?isCompliance ." +
				"?Subject wq:hasSamplingPoint ?sp ." +
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
			Resource SP = (Resource) binding.get("sp");
			String sp = SP.getURI().replace("http://group05.com/resource/sampling-point/", "");
			
			meas.setMeas(measurement);
			meas.setResult(result);
			meas.setMaterialType(materialType);
			meas.setPurpose(purpose);
			meas.setDateTime(dateTime);
			meas.setIsCompliance(isCompliance);
			SamplePoint spn = new SamplePoint();
			spn.setSP(sp);
			meas.setSamplePoint(spn);
			msmts.add(meas);
		}

		
		req.setAttribute("notation", notation);
		req.setAttribute("label", aux.getLabel());
		req.setAttribute("definition", aux.getDefinition());
		req.setAttribute("unit", aux.getUnit());
		req.setAttribute("sameAs", aux.getSameAs());
		req.setAttribute("measurement_list", msmts);
		
		getServletContext().getRequestDispatcher( "/DeterminandView.jsp" ).forward( req, resp );
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}

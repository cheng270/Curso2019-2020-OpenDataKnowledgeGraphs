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

import group05.Web.model.SamplePoint;

@WebServlet("/SamplePointListServlet")
public class SamplePointListServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
		ResultSet results = qexec.execSelect() ;
		
		Collection<SamplePoint> samplePoint_final = new ArrayList<SamplePoint>();
		
		while (results.hasNext())
		{
			QuerySolution binding = results.nextSolution();
			Resource subj = (Resource) binding.get("Subject");
			String SP = subj.getURI().replace("http://group05.com/resource/sampling-point/", "");
			SamplePoint aux = new SamplePoint();
			String label = binding.getLiteral("label").toString();
			int easting = Integer.parseInt(binding.getLiteral("east").toString());
			int northing = Integer.parseInt(binding.getLiteral("north").toString());
			
			aux.setLabel(label);
			aux.setEasting(easting);
			aux.setNorthing(northing);
			aux.setSP(SP);
			samplePoint_final.add(aux);
		}

		
		req.setAttribute( "samplePoint_list", samplePoint_final );
		getServletContext().getRequestDispatcher( "/SamplePointListView.jsp" ).forward( req, resp );
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

}

package group05.Web.servlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.vocabulary.VCARD;

import group05.Web.dao.DeterminandDAO;
import group05.Web.dao.DeterminandDAOImplementation;
import group05.Web.model.Determinand;

@WebServlet("/DeterminandListServlet")
public class DeterminandListServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
		
		Collection<Determinand> determinand_final = new ArrayList<Determinand>();
		
		while (results.hasNext())
		{
			QuerySolution binding = results.nextSolution();
			Resource subj = (Resource) binding.get("Subject");
			int notation = Integer.parseInt(subj.getURI().replace("http://group05.com/resource/determinand/", ""));
			Determinand aux = new Determinand();
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
			aux.setNotation(notation);
			determinand_final.add(aux);
		}
		
		req.setAttribute( "determinand_list", determinand_final );
		getServletContext().getRequestDispatcher( "/DeterminandListView.jsp" ).forward( req, resp );	
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}

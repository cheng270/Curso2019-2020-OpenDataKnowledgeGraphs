package upm.oeg.wsld.jena;

import java.io.InputStream;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.VCARD;

/**
 * Task 06: Modifying ontologies (RDFs)
 * @author elozano
 * @author isantana
 *
 */
public class Task06
{
	public static String ns = "http://somewhere#";
	public static String foafNS = "http://xmlns.com/foaf/0.1/";
	public static String foafEmailURI = foafNS+"email";
	public static String foafKnowsURI = foafNS+"knows";
	public static String stringTypeURI = "http://www.w3.org/2001/XMLSchema#string";
	
	public static void main(String args[])
	{
		String filename = "resources/example5.rdf";
		
		// Create an empty model
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);
		
		// Use the FileManager to find the input file
		InputStream in = FileManager.get().open(filename);
	
		if (in == null)
			throw new IllegalArgumentException("File: "+filename+" not found");
	
		// Read the RDF/XML file
		model.read(in, null);
		
		// Create a new class named "Researcher"
		OntClass researcher = model.createClass(ns+"Researcher");
		
		// ** TASK 6.1: Create a new class named "University" **
		OntClass University = model.createClass(ns+"University");
		
		// ** TASK 6.2: Add "Researcher" as a subclass of "Person" **
		OntClass Person = model.createClass(ns+"Person");
		Person.setSubClass(researcher);
		
		// ** TASK 6.3: Create a new property named "worksIn" **
		Property worksIn = model.createProperty(ns+"worksIn");
		
		// ** TASK 6.4: Create a new individual of Researcher named "Jane Smith" **

		String JaneURI = ns+"JaneSmith";
		Individual JaneSmith = researcher.createIndividual(JaneURI);

//		// ** TASK 6.5: Add to the individual JaneSmith the fullName, given and family names **
		String fullName = "Jane Smith";
		Property vacfn = model.createProperty("http://www.w3.org/2001/vcard-rdf/3.0#FN");
		JaneSmith.addLiteral(vacfn,fullName);

		String Family = "Smith";
		Property vacfam = model.createProperty("http://www.w3.org/2001/vcard-rdf/3.0#Family");
		JaneSmith.addLiteral(vacfam,Family);

		String Given = "Smith";
		Property vacgiv = model.createProperty("http://www.w3.org/2001/vcard-rdf/3.0#Given");
		JaneSmith.addLiteral(vacgiv,Given);

//		// ** TASK 6.6: Add UPM as the university where Jane Smith works **\
		String upmURI = ns+"UPM";
		Individual UPM = University.createIndividual(upmURI);
		JaneSmith.addProperty(worksIn,UPM);

		model.write(System.out, "RDF/XML-ABBREV");
	}
}

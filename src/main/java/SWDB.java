

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.BooleanQuery;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.http.HTTPRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class SWDB {

	private String queryResult;

	
	
	public void getGraphDBConnection(String repoURL, String repoId, String sparqlQuery) {
		
		String repoConn = repoURL+repoId;
		
		 // Define the repository URL
		//String repositoryURL = "http://CodeManja:7200/repositories/task2";
		String repositoryURL = repoConn;
		
		System.out.println("Hope connection established.."+repositoryURL);
		
		// Replace with your GraphDB username and password
        String username = "manja";
        String password = "manja";
        
        Repository repository = new HTTPRepository(repositoryURL);
        
		
		//String queryString = "SELECT ?s ?p ?o WHERE {?s ?p ?o}";
        ((HTTPRepository) repository).setUsernameAndPassword(username, password);

        System.out.println("Repo is reader "+repository.toString());
		
		try {
            // Initialise the repository
            repository.init();

            // Open a connection to the repository
            try (RepositoryConnection connection = repository.getConnection()) {
            	System.out.println("Connection Activated..........");
                // SPARQL query to retrieve RDF data
            	 String queryString = sparqlQuery;
            	 StringTokenizer tokenizer = new StringTokenizer(queryString);
            	  
            	 String qCommand = null;
            	 if(tokenizer.hasMoreTokens()) {
            		 qCommand = tokenizer.nextToken().toUpperCase();
            		 System.out.println(qCommand);
            		 System.out.println("TOKENS "+ tokenizer.countTokens());
            	 }
            	
            	// String queryString = "SELECT ?subject ?predicate ?object WHERE {?subject ?predicate ?object}";

            	 if(qCommand.equals("SELECT")) {
                // Prepare the query
                TupleQuery tupleQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL, queryString);

                // Evaluate the query and process the results
                try (TupleQueryResult result = tupleQuery.evaluate()) {
                  /*  while (result.hasNext()) {
                        BindingSet bindingSet = result.next();
                        // Access individual query bindings
                        Value subject = bindingSet.getValue("subject");
                        Value predicate = bindingSet.getValue("predicate");
                        Value object = bindingSet.getValue("object");

                        // Process the retrieved RDF data
                        System.out.println("Subject: " + subject);
                        System.out.println("Predicate: " + predicate);
                        System.out.println("Object: " + object);
                        System.out.println("--------");
                    }*/
                    
                    try {
                        String jsonResult = convertTupleQueryResultToJson(result);
                        queryResult = jsonResult.toString();
                        System.out.println(jsonResult);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
               } 
            	 else if(qCommand.equals("ASK")) {
            		// Prepare the ASK query
                     BooleanQuery booleanQuery = connection.prepareBooleanQuery(QueryLanguage.SPARQL, queryString);
                    boolean result = booleanQuery.evaluate();
                    queryResult = String.valueOf(result);
                    System.out.println("Return boolean "+result);
            		 
            	 }
            }
        } finally {
            // Shut down the repository properly when done
            repository.shutDown();
        }
    }
	
	private static String convertTupleQueryResultToJson(TupleQueryResult tupleQueryResult) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Convert each BindingSet to a Map
        // The Map represents a JSON object
        List<Map<String, String>> resultList = new ArrayList<>();
        while (tupleQueryResult.hasNext()) {
            BindingSet bindingSet = tupleQueryResult.next();
            Map<String, String> resultMap = new HashMap<>();
            bindingSet.forEach(binding -> resultMap.put(binding.getName(), binding.getValue().stringValue()));
            resultList.add(resultMap);
        }

        // Convert the List of Maps to a JSON array
        return objectMapper.writeValueAsString(resultList);
    }


  	
	public void searchForResult(String pathDB, String p1, String p2, String p3) {	
		//connect();
		//getGraphDBConnection();
	   System.out.println("Do query..."+p1 +" "+ p2 + " "+ p3);
		/*
       Model model = RDFDataMgr.loadModel(pathDB) ;
       OntModelSpec ontModelSpec = OntModelSpec.OWL_DL_MEM;
       OntModel ontModel = ModelFactory.createOntologyModel(ontModelSpec, model);

       String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
       		"PREFIX e: <http://example.org/ex>\n" + 
       		"PREFIX b: <http://example.org/ItemOntology>\n" + 
       		"SELECT ?itemId \n" + 
       		"WHERE {  ?item rdf:type b:Item.\n" + 
       		"         ?item b:hasParam01 \""+p1+"\".\n" + 
       		"         ?item b:hasParam02 \""+p2+"\".\n" + 
       		"         ?item b:hasParam03 \""+p3+"\".\n" + 
       		"         ?item b:itemID ?itemId.\n" + 
       		"}";
       */
	   /*
       String queryString_ = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
          		"PREFIX e: <http://example.org/ex>\n" + 
          		"PREFIX b: <http://example.org/ItemOntology>\n" + 
          		"SELECT ?itemId \n" + 
          		"WHERE {  ?item rdf:type b:Item.\n" + 
          		"         ?item b:hasParam01 \""+p1+"\".\n" + 
          		"         ?item b:itemID ?itemId.\n" + 
          		"}";
       
       Dataset dataset = DatasetFactory.create(ontModel);
       Query q = QueryFactory.create(queryString);

       QueryExecution qexec = QueryExecutionFactory.create(q, dataset);
       ResultSet resultSet = qexec.execSelect();
       
       System.out.println("Results: ---");       
       while(resultSet.hasNext()) {
    	   QuerySolution row = (QuerySolution)resultSet.next();
    	   RDFNode nextItemId = row.get("itemId");
    	   System.out.print("ItemID is: "+nextItemId.toString()+".\n"); 
    
       }
       */  
    	   
	} 	
		
	public String getResult(){
		//queryResult = "Done!";
		return queryResult;
	}
}

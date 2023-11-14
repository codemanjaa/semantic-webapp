

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.rdf4j.model.Model;
import java.util.StringTokenizer;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.BooleanQuery;
import org.eclipse.rdf4j.query.GraphQuery;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.QueryResults;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

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
            	 else if((qCommand.equals("CONSTRUCT")) || (qCommand.equals("DESCRIBE")))  {
             		// Prepare the ASK query
            		 GraphQuery graphQuery = connection.prepareGraphQuery(QueryLanguage.SPARQL, queryString);
            		 Model resultModel = QueryResults.asModel(graphQuery.evaluate());
            		 StringWriter writer = new StringWriter();
            		 Rio.write(resultModel, writer, RDFFormat.JSONLD);
            		 queryResult = writer.toString();
                     System.out.println("Return boolean "+queryResult);
             		 
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
    	   
	} 	
		
	public String getResult(){
		//queryResult = "Done!";
		return queryResult;
	}
}



import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.http.HTTPRepository;







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
                String queryString = "SELECT ?subject ?predicate ?object WHERE {?subject ?predicate ?object}";

                // Prepare the query
                TupleQuery tupleQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL, queryString);

                // Evaluate the query and process the results
                try (TupleQueryResult result = tupleQuery.evaluate()) {
                    while (result.hasNext()) {
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
                    }
                }
            }
        } finally {
            // Shut down the repository properly when done
            repository.shutDown();
        }
    
	

      /*   
	    // Create a GraphDB HTTP repository
        Repository repository = new HTTPRepository(repositoryURL);
        try {
            // Initialize the repository
            repository.init();

            // Open a connection to the repository
            try (RepositoryConnection connection = repository.getConnection()) {
                // SPARQL query example
                String queryString = "SELECT ?subject ?predicate ?object WHERE { ?subject ?predicate ?object } LIMIT 10";
                TupleQuery tupleQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL, queryString);

                try (TupleQueryResult result = tupleQuery.evaluate()) {
                    while (result.hasNext()) {
                        // Process query results
                        System.out.println(result.next());
                    }
                }
            }
        } finally {
            // Shutdown the repository
            repository.shutDown();
        }
        
        */
    }


		/*
		RepositoryManager repositoryManager =
		        new RemoteRepositoryManager(repositoryURL);
		repositoryManager.init();
		System.out.println("Hope connection established..."+repositoryManager.getRepositoryIDs());
		
		String queryString = "SELECT ?s ?p ?o WHERE {?s ?p ?o}";
		
		*/
	

      
		
		// Create an HTTPRepository to connect to the GraphDB repository
		/*
		Repository repository = new HTTPRepository(repositoryURL);
		
        try {
            // Initialize the repository
            repository.init();
            RepositoryConnection connection = repository.getConnection();
            System.out.println("Connection established"+connection);
            } catch (RepositoryException | MalformedQueryException | QueryEvaluationException e) {
            e.printStackTrace();
        }
        
        
           /*
            // Get a connection
          
            //connection = repository.getConnection();
         

         // SPARQL query
            String queryString = "SELECT ?s ?p ?o WHERE {?s ?p ?o}";

            // Prepare the SPARQL query
            TupleQuery tupleQuery = connection.prepareTupleQuery(queryString);

            // Execute the query
            TupleQueryResult queryResult = tupleQuery.evaluate();

            // Iterate through the results and print them
            while (queryResult.hasNext()) {
                BindingSet bindingSet = queryResult.next();
                Value subject = bindingSet.getValue("s");
                Value predicate = bindingSet.getValue("p");
                Value object = bindingSet.getValue("o");
                System.out.println("Subject: " + subject);
                System.out.println("Predicate: " + predicate);
                System.out.println("Object: " + object);
            }

        } catch (RepositoryException | MalformedQueryException | QueryEvaluationException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        */
 
	
	/*
	
	public  void connect() {
		
		System.out.println("Connection initiating.....");		  
		// RDF4J repository URL (SPARQL endpoint)
        String repositoryURL = "http://CodeManja:7200/repositories/task2";
        
        
        // Create an HTTP client
       // HttpClient httpClient = HttpClients.createDefault();
        HttpClient httpClient = new DefaultHttpClient();
        
        // Create an HTTP GET request to the repository URL
        HttpGet request = new HttpGet(repositoryURL);

        System.out.println("Connection established");
        try {
            // Execute the request and get the response
        	  HttpResponse response = httpClient.execute(request);
            
            // Process the response, e.g., read and parse RDF data
            // ...
        	  int statusCode = response.getStatusLine().getStatusCode();
              if (statusCode == 200) {
                  // Successful connection
                  HttpEntity entity = response.getEntity();
                  String responseText = EntityUtils.toString(entity);
                  System.out.println("Connection successful. Response: " + responseText);
              } else {
                  // Connection failed
                  System.err.println("Connection failed. Status code: " + statusCode);
              }
          } catch (Exception e) {
              e.printStackTrace();
          }
   }
  
	*
	*/
	
	
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
		queryResult = "Done!";
		return queryResult;
	}
}

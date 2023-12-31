

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class Booking
 */
@WebServlet("/Logging")
public class Logging extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logging() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		SWDB mediator = new SWDB();
		//mediator.getGraphDBConnection();
		if(request.getParameter("reqType").toString().equals("doQuery")){
			
			String repoURL = request.getParameter("repoURL").toString();
			String repoId = request.getParameter("repoId").toString();
			String sparqlQuery = request.getParameter("sparqlQuery").toString();
			String pathToDB = this.getServletContext().getRealPath("/res/BookingDB.ttl");
			//mediator.searchForResult(pathToDB, repoURL, repoId, sparqlQuery);
			mediator.getGraphDBConnection(repoURL, repoId, sparqlQuery);

	    }
		
		PrintWriter out = response.getWriter();
		out.write(mediator.getResult());  
		out.flush();
	    out.close();
		
		
		
		
	}

}

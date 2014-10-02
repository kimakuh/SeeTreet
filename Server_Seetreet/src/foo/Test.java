package foo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

/**
 * Servlet implementation class Test
 */

public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Test() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		
		response.setContentType("text/html;charset=utf8");
		System.out.println(">>>>>>.");
		
		String path = getInitParameter("path");
		System.out.println(path);		
		
		String savePath = getServletContext().getInitParameter("savePath");
		System.out.println(savePath);
		
		
		MongoClient dbClient = new MongoClient("localhost" , 27017);
		DB db = dbClient.getDB("tutorial");
		
		Set<String> cols= db.getCollectionNames();
		for(String s : cols) {
			System.out.println(s);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		System.out.println(">> init 불리움");
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		System.out.println(">> destory 콜");
	}

	@PostConstruct
	public void postContruct() {
		System.out.println("post");
	}
	
	@PreDestroy
	public void clean() {
		System.out.println("clean");
	}
}

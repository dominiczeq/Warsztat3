package pl.coderslab.warsztat3.controler;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.warsztat3.model.Solution;
import pl.coderslab.warsztat3.model.SolutionWithAuthor;

/**
 * Servlet implementation class Home
 */
@WebServlet("/")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Home() {
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		int count = Integer.parseInt(getServletContext().getInitParameter("number-solutions"));
		
		List<SolutionWithAuthor> solutions = Solution.loadAllWithAuthor(count);
		request.setAttribute("solutions", solutions);
		getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

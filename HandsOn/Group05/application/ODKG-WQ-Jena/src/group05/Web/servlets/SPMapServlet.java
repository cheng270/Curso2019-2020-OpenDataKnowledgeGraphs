package group05.Web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import group05.Web.dao.SamplePointDAO;
import group05.Web.dao.SamplePointDAOImplementation;


@WebServlet("/SPMapServlet")
public class SPMapServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SamplePointDAO sdao = SamplePointDAOImplementation.getInstance();
		
		double default_lat = 51.72;
		double default_lng = -0.22;
		int default_scale = 9;
		req.setAttribute( "samplePoint_list", sdao.readAll() );
		req.setAttribute("default_lat", default_lat);
		req.setAttribute("default_lng", default_lng);
		req.setAttribute("default_scale", default_scale);
		getServletContext().getRequestDispatcher( "/MapView.jsp" ).forward( req, resp );
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}

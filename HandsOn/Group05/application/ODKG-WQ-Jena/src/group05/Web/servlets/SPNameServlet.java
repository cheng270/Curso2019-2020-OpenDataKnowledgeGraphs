package group05.Web.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import group05.Web.dao.SamplePointDAO;
import group05.Web.dao.SamplePointDAOImplementation;
import group05.Web.model.SamplePoint;


@WebServlet("/SPNameServlet")
public class SPNameServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SamplePointDAO sdao = SamplePointDAOImplementation.getInstance();
		
		String name = req.getParameter("name");
		Collection<SamplePoint> samplePoints = sdao.readAll();
		Collection<SamplePoint> sp_final = new ArrayList<SamplePoint>();
		
		double lat = 51.6949;
		double lng = -0.1491;
		int scale = 9;
		
		for (SamplePoint sp: samplePoints) {
			if (sp.getLabel().toLowerCase().contains(name.toLowerCase())) {
				sp_final.add(sp);
			}
		}
		
		req.setAttribute("samplePoint_list", sp_final);
		req.setAttribute("default_lat", lat);
		req.setAttribute("default_lng", lng);
		req.setAttribute("default_scale", scale);
		
		getServletContext().getRequestDispatcher( "/MapView.jsp" ).forward( req, resp );
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}

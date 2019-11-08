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


@SuppressWarnings("serial")
@WebServlet("/SPSearchServlet")
public class SPSearchServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SamplePointDAO sdao = SamplePointDAOImplementation.getInstance();
		
		String SP = req.getParameter("SP");
		int distance = Integer.parseInt(req.getParameter("distance"));
		SamplePoint samplePoint = sdao.read(SP);
		
		double lat = samplePoint.getLat();
		double lng = samplePoint.getLng();
		int easting = samplePoint.getEasting();
		int northing = samplePoint.getNorthing();
		int scale = 18;
		if (distance <= 156) {
			scale = 17;
		} else if (distance <= 312) {
			scale = 16;
		} else if (distance <= 625) {
			scale = 15;
		} else if (distance <= 1250) {
			scale = 14;
		} else if (distance <= 2500) {
			scale = 13;
		} else if (distance <= 5000) {
			scale = 12;
		} else if (distance <= 10000) {
			scale = 11;
		} else if (distance <= 20000) {
			scale = 10;
		} else if (distance <= 40000) {
			scale = 9;
		} else if (distance <= 80000) {
			scale = 8;
		} else if (distance <= 160000) {
			scale = 7;
		} else {
			scale = 6;
		}
		
		
		Collection<SamplePoint> total = sdao.readAll();
		Collection<SamplePoint> sp_final = new ArrayList<SamplePoint>();
		
		for (SamplePoint sp: total) {
			if ( Math.sqrt( Math.pow((sp.getEasting()-easting), 2) + Math.pow(sp.getNorthing()-northing, 2)    )  < distance  ) {
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

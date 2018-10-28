
package coreservlets;

import adapter.Debuggable;
import model.*;
import client.DefaultSocketClient;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/ModelAttributeSelection")
public class ModelAttributeSelection extends HttpServlet implements Debuggable {
	
	////////// PROPERTIES //////////
	
	
	////////// INSTANCE METHODS //////////
	
	@Override
	public void init(ServletConfig config) {
		
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		DefaultSocketClient client = new DefaultSocketClient("192.168.1.207", 6666);
		Automobile a1 = client.getAutomobile(request.getParameter("choice"));
		HttpSession session = request.getSession();
		session.setAttribute("automobile", a1);		
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<!DOCTYPE html>\n"
				+ "<html>\n"
				+ "<head><title> Car Configuration </title></head>\n"
				+ "<h1 align=\"center\"> Car Configuration Tool </h1>\n"
				+ "<body>\n"
				+ "<p align=\"center\"> Configure your new car </p>\n"
				+ "<form method=\"post\" action=\"ModelSelectionReport.jsp\">\n");
		out.println("<table border=\"1\" align=\"center\">\n"
				+ "<tbody>\n"
					+ "<tr>\n"
						+ "<td> Automobile </td>\n"
						+ "<td> " + a1.getMakeModelYear() + "</td>\n"
					+ "</tr>\n");
		for (int i = 0; i < a1.getOptSetLength(); i++) {
			out.println(""
					+ "<tr>\n"
						+ "<td> " + a1.getOptSetName(i) + "</td>\n"
						+ "<td><select name=\"" + a1.getOptSetName(i) +"\">\n");
			for (int j = 0; j < a1.getOptLength(i); j++) {
				out.println("<option value=\"" + j + "\">" + a1.getOptName(i, j) + "</option>\n");
			}
			out.println(""
						+ "</select></td>\n"
					+ "</tr>\n"
				+ "</tbody>\n");
		}
		out.println("</table>\n"
				+ "<p align=\"center\"><br/><input type=submit value=\"Submit\"></p>\n"
				+ "</form></body></html>\n");

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request, response);
	}
	
}

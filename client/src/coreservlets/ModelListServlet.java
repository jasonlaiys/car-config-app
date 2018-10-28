
package coreservlets;

import adapter.Debuggable;
import client.DefaultSocketClient;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/ModelListServlet")
public class ModelListServlet extends HttpServlet implements Debuggable {
	
	////////// PROPERTIES //////////
	
	
	////////// INSTANCE METHODS //////////
	
	@Override
	public void init(ServletConfig config) {
		
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		DefaultSocketClient client = new DefaultSocketClient("192.168.1.207", 6666);
		String[] models = client.getModels();
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<!DOCTYPE html>\n"
				+ "<html>\n"
				+ "<head><title> Car Configuration </title></head>\n"
				+ "<h1 align=\"center\"> Car Configuration Tool </h1>\n"
				+ "<h2 align=\"center\"> Select an automobile </h2>\n"
				+ "<body>\n"
				+ "<form method=\"post\" action=\"ModelAttributeSelection\">\n"
				+ "<p align=\"center\">\n");
		for (int i = 0; i < models.length; i++) {
			out.println("<input type=\"radio\" name=\"choice\" value=\"" + (i + 1) + "\"> " + models[i] + "<br>\n");
		}
		out.println(""
				+ "</p>\n"
				+ "<p align=\"center\"><br><input type=submit value=\"Submit\"></p>\n"
				+ "</form></body></html>");
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request, response);
	}
	
}

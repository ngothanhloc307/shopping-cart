package com.techtutorial.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import com.techtutorial.connection.DbConnect;
import com.techtutorial.dao.OrderDao;

public class CancelOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CancelOrderServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try(PrintWriter out = response.getWriter()){
			String id = request.getParameter("id");
			if(id != null) {
				OrderDao orderDao = new OrderDao(DbConnect.getConnection());
				orderDao.cancelOrder(Integer.parseInt(id));
				response.sendRedirect("order.jsp");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

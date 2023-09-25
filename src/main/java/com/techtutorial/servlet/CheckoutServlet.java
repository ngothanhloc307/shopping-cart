package com.techtutorial.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.techtutorial.connection.DbConnect;
import com.techtutorial.dao.OrderDao;
import com.techtutorial.model.Cart;
import com.techtutorial.model.Order;
import com.techtutorial.model.User;

public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CheckoutServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (PrintWriter out = response.getWriter()) {
			SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();

			// retrive all products
			ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
			// user authentication
			User auth = (User) request.getSession().getAttribute("auth");
			// check auth and cart list
			if (cart_list != null && auth != null) {
				for (Cart c : cart_list) {
					Order order = new Order();
					order.setId(c.getId());
					order.setUid(auth.getId());
					order.setQuantity(c.getQuantity());
					order.setDate(dateformatter.format(date));
					
					OrderDao oDao = new OrderDao(DbConnect.getConnection());
					boolean result =  oDao.insertOrder(order);
					if(!result) {
						break;
					}
				}
				cart_list.clear();
				response.sendRedirect("order.jsp");
				
			} else {
				if (auth == null) {
					response.sendRedirect("login.jsp");
					response.sendRedirect("cart.jsp");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

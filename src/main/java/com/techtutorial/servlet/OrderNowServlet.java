package com.techtutorial.servlet;

import jakarta.servlet.ServletException;
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

public class OrderNowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (PrintWriter out = response.getWriter()) {
			SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");

			Date date = new Date();

			User auth = (User) request.getSession().getAttribute("auth");
			if (auth != null) {
				String productId = request.getParameter("id");
				int productQuantity = Integer.parseInt(request.getParameter("quantity"));
				if (productQuantity <= 0) {
					productQuantity = 1;
				}

				Order orderModel = new Order();
				orderModel.setId(Integer.parseInt(productId));
				orderModel.setUid(auth.getId());
				orderModel.setQuantity(productQuantity);
				orderModel.setDate(dateformatter.format(date));

				OrderDao orderDao = new OrderDao(DbConnect.getConnection());
				boolean result = orderDao.insertOrder(orderModel);

				   if (result) {
	                    ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
	                    if (cart_list != null) {
	                        for (Cart c : cart_list) {
	                            if (c.getId() == Integer.parseInt(productId)) {
	                                cart_list.remove(cart_list.indexOf(c));
	                                break;
	                            }
	                        }
	                    }

					response.sendRedirect("order.jsp");
				} else {
					out.print("order failed");
				}
			} else {
				response.sendRedirect("login.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

package com.techtutorial.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.techtutorial.model.Cart;

@WebServlet("/add-to-cart")
public class AddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;chartset=UTF-8");

		try (PrintWriter out = response.getWriter()) {
			ArrayList<Cart> cartList = new ArrayList<>();

			int id = Integer.parseInt(request.getParameter("id"));
			Cart cart = new Cart();
			cart.setId(id);
			cart.setQuantity(1);

			HttpSession session = request.getSession();
			ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");

			if (cart_list == null) {
				cartList.add(cart);
				session.setAttribute("cart-list", cartList);
				 response.sendRedirect("index.jsp");
			} else {
				cartList = cart_list;
				boolean exist = false;

				for (Cart c : cartList) {
					if (c.getId() == id) {
						exist = true;
						out.print(
								"<h3 style='color:crimson; text-align:center'> Item already exist in Cart. <a href='cart.jsp'>Go to Cart Page</a></h3>");
					}

				}

				if (!exist) {
					cartList.add(cart);
					response.sendRedirect("index.jsp");
				}

				/*
				 * for (Cart c : cartList) { out.println(c.getId()); }
				 */

			}
		}
	}

}

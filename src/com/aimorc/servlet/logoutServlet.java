package com.aimorc.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/LogoutServlet")
public class logoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public logoutServlet() {
        super();
   
    }

	
@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
                 response.setContentType("text/html");
				HttpSession session = request.getSession(false);
					if (session != null) {
						session.invalidate();
                System.out.println("logged out successfully");
						try
						{
						RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
						  dispatcher.forward(request, response);
						}catch(ServletException | IOException e)
						{
							e.printStackTrace();
						}
					}

				}

	}

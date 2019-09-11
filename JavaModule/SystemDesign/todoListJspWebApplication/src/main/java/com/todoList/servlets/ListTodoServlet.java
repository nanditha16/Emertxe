package com.todoList.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.todoList.services.TodoService;

@WebServlet(urlPatterns = "/list-todos.do")
public class ListTodoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7036063502728556135L;
	private TodoService todoService = new TodoService();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("todos", todoService.retrieveTodos());
		request.getRequestDispatcher("/views/list-todos.jsp").forward(
				request, response);
	}
}
package com.todoList.services;

public class LoginService {

	public boolean isUserValid(String user, String password) {
		if (user.equals("admin") && password.equals("admin"))
			return true;

		return false;
	}

}

package com.dips.demo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetCookie
 */
public class GetCookie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCookie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Cookie myCookie[]=request.getCookies();
		boolean flag=false;
		if(myCookie!=null){
		for(int i=0;i<myCookie.length;i++){
			if(myCookie[i].getName().equals("username")){
				FileExt fe=new FileExt();
				fe.setPath("FileServer");
				System.out.println("cookie name:"+myCookie[i].getValue());
				fe.setName(myCookie[i].getValue());
				request.setAttribute("FileName","");
				request.getSession().setAttribute("FileExt", fe);
				response.sendRedirect("GetFile");
				request.getSession().setAttribute("title",myCookie[i].getValue());
				flag=true;
			}
			}
		}
		if(!flag){
		response.sendRedirect("index.jsp");}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

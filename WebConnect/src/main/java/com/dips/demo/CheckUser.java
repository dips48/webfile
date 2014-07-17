package com.dips.demo;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;

import com.dips.db.DBconnect;

/**
 * Servlet implementation class CheckUser
 */
public class CheckUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String str=request.getParameter("username");
		String pwd=request.getParameter("password");
		if(str.equals("")|pwd.equals("")){
			response. sendRedirect ("login.jsp") ;
			return;
		}
		if(DBconnect.CheckUser(str, pwd)){
			FileExt fe=new FileExt();
			fe.setPath("FileServer");
			fe.setName(str);
			request.setAttribute("FileName","");
			request.getSession().setAttribute("FileExt", fe);
			request.getSession().setAttribute("title",str);
			response.sendRedirect("GetFile");
		}else{
			response. sendRedirect ("login.jsp") ;	
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

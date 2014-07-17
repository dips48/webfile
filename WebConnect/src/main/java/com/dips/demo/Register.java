package com.dips.demo;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;

import com.dips.db.DBconnect;

/**
 * Servlet implementation class Register
 */
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String str=""+request.getParameter("username");
		String pwd=""+request.getParameter("password");
		if(str.equals("")|pwd.equals("")){
			response. sendRedirect ("register.jsp") ;
			return;
		}
		System.out.println("½øÈë×¢²á¼ì²é");
		if(DBconnect.InsertUser(str, pwd)){
		Cookie cookie_name=new Cookie("username",str);
		response.addCookie(cookie_name);
		FileUpload fu=new FileUpload();
		fu.setFilePath("FileServer");
		fu.setFileName(str);
		fu.setOperation(1);
		fu.setContent(" ");
		
		SocketChannel sc=SocketChannel.open();
		sc.connect(new InetSocketAddress(2001));
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		ObjectOutputStream oout=new ObjectOutputStream(out);
		oout.writeObject(fu);
		oout.flush();
		byte[] obArray=out.toByteArray();
		ByteBuffer buf=ByteBuffer.allocate(obArray.length);
		buf.put(obArray);
		buf.flip();
		sc.write(buf);
		sc.close();
		
		FileExt fe=new FileExt();
		fe.setPath(fu.getFilePath());
		fe.setName(fu.getFileName());
		fe.setContent("");
		request.getSession().setAttribute("title",str);
		request.getSession().setAttribute("FileExt", fe);
		response. sendRedirect ("home.jsp") ;}else{
			response. sendRedirect ("register.jsp") ;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

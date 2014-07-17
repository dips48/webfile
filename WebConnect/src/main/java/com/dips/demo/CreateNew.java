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

/**
 * Servlet implementation class CreateNew
 */
public class CreateNew extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateNew() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		FileExt fe=(FileExt)request.getSession().getAttribute("FileExt");
		String str=fe.getName();
		FileUpload fu=new FileUpload();
		fu.setFilePath(fe.getPath()+File.separator+fe.getName());
		fu.setFileName(request.getParameter("bt"));
		fu.setOperation(1);
		fu.setContent("  "+request.getParameter("content"));
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
		fe=new FileExt();
		fe.setPath(fu.getFilePath());
		fe.setName(fu.getFileName());
		fe.setContent(fu.getContent());
		request.getSession().setAttribute("FileExt", fe);
		response. sendRedirect ("home.jsp") ;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

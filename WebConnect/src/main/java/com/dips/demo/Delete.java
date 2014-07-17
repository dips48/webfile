package com.dips.demo;

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
 * Servlet implementation class Delete
 */
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Delete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		FileUpload fu=new FileUpload();
		fu.setOperation(3);
		FileExt fe=(FileExt)request.getSession().getAttribute("FileExt");
		fu.setFilePath(fe.getPath());
		fu.setFileName(fe.getName());
		
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
		request.setAttribute("FileName", null);
		response.sendRedirect("GetFile");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

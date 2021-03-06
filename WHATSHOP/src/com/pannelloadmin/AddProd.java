package com.pannelloadmin;

import com.bean.CategoriaBean;
import com.bean.NegozioBean;
import java.io.File;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;




/**
 * Servlet implementation class AddProd
 */
@WebServlet("/AddProd")

public class AddProd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddProd() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub


	}

	private static final String SAVE_DIR = "img/prod";
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try{

			String applicationPath = request.getServletContext().getRealPath("");
			// constructs path of the directory to save uploaded file
			String uploadFilePath = applicationPath + SAVE_DIR;

			// creates the save directory if it does not exists
			File fileSaveDir = new File(uploadFilePath);

			String fileName = null;
			if (!fileSaveDir.exists()) {
				fileSaveDir.mkdirs();
			}

			//Get all the parts from request and write it to the file on server
			Part part = request.getPart("imgProd");
			fileName = getFileName(part);
			part.write(uploadFilePath + File.separator + fileName);



			String nomeProd = (String) request.getParameter("nomeProd");
			String prezzoProd = (String) request.getParameter("prezzoProd");
			String descProd = (String) request.getParameter("descProd");
			String qtProd = (String) request.getParameter("qtProd");
			String nomeNeg = (String) request.getParameter("selNeg");
			String nomeCat = (String) request.getParameter("selCat");


			AddProdModel connessione = new AddProdModel();
			CategoriaBean cat = new CategoriaBean();
			NegozioBean neg = new NegozioBean();

			try {
				if(nomeProd != null && descProd != null){
					
					int categoriaIdProd = cat.getIdCat(nomeCat);
					int negozioIdProd = neg.getIdNeg(nomeNeg);
					Double prezzoProdD = Double.parseDouble(prezzoProd);
					int qtProdI = Integer.parseInt(qtProd);
					connessione.addProd(nomeProd, prezzoProdD, true, qtProdI, "img/prod/"+fileName, categoriaIdProd, negozioIdProd, descProd);
					
					request.setAttribute("msg", "ok");
					RequestDispatcher rd = request.getRequestDispatcher("Admin");
					rd.forward(request, response);
				
				}else {
					request.setAttribute("msg", "error");
					RequestDispatcher rd = request.getRequestDispatcher("Admin");
					rd.forward(request, response);
				}
			
			} catch (Exception a) {
				request.setAttribute("msg", "error");
				RequestDispatcher rd = request.getRequestDispatcher("Admin");
				rd.forward(request, response);
			}
		}
		catch (IOException e){
			
			
			String nomeProd = (String) request.getParameter("nomeProd");
			String prezzoProd = (String) request.getParameter("prezzoProd");
			String descProd = (String) request.getParameter("descProd");
			String qtProd = (String) request.getParameter("qtProd");
			String nomeNeg = (String) request.getParameter("selNeg");
			String nomeCat = (String) request.getParameter("selCat");

			
			AddProdModel connessione = new AddProdModel();
			CategoriaBean cat = new CategoriaBean();
			NegozioBean neg = new NegozioBean();
			
			try {
				if(nomeProd != null && descProd != null){
					
					int categoriaIdProd = cat.getIdCat(nomeCat);
					int negozioIdProd = neg.getIdNeg(nomeNeg);
					Double prezzoProdD = Double.parseDouble(prezzoProd);
					int qtProdI = Integer.parseInt(qtProd);
					connessione.addProd(nomeProd, prezzoProdD, true, qtProdI, "img/prod/noimage.png", categoriaIdProd, negozioIdProd, descProd);
					
					request.setAttribute("msg", "ok");
					RequestDispatcher rd = request.getRequestDispatcher("Admin");
					rd.forward(request, response);
				
				} else {
					request.setAttribute("msg", "error");
					RequestDispatcher rd = request.getRequestDispatcher("Admin");
					rd.forward(request, response);
				}
			
			} catch (Exception a) {
				request.setAttribute("msg", "error");
				RequestDispatcher rd = request.getRequestDispatcher("Admin");
				rd.forward(request, response);
			}
			
			
			
			
			
			


		}
	}
	/**
	 * Extracts file name from HTTP header content-disposition
	 */
	private String getFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");

		String[] tokens = contentDisp.split(";");
		for (String token : tokens) {
			if (token.trim().startsWith("filename")) {
				return token.substring(token.indexOf("=") + 2, token.length()-1);
			}
		}
		return "";
	}


}


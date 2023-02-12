package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class UpdateProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
					
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		System.out.println("updateAction내부로 prodNo오니?"+prodNo);
		
		Product product = new Product();
				
		product.setProdNo(prodNo);
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(request.getParameter("manuDate"));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setFileName(request.getParameter("fileName"));
		product.setStock(Integer.parseInt(request.getParameter("stock")));	

		ProductService productService = new ProductServiceImpl();
		productService.updateProduct(product);
		product = productService.getProduct(prodNo);
		
		boolean updateChecker = true;
		String menu = request.getParameter("menu");
		request.setAttribute("updateChecker", updateChecker);
		
		// TODO navigating 방식 및 URI체크
		return "foward:/getProduct.do?prodNo=" + prodNo + "&menu="+menu;
	}

}

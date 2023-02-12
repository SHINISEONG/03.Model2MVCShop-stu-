package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class AddPurchaseAction extends Action {

	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ProductService productService = new ProductServiceImpl();
		HttpSession session = request.getSession(true);
		Purchase purchase=new Purchase();
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("get param prodNo: " + prodNo);
		Product product = productService.getProduct(prodNo);
		product.setStock(product.getStock()-Integer.parseInt(request.getParameter("quantity")));
		productService.updateProduct(product);
		User user = (User)session.getAttribute("user");
		
		System.out.println("addPurchaseAct로 불러온 prod: " + product);
		System.out.println("session에 세팅된 userVO in AddPurAct : " + user);
		
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("receiverDate"));
		purchase.setQuantity(Integer.parseInt(request.getParameter("quantity")));
		purchase.setTranCode("1");
		
		System.out.println("addPurchaseAction안에서 모든 값 잘 세팅 됐니?"+purchase);
		
		
		PurchaseService purchaseService=new PurchaseServiceImpl();
		purchaseService.addPurchase(purchase);
		request.setAttribute("purchase", purchase);
		return "forward:/purchase/addPurchase.jsp";
	}

}

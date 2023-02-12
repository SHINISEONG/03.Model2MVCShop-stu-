package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdatePurchaseAction extends Action {

	public UpdatePurchaseAction() {
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		Purchase purchase = new Purchase();
		Product product = null;
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		
		purchase.setTranNo(tranNo);
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("divyDate"));
		purchase.setQuantity(Integer.parseInt(request.getParameter("quantity")));
		purchaseService.updatePurchase(purchase);
		
		ProductService productService = new ProductServiceImpl();
		int stock = 0;
		
		product = productService.getProduct(Integer.parseInt(request.getParameter("prodNo")));
		stock = product.getStock() - (Integer.parseInt(request.getParameter("quantity"))-Integer.parseInt(request.getParameter("originalPurchaseQuantity")));
		product.setStock(stock);
		productService.updateProduct(product);
		
		purchase = purchaseService.findPerchase(tranNo);
		request.setAttribute("purchase", purchase);
		
		return "forward:/purchase/getPurchase.jsp";
	}

}

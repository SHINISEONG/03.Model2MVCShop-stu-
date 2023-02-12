package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdatePurchaseViewAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int tranNo= Integer.parseInt(request.getParameter("tranNo"));
		
		Purchase purchase = null;
		Product product = null;
		PurchaseService purchaseService = new PurchaseServiceImpl();
		
		purchase = purchaseService.findPerchase(tranNo);
		product = purchase.getPurchaseProd();
		
		request.setAttribute("purchase", purchase);
		request.setAttribute("product", product);
		
		return "forward:/purchase/updatePurchaseView.jsp";
	}

}

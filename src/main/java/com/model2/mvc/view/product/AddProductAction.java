package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class AddProductAction extends Action {

	public AddProductAction() {
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*ProductVO productVO = request.getAttribute("productVO");
		if()*/
		Product product=new Product();
		String prodManuDateSQL = request.getParameter("manuDate").replaceAll("-", "");
		 
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(prodManuDateSQL);
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setFileName(request.getParameter("fileName"));
		
		
		System.out.println(product);
		request.setAttribute("product", product);
		ProductService service=new ProductServiceImpl();
		service.addProduct(product);
		
		//TODO �Ʒ� resultPage navigating ��İ� URIȮ�� ö���� �ϱ� 
		return "forward:/product/addProductView.jsp";
		// ?���� ���� ��� �Ǵ���? <-- ���� : ��üũ�� view���������� js�� �ϰ� ��ǰ ��ȸ�� ��ǰ ��ȣ�� where������ �ɾ� ������ �ϹǷ� ������ 1�� �����⶧���� ���� 
	}// end of AddProduct execute()

}

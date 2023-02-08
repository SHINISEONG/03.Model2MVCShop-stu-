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
		
		//TODO 아래 resultPage navigating 방식과 URI확인 철저히 하기 
		return "forward:/product/addProductView.jsp";
		// ?쿼리 실행 결과 판단은? <-- 추측 : 널체크를 view페이지에서 js로 하고 상품 조회후 상품 번호로 where조건을 걸어 쿼리를 하므로 무조건 1이 나오기때문에 생략 
	}// end of AddProduct execute()

}

package com.model2.mvc.view.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class ListProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Search search = new Search();

		int currentPage = 1;
		if (request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}

		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		int searchMinPrice = 0;
		int searchMaxPrice = 0;
		
		if (request.getParameter("searchMinPrice") != null) {
			searchMinPrice = Integer.parseInt(request.getParameter("searchMinPrice"));
		}
		
		if	(request.getParameter("searchMaxPrice") != null) {
			searchMaxPrice = Integer.parseInt(request.getParameter("searchMaxPrice"));
		}
		
		System.out.println("최소가 : "+searchMinPrice+"최대가 : "+searchMaxPrice);
		
		if (searchMaxPrice < searchMinPrice) {
			int tmp=0;
		
			tmp = searchMaxPrice;
			searchMaxPrice = searchMinPrice;
			searchMinPrice = tmp;
		}
		
		System.out.println(":: 스위칭 후 최소가 : "+searchMinPrice+"최대가 : "+searchMaxPrice);
		
		search.setSearchMinPrice(searchMinPrice);
		search.setSearchMaxPrice(searchMaxPrice);
		
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));  //servletcontext를 각 액션에 부여한 이유.
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		search.setPageUnit(pageUnit);		
		
		System.out.println(search);
		
		ProductService productService = new ProductServiceImpl();
		Map<String, Object> map = productService.getProductList(search);
		
		Page resultPage = new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		

		return "forward:/product/listProduct.jsp";
	}

}

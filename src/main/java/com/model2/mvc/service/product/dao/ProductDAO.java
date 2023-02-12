package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;

public class ProductDAO {
	/// field
	/// constructor
	public ProductDAO() {
	}

	/// method
	public void insertProduct(Product productVO) throws Exception {
		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO product VALUES(seq_product_prod_no.NEXTVAL, ?, ?, ?, ?, ?, sysdate)";
		
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.executeUpdate();
		
		sql = "INSERT INTO stock VALUES(seq_product_prod_no.CURRVAL, ?)";
		stmt = con.prepareStatement(sql);
		stmt.setInt(1, productVO.getStock());
		stmt.executeUpdate();
		
		con.close();
	}// end of insertProduct()

	public Product findProduct(int prodNo) throws Exception {
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT p.*, NVL(s.stock,0) stock, NVL(t.tran_status_code,0) tran_code, NVL(t.tran_no,0) tran_no "
				+ 									" FROM product p, transaction t, stock s "
				+ 									" WHERE p.prod_no = t.prod_no(+) "
				+									"  AND p.prod_no = s.prod_no(+) "
				+ 									"  AND p.prod_no = ? ");
		
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();	
		
		Product productVO = null;
		
		while (rs.next()) {
			productVO = new Product();
			productVO.setProdNo(prodNo);
			
			productVO.setProdName(rs.getString("PROD_NAME"));
			productVO.setFileName(rs.getString("IMAGE_FILE"));
			productVO.setProdDetail(rs.getString("PROD_DETAIL"));
			productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
			productVO.setPrice(rs.getInt("PRICE"));
			productVO.setRegDate(rs.getDate("REG_DATE"));
			productVO.setProTranCode(rs.getString("tran_code").trim());
			productVO.setProTranNo(rs.getInt("TRAN_NO"));
			productVO.setStock(rs.getInt("STOCK"));
		}
		
		rs.close();
		stmt.close();
		con.close();
		
		return productVO;
	}// end of findProduct

	public Map<String, Object> getProductList(Search search) throws Exception {

		
		
		String sql = "SELECT p.*, NVL(s.stock,0) stock, NVL(t.tran_status_code,0) tran_code, NVL(t.tran_no,0) tran_no "
				+ "   FROM product p, transaction t, stock s "
				+ "   WHERE p.prod_no = t.prod_no(+) "
				+ "     AND p.prod_no = s.prod_no(+) ";
			System.out.println("getPL 내부 search"+search);
			if(search.getSearchCondition() != null && 
					(search.getSearchKeyword()!=null || (search.getSearchMinPrice() + search.getSearchMaxPrice() != 0))) {
				if (search.getSearchCondition().equals("0") && !search.getSearchKeyword().equals("")) {
					sql += " AND p.prod_no='" + search.getSearchKeyword() + "'";
				} else if (search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("")) {
					sql += " AND p.prod_name like '%" + search.getSearchKeyword() + "%'";
				} else if (search.getSearchCondition().equals("2") && (search.getSearchMinPrice() + search.getSearchMaxPrice() != 0)) {
					sql += " AND p.price BETWEEN " + search.getSearchMinPrice() +" AND " + search.getSearchMaxPrice();
				}
			}
		
		System.out.println("total Count 전 Query : " + sql);
		int totalCount = getTotalCount(sql);
		
		System.out.println("상품검색 로우의 수:" + totalCount);
		
		if(search.getSearchOrderType().equals("orderByDateDESC")) {
			sql += " ORDER BY p.reg_date DESC ";
		} else if (search.getSearchOrderType().equals("orderByDateASC")) {
			sql += " ORDER BY p.reg_date ASC ";
		} else if (search.getSearchOrderType().equals("orderByPriceDESC")) {
			sql += " ORDER BY p.price DESC ";
		} else if (search.getSearchOrderType().equals("orderByPriceASC")) {
			sql += " ORDER BY p.price ASC ";
		}	
		
		sql = makeCurrentPageSql(sql, search);
		
		System.out.println("makeCurrentPage result : " + sql);
		
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
				
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("totalCount", new Integer(totalCount)); 

		System.out.println("searchVO.getPage():" + search.getCurrentPage());
		System.out.println("searchVO.getPageSize():" + search.getPageSize());

		List<Product> list = new ArrayList<Product>();
		if (totalCount > 0) { // 검색 결과가 있으면!
			
			while(rs.next()) {
				Product product = new Product();
				
				product.setProdNo(rs.getInt("prod_no"));
				product.setProdName(rs.getString("prod_name"));
				product.setPrice(rs.getInt("price"));
				product.setRegDate(rs.getDate("reg_date"));
				product.setProTranCode(rs.getString("tran_code").trim());
				product.setProTranNo(rs.getInt("tran_no"));
				product.setStock(rs.getInt("stock"));
				list.add(product);
			}
		}
		System.out.println("list.size() : " + list.size());
		map.put("list", list); // "list"에 검색결과 할당
		System.out.println("map().size() : " + map.size());

		con.close();
		stmt.close();
		rs.close();
		
		return map;
	}// end of getProductList

	public void updateProduct(Product productVO) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "UPDATE product SET prod_name=?, prod_detail=?, manufacture_day=?, price=?, image_file=?"
					+ "WHERE prod_no=?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.setInt(6, productVO.getProdNo());
		stmt.executeUpdate();
		
		sql = "UPDATE stock SET stock = ? WHERE prod_no = ?";
		stmt = con.prepareStatement(sql);
		stmt.setInt(1, productVO.getStock());
		stmt.setInt(2, productVO.getProdNo());
		stmt.executeUpdate();
		
		stmt.close();
		con.close();
	}// end of updateProduct
	
	private int getTotalCount(String sql) throws Exception {
		Connection con = DBUtil.getConnection();
		sql = "SELECT COUNT(*) total FROM "
				+ "(" + sql + ")";
		
		System.out.println("totalCount query " + sql);
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		
		int totalCount = 0; 
		rs.next();
		totalCount = rs.getInt("total");
		
		con.close();
		stmt.close();
		rs.close();
		
		return totalCount;
	}
	
	private String makeCurrentPageSql(String sql, Search search) {
		sql = "SELECT iv2.* "
		   + " FROM (SELECT ROWNUM num, iv.* "
		   + "       FROM (" + sql + ") iv "
		   + "       WHERE ROWNUM <= " +(search.getCurrentPage()*search.getPageSize()) + " ) iv2 "
		   + " WHERE num >= " + ((search.getCurrentPage()-1)*search.getPageSize()+1)
		   + " ORDER BY num ";
		
		System.out.println("UserDAO :: make SQL :: "+ sql);
		
	return sql;
	}
	
}

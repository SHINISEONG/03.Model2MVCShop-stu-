package com.model2.mvc.service.purchase.dao;

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
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class PurchaseDAO {
	/// field
	ProductService productService = new ProductServiceImpl();
	UserService userService = new UserServiceImpl();

	/// constructor
	public PurchaseDAO() {
	}

	/// method
	public void insertPurchase(Purchase purchaseVO) throws Exception {
		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO transaction VALUES(seq_transaction_tran_no.NEXTVAL, "
				+ "?, ?, ?, ?, ?, ?, ?, ?, ? , sysdate,?)";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
		stmt.setString(2, purchaseVO.getBuyer().getUserId());
		stmt.setString(3, purchaseVO.getPaymentOption());
		stmt.setInt(4, purchaseVO.getQuantity());
		stmt.setString(5, purchaseVO.getReceiverName());
		stmt.setString(6, purchaseVO.getReceiverPhone());
		stmt.setString(7, purchaseVO.getDivyAddr());
		stmt.setString(8, purchaseVO.getDivyRequest());
		stmt.setString(9, purchaseVO.getTranCode());
		stmt.setString(10, purchaseVO.getDivyDate());
		stmt.executeUpdate();

		con.close();
	}// end of insertPurchase()

	public Purchase findPurchase(int tranNo) throws Exception {
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT "
				+ "									   tran_no, prod_no, buyer_id, payment_option, receiver_name, "
				+ "									   receiver_phone, dlvy_addr, dlvy_request, NVL(tran_status_code,0) \"tran_status_code\", "
				+ "									   order_date, dlvy_date "
				+ "									   FROM transaction "
				+ "									   WHERE tran_no = ?");
		
		stmt.setInt(1, tranNo);

		ResultSet rs = stmt.executeQuery();

		Purchase purchase = null;
		Product product = null;
		User user = null;

		while (rs.next()) {
			product = productService.getProduct(rs.getInt("PROD_NO"));
			System.out.println("purDAO로 proVO잘 넘어와?" + product);

			user = userService.getUser(rs.getString("BUYER_ID"));
			System.out.println("purDAO로 userVO잘 넘어와?" + user);

			purchase = new Purchase();
			purchase.setTranNo(rs.getInt("TRAN_NO"));
			purchase.setPurchaseProd(product);
			purchase.setBuyer(user);
			purchase.setPaymentOption(rs.getString("PAYMENT_OPTION").trim());
			purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchase.setDivyAddr(rs.getString("DLVY_ADDR"));
			purchase.setDivyRequest(rs.getString("DLVY_REQUEST"));
			purchase.setTranCode(rs.getString("TRAN_STATUS_CODE").trim());
			purchase.setOrderDate(rs.getDate("ORDER_DATE"));
			purchase.setDivyDate(rs.getString("DLVY_DATE"));

		}
		con.close();
		return purchase;
	}// end of findPurchase()

	public Map<String, Object> getPurchaseList(Search search, String userId) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();

		String sql = "SELECT tran_no, buyer_id, receiver_name, receiver_phone, NVL(tran_status_code,0) \"TRAN_STATUS_CODE\", order_date FROM transaction ";
		
		if(!(userId.equals("admin"))) {
			sql += " WHERE buyer_id = ? ";
		}
		
		if(search.getSearchOrderType().equals("orderByDateDESC")) {
			sql += " ORDER BY order_date DESC ";
		} else if (search.getSearchOrderType().equals("orderByDateASC")) {
			sql += " ORDER BY order_date ASC ";
		}
		
		int totalCount = getTotalCount(sql, userId);
		
		System.out.println("구매목록검색 로우의 수:" + totalCount);

		map.put("totalCount", new Integer(totalCount)); // 맵 키 count에 총 검색 결과 수 할당.
		
		sql = makeCurrentPageSql(sql, search);
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		if(!(userId.equals("admin"))) {
			stmt.setString(1, userId);
		}
		
		ResultSet rs = stmt.executeQuery();
		
		List<Purchase> list = new ArrayList<Purchase>();
		
		if (totalCount > 0) { // 검색 결과가 있으면!
			while(rs.next()){
				
				User user = null;

				Purchase purchase = new Purchase();

				user = userService.getUser(rs.getString("BUYER_ID"));
				System.out.println("purDAO로 findList로 userVO잘 넘어와?" + user);

				purchase = new Purchase();
				purchase.setTranNo(rs.getInt("tran_no"));
				purchase.setBuyer(user);
				purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
				purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
				purchase.setTranCode(rs.getString("TRAN_STATUS_CODE").trim());
				purchase.setOrderDate(rs.getDate("order_date"));
				
				list.add(purchase);
				
			}
		}
		System.out.println("list.size() : " + list.size());
		map.put("list", list); // "list"에 검색결과 할당
		System.out.println("map().size() : " + map.size());

		con.close();

		return map;
	}// end of getPurchaseList()
	
	
	public HashMap<String, Object> getSaleList(Search searchVO) throws Exception {
			return null;
	}//end of getSaleList()
	

	public void updatePurchase(Purchase purchaseVO) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "UPDATE transaction SET payment_option=?, receiver_name=?, receiver_phone=?, dlvy_addr=?, dlvy_request=?, dlvy_date=? "
				+ "	  WHERE tran_no=?";
		
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchaseVO.getPaymentOption());
		stmt.setString(2, purchaseVO.getReceiverName());
		stmt.setString(3, purchaseVO.getReceiverPhone());
		stmt.setString(4, purchaseVO.getDivyAddr());
		stmt.setString(5, purchaseVO.getDivyRequest());
		stmt.setString(6, purchaseVO.getDivyDate());

		stmt.setInt(7, purchaseVO.getTranNo());

		stmt.executeUpdate();

		con.close();
	}// end of updatePurchase

	public void updateTranCode(Purchase purchaseVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "	UPDATE transaction SET tran_status_code=? "
				+ "	  WHERE tran_no=?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchaseVO.getTranCode());
		stmt.setInt(2, purchaseVO.getTranNo());

		stmt.executeUpdate();

		con.close();
	}//end of updateTranCode()
	
	private int getTotalCount(String sql, String userId) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ")";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = con.prepareStatement(sql);
	
		if(!(userId.equals("admin"))) {
			stmt.setString(1, userId);
		}
		
		ResultSet rs = stmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		stmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}//end of getTotalCount()
	
	private String makeCurrentPageSql(String sql , Search search ){
		sql = 	" SELECT * FROM "
				+ " (SELECT ROWNUM num, iv.* FROM( " + sql + ")iv WHERE ROWNUM <= "+search.getCurrentPage()*search.getPageSize()+") "
						+ " WHERE num >=  " + ((search.getCurrentPage()-1)*search.getPageSize()+1);
		
		System.out.println("UserDAO :: make SQL :: "+ sql);	
		
		return sql;
	}//end of makeCurrentPageSql()
	
}// end of class

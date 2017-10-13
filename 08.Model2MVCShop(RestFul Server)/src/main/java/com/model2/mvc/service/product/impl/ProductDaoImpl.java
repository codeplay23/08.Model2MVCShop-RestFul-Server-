package com.model2.mvc.service.product.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;




@Repository("productDaoImpl")
public class ProductDaoImpl implements ProductDao {

	
	//field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;//has a
	
	public void setSqlSession(SqlSession sqlSession){
	System.out.println("::"+getClass()+".setSqlSession() Call.....");
	 this.sqlSession=sqlSession;
	}
	
	//여기까지 sqlSession 인젝션
	
	//constructor
	public ProductDaoImpl() {
	System.out.println("::"+getClass()+".default Constructor Call.....");
	}

	
	
	//method
	@Override
	public void addProduct(Product product) throws Exception {
		sqlSession.insert("ProductMapper.addProduct", product);
	}

	@Override
	public Product getProduct(int prodNo) throws Exception {
		System.out.println("hi hello");
      return (Product)sqlSession.selectOne("ProductMapper.getProduct",prodNo);
	}

	@Override
	public List<Product> getProductList(Search search) throws Exception {
	return sqlSession.selectList("ProductMapper.getProductList", search);
	//List로 받아서 DAO interface에 넣어주고 service에서 map 형태로 controller에 보낸다.
	}
 
	@Override
	public void updateProduct(Product product) throws Exception {
     sqlSession.update("ProductMapper.updateProduct", product);
	}

	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
	public int getTotalCount(Search search) throws Exception {
		return sqlSession.selectOne("UserMapper.getTotalCount", search);
	}
}

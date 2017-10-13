package com.model2.mvc.service.user.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.product.ProductService;





@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class ProductServiceTest {

	/*
	 *	FileName :  UserServiceTest.java
	 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
	 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
	 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
	 * ㅇ @ContextConfiguration : Meta-data location 지정
	 * ㅇ @Test : 테스트 실행 소스 지정
	 */


		//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
		@Autowired
		@Qualifier("productServiceImpl")
		private ProductService productService;
		
		@Autowired
		@Qualifier("productDaoImpl")
		private ProductDao productDao;
		

		public void setProductService(ProductService productService) {
			this.productService = productService;
		}

		public void setProductDao(ProductDao productDao) {
			this.productDao = productDao;
		}

		//@Test
		public void testAddProduct() throws Exception {
			
		
			
			Product product=new Product();
     		product.setProdNo(1);
			product.setProdName("노트북");
			product.setProdDetail("asdf");
			product.setFileName("123.gif");
			product.setManuDate("123456");
			product.setPrice(20000);
		
			
		   productService.addProduct(product);
		   
		   product=productService.getProduct(1000);

			System.out.println(product);
			
			
			
			//==> API 확인
/*//			Assert.assertEquals("노트북", product.getProdName());
			Assert.assertEquals("좋음", product.getProdDetail());
			Assert.assertEquals("20011234", product.getManuDate());
			Assert.assertEquals("20000", product.getPrice());
			Assert.assertEquals("jkld.gif", product.getFileName());*/
			
		}
		
		//@Test
		public void testGetProduct() throws Exception {
			
			
		    Product product=new Product();
		
			product.setProdNo(1);
			product.setProdName("노트북");
			product.setProdDetail("asdf");
			product.setFileName("123.gif");
			product.setManuDate("123456");
			product.setPrice(20000);
			System.out.println("????");
		    product=productService.getProduct(10000);

		
			System.out.println(product);
			
			//==> API 확인
/*			Assert.assertEquals(1, product.getProdNo());
			Assert.assertEquals("노트북", product.getProdName());
			Assert.assertEquals("좋음", product.getProdDetail());
			Assert.assertEquals("20011234", product.getManuDate());
			Assert.assertEquals(20000, product.getPrice());
			Assert.assertEquals("jkld.gif", product.getFileName());

			Assert.assertNotNull(productService.getProduct(1000));
			Assert.assertNotNull(productService.getProduct(1002));
*/		}
		
		//@Test
		 public void testUpdateProduct() throws Exception{
			 
			Product product = productService.getProduct(1);
			Assert.assertNotNull(product);
			
			Assert.assertEquals(1, product.getProdNo());
			Assert.assertEquals("노트북", product.getProdName());
			Assert.assertEquals("좋음", product.getProdDetail());
			Assert.assertEquals("20011234", product.getManuDate());
			Assert.assertEquals(20000, product.getPrice());
			Assert.assertEquals("jkld.gif", product.getFileName());
			
			
			product.setProdName("자전거");
			product.setProdDetail("와라와라왕");
			product.setManuDate("12345");
		
			
			productService.updateProduct(product);
			
			product = productService.getProduct(1);
			Assert.assertNotNull(product);
			
			//==> console 확인
			//System.out.println(user);
				
			//==> API 확인
			/*Assert.assertEquals("change", product.getProdName());
			Assert.assertSame("두객체가 같아야 합니다",this, this);
			Assert.assertTrue("true 여야합니다", true);
			Assert.assertNull ("null값이 들어와야 합니다",new Object());
			*/
			
		 }
		 
		

	@Test
		 public void testGetProductList() throws Exception{
			 
		 	Search search = new Search();
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	Map<String,Object> map = productService.getProductList(search);
		 	
		 	List<Object> list = (List<Object>)map.get("list");
		 	Assert.assertEquals(3, list.size());
		 	
			//==> console 확인
		 	System.out.println(list);
		 	
		 	Integer totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 	
		 	System.out.println("=======================================");
		 	
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	search.setSearchCondition("0");
		 	search.setSearchKeyword("");
		 	map = productService.getProductList(search);
		 	
		 	list = (List<Object>)map.get("list");
		 	Assert.assertEquals(3, list.size());
		 	
		 	//==> console 확인
		 	System.out.println(list);
		 	
		 	totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 }
		 
		 //@Test
		 public void testGetProductListByProdName() throws Exception{
			 
		 	Search search = new Search();
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	search.setSearchCondition("1");
		 	search.setSearchKeyword("노트북");
		 	Map<String,Object> map = productService.getProductList(search);
		 	
		 	List<Object> list = (List<Object>)map.get("list");
		 	Assert.assertEquals(1, list.size());
		 	
			//==> console 확인
		 	System.out.println(list);
		 	
		 	Integer totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 	
		 	System.out.println("=======================================");
		 	
		 	search.setSearchCondition("0");
		 	search.setSearchKeyword(""+System.currentTimeMillis());
		 	map = productService.getProductList(search);
		 	
		 	list = (List<Object>)map.get("list");
		 	Assert.assertEquals(0, list.size());
		 	
			//console확인
		 	System.out.println(list);
		 	
		 	totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 }
		 
		

		
	}



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
	 * �� JUnit4 (Test Framework) �� Spring Framework ���� Test( Unit Test)
	 * �� Spring �� JUnit 4�� ���� ���� Ŭ������ ���� ������ ��� ���� �׽�Ʈ �ڵ带 �ۼ� �� �� �ִ�.
	 * �� @RunWith : Meta-data �� ���� wiring(����,DI) �� ��ü ����ü ����
	 * �� @ContextConfiguration : Meta-data location ����
	 * �� @Test : �׽�Ʈ ���� �ҽ� ����
	 */


		//==>@RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
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
			product.setProdName("��Ʈ��");
			product.setProdDetail("asdf");
			product.setFileName("123.gif");
			product.setManuDate("123456");
			product.setPrice(20000);
		
			
		   productService.addProduct(product);
		   
		   product=productService.getProduct(1000);

			System.out.println(product);
			
			
			
			//==> API Ȯ��
/*//			Assert.assertEquals("��Ʈ��", product.getProdName());
			Assert.assertEquals("����", product.getProdDetail());
			Assert.assertEquals("20011234", product.getManuDate());
			Assert.assertEquals("20000", product.getPrice());
			Assert.assertEquals("jkld.gif", product.getFileName());*/
			
		}
		
		//@Test
		public void testGetProduct() throws Exception {
			
			
		    Product product=new Product();
		
			product.setProdNo(1);
			product.setProdName("��Ʈ��");
			product.setProdDetail("asdf");
			product.setFileName("123.gif");
			product.setManuDate("123456");
			product.setPrice(20000);
			System.out.println("????");
		    product=productService.getProduct(10000);

		
			System.out.println(product);
			
			//==> API Ȯ��
/*			Assert.assertEquals(1, product.getProdNo());
			Assert.assertEquals("��Ʈ��", product.getProdName());
			Assert.assertEquals("����", product.getProdDetail());
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
			Assert.assertEquals("��Ʈ��", product.getProdName());
			Assert.assertEquals("����", product.getProdDetail());
			Assert.assertEquals("20011234", product.getManuDate());
			Assert.assertEquals(20000, product.getPrice());
			Assert.assertEquals("jkld.gif", product.getFileName());
			
			
			product.setProdName("������");
			product.setProdDetail("�Ͷ�Ͷ��");
			product.setManuDate("12345");
		
			
			productService.updateProduct(product);
			
			product = productService.getProduct(1);
			Assert.assertNotNull(product);
			
			//==> console Ȯ��
			//System.out.println(user);
				
			//==> API Ȯ��
			/*Assert.assertEquals("change", product.getProdName());
			Assert.assertSame("�ΰ�ü�� ���ƾ� �մϴ�",this, this);
			Assert.assertTrue("true �����մϴ�", true);
			Assert.assertNull ("null���� ���;� �մϴ�",new Object());
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
		 	
			//==> console Ȯ��
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
		 	
		 	//==> console Ȯ��
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
		 	search.setSearchKeyword("��Ʈ��");
		 	Map<String,Object> map = productService.getProductList(search);
		 	
		 	List<Object> list = (List<Object>)map.get("list");
		 	Assert.assertEquals(1, list.size());
		 	
			//==> console Ȯ��
		 	System.out.println(list);
		 	
		 	Integer totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 	
		 	System.out.println("=======================================");
		 	
		 	search.setSearchCondition("0");
		 	search.setSearchKeyword(""+System.currentTimeMillis());
		 	map = productService.getProductList(search);
		 	
		 	list = (List<Object>)map.get("list");
		 	Assert.assertEquals(0, list.size());
		 	
			//consoleȮ��
		 	System.out.println(list);
		 	
		 	totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 }
		 
		

		
	}



package com.model2.mvc.web.product;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@RestController
@RequestMapping("/product/*")
public class ProductRestController {

	// field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	public ProductRestController() {
		System.out.println(this.getClass());
	}

	//@Value("#{commonProperties['pageUnit']}")
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	//@Value("#{commonProperties['pageSize']}")
	@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;

	@RequestMapping(value = "json/addProduct", method = RequestMethod.POST)
	public Product addProduct(
		@RequestBody Product product) throws Exception {
		
       System.out.println("/product/json/addProduct: POST");
		
		productService.addProduct(product);
		//���� productService�� ���� db���ٰ� ������ �Է���
	   //���� Product product=productService.addProduct(product)�� �����ϸ� 
		//productDao productService �� �� void �����̱� ������ �� �ٲ������
		//�ϴ� controller�� ����Ÿ�� �ְ� �޴� �����̱� ������..Ŭ���̾�Ʈ���� ����Ÿ�� �� �޾Ҵ����� Ȯ���Ѵ�.
		
		return product;
		//������Ʈ�� ������ product ������ ��ü �״�� ������ �����ϴ� ��
	}
	

	@RequestMapping(value = "json/getProduct/{prodNo}", method = RequestMethod.GET)
	public Product getProduct(@PathVariable int prodNo
			/*@RequestBody Product product,*/
		  /*  @RequestParam("prodNo") int prodNo*/
			/*@RequestParam(value="menu", defaultValue="search") String menu,*/
			/*HttpServletRequest request, HttpServletResponse response*/) throws Exception {

		System.out.println("/product/json/getProduct: GET");

		// jsp�� ȭ���� ������ �ʴµ� ���� menu=search�� menu=manage�� ���� �ʿ䰡 ������?

	/*	Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if (cookie.getName().equals("history")) {
					cookie = new Cookie("history", cookies[i].getValue() + "," + prodNo);
				} else {
					cookie = new Cookie("history", "prodNo");
				}
			}
		}

		cookie.setMaxAge(60 * 5);
		response.addCookie(cookie);*/

		// �׳� �Ľ��ؼ� �����͸� �ְ�ް�
		// �𵨰� �� �����ؼ� ����
		return  productService.getProduct(prodNo);
		 
	
		 
		 

		
	}

	@RequestMapping(value = "json/updateProduct", method = RequestMethod.POST)
	public Product updateProduct(@RequestBody Product product) throws Exception {

		System.out.println("/product/json/updateProduct : Post");
		
		productService.updateProduct(product);

		
		return product;
	}

	@RequestMapping(value = "json/listProduct", method = RequestMethod.POST)
	public Map<String,Object> listProduct(@RequestBody Search search) throws Exception {

		System.out.println("/json/product/listProduct");

		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		
		search.setPageSize(pageSize);
		
	    Map<String,Object>map=productService.getProductList(search);
		
        Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit, pageSize);
		
		System.out.println(resultPage);

	
		//pageSize�� result page�� view�� ���� ������ �ϱ�  �ʿ����
		map.put("list", map.get("list"));
		
		map.put("search", search);
		
		map.put("resultPage", resultPage);

		return map;
	}

}
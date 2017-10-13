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
		//따로 productService를 통해 db에다가 정보를 입력함
	   //만약 Product product=productService.addProduct(product)로 수행하면 
		//productDao productService 가 다 void 형태이기 때문에 다 바꿔줘야함
		//일단 controller는 데이타를 주고 받는 목적이기 때문에..클라이언트에게 데이타를 잘 받았는지만 확인한다.
		
		return product;
		//리퀘스트된 정보를 product 도메인 객체 그대로 담은걸 리턴하는 셈
	}
	

	@RequestMapping(value = "json/getProduct/{prodNo}", method = RequestMethod.GET)
	public Product getProduct(@PathVariable int prodNo
			/*@RequestBody Product product,*/
		  /*  @RequestParam("prodNo") int prodNo*/
			/*@RequestParam(value="menu", defaultValue="search") String menu,*/
			/*HttpServletRequest request, HttpServletResponse response*/) throws Exception {

		System.out.println("/product/json/getProduct: GET");

		// jsp로 화면이 변하질 않는데 굳이 menu=search랑 menu=manage를 보낼 필요가 있을까?

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

		// 그냥 파싱해서 데이터만 주고받고
		// 모델과 뷰 연결해서 리턴
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

	
		//pageSize나 result page은 view를 위한 데이터 니깐  필요없다
		map.put("list", map.get("list"));
		
		map.put("search", search);
		
		map.put("resultPage", resultPage);

		return map;
	}

}
package com.model2.mvc.web.product;

import java.util.Map;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;

@Controller
@RequestMapping("/product/*")
public class ProductController {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	public ProductController() {
		System.out.println(this.getClass());
	}

	//@Value("#{commonProperties['pageUnit']}")
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	//@Value("#{commonProperties['pageSize']}")
	@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	

	@RequestMapping(value="addProduct", method=RequestMethod.POST)
	public ModelAndView addProduct(@ModelAttribute("product")Product product)throws Exception {
     
	   System.out.println("/product/addProduct:POST");
	   //Business Logic
       productService.addProduct(product);
	
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/product/addProduct.jsp");

		return modelAndView;
	}
	
	
	@RequestMapping(value="addProductView", method=RequestMethod.GET)
	public ModelAndView addProductView()throws Exception {
     
	   System.out.println("/product/addProductView:GET");
	   //Business Logic
      
	
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/product/addProductView.jsp");

		return modelAndView;
	}
	
	

	@RequestMapping(value="getProduct/{menu}/{prodNo}", method=RequestMethod.GET)
	public ModelAndView getProduct(@PathVariable String menu,
			@PathVariable String prodNo, 
			HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
			
			//@CookieValue("history") int prodNo,
            //response.setAttribute("history", prodNo);
		
		System.out.println("/product/getProduct: GET");

		Product product=productService.getProduct(Integer.parseInt(prodNo));
		model.addAttribute("product", product);//�̰� modelAttribute�� �ȹ޾ұ� ������ ObjectScope�� �־������

		ModelAndView modelAndView=new ModelAndView();
		if(menu.equals("search")){
		modelAndView.setViewName("/product/getProduct.jsp");
		}else if(menu.equals("manage")){
		modelAndView.setViewName("/product/updateProductView.jsp");
		}//Object�� �ƴ϶� view�� �޾ƾ� �ϱ⶧����
		
		
		
		
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		if (cookies!=null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if (cookie.getName().equals("history")) {
					cookie = new Cookie("history", cookies[i].getValue()+","+prodNo);
				}else{
					cookie = new Cookie("history", "prodNo");
				}
			}
		}
		
		cookie.setMaxAge(60*5);
		response.addCookie(cookie);
		
		
	    return modelAndView;
		
	}
	

  @RequestMapping(value="updateProduct/{prodNo}", method=RequestMethod.GET)
	public ModelAndView updateProduct(@PathVariable String prodNo)throws Exception{

		    System.out.println("/product/updateProduct:GET");
		   
		    int ProdNum=Integer.parseInt(prodNo);
		    
		    Product product=productService.getProduct(ProdNum);
		    
		    
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("prodcut", product);
			modelAndView.setViewName("redirect:/product/updateProductView.jsp");
				
			return modelAndView;
	}
	
	
	

	@RequestMapping(value="updateProduct", method=RequestMethod.POST)
	public ModelAndView updateProduct(@ModelAttribute("product") Product product, HttpServletRequest request) throws Exception {
          
		  //ModelAttribute�� �̹� prodNo�� ���� ����
		  System.out.println("/product/updateProduct:POSTgg");
	      productService.updateProduct(product);
	       //update����(�Է��ѻ���)���� getProductȭ��(Ȯ��ȭ��)�� ������� �ϴϱ�
			ModelAndView modelAndView = new ModelAndView();
			
			modelAndView.addObject("product", product);
			modelAndView.addObject("menu", "manage");
			modelAndView.setViewName("/product/getProduct.jsp");
			
			//forward�� request�� ���� response ���� ���� �����̱� ������ prodNo�� �ߺ��ȴ�.
			//redirect�� �ؼ� response �����ų�, +request.getParameter("prodNo")+�� �� ������ �ʴ´�.
			//@������ ������̼ǿ����� ����ȯ�� ������ �ʾƵ��ȴ�.

			return modelAndView;

	}

	@RequestMapping(value="listProduct/{menu}")
	public ModelAndView listProduct(@ModelAttribute("search") Search search, 
			@PathVariable String menu)throws Exception {
         //list�� get ��� post ����� ���ʿ��ϴϱ�
		System.out.println("/product/listProduct:GET/POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		
		search.setPageSize(pageSize);

		Map<String,Object>map= productService.getProductList(search);
        System.out.println("��"+map);

		Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit, pageSize);
		
		System.out.println(resultPage);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("list", map.get("list"));
		System.out.println("@@@@@@@@list"+map.get("list"));
		
		modelAndView.addObject("search", search);
		modelAndView.addObject("resultPage", resultPage);

		
		modelAndView.setViewName("/product/listProduct.jsp");

		return modelAndView;

	}
}
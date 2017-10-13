package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.io.JsonStringEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

@RestController
@RequestMapping("/purchase/*")
public class PurchaseRestController {

	//field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	
	//constructor
	public PurchaseRestController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;

	@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	

	@RequestMapping(value="json/addPurchase", method=RequestMethod.POST)
	public Purchase addPurchase(@RequestBody Purchase purchase
		) throws Exception {
	//리턴값이 Purchase인 이유는 client측에 purchase vo를 보내기 때문
		
     System.out.println("/purchase/json/addPurchase:POST");	
		
	purchaseService.addPurchase(purchase);
	//서버단은 이미 void 형태이기 때문에 건들지 말고 데이터만 잘받았는지 확인한다
		
	
	return purchase;
}
	
	
	 @RequestMapping(value="json/addPurchaseView/{prodNo}", method=RequestMethod.GET) 
	  public Product addPurchaseView(@PathVariable int prodNo)throws Exception{
	  
	   System.out.println("/purchase/json/addPurchaseView:GET");
	  
		return productService.getProduct(prodNo);
	  }
		
	
	 @RequestMapping(value="json/getPurchase/{tranNo}",method=RequestMethod.GET)
		public Purchase getPurchase(@PathVariable int tranNo) throws Exception {

			System.out.println("/purchase/json/getPurchase:GET");
			
			return purchaseService.getPurchase(tranNo);
		}
	 
	 @RequestMapping(value="json/listPurchase/{buyerId}", method = RequestMethod.POST)
		public Map<String,Object> listPurchase(@PathVariable String buyerId,
				@RequestBody Search search) throws Exception {
		 	
		 
	
		   System.out.println("서치 투스트링"+search);
			System.out.println("/purchase/json/listPurchase");
		 
			
			
			if (search.getCurrentPage() == 0) {
				search.setCurrentPage(1);
			}
	        search.setPageSize(pageSize);
	 
	        
	        //모델과 뷰 연결
	     //String buyerId=((User)(request.getSession(true).getAttribute("user"))).getUserId();
	      
	     
	      
	       //String buyerId=purchase.getBuyer().getUserId();
	       
           /*User buyer = (User)session.getAttribute("user");
			String buyerId =purchase.getBuyer().getUserId();*/
	       
	       /* String buyerId=buyer.getUserId();*/
	        
	        //System.out.println("바이어아이디"+buyerId);
	        
	        Map<String, Object> map=purchaseService.getPurchaseList(search,buyerId);
	        
	        System.out.println("맵"+map);
	        System.out.println("서치"+search);
	        System.out.println("토탈카운트"+map.get("totalCount"));
	       
	        

			Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit, pageSize);
			
			map.put("resultPage", resultPage);
			
			//뷰단에 보내는 것이기 때문에 resultPage(totalCount)나 pageSize는 필요없다
		

		    map.put("list", map.get("list"));
			System.out.println("&&&&&리스트&&&&&"+ map.get("list"));
			
			map.put("search", search);
			
		
          return map;

		}
	 
	 @RequestMapping(value="json/updatePurchaseView/{tranNo}", method=RequestMethod.GET)
		public Purchase updatePurchaseView(@PathVariable int tranNo) throws Exception {

		System.out.println("/purchase/json/updatePurchaseView:GET");
		 
		 return purchaseService.getPurchase(tranNo);
	
		}
	 
	 @RequestMapping(value="json/updatePurchase", method=RequestMethod.POST)
		public Purchase updatePurchase(@RequestBody Purchase purchase) throws Exception {

		System.out.println("/purchase/json/updatePurchase:POST");
		 
		 purchaseService.updatePurchase(purchase);
			
		/*purchase=purchaseService.getPurchase(purchase.getTranNo());*/
		
		return purchase;
	    } 
	 
	 @RequestMapping(value="json/updateTranCode/{prodNo}/{tranCode}", method=RequestMethod.GET)
		public Purchase updateTranCode(@PathVariable int prodNo, 
				@PathVariable String tranCode) throws Exception {

		 System.out.println("/purchase/json/updateTranCode:GET");
		 
			//purchaseProd의 prodNo은 String이다.
			System.out.println("업데이트트랜코드 tranCode"+tranCode);
			System.out.println("업데이트트랜코드 prodNo"+prodNo);
			
			
			
			Product purchaseProd=new Product();
			purchaseProd.setProdNo(prodNo);
			
			Purchase purchase=new Purchase();
			
			if(tranCode !=null && tranCode.equals("2")){
				tranCode="3";
			}
			
			System.out.println("변경된 트랜코드"+tranCode);
			
			purchase.setTranCode(tranCode);
			purchase.setPurchaseProd(purchaseProd);
			
			/*purchaseService.getPurchase(prodNo);*/
			
			purchaseService.updateTranCode(purchase);
		
			return purchase;

		}
	 
	 
	 @RequestMapping(value="json/updateTranCodeByProd/{prodNo}/{tranCode}", method=RequestMethod.GET)
		public Purchase updateTranCodeByProd(@PathVariable String prodNo,
				@PathVariable String tranCode) throws Exception {
			
		 System.out.println("/purchase/json/updateTranCodeProd:GET");
		 
			/*purchaseProd의 prodNo은 String이다.*/
			System.out.println("업데이트트랜코드 tranCode"+tranCode);
			System.out.println("업데이트트랜코드 prodNo"+prodNo);
			
			int prodNum=Integer.parseInt(prodNo);
			
			Product purchaseProd=new Product();
			purchaseProd.setProdNo(prodNum);
			
			Purchase purchase=new Purchase();
			
			if(tranCode !=null && tranCode.equals("1")){
				tranCode="2";
			}
			
			System.out.println("변경된 트랜코드"+tranCode);
			
			purchase.setTranCode(tranCode);
			purchase.setPurchaseProd(purchaseProd);
			/*purchaseService.getPurchase(Integer.parseInt(prodNo));*/
			
			purchaseService.updateTranCode(purchase);
			
			
		return purchase;

		}
	 
	 
	
}

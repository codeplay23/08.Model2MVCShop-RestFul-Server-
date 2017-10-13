package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {

	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	public PurchaseController() {
		System.out.println(this.getClass());
		
	}
	
	
		@Value("#{commonProperties['pageUnit'] ?: 3}")
		int pageUnit;
	
		@Value("#{commonProperties['pageSize'] ?: 2}")
		int pageSize;

		@RequestMapping(value="addPurchase", method=RequestMethod.POST)
		public String addPurchase(@ModelAttribute("purchase") Purchase purchase, 
				Model model) throws Exception {
		
			System.out.println("/purchase/addProduct");
			
			purchaseService.addPurchase(purchase);
			   
			
		    model.addAttribute("purchase", purchase);
			
			return  "/purchase/addPurchase.jsp";
		}
			
		
		 @RequestMapping(value="addPurchaseView", method=RequestMethod.GET) 
		  public String addPurchaseView(@RequestParam("prodNo") int prodNo, Model model)throws Exception{
		  
		  System.out.println("/purchase/addPurchaseView");
		   
		   Product product=productService.getProduct(prodNo);
	       model.addAttribute("product", product);
		  
			return "/purchase/addPurchaseView.jsp";
		  }
			
		 
		 
		 @RequestMapping(value="getPurchase",method=RequestMethod.GET)
			public String getPurchase(@RequestParam("tranNo") int tranNo, Model model) throws Exception {

				System.out.println("/purchase/addPurchase");
				
				Purchase purchase=purchaseService.getPurchase(tranNo);
				model.addAttribute("purchase", purchase);
			
				return "/purchase/getPurchase.jsp";

			}
		 
		 
		 @RequestMapping(value="listPurchase")
			public String listPurchase( @ModelAttribute("search") Search search, 
					HttpSession session,
					Model model) throws Exception {

				
				if (search.getCurrentPage() == 0) {
					search.setCurrentPage(1);
				}
		        search.setPageSize(pageSize);// commonproperties에서가져온 pageSize
		     
		        
		        //모델과 뷰 연결
		        /*String buyerId=((User)(request.getSession(true).getAttribute("user"))).getUserId();*/
		        
		        User user = (User)session.getAttribute("user");
				
				String buyerId = user.getUserId();
		        
		        Map<String, Object>map=purchaseService.getPurchaseList(search, buyerId);
		        
		        System.out.println("맵"+map);
		        System.out.println("서치"+search);
		        
		        System.out.println("토탈카운트"+map.get("totalCount"));
		       
		        

				Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit, pageSize);
				// currentPage,totalCount,pageUnit,pageSize를 인자값으로 보내서 받는 리턴값이
				// resultPage
				// .intValue() int형의 변환후에 이 객체가 갖는 수치
				
				
				System.out.println("\n\n\n\n\n\n\n"+resultPage);

				model.addAttribute("list", map.get("list"));
				
				System.out.println("리스트"+ map.get("list"));
				
				model.addAttribute("search", search);
				model.addAttribute("resultPage", resultPage);
				
				//jsp에 보내는 것들

				return "/purchase/listPurchase.jsp";

			}
		 
		 @RequestMapping(value="updatePurchaseView", method=RequestMethod.GET)
			public String updatePurchaseView(@RequestParam("tranNo") int tranNo
					,Model model) throws Exception {

				Purchase purchase=purchaseService.getPurchase(tranNo);
				model.addAttribute("purchase",purchase);

				return "/purchase/updatePurchaseView.jsp";
			}

		 @RequestMapping(value="updatePurchase", method=RequestMethod.POST)
			public String updatePurchase(@ModelAttribute("purchase") Purchase purchase
					,Model model) throws Exception {

				purchaseService.updatePurchase(purchase);
				
				purchase = purchaseService.getPurchase(purchase.getTranNo());
			
			/*	return "/getPurchase.do?tranNo=";*/
				// getPurchase에서 tranNo가 오기 때문에 +request.getParameter 해줄 필요가 없다
			
				model.addAttribute("purchase", purchase);
				
				return"/purchase/getPurchase.jsp";
			} 
		 
		 
		 @RequestMapping(value="updateTranCode", method=RequestMethod.GET)
			public String updateTranCode(@RequestParam("prodNo") int prodNo, 
					@RequestParam("tranCode") String tranCode) throws Exception {

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
			
				return "/purchase/listPurchase";

			}
		 
		 
		 @RequestMapping(value="updateTranCodeByProd", method=RequestMethod.GET)
			public String updateTranCodeByProd(@RequestParam("prodNo") String prodNo,
					@RequestParam("tranCode") String tranCode) throws Exception {
				
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
				
				
				return "/product/listProduct?"+"menu=manage";

			}
		 
}

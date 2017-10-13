package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;



@Service("purchaseServiceImpl")//����
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	@Qualifier("purchaseDaoImpl")//������(�ʵ尪+setter�޼ҵ�)
	private PurchaseDao purchaseDao;
	private Search Search;
	public void setPurchaseDao(Purchase purchase){
		this.purchaseDao=purchaseDao;
	}
	
	
	public PurchaseServiceImpl() {
		System.out.println(this.getClass()+"purchaseServiceImpl call.....");
	}

	@Override
	public void addPurchase(Purchase purchase) throws Exception {
		purchaseDao.addPurchase(purchase);		
	}

	@Override
	public Purchase getPurchase(int tranNo) throws Exception {
		return purchaseDao.getPurchase(tranNo);
	}


	public Purchase getPurchase2(int prodNo) throws Exception {
		Purchase purchase = new Purchase();
		purchase.setPurchaseProd((new ProductServiceImpl().getProduct(prodNo)));
		return purchase;
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
	
		return purchaseDao.getPurchaseList(search, buyerId);
		
    //����Ÿ���� map �ϻ� ���ڰ��� search buyer�� �߰� �װ� �޾� map�� �����Ѱű� ������()�ȿ� map�� ���� �ȵȴ�
	}

	@Override
	public void updatePurchase(Purchase purchase) throws Exception {
		purchaseDao.updatePurchase(purchase);

	}

	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
		purchaseDao.updateTranCode(purchase);
	}


	
}

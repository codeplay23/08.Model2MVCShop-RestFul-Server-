package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseDao;

@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDao {

	//field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;//has a
	
	//sqlSession ������
	public void setSqlSession(SqlSession sqlSession){
		System.out.println("::"+getClass()+".setSqlSession() Call.....");
		this.sqlSession=sqlSession;
	}
	
	
	
	//constructor
	
	public PurchaseDaoImpl() {
		System.out.println("::"+getClass()+".default Constructor Call.....");
	}

	@Override
	public void addPurchase(Purchase purchase) throws Exception {
		sqlSession.insert("PurchaseMapper.addPurchase", purchase);

	}

	@Override
	public Purchase getPurchase(int tranNo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("PurchaseMapper.getPurchase",tranNo);
	}

	/*@Override
	public Purchase getPurchase2(int prodNo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("PurchaseMapper.getPurchaseList2",prodNo);
	}
	//tranNo=prodno�� �������ֱ� ������ ���� ���� �ʿ䰡 ����
*/
	@Override
	public Map<String, Object> getPurchaseList(Search search,String buyerId) throws Exception {
		// TODO Auto-generated method stub
		Map<String , Object>  map = new HashMap<String, Object>();
		
		map.put("search", search);
		map.put("buyerId", buyerId); 
		
		System.out.println("\n\n\n\n\n\n\n\n"+buyerId+"\n\n\n\n\n\n\n\n");
		
		
		/*return sqlSession.selectList("PurchaseMapper.getPurchaseList",map );*/
		List<Purchase> list=sqlSession.selectList("PurchaseMapper.getPurchaseList",map );
		
		System.out.println("\n\n\n\n\n\n\n\n"+list+"\n\n\n\n\n\n\n\n");
				
	    for (int i = 0; i < list.size(); i++) {
			list.get(i).setBuyer((User)sqlSession.selectOne("UserMapper.getUser", list.get(i).getBuyer().getUserId()));
			//list�� index��°�� setBuyer�� ���ִ°� 
			//list�� purchase������ �������� ������ִµ� ���� purchase.buyer���� userId�� �����ͼ� �� userId�� 
			//userMapper�� getUser�� �̿��� user ������ �����ͼ� list�� index��° buyer�� ������ �������ش�.
			System.out.println("hi");
			list.get(i).setPurchaseProd((Product)sqlSession.selectOne("ProductMapper.getProduct", list.get(i).getPurchaseProd().getProdNo()));
		}
		
	
		map.put("totalCount", sqlSession.selectOne("PurchaseMapper.getTotalCount", buyerId));
		
		System.out.println("\n\n\n\n\n\n\n\n"+map.get("totalCount")+"\n\n\n\n\n\n\n\n");
		
		
		
		
		map.put("list", list);

		
	return map;
	
	}

	@Override
	public void updatePurchase(Purchase purchase) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update("PurchaseMapper.updatePurchase",purchase);

	}

	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
		// TODO Auto-generated method stub
       sqlSession.update("PurchaseMapper.updateTranCode",purchase);
	}

/*	public int getTotalCount(String buyerId) throws Exception {
		return sqlSession.selectOne("PurchaseMapper.getTotalCount", buyerId );
	}*/

	
}


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
	
	//sqlSession 인젝션
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
	//tranNo=prodno를 조인해주기 때문에 따로 만들 필요가 없다
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
			//list의 index번째에 setBuyer를 해주는거 
			//list에 purchase에대한 정보들이 흩어져있는데 그중 purchase.buyer에서 userId를 가져와서 그 userId로 
			//userMapper의 getUser를 이용해 user 정보를 가져와서 list의 index번째 buyer에 정보를 세팅해준다.
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


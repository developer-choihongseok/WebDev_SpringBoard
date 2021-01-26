package com.koreait.sboard;

import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.koreait.sboard.common.Const;

@Component("menuPreparer")
public class MenuPreparer implements ViewPreparer{
	
	@Autowired
	private CommonMapper mapper;
	
	@Override
	public void execute(Request tilesContext, AttributeContext attributeContext) {
		
		if(Const.menuList == null) {
			System.out.println(" ----- get menus from DB -----");
			Const.menuList = mapper.selManageBoardList();
		}
		
		// cascade : 자식한테도 menuList를 전달해줄려면 true를 줘야한다. -> header에서 열어야 하기 때문!!
		attributeContext.putAttribute("menuList", new Attribute(Const.menuList), true);
	}
}

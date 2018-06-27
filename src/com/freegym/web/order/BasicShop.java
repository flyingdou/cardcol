package com.freegym.web.order;

import javax.persistence.MappedSuperclass;

import com.sanmen.web.core.bean.CommonId;

@SuppressWarnings("serial")
@MappedSuperclass
public class BasicShop extends CommonId {
	
	public static String getShopSql(){
		final StringBuffer sb = new StringBuffer();
		sb.append("SELECT GS.id ID,g.id pro_id,g.name pro_NAME,m.id member_id, m.role member_role,M.nick member_nick,'6' TYPE, G.PRICE PRICE FROM tb_goods_shop gs LEFT JOIN tb_goods G ON gs.goods = g.id LEFT JOIN tb_member m ON gs.member = m.id");
		sb.append(" UNION ALL");
		sb.append(" SELECT PS.id ID,p.id pro_id,P.name pro_NAME,m.id member_id, m.role member_role,M.nick member_nick,'1' TYPE, (CASE WHEN (protype<3) THEN p.promiseCost ELSE p.cost END) PRICE FROM TB_PRODUCT_SHOP PS LEFT JOIN tb_PRODUCT P ON PS.product=P.ID LEFT JOIN tb_member m ON PS.member = m.id");
		sb.append(" UNION ALL");
		sb.append(" SELECT PS1.id ID,p.id pro_id,P.plan_name pro_NAME,m.id member_id, m.role member_role,M.nick member_nick,'3' TYPE, P.unit_PRICE PRICE FROM TB_PLANRELEASE_SHOP PS1 LEFT JOIN tb_PlAN_RELEASE P ON PS1.planrelease=P.ID LEFT JOIN tb_member m ON PS1.member = m.id");
		sb.append(" UNION ALL");
		sb.append(" SELECT cs.id ID,ci.id pro_id,ci.name pro_NAME,m.id member_id, m.role member_role,M.nick member_nick,'5' TYPE, CASE WHEN f.member IS NULL then c.member_Price ELSE c.hour_Price END price FROM TB_course_SHOP cs LEFT JOIN tb_course c ON cs.course = c.ID LEFT JOIN tb_member m ON cs.member = m.id LEFT JOIN tb_member_friend f ON c.member = f.friend AND cs.member = f.member AND f.type = '1' LEFT JOIN tb_course_info ci ON c.courseid=ci.id ");
		sb.append(" UNION ALL");
		sb.append(" SELECT as1.id ID,a.id pro_id,a.name pro_NAME,m.id member_id, m.role member_role,M.nick member_nick,'2' TYPE, a.amerce_Money PRICE FROM TB_active_SHOP as1 LEFT JOIN tb_active a ON as1.active=a.ID LEFT JOIN tb_member m ON as1.member = m.id");
		sb.append(" UNION ALL");
		sb.append(" SELECT fs.id ID,fc.id pro_id,fc.name pro_NAME,m.id member_id, m.role member_role,M.nick member_nick,'4' TYPE, CASE WHEN f.member IS NULL THEN fc.costs2 ELSE fc.costs1 END price FROM TB_FACTORY_SHOP fs LEFT JOIN TB_MEMBER_FACTORY_COSTS fc ON fs.factorycosts=fc.ID LEFT JOIN tb_member m ON fs.member = m.id LEFT JOIN tb_member_friend f ON fs.member = f.member AND f.type = '1' ");
		return sb.toString();
	}

	@Override
	public String getTableName() {
		return null;
	}

}

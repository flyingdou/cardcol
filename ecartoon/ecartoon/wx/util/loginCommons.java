package ecartoon.wx.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cardcol.web.service.IClub45Service;
import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Member;

public class loginCommons {

	public static String username;
	public static String password;
	public static String thirdType;
	public static String code;
	public static int count;

	public Member setMember(HttpServletRequest request, IClub45Service service) {
		Member member = null;
		try {
			// 获取微信推送的openId
			String sqlxx = "select openid from tb_openid where id = 2";
			Map<String, Object> map = DataBaseConnection.getOne(sqlxx, null);
			String openId = (String) map.get("openid");
			request.getSession().setAttribute("openId", openId);

			// 查询记录E卡通微信公众号的openId表 , 判断当前用户是否有记录
			String sqlx = "select * from tb_openid_member where openid = ? and origin = 'EW'";
			Object[] objx = { openId };

			List<Map<String, Object>> listx = DataBaseConnection.getList(sqlx, objx);
			if (listx.size() < 1) {
				// 如果没有记录,创建一条用户数据和记录E卡通微信公众号的openId数据
				member = new Member();
				member.setName("wx" + Math.round(Math.random() * 1000000));
				member.setThirdType("W");
				member.setRole("M");
				member.setSex("M");
				member.setImage("users.png");
				member = (Member) service.saveOrUpdate(member);

				String addOpenId = "insert into tb_openid_member(id,member,openid,origin,login_date) values(null,?,?,?,?)";
				Object[] args = new Object[] { member.getId(), openId, "EW", new Date() };
				DataBaseConnection.updateData(addOpenId, args);

				request.getSession().setAttribute("member", member);
			} else {
				// 如果有记录,直接查询出来并返回
				String sqlq = "select m.* from tb_member m inner join tb_openid_member mo on m.id = mo.member where mo.openid = '"
						+ openId + "'";
				member = (Member) DataBaseConnection.load(sqlq, Member.class, 0);
				String sqlddd = "update tb_openid_member set login_date =? where id = ?";
				Object[] objddd = {new Date(),listx.get(0).get("id")};
				DataBaseConnection.updateData(sqlddd, objddd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return member;
	}
	
	public Member setMember(HttpServletRequest request, IClub45Service service, String openId) {
		Member member = null;
		try {
			// 获取微信推送的openId
//			String sqlxx = "select openid from tb_openid where id = 2";
//			Map<String, Object> map = DataBaseConnection.getOne(sqlxx, null);
//			String openId = (String) map.get("openid");
			request.getSession().setAttribute("openId", openId);

			// 查询记录E卡通微信公众号的openId表 , 判断当前用户是否有记录
			String sqlx = "select * from tb_openid_member where openid = ? and origin = 'EW'";
			Object[] objx = { openId };

			List<Map<String, Object>> listx = DataBaseConnection.getList(sqlx, objx);
			if (listx.size() < 1) {
				// 如果没有记录,创建一条用户数据和记录E卡通微信公众号的openId数据
				member = new Member();
				member.setName("wx" + Math.round(Math.random() * 1000000));
				member.setThirdType("W");
				member.setRole("M");
				member.setSex("M");
				member.setImage("users.png");
				member = (Member) service.saveOrUpdate(member);

				String addOpenId = "insert into tb_openid_member(id,member,openid,origin,login_date) values(null,?,?,?,?)";
				Object[] args = new Object[] { member.getId(), openId, "EW", new Date() };
				DataBaseConnection.updateData(addOpenId, args);

				request.getSession().setAttribute("member", member);
			} else {
				// 如果有记录,直接查询出来并返回
				String sqlq = "select m.* from tb_member m inner join tb_openid_member mo on m.id = mo.member where mo.openid = '"
						+ openId + "'";
				member = (Member) DataBaseConnection.load(sqlq, Member.class, 0);
				String sqlddd = "update tb_openid_member set login_date =? where id = ?";
				Object[] objddd = {new Date(),listx.get(0).get("id")};
				DataBaseConnection.updateData(sqlddd, objddd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return member;
	}

}

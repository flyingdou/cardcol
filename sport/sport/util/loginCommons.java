package sport.util;

import javax.servlet.http.HttpServletRequest;

import com.cardcol.web.service.IClub45Service;
import com.freegym.web.basic.Member;

import common.util.HttpRequestUtils;
import net.sf.json.JSONObject;

public class loginCommons   {

	/**
	 * 
	 */
	public static String username;
	public static String password;
	public static String thirdType;
	public static String code;
	public static int count;
	
	public static String orderNo;
	public static int orderType;
	
	
	public  void setMember(HttpServletRequest request,IClub45Service service){
		    JSONObject mid  =   HttpRequestUtils.httpGet("http://localhost:80/cardcolv4/sloginwx.asp");
	        long memberId = Long.parseLong(mid.get("key").toString());
	        Member member = (Member) service.load(Member.class, memberId);
	        request.getSession().setAttribute("member", member);
	}
}

package com.cardcolv45.mobile.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Live;
import com.freegym.web.basic.Member;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.Product;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.OpenImUser;
import com.taobao.api.request.OpenimTribeCreateRequest;
import com.taobao.api.response.OpenimTribeCreateResponse;

import common.util.HttpRequestUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 直播相关Action
 * 
 * @author admin
 *
 */
@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MLiveV45ManageAction extends BasicJsonAction {

	private static final long serialVersionUID = 1L;

	private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	/**
	 * 腾讯云直播服务的bizid
	 */
	private static final String BIZID = "10803";
	/**
	 * 腾讯云直播服务的APPID
	 */
	private static final String APPID = "1252000172";
	/**
	 * 防盗链key
	 */
	private static final String KEY = "5e3daa3446d2ca2be1d2479a50edc749";
	/**
	 * 调用直播相关API的key
	 */
	private static final String API_KEY = "e0a4e017c4fa74c955a27a4ec6f4224a";

	/**
	 * 直播封面
	 */
	public File image;

	public String imageFileName;

	/**
	 * 用户id
	 */
	public Integer memberId;

	/**
	 * 主播id
	 */
	public Integer anchor;

	/**
	 * 保存直播间相关信息
	 */
	@SuppressWarnings("unused")
	public void saveLive() {
		try {
			// 编码转换,防止中文乱码
			JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			JSONObject jsonObj = (JSONObject) arr.get(0);
			int resultCount = 0;
			String id = String.valueOf(DataBaseConnection
					.getOne("select * from tb_live where member= " + getMobileUser().getId(), null).get("id"));
			Live live = null;
			if (id != null) {
				live = (Live) service.load(Live.class, Long.valueOf(id));
				id = null;
			}
			if (live == null || live.getId() == 0) {
				// 创建直播对象并补全属性
				live = new Live();
				live.setMember((Member) getMobileUser());
				live.setLiveName(jsonObj.getString("liveName"));
				live.setLiveCost(jsonObj.getDouble("liveCost"));
				// 上传图片并返回上传后的文件名
				String imageName = image != null ? saveFile("picture", image, imageFileName, null) : null;
				live.setLiveImage(imageName);
				live.setLiveNotice(jsonObj.getString("liveNotice"));
				live.setLiveState(0);
				live.setStartTime(jsonObj.getString("startTime"));

				// 创建阿里IM群组并返回ID
				String tribeId = createTribe();

				String insertLive = "insert into tb_live(member,live_name,live_cost,live_image,"
						+ "live_notice,live_state,start_time,tribe_id) values(?,?,?,?,?,?,?,?)";

				Object[] liveArgs = { live.getMember().getId(), live.getLiveName(), live.getLiveCost(),
						live.getLiveImage(), live.getLiveNotice(), live.getLiveState(), live.getStartTime(), tribeId };

				// 持久化数据
				resultCount = DataBaseConnection.updateData(insertLive, liveArgs);
				id = String.valueOf(
						DataBaseConnection.getOne("select id from tb_live order by id desc limit 1", null).get("id"));
				if (id != null) {
					live = (Live) service.load(Live.class, Long.valueOf(id));
					id = null;
				}
			} else {
				// 补全对象属性
				live.setLiveName(jsonObj.getString("liveName"));
				live.setLiveCost(jsonObj.getDouble("liveCost"));
				// 上传图片并返回上传后的文件名
				String imageName = image != null ? saveFile("picture", image, imageFileName, null) : null;
				live.setLiveImage(imageName);
				live.setLiveNotice(jsonObj.getString("liveNotice"));
				live.setStartTime(jsonObj.getString("startTime"));
				live.setLiveState(0);

				StringBuilder updateLive = new StringBuilder("update tb_live set");
				if (live.getLiveName() != null && !"".equals(live.getLiveName())) {
					updateLive.append(" live_name=").append("'").append(live.getLiveName()).append("'");
				}
				if (live.getLiveCost() != null && !"".equals(live.getLiveCost())) {
					updateLive.append(",live_cost=").append(live.getLiveCost());
				}
				if (live.getLiveImage() != null && !"".equals(live.getLiveImage())) {
					updateLive.append(",live_image='").append(live.getLiveImage()).append("'");
				}
				if (live.getLiveNotice() != null && !"".equals(live.getLiveNotice())) {
					updateLive.append(",live_Notice=").append("'").append(live.getLiveNotice()).append("'");
				}
				if (live.getStartTime() != null && !"".equals(live.getStartTime())) {
					updateLive.append(",start_time=").append("'").append(live.getStartTime()).append("'");
				}
				updateLive.append(" where id=").append(live.getId());
				// 持久化数据
				resultCount = DataBaseConnection.updateData(updateLive.toString(), null);
				live = (Live) service.load(Live.class, Long.valueOf(live.getId()));
			}
			if (live.getTribeId() == null || "".equals(live.getTribeId())) {
				String tribeId = createTribe();
				DataBaseConnection.updateData("update tb_live set tribe_id = ? where id = ?",
						new Object[] { tribeId, live.getId() });
			}
			response(new JSONObject().accumulate("success", true).accumulate("id", live.getId())
					.accumulate("memberId", live.getMember().getId()).accumulate("liveName", live.getLiveName())
					.accumulate("liveCost", live.getLiveCost()).accumulate("liveImage", live.getLiveImage())
					.accumulate("liveState", live.getLiveState()).accumulate("tribeId", live.getTribeId()));
		} catch (Exception e) {
			response(e);
		}
	}

	/**
	 * 调用阿里IM服务创建会话群组
	 * 
	 * @return
	 */
	private String createTribe() {
		String url = "http://gw.api.taobao.com/router/rest";
		TaobaoClient client = new DefaultTaobaoClient(url, "23330566", "0c6b77f937d49f3a14ca98a6316d110e");
		OpenimTribeCreateRequest openRequest = new OpenimTribeCreateRequest();
		OpenImUser user = new OpenImUser();
		user.setUid(getMobileUser().getId().toString());
		user.setTaobaoAccount(false);
		user.setAppKey("23330566");
		openRequest.setUser(user);
		openRequest.setTribeName(String.valueOf(Math.floor(Math.random() * 100000)));
		openRequest.setNotice("123456");
		openRequest.setTribeType(0L);
		try {
			OpenimTribeCreateResponse openResponse = client.execute(openRequest);
			// {"openim_tribe_create_response":
			// {"tribe_info":{"check_mode":0,"name":"60883.0","notice":"123456","recv_flag":0,
			// "tribe_id":2410141833,"tribe_type":0},"request_id":"101zddx2enn3f"}}
			JSONObject res = JSONObject.fromObject(JSONObject
					.fromObject(JSONObject.fromObject(openResponse.getBody()).get("openim_tribe_create_response"))
					.get("tribe_info"));
			return res.getString("tribe_id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获得直播推流URL
	 * 
	 * @return
	 */
	public void liveUrl() {
		try {
			String liveNumber = null;
			String sql = null;
			// 过期时间戳(当前时间加上24个小时)
			long time = (System.currentTimeMillis() + (24 * 60 * 60 * 1000)) / 1000;
			Member member = (Member) getMobileUser();
			id = member.getId();
			Timestamp history = new Timestamp(System.currentTimeMillis());
			Live live = (Live) DataBaseConnection.getOne("select live_number from tb_live where member=" + id, null,
					Live.class);
			if (live.getLiveNumber() != null && !"".equals(live.getLiveNumber())) {
				liveNumber = live.getLiveNumber();
				sql = "update tb_live set time_out='" + time + "',live_history_time = '" + history + "' where member="
						+ id;
			} else {
				// 生成随机码
				String streamId = getStreamId(id);
				// 直播房间号
				liveNumber = new StringBuilder().append(BIZID).append("_").append(streamId).toString();
				sql = "update tb_live set live_state=1,live_number='" + liveNumber + "',time_out='" + time
						+ "',live_history_time = '" + history + "' where member=" + id;
			}
			// 直播推流的url
			String liveUrl = new StringBuilder("rtmp://").append(BIZID).append(".livepush.myqcloud.com/live/")
					.append(liveNumber).append("?bizid=").append(BIZID).append("&")
					.append(getSafeUrl(KEY, liveNumber, time)).toString();

			// 修改该主播的直播状态
			DataBaseConnection.updateData(sql, null);
			response(new JSONObject().accumulate("success", true).accumulate("url", liveUrl));
		} catch (Exception e) {
			response(e);
		}
	}

	/**
	 * 查询直播列表
	 */
	@SuppressWarnings("unchecked")
	public void liveList() {
		try {
			JSONArray jsonArray = new JSONArray();
			
			List<Live> lives = (List<Live>) service
					.findObjectBySql("from Live l where l.liveState in(0,1) order by l.liveState desc");
			
			for (Live live : lives) {
				if (live == null) {
					continue;
				}
				Member member = live.getMember();
				// 获取当前直播间的观看人数
				Object total_online = null;
				if (live.getLiveState() == 1) {
					total_online = getUserCount(live.getLiveNumber());
				} else {
					total_online = 0;
				}
				// 返回直播间的信息
				jsonArray.add(new JSONObject().accumulate("id", live.getId()).accumulate("memberId", member.getId())
						.accumulate("memberName", member.getName()).accumulate("memberImage", member.getImage())
						.accumulate("liveName", live.getLiveName()).accumulate("liveCost", live.getLiveCost())
						.accumulate("liveImage", live.getLiveImage()).accumulate("liveNotice", live.getLiveNotice())
						.accumulate("liveState", live.getLiveState()).accumulate("startTime", live.getStartTime())
						.accumulate("totalOnline", total_online).accumulate("tribeId", live.getTribeId()));
			}
			response(new JSONObject().accumulate("success", true).accumulate("items", jsonArray));
		} catch (Exception e) {
			response(e);
		}
	}

	/**
	 * 获取直播间信息和播放的Url
	 */
	@SuppressWarnings("unchecked")
	public void play() {
		try {
			Live live = null;
			// 当前用户
			String user = String.valueOf(getMobileUser().getId());
			String anchor = request.getParameter("anchor");
			// 进入的直播间信息
			if (anchor == null) {
				Product product = (Product) service.load(Product.class, id);
				anchor = product.getMember().getId().toString();
			}
			List<Live> lives = (List<Live>) service.findObjectBySql("from Live l where l.member.id = ?",
					Long.valueOf(anchor));
			// 超时时间
			String timeOut = null;
			if (lives != null && lives.size() > 0) {
				live = lives.get(0);
				Map<String, Object> map = DataBaseConnection.getOne(
						"select p.remark from tb_product_order po inner join"
								+ " tb_product p on po.product = p.id where po.status=1 and po.member=? and p.member=?"
								+ " and p.remark=? order by p.id desc limit 1",
						new Object[] { user, anchor, live.getTimeOut() });
				if (map != null && map.containsKey("remark")) {
					timeOut = (String) map.get("remark");
				}
			}

			// 检测当前用户是否付费,已付费获取视频地址,未付费生成费用订单
			if (timeOut != null || live.getLiveCost() == 0 || user.equals(String.valueOf(anchor))) {
				String liveNumber = (String) DataBaseConnection
						.getOne("select live_number from tb_live where member=" + anchor, null).get("live_number");
				if (liveNumber != null && !"".equals(liveNumber)) {
					JSONObject res = new JSONObject();
					JSONObject ret = new JSONObject();

					// 拼接播放url
					ret.accumulate("RTMP", new StringBuilder("rtmp://").append(BIZID)
							.append(".liveplay.myqcloud.com/live/").append(liveNumber).toString());
					ret.accumulate("FLV", new StringBuilder("http://").append(BIZID)
							.append(".liveplay.myqcloud.com/live/").append(liveNumber).append(".flv").toString());
					ret.accumulate("HLS", new StringBuilder("http://").append(BIZID)
							.append(".liveplay.myqcloud.com/live/").append(liveNumber).append(".m3u8").toString());

					Member member = live.getMember();
					// 获取直播间观看人数
					Object total_online = getUserCount(live.getLiveNumber());
					// 返回直播间信息和播放url
					res.accumulate("success", true).accumulate("playUrl", ret).accumulate("memberId", member.getId())
							.accumulate("memberName", member.getName()).accumulate("memberImage", member.getImage())
							.accumulate("liveName", live.getLiveName()).accumulate("liveCost", live.getLiveCost())
							.accumulate("liveImage", live.getLiveImage()).accumulate("liveNotice", live.getLiveNotice())
							.accumulate("liveState", live.getLiveState()).accumulate("startTime", live.getStartTime())
							.accumulate("totalOnline", total_online).accumulate("tribeId", live.getTribeId());
					response(res);
				}
			} else {
				// 生成直播付费商品
				Product pLive = new Product();
				pLive.setName(live.getLiveName());
				pLive.setCost(live.getLiveCost());
				pLive.setType("5");
				pLive.setMember(new Member(Long.valueOf(anchor)));
				pLive.setImage1(live.getLiveImage());
				pLive.setRemark(live.getTimeOut());
				pLive = (Product) service.saveOrUpdate(pLive);
				response(new JSONObject().accumulate("success", false).accumulate("message", "您还未对本次直播付费，请前去付费！")
						.accumulate("productId", pLive.getId()).accumulate("price", pLive.getCost())
						.accumulate("productType", 1));
			}
		} catch (Exception e) {
			response(e);
		}
	}

	/**
	 * 获取直播观看人数
	 */
	public String getUserCount(String streamId) {
		// 过期时间戳(当前时间加上24个小时)
		long time = System.currentTimeMillis() / 1000 + 60;
		StringBuilder url = new StringBuilder("http://statcgi.video.qcloud.com/common_access");
		url.append("?cmd=").append(APPID);
		url.append("&interface=Get_LivePlayStat");
		url.append("&Param.s.stream_id=").append(streamId);
		url.append("&sign=").append(getSign(API_KEY, time));
		url.append("&t=").append(time);
		String result = HttpRequestUtils.httpReques(url.toString(), null);
		JSONObject res = JSONObject.fromObject(result);
		try {
			if (!"null".equals(String.valueOf(res.get("output")))) {
				JSONObject outputJson = JSONObject.fromObject(res.get("output"));
				JSONArray streamInfo = JSONArray.fromObject(outputJson.get("stream_info"));
				result = streamInfo.getJSONObject(0).get("online").toString();
			} else {
				// 未直播直接返回人数0
				result = "0";
			}
		} catch (Exception e) {
			result = "0";
		}
		return result;
	}

	/**
	 * 关闭直播
	 */
	public void closeLive() {
		// 修改主播的直播状态
		Timestamp history = new Timestamp(System.currentTimeMillis());
		String sql = "update tb_live set live_state = '0',live_history_time = '" + history + "' where member ="
				+ getMobileUser().getId();
		int resultCount = DataBaseConnection.updateData(sql, null);
		if (resultCount > 0) {
			response(new JSONObject().accumulate("success", true));
		} else {
			response(new JSONObject().accumulate("success", false));
		}
	}

	/**
	 * 获得直播码 用户id + bizid + 随机四位数 转换成十六进制
	 * 
	 * @return
	 */
	private static String getStreamId(long id) {
		StringBuilder str = new StringBuilder();
		str.append(id).append(BIZID).append(Math.round(Math.random() * 10000));
		return Long.toHexString(Long.valueOf(str.toString()));
	}

	/**
	 * 获得url参数列表
	 */
	private static String getSafeUrl(String key, String streamId, long txTime) {
		String input = new StringBuilder().append(key).append(streamId).append(Long.toHexString(txTime).toUpperCase())
				.toString();

		String txSecret = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			txSecret = byteArrayToHexString(messageDigest.digest(input.getBytes("UTF-8")));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return txSecret == null ? ""
				: new StringBuilder().append("txSecret=").append(txSecret).append("&").append("txTime=")
						.append(Long.toHexString(txTime).toUpperCase()).toString();
	}

	/**
	 * 获得防盗链签名sign
	 */
	private static String getSign(String key, long txTime) {
		String input = new StringBuilder().append(key).append(txTime).toString();
		String txSecret = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			txSecret = byteArrayToHexString(messageDigest.digest(input.getBytes("UTF-8")));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return txSecret;
	}

	/**
	 * 加密
	 * 
	 * @return
	 */
	private static String byteArrayToHexString(byte[] data) {
		char[] out = new char[data.length << 1];

		for (int i = 0, j = 0; i < data.length; i++) {
			out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS_LOWER[0x0F & data[i]];
		}
		return new String(out);
	}

	/**
	 * getter and setter
	 * 
	 * @return
	 */
	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getAnchor() {
		return anchor;
	}

	public void setAnchor(Integer anchor) {
		this.anchor = anchor;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
}

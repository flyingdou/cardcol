package com.freegym.web.active;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import net.sf.json.JSONObject;

import org.hibernate.annotations.Where;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.basic.Member;
import com.freegym.web.common.Constants;
import com.freegym.web.order.ActiveOrder;
import com.sanmen.web.core.bean.CommonId;

@Entity
@Table(name = "tb_active")
public class Active extends CommonId {

	private static final long serialVersionUID = -2350286476947947115L;

	private String memo; // 增加+ memo：挑战注意事项

	private String content; // content：String,自定义挑战内容
	private String customTareget; // customTareget：String, 自定义挑战目标
	private String unit; // unit：String, 自定义挑战计量单位
	private String evaluationMethod; // evaluationMethod：String, 自定义挑战评定方式

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getJudgeID() {
		return judgeID == null ? "" : judgeID;
	}

	public void setJudgeID(String judgeID) {
		this.judgeID = judgeID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCustomTareget() {
		return customTareget;
	}

	public void setCustomTareget(String customTareget) {
		this.customTareget = customTareget;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getEvaluationMethod() {
		return evaluationMethod;
	}

	public void setEvaluationMethod(String evaluationMethod) {
		this.evaluationMethod = evaluationMethod;
	}

	@Column(name = "judgeID")
	private String judgeID; // judgeID：array, 选中裁判id
	/**
	 * 创建者
	 */
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "creator")
	private Member creator;

	/**
	 * 活动名称
	 */
	@Column(name = "name", length = 50, nullable = false)
	private String name;

	/**
	 * 活动模式。A个人挑战，B团体挑战
	 */
	private Character mode = 'A';

	/**
	 * 完成时间（天）
	 */
	private Integer days;

	/**
	 * 每个团体人数
	 */
	@Column(name = "team_num")
	private Integer teamNum;

	/**
	 * 活动目标，A体重减少，B体重增加，C运动时间，D运动次数，E自定义目标
	 */
	private Character target = 'A';

	/**
	 * 奖励
	 */
	@Column(name = "award", length = 200)
	private String award;

	/**
	 * 惩罚.处理机构
	 */
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "institution")
	private Member institution;

	/**
	 * 惩罚，金额
	 */
	@Column(name = "amerce_money", precision = 18, scale = 2)
	private Double amerceMoney;

	/**
	 * 裁判方式，A参加者选择裁判，B活动发起人为裁判
	 */
	@Column(name = "judge_mode")
	private Character judgeMode = 'A';

	/**
	 * 参加对象方式，A所有对象，B仅限所属会员
	 */
	@Column(name = "join_mode")
	private Character joinMode = 'A';

	/**
	 * 重量次数分类,A增加，B减少，C保持比率，D次数，E小时，F每周次数,G运动量KM,H运动量KG
	 */
	private Character category = 'A';

	/**
	 * 有氧运动的项目名称，如果是有氧则有此值
	 */
	private String action;

	/**
	 * 值，依据其目标、重量分类，其值会不同
	 */
	@Column(name = "value", precision = 10, scale = 2)
	private Double value;

	/**
	 * 状态，A关闭，B开启
	 */
	private Character status = 'A';

	/**
	 * 评价总人数
	 */

	@Column(name = "appraisenum", length = 10)
	private Integer appraiseNum;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;

	/**
	 * 置顶时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "top_time")
	private Date stickTime;

	@Column(name = "active_image", length = 50)
	private String image;

	/**
	 * 参与明细表(个人)
	 */
	@OneToMany(targetEntity = ActiveOrder.class, mappedBy = "active", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@OrderBy("orderDate desc")
	private Set<ActiveOrder> orders = new HashSet<ActiveOrder>();

	/**
	 * 参与明细表(按训练的数据降序)
	 */
	@OneToMany(targetEntity = ActiveOrder.class, mappedBy = "active", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@OrderBy("value desc")
	@Where(clause = "status <> '0'")
	private Set<ActiveOrder> sorts = new HashSet<ActiveOrder>();

	/**
	 * 参与明细表(个人)
	 */
	@OneToMany(targetEntity = ActiveJudge.class, mappedBy = "active", cascade = { CascadeType.REMOVE, CascadeType.MERGE }, fetch = FetchType.LAZY)
	private Set<ActiveJudge> judges = new HashSet<ActiveJudge>();

	public Active() {

	}

	public Active(long id) {
		setId(id);
	}

	public Member getCreator() {
		return creator;
	}

	public void setCreator(Member creator) {
		this.creator = creator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Character getMode() {
		return mode;
	}

	public void setMode(Character mode) {
		this.mode = mode;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getTeamNum() {
		return teamNum;
	}

	public void setTeamNum(Integer teamNum) {
		this.teamNum = teamNum;
	}

	public Character getTarget() {
		return target;
	}

	public void setTarget(Character target) {
		this.target = target;
	}

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public Member getInstitution() {
		return institution;
	}

	public void setInstitution(Member institution) {
		this.institution = institution;
	}

	public Double getAmerceMoney() {
		return amerceMoney;
	}

	public void setAmerceMoney(Double amerceMoney) {
		this.amerceMoney = amerceMoney;
	}

	public Character getJudgeMode() {
		return judgeMode;
	}

	public void setJudgeMode(Character judgeMode) {
		this.judgeMode = judgeMode;
	}

	public Character getJoinMode() {
		return joinMode;
	}

	public void setJoinMode(Character joinMode) {
		this.joinMode = joinMode;
	}

	public Character getCategory() {
		return category;
	}

	public void setCategory(Character category) {
		this.category = category;
	}

	public String getAction() {
		return action == null ? "" : action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public Integer getAppraiseNum() {
		return appraiseNum;
	}

	public void setAppraiseNum(Integer appraiseNum) {
		this.appraiseNum = appraiseNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getStickTime() {
		return stickTime;
	}

	public void setStickTime(Date stickTime) {
		this.stickTime = stickTime;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Set<ActiveOrder> getOrders() {
		return orders;
	}

	public void setOrders(Set<ActiveOrder> orders) {
		this.orders = orders;
	}

	public Set<ActiveOrder> getSorts() {
		return sorts;
	}

	public void setSorts(Set<ActiveOrder> sorts) {
		this.sorts = sorts;
	}

	public Set<ActiveJudge> getJudges() {
		return judges;
	}

	public void setJudges(Set<ActiveJudge> judges) {
		this.judges = judges;
	}

	@Override
	public String getTableName() {
		return "活动信息表";
	}

	public static DetachedCriteria getCriteriaQuery(Active query) {
		final DetachedCriteria dc = DetachedCriteria.forClass(Active.class);
		dc.createAlias("creator", "c");
		if (query != null) {
			if (query.getMode() != null) dc.add(Restrictions.eq("mode", query.getMode()));
			if (query.getTarget() != null) dc.add(Restrictions.eq("target", query.getTarget()));
		}
		return dc;
	}

	public static String getActiveSql(String mode, String target, String circle) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM tb_active a LEFT JOIN (SELECT active, COUNT(*) partake_num FROM tb_active_order WHERE STATUS != '0' GROUP BY active) b ON a.id = b.active ");
		sql.append(
				"LEFT JOIN (SELECT m3.id product_id, COUNT(*) emp_num, round(SUM(IFNULL(m1.grade, 0)) / COUNT(*)) avg_grade FROM tb_member_appraise m1 LEFT JOIN tb_active_order m2 ON m1.order_id = m2.id LEFT JOIN tb_active m3 ON m2.active = m3.id  WHERE m1.order_type = '2') c ON a.id = c.product_id ");
		sql.append("LEFT JOIN (SELECT id memberId, name creatorName FROM tb_member) d ON d.memberId = a.creator ");
		sql.append("LEFT JOIN (SELECT id institutionId,name institutionName FROM tb_member) e ON e.institutionId = a.institution");
		sql.append(" where 1=1 ");
		if (mode != null && !"".equals(mode)) {
			sql.append(" and a.mode = '").append(mode).append("'");
		}
		if (target != null && !"".equals(target)) {
			sql.append(" and a.target = '").append(target).append("'");
		}
		if (circle != null && !"".equals(circle)) {
			if ("A".equals(circle)) {
				sql.append(" and a.days < ").append(Constants.ACTIVE_DATE1);
			} else if ("B".equals(circle)) {
				sql.append(" and a.days >= ").append(Constants.ACTIVE_DATE1).append(" and a.days <=").append(Constants.ACTIVE_DATE2);
			} else if ("C".equals(circle)) {
				sql.append(" and a.days > ").append(Constants.ACTIVE_DATE2).append(" and a.days <=").append(Constants.ACTIVE_DATE3);
			} else if ("D".equals(circle)) {
				sql.append(" and a.days > ").append(Constants.ACTIVE_DATE3);
			}
		}
		return sql.toString();
	}
	
	public static String getActiveSql2(String mode, String target, String circle) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM tb_active a LEFT JOIN (SELECT active, COUNT(*) partake_num FROM tb_active_order WHERE STATUS != '0' GROUP BY active) b ON a.id = b.active ");
		sql.append(
				"LEFT JOIN (SELECT m3.id product_id, COUNT(*) emp_num, round(SUM(IFNULL(m1.grade, 0)) / COUNT(*)) avg_grade FROM tb_member_appraise m1 LEFT JOIN tb_active_order m2 ON m1.order_id = m2.id LEFT JOIN tb_active m3 ON m2.active = m3.id  WHERE m1.order_type = '2') c ON a.id = c.product_id ");
		sql.append("LEFT JOIN (SELECT id memberId, name creatorName FROM tb_member) d ON d.memberId = a.creator ");
		sql.append("LEFT JOIN (SELECT id institutionId,name institutionName FROM tb_member) e ON e.institutionId = a.institution");
		sql.append(" where a.status = 'B'");
		if (mode != null && !"".equals(mode)) {
			sql.append(" and a.mode = '").append(mode).append("'");
		}
		if (target != null && !"".equals(target)) {
			sql.append(" and a.target = '").append(target).append("'");
		}
		if (circle != null && !"".equals(circle)) {
			if ("A".equals(circle)) {
				sql.append(" and a.days < ").append(Constants.ACTIVE_DATE1);
			} else if ("B".equals(circle)) {
				sql.append(" and a.days >= ").append(Constants.ACTIVE_DATE1).append(" and a.days <=").append(Constants.ACTIVE_DATE2);
			} else if ("C".equals(circle)) {
				sql.append(" and a.days > ").append(Constants.ACTIVE_DATE2).append(" and a.days <=").append(Constants.ACTIVE_DATE3);
			} else if ("D".equals(circle)) {
				sql.append(" and a.days > ").append(Constants.ACTIVE_DATE3);
			}
		}
		return sql.toString();
	}

	public static String getActiveModeSql(String mode) {
		StringBuffer sql = new StringBuffer();
		if ("modeA".endsWith(mode)) {
			sql.append("select sum(a.weight) as weight ");
		} else if ("modeB".endsWith(mode)) {
			sql.append("select sum(a.actionQuan) as quantity ");
		} else if ("modeC".endsWith(mode)) {
			sql.append("select count(*) as zcs ");
		} else if ("modeD".endsWith(mode)) {
			sql.append("select sum(a.times) / 60 as times ");
		}
		sql.append("from tb_plan_record a, tb_active_order b, tb_active c where a.active_order = b.id and b.active = c.id and b.active is not null");
		return sql.toString();

	}

	public JSONObject toJson() {
		final JSONObject obj = new JSONObject();
		obj.accumulate("id", getId()).accumulate("name", getName()).accumulate("judgeID", getJudgeID());
		return obj;
	}

	public void addJudge(ActiveJudge judge) {
		if (judge != null) {
			judge.setActive(this);
			getJudges().add(judge);
		}
	}
}

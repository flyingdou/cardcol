package com.freegym.web.plan.gen.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.freegym.web.basic.Member;
import com.freegym.web.config.Action;
import com.freegym.web.config.Setting;
import com.freegym.web.plan.Course;
import com.freegym.web.plan.Workout;
import com.freegym.web.plan.WorkoutDetail;
import com.freegym.web.plan.gen.AbstractGenerator;
import com.freegym.web.service.IBasicService;
import com.sanmen.web.core.common.LogicException;

import fr.expression4j.core.Expression;
import fr.expression4j.core.Parameters;
import fr.expression4j.core.exception.EvalException;
import fr.expression4j.core.exception.ParsingException;
import fr.expression4j.factory.ExpressionFactory;

public class WangyGeneratorImpl extends AbstractGenerator {
	protected final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	// 运动频率 1:力量 2:有氧 0:休息
	private final int[] low_frequency = new int[] { 1, 2, 1, 2, 1, 2, 0, 1, 2, 1, 2, 1, 2, 0, 1, 2, 1, 2, 1, 2, 0, 1, 2, 1, 2, 1, 2, 0 };
	private final int[] medium_frequency = new int[] { 1, 1, 2, 1, 1, 2, 0, 1, 1, 2, 1, 1, 2, 0, 1, 1, 2, 1, 1, 2, 0, 1, 1, 2, 1, 1, 2, 0 };
	private final int[] high_frequency = new int[] { 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 1, 2 };
	// 动作数量 (1: 每个部位1个动作, 2: 每个部位2个动作, 3: 每个部位3个动作)
	private final int[] low_num = new int[] { 1 };
	private final int[] medium_num = new int[] { 2 };
	private final int[] high_num = new int[] { 3 };
	// 动作类型
	private final String[] low_playtype = new String[] { "胸部,肱二头肌,大腿,臀部,肩部,背部,肱三头肌,小腿,腰部,腹部" };
	private final String[] medium_playtype = new String[] { "胸部,肱二头肌,肩部,背部,肱三头肌", "大腿,臀部,小腿,腰部,腹部" };
	private final String[] high_playtype = new String[] { "胸部,肩部,肱三头肌,腹部", "大腿,小腿,臀部", "背部,肱二头肌,腰部" };
	// 运行组数 (第天运动几组)
	private final int[] low_group = new int[] { 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 };
	private final int[] medium_group = new int[] { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 };
	private final int[] high_group = new int[] { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 };
	// 减脂塑形 - 动作次数
	private final int[] item_rw_one = new int[] { 20, 16, 12, 9, 6 }; // 第一个等级（5以内）
	private final int[] item_rw_two = new int[] { 25, 20, 15, 10, 6 }; // 第二级（5-10）
	private final int[] item_rw_three = new int[] { 30, 25, 20, 15, 10 }; // 第三级（10-15）
	// 健美增肌 - 动作次数
	private final int[] item_muscle_one = new int[] { 16, 12, 9, 6, 4 }; // 腰围标准（胖瘦）
	private final int[] item_muscle_two = new int[] { 15, 11, 8, 6, 5 }; // 标准偏瘦
	private final int[] item_muscle_three = new int[] { 12, 9, 6, 4, 4 }; // 瘦
	// 增加力量 - 动作次数
	private final int[] item_add_strength = new int[] { 3, 3, 2, 2, 1, 1 };

	// 用于计算动作使用器械的重量的参数
	private final int[] p_w_percentage = new int[] { 100, 90, 85, 80, 75, 70, 60, 50, 45 };
	private final int[] p_w_rm = new int[] { 1, 3, 6, 9, 12, 16, 20, 25, 30 };

	public WangyGeneratorImpl(IBasicService service, Long coachId, Long memberId, Date startDate) {
		super(service, coachId, memberId, startDate);
	}

	@Override
	public boolean generator() throws Exception {
		final Member m = (Member) service.load(Member.class, memberId);
		// 获取性别
		String sex = m.getSex();
		if (sex == null || "".equals(sex)) throw new LogicException("你的性别尚未设置！");
		final Setting setting = service.loadSetting(memberId);
		if (setting == null) throw new LogicException("请先完成您的设置！");
		if (setting.getHeight() == null || "".equals(setting.getHeight())) throw new LogicException("您的身高尚未设置！");
		if (setting.getWeight() == null || "".equals(setting.getWeight())) throw new LogicException("您的体重尚未设置！");
		if (setting.getWaistline() == null || "".equals(setting.getWaistline())) throw new LogicException("您的腰围尚未设置！");
		if (setting.getFavoriateCardio() == null || "".equals(setting.getFavoriateCardio())) throw new LogicException("您尚未设置所喜爱的有氧训练项目!");
		Double maxwm = setting.getMaxwm();
		if (maxwm == null || "".equals(maxwm)) throw new LogicException("您的最大卧推重量尚未设置！");
		// 健身目的
		String target = setting.getTarget();
		if (target == null || "".equals(target)) throw new LogicException("您的健身目的尚未设置！");

		getCourseInfo(target);

		// 运动频率,动作数量,动作组数
		int[] frequency, num, group;
		String[] playtype; // 动作类型
		// 根据腰围确定胖瘦
		int pagshou = findPangShou(sex, setting.getHeight(), setting.getWaistline());
		// 根据胖瘦与训练目的获取动作次数(RM)
		int[] rm = this.getRM(pagshou, target);
		// 体能判断
		int physicalfitness = getPhysicalfitness(setting.getMaxwm(), setting.getWeight());
		// 根据体能来确定运动频率,动作数量,动作组数,动作类型
		log.error("======胖瘦: " + pagshou + "======体能: " + physicalfitness + "=======");
		if (physicalfitness == 1) {
			frequency = low_frequency;
			num = low_num;
			group = low_group;
			playtype = low_playtype;
		} else if (physicalfitness == 2) {
			frequency = medium_frequency;
			num = medium_num;
			group = medium_group;
			playtype = medium_playtype;
		} else {
			frequency = high_frequency;
			num = high_num;
			group = high_group;
			playtype = high_playtype;
		}

		// 这个时间应该用户在付款时填写的开始时间
		List<?> coures = service.findObjectBySql("from Course c where c.member.id = ? and c.planDate >= ?", memberId, sdf.format(startDate));
		for (final Iterator<?> it = coures.iterator(); it.hasNext();) {
			final Course c = (Course) it.next();
			service.delete(c);
		}
		this.createPlan(setting, maxwm, frequency, num, group, playtype, rm, startDate);

		return true;
	}

	private void createPlan(Setting setting, Double maxwm, int[] frequency, int[] num, int[] group, String[] playtype, int[] rm, Date startDate) {
		// 一共四周 (28天)
		final Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		int typelen = 0;
		final List<Course> courses = new ArrayList<Course>();
		for (int i = 0; i < frequency.length; i++) {
			if (frequency[i] == 1) { // 力量
				final Course c = getCourse(cal, sTime, eTime);
				c.setMemo("请您在正式训练前进行10分钟左右的热身，身体微微出汗即可。训练结束后进行进行全身拉伸训练放松。");
				if (num[0] == 3) c.setMemo(c.getMemo() + "\n有氧训练也可以选择一个力量训练日进行，但要与力量训练间隔8个小时。");
				courses.add(c);
				if (typelen >= playtype.length) {
					typelen = 0;
				}
				// 取得天数
				String[] types = playtype[typelen++].split(",");
				for (int z = 0; z < types.length; z++) {
					final String type = types[z];
					final List<Action> actions = service.findActionByCoachAndPart('1', 1l, type);
					final List<Action> acts = new ArrayList<Action>();
					// 写个方法去获取 playtype 对应的 动作
					for (int m = 0; m < num[0]; m++) {
						Action a = getRandomAction(actions, acts);
						final Workout wo = getWorkout(a, c, z);
						c.addWorkout(wo);
						Double playweith = getPlayWeigh(a, maxwm, rm[0]);// 力量
						for (int n = 0; n < group[i]; n++) {
							Double playtime = new Double(rm[n]);
							if (n > 0) playweith = null;
							final WorkoutDetail wd = getWorkoutDetail(playweith, playtime, n);
							wd.setPlanIntervalSeconds(getIntervalSeconds(setting.getTarget()));
							wo.addDetail(wd);
						}
					}
				}
			} else if (frequency[i] == 2) { // 有氧
				final Course c = getCourse(cal, sTime, eTime);
				c.setMemo("请您在正式训练前进行10分钟左右的热身，身体微微出汗即可。训练结束后进行进行全身拉伸训练放松。");
				if (num[0] == 3) c.setMemo(c.getMemo() + "\n有氧训练也可以选择一个力量训练日进行，但要与力量训练间隔8个小时。");
				courses.add(c);
				final List<Action> catalogs = service.getFavoriateCardioCatalogs(setting.getFavoriateCardio(), 1l);
				if (catalogs.size() > 0) {
					final Random r = new Random();
					final Action a = catalogs.get(r.nextInt(catalogs.size()));
					final Workout wo = getWorkout(a, c, 0);
					c.addWorkout(wo);
					final WorkoutDetail wd = new WorkoutDetail();
					wd.setPlanDuration("2400");
					wo.addDetail(wd);
				}
			} else {
				// 休息
				// do nothing
			}
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		service.saveOrUpdate(courses);
	}

	private WorkoutDetail getWorkoutDetail(Double playweith, Double playtime, int sort) {
		final WorkoutDetail wd = new WorkoutDetail();
		wd.setPlanWeight(handleWeight(playweith));
		wd.setPlanTimes(playtime == null ? "" : String.valueOf(playtime.intValue()));
		wd.setPlanDuration(String.valueOf(playtime.intValue() * 2));
		wd.setSort(sort);
		return wd;
	}

	private String handleWeight(Double playweith) {
		if (playweith == null) return "";
		if (playweith < 10 && playweith > 0) return "5";
		String weight = String.valueOf(playweith.intValue());
		char c = ' ';
		if (weight.length() > 1) c = weight.charAt(weight.length() - 1);
		else c = weight.charAt(0);
		if (c <= '4') c = '0';
		else c = '5';
		return weight.length() > 1 ? weight.substring(0, weight.length() - 1) + String.valueOf(c) : String.valueOf(c);
	}

	private Workout getWorkout(Action a, Course c, int m) {
		final Workout wo = new Workout();
		wo.setAction(a);
		wo.setCourse(c);
		wo.setSort(m);
		return wo;
	}

	private Course getCourse(Calendar cal, String sTime, String eTime) {
		final Course c = new Course();
		c.setPlanDate(sdf.format(cal.getTime()));
		c.setCourseInfo(ci);
		c.setCycleCount(1);
		c.setCountdown(10);
		c.setMember(new Member(memberId));
		c.setCreateTime(new Date());
		c.setStartTime(sTime);
		c.setEndTime(eTime);
		c.setHourPrice(0d);
		c.setMemberPrice(0d);
		c.setPlanSource("3");
		c.setPlace("");
		c.setCoach(new Member(memberId));
		return c;
	}

	private Action getRandomAction(List<Action> actions, List<Action> acts) {
		Random random = new Random();
		if (actions.size() > 0) {
			Action a = actions.get(random.nextInt(actions.size()));
			while (exists(a, acts)) {
				a = actions.get(random.nextInt(actions.size()));
			}
			acts.add(a);
			return a;
		}
		return null;
	}

	private boolean exists(Action a, List<Action> acts) {
		boolean isExist = false;
		for (final Action a1 : acts) {
			if (a1.getId().equals(a.getId())) {
				isExist = true;
				break;
			}
		}
		return isExist;
	}

	// 获取使用器械的重量
	private Double getPlayWeigh(Action a, Double maxwm, int rm) {
		Double _playweight = null;
		for (int i = 0; a != null && i < p_w_rm.length; i++) {
			if (rm <= p_w_rm[i]) {
				// 根据提供的一个系数表来计算获取相应重量
				String ful = maxwm + "*(" + p_w_percentage[i] + "/100)" + (a.getCoefficient() == null ? "*1" : a.getCoefficient());
				return getPlayWeight(ful);
			}
		}
		return _playweight;
	}

	private Double getPlayWeight(String formula) {
		try {
			Expression exp = ExpressionFactory.createExpression("f(x,y,z)=" + formula);
			Parameters parms = ExpressionFactory.createParameters();
			return exp.evaluate(parms).getRealValue();
		} catch (ParsingException e) {
			e.printStackTrace();
		} catch (EvalException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 根据胖瘦与训练目的获取动作次数(RM)
	private int[] getRM(int pagshou, String target) {
		if ("1".equals(target)) {
			if (pagshou == 3) {
				return item_rw_three;
			} else if (pagshou == 2) {
				return item_rw_two;
			} else {
				return item_rw_one;
			}
		} else if ("2".equals(target)) {
			if (pagshou == 6) {
				return item_muscle_three;
			} else if (pagshou == 5) {
				return item_muscle_two;
			} else {
				return item_muscle_one;
			}
		} else if ("3".equals(target)) {
			return item_add_strength;
		} else {
			return null;
		}
	}

	// 体能判断
	// 1. 初， 2. 中。 3. 高
	private int getPhysicalfitness(Double maxwm, Double weight) {
		Double val = maxwm / weight;
		if (val < 1) {
			return 1;
		} else if (val < 1.3) {
			return 2;
		} else {
			return 3;
		}
	}

	// 标准腰围： 男性：身高-100
	// 女性：身高-95
	// 1. 超重：>=0，小于5 2. 肥胖：5-10 3.超胖：大于11
	// 4. 标准：（-5）-0 5. 偏瘦：（-10）-（-5） 6. 瘦：小于-11
	private int findPangShou(String sex, int height, Double waistline) {
		int flag = 0;
		Double val = 0d;
		if ("M".equals(sex)) {
			val = waistline - (height - 95);
		} else if ("F".equals(sex)) {
			val = waistline - (height - 100);
		} else {
			// error
			return 0;
		}

		if (val >= 11) {
			flag = 3;
		} else if (val >= 5) {
			flag = 2;
		} else if (val >= 0) {
			flag = 1;
		} else if (val <= -11) {
			flag = 6;
		} else if (val <= -5) {
			flag = 5;
		} else {
			flag = 4;
		}

		return flag;
	}

}

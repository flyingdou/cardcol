package com.freegym.web.utils;

import com.freegym.web.basic.Body;

public class BodyUtils {

	public static String handlerBody(Body body) {
		final StringBuffer sb = new StringBuffer();
		getConclusionForHead(sb, body);
		getConclusionForCervical(sb, body);
		getConclusionForShoulder(sb, body);
		getConclusionForScapul(sb, body);
		getConclusionForThoracic(sb, body);
		getConclusionForLumbar(sb, body);
		getConclusionForKnee(sb, body);
		getConclusionForPelvis(sb, body);
		getConclusionForFoot(sb, body);
		if ("".equals(sb) || sb.length() <= 0) {
			sb.append("您的体态很好，请继续保持!");
		}
		return sb.toString();
	}

	private static void getConclusionForHead(StringBuffer sb, Body body) {
		final String headBack = body.getHeadBack();
		final String headSide = body.getHeadSide();
		if ((headBack != null && (headBack.equals("B") || headBack.equals("C"))) || (headSide != null && (headSide.equals("B") || headSide.equals("C")))) {
			sb.append("头部体态不正常会引起失眠、落枕、头痛、肩颈酸痛。可通过肩颈伸展运动、热敷舒缓。\n");
		}
	}

	private static void getConclusionForCervical(final StringBuffer sb, Body body) {
		final String cervical = body.getCervicalSide();
		if (cervical != null && cervical.equals("B")) sb.append("颈椎不正常曲度会使人的精神压抑、倦怠乏力、失眠、记忆力减退。可通过肩颈伸展运动、热敷放松。\n");
	}

	private static void getConclusionForShoulder(final StringBuffer sb, final Body body) {
		final String shoulder = body.getShoulderBack();
		if (shoulder != null && (shoulder.equals("B") || shoulder.equals("C"))) sb.append("肩部体态不正常会使脊椎和髋骨变形，造成胸廓压迫心肺，严重者可造成神经功能损伤。可加强或放松胸部、肩部肌肉矫正。\n");
	}

	private static void getConclusionForScapul(final StringBuffer sb, final Body body) {
		final String scapul = body.getScapulaSide();
		if (scapul != null && scapul.equals("B")) sb.append("\n");
	}

	private static void getConclusionForThoracic(final StringBuffer sb, final Body body) {
		final String thoracicSide = body.getThoracicSide();
		final String thoracicBack = body.getThoracicBack();
		if ((thoracicSide != null && thoracicSide.equals("B")) || (thoracicBack != null && (thoracicBack.equals("B") || thoracicBack.equals("C"))))
			sb.append("弯曲的脊椎容易造成压迫退化影响到神经和心肺功能。通过补充钙质和背部伸展运动可改善。\n");
	}

	private static void getConclusionForLumbar(final StringBuffer sb, final Body body) {
		final String lumbarSide = body.getLumbarSide();
		final String lumbarBack = body.getLumbarBack();
		if ((lumbarSide != null && lumbarSide.equals("B")) || (lumbarBack != null && (lumbarBack.equals("B") || lumbarBack.equals("C"))))
			sb.append("腰椎弯曲会导致椎骨变位、滑脱，腰部疼痛、无力。通过补充葡萄糖胺、腰肌强化运动和伸展运动解决。\n");
	}

	private static void getConclusionForPelvis(final StringBuffer sb, final Body body) {
		final String pelviSide = body.getPelvisSide();
		final String pelviBack = body.getPelvisBack();
		if ((pelviSide != null && (pelviSide.equals("B") || pelviSide.equals("C"))) || (pelviBack != null && pelviBack.equals("B")))
			sb.append("骨盆是身体重心，能牵制身体各部位体态，影响身体曲线和内脏器官功能。通过桥式伸展运动或低弓箭步可回到正确位置。\n");
	}

	private static void getConclusionForKnee(final StringBuffer sb, final Body body) {
		final String kneeSide = body.getKneeSide();
		final String kneeBack = body.getKneeBack();
		if ((kneeSide != null && (kneeSide.equals("B") || kneeSide.equals("C"))) || (kneeBack != null && (kneeBack.equals("B") || kneeBack.equals("C"))))
			sb.append("膝关节变形使骨盆、腰椎受力失衡，造成膝盖内侧肌肉发炎酸痛。补充葡萄糖胺，使用矫正鞋垫和四头肌强化运动会有效果。\n");
	}

	private static void getConclusionForFoot(final StringBuffer sb, final Body body) {
		final String foot = body.getFootSide();
		if (foot != null && foot.equals("B")) sb.append("扁平足导致走路外八字，左右晃动。通过小腿肌肉伸展和足底筋膜放松运动效果显著。\n");
	}
}

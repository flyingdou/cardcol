package com.freegym.web.order;

import com.sanmen.web.core.bean.CommonId;

public class Statistic extends CommonId {

	private static final long serialVersionUID = -7358255008331013507L;

	private Double statData1;// 统计数据1

	private Double statData2;// 统计数据2

	private Double statData3;// 统计数据3

	private Double statData4;// 统计数据4

	private Double statData5;// 统计数据5

	private Double statData6;// 统计数据6

	public Double getStatData1() {
		return statData1;
	}

	public void setStatData1(Double statData1) {
		this.statData1 = statData1;
	}

	public Double getStatData2() {
		return statData2;
	}

	public void setStatData2(Double statData2) {
		this.statData2 = statData2;
	}

	public Double getStatData3() {
		return statData3;
	}

	public void setStatData3(Double statData3) {
		this.statData3 = statData3;
	}

	public Double getStatData4() {
		return statData4;
	}

	public void setStatData4(Double statData4) {
		this.statData4 = statData4;
	}

	public Double getStatData5() {
		return statData5;
	}

	public void setStatData5(Double statData5) {
		this.statData5 = statData5;
	}

	public Double getStatData6() {
		return statData6;
	}

	public void setStatData6(Double statData6) {
		this.statData6 = statData6;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

}

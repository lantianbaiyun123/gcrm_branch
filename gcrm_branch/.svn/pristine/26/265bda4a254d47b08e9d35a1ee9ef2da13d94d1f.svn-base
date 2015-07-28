package com.baidu.gcrm.bpm.web.helper;

public enum ProcessStatus {
	ALL("", -1), RUN("运行中", 0), COMPLETE("已完成", 1), SUSPEND("已暂停", 2), TERMINAL("已终止", 3);

	public final String status;
	public final int value;

	private ProcessStatus(String status, int value) {
		this.status = status;
		this.value = value;
	}

	public static ProcessStatus findStatus(int value) {
		switch (value) {
			case 0 :
				return RUN;
			case 1 :
				return COMPLETE;
			case 2 :
				return SUSPEND;
			case 3 :
				return TERMINAL;
			default :
				return ALL;
		}
	}

	public String getStatus() {
		return status;
	}

	public int getValue() {
		return value;
	}
}
package com.pereira.kafka.commons.entity;

public enum SchedulerMode {
	START("START"), SHUTDOWN("SHUTDOWN"), STANDBY("STANDBY");

	private String value;

	SchedulerMode(final String value) {
		this.value = value;
	}

	public static SchedulerMode forName(final String val) {
		for (SchedulerMode schedulerMode : SchedulerMode.values()) {
			if (schedulerMode.value.equalsIgnoreCase(val)) {
				return schedulerMode;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.value;
	}
}

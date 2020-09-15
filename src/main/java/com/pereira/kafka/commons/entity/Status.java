package com.pereira.kafka.commons.entity;

public enum Status {

	SUCCESS("SUCCESS"), FAILURE("FAILURE");

	private String value;

	Status(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}
}
package com.pereira.kafka.commons.entity;

import lombok.Data;

@Data
public class Retry {
	private Integer count;
	private Integer maxRetries;
	private Integer base;
	private Integer delay;
}

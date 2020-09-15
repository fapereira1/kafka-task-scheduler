package com.pereira.kafka.commons.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Task {

	private String taskId;
	private SchedulingType schedlingType;
	private String taskType;
	private String taskMetadata;
	private Retry retry;
	private Integer priority;
	private LocalDateTime executionTime;
	private String cronexp;

	public Task(SchedulingType type) {
		this.schedlingType = type;
	}

}

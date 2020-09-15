package com.pereira.kafka.commons.serializer;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.core.serializer.Serializer;

import com.pereira.kafka.commons.entity.Task;

public class TaskSerializer implements Serializer<Task> {

	@Override
	public void serialize(Task object, OutputStream outputStream) throws IOException {

	}

}

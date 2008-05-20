/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.integration.router.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.channel.MessageChannel;
import org.springframework.integration.message.Message;
import org.springframework.integration.message.StringMessage;

/**
 * @author Mark Fisher
 */
public class SplitterParserTests {

	@Test
	public void testSplitter() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"splitterParserTests.xml", this.getClass());
		context.start();
		MessageChannel channel1 = (MessageChannel) context.getBean("channel1");
		MessageChannel channel2 = (MessageChannel) context.getBean("channel2");
		channel1.send(new StringMessage("this.is.a.test"));
		Message<?> result1 = channel2.receive(1000);
		assertEquals("this", result1.getPayload());
		Message<?> result2 = channel2.receive(1000);
		assertEquals("is", result2.getPayload());
		Message<?> result3 = channel2.receive(1000);
		assertEquals("a", result3.getPayload());
		Message<?> result4 = channel2.receive(1000);
		assertEquals("test", result4.getPayload());
		assertNull(channel2.receive(0));
	}

}

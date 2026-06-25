package org.springframework.cloud.stream.binder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.messaging.Message;

/**
 * Common consumer properties - spring.cloud.stream.bindings.[destinationName].consumer.
 *
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ConsumerProperties {

	/**
	 * Binding name for this consumer binding.
	 */
	private String bindingName;

	/**
	 * Signals if this consumer needs to be started automatically.
	 *
	 * Default: true
	 */
	private boolean autoStartup = true;

	/**
	 * The concurrency setting of the consumer. Default: 1.
	 */
	private int concurrency = 1;

	/**
	 * Whether the consumer receives data from a partitioned producer. Default: 'false'.
	 */
	private boolean partitioned;

	/**
	 * When set to a value greater than equal to zero, allows customizing the instance
	 * count of this consumer (if different from spring.cloud.stream.instanceCount). When
	 * set to a negative value, it will default to spring.cloud.stream.instanceCount. See
	 * that property for more information. Default: -1 NOTE: This setting will override
	 * the one set in 'spring.cloud.stream.instance-count'
	 */
	private int instanceCount = -1;

	/**
	 * When set to a value greater than equal to zero, allows customizing the instance
	 * index of this consumer (if different from spring.cloud.stream.instanceIndex). When
	 * set to a negative value, it will default to spring.cloud.stream.instanceIndex. See
	 * that property for more information. Default: -1 NOTE: This setting will override
	 * the one set in 'spring.cloud.stream.instance-index'
	 */
	private int instanceIndex = -1;

	/**
	 * When set it will allow the customization of the consumer to spawn a consumer for
	 * each item in the list. All negative indexes will be discarded. Default: null
	 * NOTE: This setting will disable the instance-index
	 */
	private List<Integer> instanceIndexList;

	/**
	 * The number of attempts to process the message (including the first) in the event of
	 * processing failures. This is a RetryTemplate configuration which is provided by the
	 * framework. Default: 3. Set to 1 to disable retry. You can also provide custom
	 * RetryTemplate in the event you want to take complete control of the RetryTemplate.
	 * Simply configure it as @Bean inside your application configuration.
	 */
	private int maxAttempts = 3;

	/**
	 * The backoff initial interval on retry. This is a RetryTemplate configuration which
	 * is provided by the framework. Default: 1000 ms. You can also provide custom
	 * RetryTemplate in the event you want to take complete control of the RetryTemplate.
	 * Simply configure it as @Bean inside your application configuration.
	 */
	private int backOffInitialInterval = 1000;

	/**
	 * The maximum backoff interval. This is a RetryTemplate configuration which is
	 * provided by the framework. Default: 10000 ms. You can also provide custom
	 * RetryTemplate in the event you want to take complete control of the RetryTemplate.
	 * Simply configure it as @Bean inside your application configuration.
	 */
	private int backOffMaxInterval = 10000;

	/**
	 * The backoff multiplier.This is a RetryTemplate configuration which is provided by
	 * the framework. Default: 2.0. You can also provide custom RetryTemplate in the event
	 * you want to take complete control of the RetryTemplate. Simply configure it
	 * as @Bean inside your application configuration.
	 */
	private double backOffMultiplier = 2.0;

	/**
	 * Whether exceptions thrown by the listener that are not listed in the
	 * 'retryableExceptions' are retryable.
	 */
	private boolean defaultRetryable = true;

	/**
	 * Allows you to further qualify which RetryTemplate to use for a specific consumer
	 * binding..
	 */
	private String retryTemplateName;

	/**
	 * A map of Throwable class names in the key and a boolean in the value. Specify those
	 * exceptions (and subclasses) that will or won't be retried.
	 */
	private Map<Class<? extends Throwable>, Boolean> retryableExceptions = new LinkedHashMap<>();

	/**
	 * When set to none, disables header parsing on input. Effective only for messaging
	 * middleware that does not support message headers natively and requires header
	 * embedding. This option is useful when consuming data from non-Spring Cloud Stream
	 * applications when native headers are not supported. When set to headers, uses the
	 * middleware’s native header mechanism. When set to 'embeddedHeaders', embeds headers
	 * into the message payload. Default: depends on binder implementation. Rabbit and
	 * Kafka binders currently distributed with spring cloud stream support headers
	 * natively.
	 */
	private HeaderMode headerMode;

	/**
	 * When set to true, the inbound message is deserialized directly by client library,
	 * which must be configured correspondingly (e.g. setting an appropriate Kafka
	 * producer value serializer). NOTE: This is binder specific setting which has no
	 * effect if binder does not support native serialization/deserialization. Currently
	 * only Kafka binder supports it. Default: 'false'
	 */
	private boolean useNativeDecoding;

	/**
	 * When set to true, the underlying binder will natively multiplex destinations on the
	 * same input binding. For example, in the case of a comma separated multiple
	 * destinations, the core framework will skip binding them individually if this is set
	 * to true, but delegate that responsibility to the binder.
	 *
	 * By default this property is set to `false` and the binder will individually bind
	 * each destinations in case of a comma separated multi destination list. The
	 * individual binder implementations that need to support multiple input bindings
	 * natively (multiplex) can enable this property.
	 */
	private boolean multiplex;

	/**
	 * When set to true, if the binder supports it, the messages emitted will have a {@link List}
	 * payload; When used in conjunction with functions, the function can receive a list of
	 * objects (or {@link Message}s) with the payloads converted if necessary.
	 *
	 * @since 3.0
	 */
	private boolean batchMode;

	/**
	 * This method is not intended as a configuration property to set by the applications.
	 * Therefore, we are not providing a proper setter method for this.
	 * @param bindingName binding name populated by the framework.
	 */
	public void populateBindingName(String bindingName) {
		this.bindingName = bindingName;
	}

	public String getRetryTemplateName() {
		return retryTemplateName;
	}

	public void setRetryTemplateName(String retryTemplateName) {
		this.retryTemplateName = retryTemplateName;
	}

	public int getConcurrency() {
		return this.concurrency;
	}

	public void setConcurrency(int concurrency) {
		this.concurrency = concurrency;
	}

	// ...... getters and setters for other properties

}

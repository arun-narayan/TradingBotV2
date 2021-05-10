package com.getbux.app.v2.entities.message;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.getbux.app.v2.common.Constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

	private List<String> subscribeTo;
	private List<String> unsubscribeFrom;
	
	public static Subscription subscribeTo(String...productIds) {
		return create(true, productIds);
	}
	
	public static Subscription unsubscribeFrom(String...productIds) {
		return create(false, productIds);
	}
	
	private static Subscription create(boolean subscribe, String...productIds) {
		List<String> subscriptionList = null;
		if (0 < productIds.length) {
			subscriptionList = Stream.of(productIds).parallel().map(id -> Constants.SUBSCRIPTION_MSG_PREFIX + id)
					.collect(Collectors.toList());
		} else {
			return null;
		}
		return subscribe ? new Subscription(subscriptionList, null) : new Subscription(null, subscriptionList);
	}
}

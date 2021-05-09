package com.getbux.app.v2.listeners;

import com.getbux.app.v2.entities.BotTradingResponse;

public interface OnCompletionListener {

	void onCompletion(BotTradingResponse<?> response);
	
}
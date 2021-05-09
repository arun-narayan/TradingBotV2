package com.getbux.app.v2.repositories;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.getbux.app.v2.entities.BotTradingRequest;
import com.getbux.app.v2.exceptions.BotException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class ProductRepository {

	// In-Memory cache to store incoming trading requests
    private final Map<String, BotTradingRequest> products = new ConcurrentHashMap<>(20);

    /**
     * Saves {@link BotTradingRequest} for the given product id
     * 
     * @param {@link BotTradingRequest}
     */
    public void save(BotTradingRequest tradingRequest) {
    	log.debug("Saving product {}", tradingRequest.getProductId());
        products.put(tradingRequest.getProductId(), tradingRequest);
    }

    /**
     * Finds {@link BotTradingRequest} by product id
     * 
     * @param id 
     * @return {@link BotTradingRequest}
     */
    public BotTradingRequest findById(String id) {
    	log.debug("Finding product {}", id);
        if (!products.containsKey(id)) {
            throw new BotException("Could not find product {}" + id);
        }

        return products.get(id);
    }
    
    /**
     * 
     * Finds all {@link BotTradingRequest} saved in the in-memory cache
     * 
     * @return List of {@link BotTradingRequest}
     */
    public List<BotTradingRequest> findAll() {
    	log.debug("Finding trading request for all products");
        return products.values().parallelStream().collect(Collectors.toList());
    }
    
    /**
     * Deletes product from in the in-memory cache
     * 
     * @param id
     */
    public void deleteById(String id) {
    	log.debug("Deleting product {}", id);
    	
    	if (!products.containsKey(id)) {
            log.warn("Could not delete product {}" + id);
            return;
        }

    	products.remove(id);
    }
    
    /**
     * 
     * Deletes products from in the in-memory cache for the given product ids
     * 
     * @param ids to be deleted
     */
    public void deleteAll(List<String> ids) {
    	ids.parallelStream().forEach(id -> products.remove(id));
    }
}

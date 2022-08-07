package cache;

import model.Foundation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static cache.FileStorage.updateStorage;

public class cacheStore {
    private static final Map<String, Foundation> foundationMapCache = new ConcurrentHashMap<>();

    public static Map<String, Foundation> getFoundationMapCache() {
        return foundationMapCache;
    }

    public static void setFoundationMapCache(String code, Foundation foundation) {
        foundationMapCache.put(code, foundation);
        updateStorage(foundation);
    }
}

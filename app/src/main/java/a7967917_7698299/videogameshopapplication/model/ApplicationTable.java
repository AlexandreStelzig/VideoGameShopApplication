package a7967917_7698299.videogameshopapplication.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 2017-06-26.
 */

public class ApplicationTable {
    private long appId;
    private long activeUserId;

    public ApplicationTable(long appId, long activeUserId) {
        this.appId = appId;
        this.activeUserId = activeUserId;
    }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public long getActiveUserId() {
        return activeUserId;
    }

    public void setActiveUserId(long activeUserId) {
        this.activeUserId = activeUserId;
    }
}

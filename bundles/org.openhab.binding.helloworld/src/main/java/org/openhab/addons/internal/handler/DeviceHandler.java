package org.openhab.addons.internal.handler;

import static org.openhab.binding.helloworld.MyBindingConstants.CHANNEL_COUNTER;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.binding.BaseThingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NonNullByDefault
public class DeviceHandler extends BaseThingHandler {
    private final Logger logger = LoggerFactory.getLogger(DeviceHandler.class);

    private volatile int counter = 0;
    private ScheduledFuture<?> job;

    public DeviceHandler(Thing thing) { super(thing); }

    @Override
    public void initialize() {
        updateStatus(ThingStatus.UNKNOWN);
        logger.info("HelloWorld: initialize()");
        // Aucune config : on passe ONLINE et on commence à publier
        updateStatus(ThingStatus.ONLINE);
        job = scheduler.scheduleWithFixedDelay(() -> {
            try {
                updateState(CHANNEL_COUNTER, new DecimalType(counter++));
            } catch (Exception e) {
                logger.warn("Publish failed", e);
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    @Override
    public void dispose() {
        if (job != null) {
            job.cancel(true);
            job = null;
        }
        logger.info("HelloWorld: dispose()");
    }
}

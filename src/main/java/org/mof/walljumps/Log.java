package org.mof.walljumps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.mof.walljumps.WallJumpsMod.MOD_ID;

@SuppressWarnings("unused")
public final class Log {
    private static final Logger logger = LogManager.getLogger(MOD_ID);

    public static void debug(String message) { logger.debug(message); }
    public static void info(String message) { logger.info(message); }
    public static void warn(String message) { logger.warn(message); }
    public static void error(String message) { logger.error(message); }
}

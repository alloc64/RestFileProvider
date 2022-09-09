/***********************************************************************
 * Copyright (c) 2018 Milan Jaitner                                    *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.restfileprovider.server.shared.log;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

import java.util.Date;
import java.util.logging.LogManager;

public class Log
{
    private static Logger LOGGER;

    public static Logger getLogger()
    {
        return LOGGER;
    }

    public static void initialize(String name)
    {
        initialize(String.format("logs/%s.log", name), name);
    }

    public static void initialize(String logFile, String name)
    {
        LogManager.getLogManager().reset();

        ConsoleAppender console = new ConsoleAppender();

        console.setLayout(new PatternLayout("%m%n"));
        console.setThreshold(Level.INFO);
        console.activateOptions();

        Logger.getRootLogger().addAppender(console);

        RollingFileAppender fa = new RollingFileAppender();
        fa.setMaximumFileSize(1024 * 512);
        fa.setMaxBackupIndex(10);
        fa.setName(name);
        fa.setFile(logFile);
        fa.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
        fa.setThreshold(Level.INFO);
        fa.setAppend(true);
        fa.activateOptions();

        Logger.getRootLogger().addAppender(fa);

        LOGGER = Logger.getLogger(name);

        Log.i("Started at: " + new Date());
    }

    public static void d(String msg)
    {
        if(LOGGER != null)
            LOGGER.debug(msg);
    }

    public static void e(String msg)
    {
        if(LOGGER != null)
            LOGGER.error(msg);
    }

    public static void i(String msg)
    {
        if(LOGGER != null)
            LOGGER.info(msg);
    }

    public static void ex(Throwable t)
    {
        if(LOGGER != null)
            LOGGER.error("Exception thrown:", t);
    }
}

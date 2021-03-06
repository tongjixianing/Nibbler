/*
 * Copyright (c)
 *
 * Date: 30/11/2017
 *
 * Author: Chun Gao & Mike Zhang
 *
 */

package com.datastax.test;

import com.datastax.support.Util.ValFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by Chun Gao on 30/11/17
 */

public class NodetoolStatusFileParseTest extends Test{

    private static final Logger logger = LogManager.getLogger(NodetoolStatusFileParseTest.class);

    private JSONObject nodetoolStatusJSON = new JSONObject();

    public void parseFiles() {
        nodetoolStatusJSON = fileFactory.getNodetoolStatusJSON();
    }

    public void readNodetoolStatusJSON() {
        logger.debug("Nodetool Status JSON: " + nodetoolStatusJSON);
        logger.debug(nodetoolStatusJSON.get(ValFactory.STATUS));
        JSONArray dcArray = (JSONArray) nodetoolStatusJSON.get(ValFactory.STATUS);
        logger.debug("Number of DCs: " + dcArray.size());
        for (Object ja : dcArray) {
            JSONObject tmp = (JSONObject) ja;
            logger.debug("DC: " + tmp.get(ValFactory.DATACENTER));
            JSONArray ja1 =  (JSONArray)tmp.get(ValFactory.NODES);
            for(Object ja2 :ja1)
            {
                JSONObject tmp1 = (JSONObject) ja2;
                logger.debug("node: " + tmp1.get(ValFactory.ADDRESS));
            }
        }
    }

    public static void main (String[] args) {
        NodetoolStatusFileParseTest ntfpt = new NodetoolStatusFileParseTest();
        ntfpt.initiate();
        ntfpt.parseFiles();
        ntfpt.readNodetoolStatusJSON();
    }
}

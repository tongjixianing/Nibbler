/*
 * Copyright (c)
 *
 * Date: 18/12/2017
 *
 * Author: Chun Gao & Mike Zhang
 *
 */

package com.datastax.support.Parser;

import com.datastax.support.Util.Inspector;
import com.datastax.support.Util.ValFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Chun Gao on 18/12/2017
 */

public class TpstatsFileParser extends FileParser {

    private static final Logger logger = LogManager.getLogger(TpstatsFileParser.class);

    private ArrayList<JSONObject> tpstatsJSONList;
    private JSONObject tpstatsJSON;
    private JSONArray tpstatsArray;
    private JSONObject valueJSON;

    public TpstatsFileParser (ArrayList<File> files) {
        super(files);
        parse();
    }

    private void parse() {
        tpstatsJSONList = new ArrayList<JSONObject>();

        /**
         {
         "file_id":"13.57.154.111","file_name":"tpstats","file_path":"./nodes/13.57.154.111/nodetool/tpstats",
         "tpstats":
            [
                {"Active":"0","Pool Name":"MutationStage","All time blocked":"0","Completed":"29363","Blocked":"0","Pending":"0"},
                {"Active":"0","Pool Name":"ReadStage","All time blocked":"0","Completed":"1373553","Blocked":"0","Pending":"0"},
                ...
                {"Dropped":"0","Message type":"READ"},
                {"Dropped":"0","Message type":"RANGE_SLICE"},
                ...
            ]
         }
         **/

        for (File file : files) {
            if (file.getAbsolutePath().contains(ValFactory.TPSTATS)) {
                tpstatsJSON = new JSONObject();
                tpstatsArray = new JSONArray();

                tpstatsJSON.put(ValFactory.FILE_PATH, file.getAbsolutePath());
                tpstatsJSON.put(ValFactory.FILE_NAME, file.getName());
                tpstatsJSON.put(ValFactory.FILE_ID, Inspector.getFileID(file));

                try {
                    Scanner scanner = new Scanner(file);
                    while (scanner.hasNextLine()) {
                        ArrayList<String> values = new ArrayList<String>(Arrays.asList(Inspector.splitBySpace(scanner.nextLine())));

                        if (values.size() == 6) {
                            valueJSON = new JSONObject();
                            for (int i=0; i<values.size(); i++) {
                                valueJSON.put(ValFactory.TPSTATS_POOL.get(i), values.get(i));
                            }
                            tpstatsArray.add(valueJSON);
                        } else if (values.size() == 2) {
                            valueJSON = new JSONObject();
                            for(int i=0; i<values.size(); i++) {
                                valueJSON.put(ValFactory.TPSTATS_MSG.get(i), values.get(i));
                            }
                            tpstatsArray.add(valueJSON);
                        }
                    }
                    tpstatsJSON.put(ValFactory.TPSTATS, tpstatsArray);
                    tpstatsJSONList.add(tpstatsJSON);
                } catch (FileNotFoundException fnfe) {
                    logCheckedException(logger, fnfe);
                }
            }
        }
    }

    public ArrayList<JSONObject> getTpstatsJSONList() {
        return tpstatsJSONList;
    }
}

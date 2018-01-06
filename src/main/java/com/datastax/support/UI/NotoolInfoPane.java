/*
 * Copyright (c)
 *
 * Date: 24/12/2017
 *
 * Author: Chun Gao & Mike Zhang
 *
 */

package com.datastax.support.UI;

import com.datastax.support.Analyzer.NodetoolInfoAnalyzer;
import com.datastax.support.Util.FileFactory;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.stage.Screen;

/**
 * Created by Mike Zhang on 24/12/2017.
 */

public class NotoolInfoPane {

    private String nodetool_info_report;

    TitledPane createinfoPane(FileFactory ff)
    {
        TitledPane tp = new TitledPane();

        tp.setExpanded(false);
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        double screen_height =  visualBounds.getHeight() ;
        double screen_width = visualBounds.getWidth();
        //tp.setPrefWidth(screen_width*0.5);
        tp.setMinWidth(screen_width*0.7);
        //tp.setPrefWidth(1024);
        //tp.setMinHeight(768);
        tp.setStyle( "-fx-font-family: Courier New");
        //tp.setPrefSize(1024, 10);
        tp.setText("nodetool info(group by DC)");
        TextArea info_area = new NodetoolInfoAnalyzer().generateNodeStatusOutput(ff);
        tp.setContent(info_area);
        nodetool_info_report = info_area.getText();
        return tp;

    }

    public String getNodetool_info_report() {
        return nodetool_info_report;
    }
}

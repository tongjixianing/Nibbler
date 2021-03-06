/*
 * Copyright (c)
 *
 * Date: 2/12/2017
 *
 * Author: Chun Gao & Mike Zhang
 *
 */

package com.datastax.support.UI;

import com.datastax.support.Analyzer.ClusterInfoAnalyzer;
import com.datastax.support.Analyzer.ConfAnalyzer;
import com.datastax.support.Util.FileFactory;
import com.datastax.support.Util.Inspector;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.stage.Screen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Mike Zhang on 2/12/2017.
 */

public class ClusterinfoPane extends ComponentTitledPane{

    private String cluster_info_report;
    private static final Logger logger = LogManager.getLogger(ClusterinfoPane.class);
    private static final String title = "Cluster Configuration Summary";
    private ClusterInfoAnalyzer clusterInfoAnalyzer;
    private String output = "";

    public ClusterinfoPane(FileFactory fileFactory) {
        super(title);
        try{
            clusterInfoAnalyzer = new ClusterInfoAnalyzer(fileFactory);
            output = clusterInfoAnalyzer.generateClusterInfoOutput();

        } catch (Exception e) {
            output = "Encoutntered Unchecked Exception";
            Inspector.logException(logger, e);

        }

        titledPane.setContent(generateTextArea(output));
    }

    public TitledPane getClusterInfoPane() {
        return titledPane;
    }

    /*TitledPane createClusterInfoPane(FileFactory ff)
    {
        TitledPane tp = new TitledPane();

        tp.setExpanded(false);
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        double screen_height =  visualBounds.getHeight() ;
        double screen_width = visualBounds.getWidth();
        //tp.setPrefWidth(screen_width*0.5);
        tp.setMinWidth(screen_width*0.7);
        //tp.setMinHeight(screen_height*0.4);
        tp.setStyle( "-fx-font-family:monospace; -fx-font-size:11pt");
        //tp.setPrefSize(1024, 10);
        tp.setText("Cluster Configuration Summary");
        try {
        TextArea cluster_info_area = new ClusterInfoAnalyzer(ff).generateClusterInfoOutput();
       // cluster_info_area.setStyle("-fx-font-family: Courier New");
        tp.setContent(cluster_info_area);
        cluster_info_report = cluster_info_area.getText();
        }catch(Exception e)
        {
            TextArea cluster_info_area = new TextArea();
           // cluster_info_area.setStyle("-fx-font-family: Courier New");
            cluster_info_area.setText("Exception happened when retrieving cluster configuration information!!!");
            tp.setContent(cluster_info_area);
            cluster_info_report = cluster_info_area.getText();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            logger.error(sw.toString());
            return tp;
        }
        return tp;

    }*/

    public String getCluster_info_report() {
        return cluster_info_report;
    }
    public String save_cluster_info_report()
    {
        return Inspector.saveReportFile(output,"cluster_info.out");
    }
}

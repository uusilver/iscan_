package com.tmind.iscan.model;

/**
 * Created by lijunying on 15/11/28.
 */
public class QrCodeExportModel {
    private String visitUrl;

    public QrCodeExportModel(String visitUrl) {
        this.visitUrl = visitUrl;
    }

    public String getVisitUrl() {
        return visitUrl;
    }

    public void setVisitUrl(String visitUrl) {
        this.visitUrl = visitUrl;
    }
}

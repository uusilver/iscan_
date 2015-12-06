package com.tmind.iscan.model;

/**
 * Created by lijunying on 15/11/28.
 */
public class QrCodeExportModel {
    private String visitUrl;
    private String unqueKey;

    public QrCodeExportModel(String visitUrl, String unqueKey) {
        this.visitUrl = visitUrl;
        this.unqueKey = unqueKey;
    }

    public String getVisitUrl() {
        return visitUrl;
    }

    public void setVisitUrl(String visitUrl) {
        this.visitUrl = visitUrl;
    }

    public String getUnqueKey() {
        return unqueKey;
    }

    public void setUnqueKey(String unqueKey) {
        this.unqueKey = unqueKey;
    }
}

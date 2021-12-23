/**
  * Copyright 2021 jb51.net 
  */
package com.cheeli.models.taobao.waybill;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Routinginfo {

    @JsonProperty("blockCode")
    private String blockcode;
    private Consolidation consolidation;
    private Origin origin;
    @JsonProperty("receiveBranch")
    private Receivebranch receivebranch;
    @JsonProperty("routeCode")
    private String routecode;
    private Sortation sortation;
    @JsonProperty("startCenter")
    private Startcenter startcenter;
    @JsonProperty("terminalCenter")
    private Terminalcenter terminalcenter;
    public void setBlockcode(String blockcode) {
         this.blockcode = blockcode;
     }
     public String getBlockcode() {
         return blockcode;
     }

    public void setConsolidation(Consolidation consolidation) {
         this.consolidation = consolidation;
     }
     public Consolidation getConsolidation() {
         return consolidation;
     }

    public void setOrigin(Origin origin) {
         this.origin = origin;
     }
     public Origin getOrigin() {
         return origin;
     }

    public void setReceivebranch(Receivebranch receivebranch) {
         this.receivebranch = receivebranch;
     }
     public Receivebranch getReceivebranch() {
         return receivebranch;
     }

    public void setRoutecode(String routecode) {
         this.routecode = routecode;
     }
     public String getRoutecode() {
         return routecode;
     }

    public void setSortation(Sortation sortation) {
         this.sortation = sortation;
     }
     public Sortation getSortation() {
         return sortation;
     }

    public void setStartcenter(Startcenter startcenter) {
         this.startcenter = startcenter;
     }
     public Startcenter getStartcenter() {
         return startcenter;
     }

    public void setTerminalcenter(Terminalcenter terminalcenter) {
         this.terminalcenter = terminalcenter;
     }
     public Terminalcenter getTerminalcenter() {
         return terminalcenter;
     }

}
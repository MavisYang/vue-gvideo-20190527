/** 
 * Project Name:interactiveLive 
 * File Name:WatermarkConfig.java 
 * Package Name:com.live.pojo.vod 
 * Date:2018年12月21日上午11:01:39 
 * Copyright (c) 2018, chenzhou1025@126.com All Rights Reserved. 
 * 
*/  
  
package com.alivc.vod.pojo;  
/** 
 * ClassName:WatermarkConfig <br/> 
 * Function: TODO 水印参数实体类. <br/> 
 * Reason:   TODO 用于配置添加水印的参数. <br/> 
 * Date:     2018年12月21日 上午11:01:39 <br/> 
 * @author   tz 
 * @version   
 * @since    JDK 1.8 
 * @see       
 */
public class WatermarkConfig {
    
    /**
     * 水平偏移
     */          
    private String  dx = "";
    /**
     * 垂直偏移
     */
    private String dy = "";
    /**
     * 水印宽
     */
    private String width = "";
    /**
     * 水印高
     */
    private String height = "";
    /**
     * ReferPos
     */
    private String referPos = "";
    /**
     * 水印时间线
     */
    private TimeLine timeline = null;
    
    
    /**
     * 文字水印内容
     */          
    private String  content = "";
    /**
     * 字体颜色
     */
    private String fontName = "";
    /**
     * 水印宽
     */
    private String fontColor = "";
    /**
     * 字体透明度
     */
    private String fontAlpha = "";
    /**
     * 描边颜色
     */
    private String borderColor = "";
    /**
     * 文本上边距
     */
    private String top = "";
    /**
     * 文本左边距
     */
    private String left = "";
    /**
     * 字体大小
     */
    private String fontSize = "";
    /**
     * 描边宽度
     */
    private String borderWidth = "";
    
    
    public String getDx() {
        return dx;
    }
    public void setDx(String dx) {
        this.dx = dx;
    }
    public String getDy() {
        return dy;
    }
    public void setDy(String dy) {
        this.dy = dy;
    }
    public String getWidth() {
        return width;
    }
    public void setWidth(String width) {
        this.width = width;
    }
    public String getHeight() {
        return height;
    }
    public void setHeight(String height) {
        this.height = height;
    }
    public String getReferPos() {
        return referPos;
    }
    public void setReferPos(String referPos) {
        this.referPos = referPos;
    }
    public TimeLine getTimeline() {
        return timeline;
    }
    public void setTimeline(TimeLine timeline) {
        this.timeline = timeline;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getFontName() {
        return fontName;
    }
    public void setFontName(String fontName) {
        this.fontName = fontName;
    }
    public String getFontColor() {
        return fontColor;
    }
    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }
    public String getFontAlpha() {
        return fontAlpha;
    }
    public void setFontAlpha(String fontAlpha) {
        this.fontAlpha = fontAlpha;
    }
    public String getBorderColor() {
        return borderColor;
    }
    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }
    public String getTop() {
        return top;
    }
    public void setTop(String top) {
        this.top = top;
    }
    public String getLeft() {
        return left;
    }
    public void setLeft(String left) {
        this.left = left;
    }
    public String getFontSize() {
        return fontSize;
    }
    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }
    public String getBorderWidth() {
        return borderWidth;
    }
    public void setBorderWidth(String borderWidth) {
        this.borderWidth = borderWidth;
    }
    
    

}
 
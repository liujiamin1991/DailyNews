package com.konka.dailynews.model;

/**
 * Created by ljm on 2017-6-2.
 */
public class TopDailyBean
{
    private String imgUrl;
    private String text;

    public TopDailyBean(String imgUrl, String text)
    {
        this.imgUrl = imgUrl;
        this.text = text;
    }

    public String getImgUrl()
    {
        return imgUrl;
    }

    public String getText()
    {
        return text;
    }

    public void setImgUrl(String imgUrl)
    {
        this.imgUrl = imgUrl;
    }

    public void setText(String text)
    {
        this.text = text;
    }
}

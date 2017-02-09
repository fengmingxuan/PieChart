/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-2-9上午10:02:43
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.example.peichart;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-2-9上午10:02:43
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public   class PieItemBean {
    private String itemType;
    private float itemValue;
    private int itemColor;
    private String itemLineType;
    private int itemTextColor;
    
    public PieItemBean(String itemType, float itemValue,int itemColor,String itemLineType,int itemTextColor) {
        this.itemType = itemType;
        this.itemValue = itemValue;
        this.itemColor = itemColor;
        this.itemLineType = itemLineType;
        this.itemTextColor = itemTextColor;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public float getItemValue() {
        return itemValue;
    }

    public void setItemValue(float itemValue) {
        this.itemValue = itemValue;
    }

	public int getItemColor() {
		return itemColor;
	}

	public void setItemColor(int itemColor) {
		this.itemColor = itemColor;
	}

	public String getItemLineType() {
		return itemLineType;
	}

	public void setItemLineType(String itemLineType) {
		this.itemLineType = itemLineType;
	}

	public int getItemTextColor() {
		return itemTextColor;
	}

	public void setItemTextColor(int itemTextColor) {
		this.itemTextColor = itemTextColor;
	}
    
    
}
